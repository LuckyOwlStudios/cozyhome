package net.luckystudio.cozyhome.client.renderer;

import net.luckystudio.cozyhome.block.custom.fountains.FountainBlock;
import net.luckystudio.cozyhome.block.custom.water_holding_blocks.AbstractWaterHoldingBlockEntity;
import net.luckystudio.cozyhome.block.custom.water_holding_blocks.bathtub.BathTubBlock;
import net.luckystudio.cozyhome.block.custom.water_holding_blocks.sink.AbstractSinkBlock;
import net.luckystudio.cozyhome.block.util.LiquidHelper;
import net.luckystudio.cozyhome.block.util.ModProperties;
import net.luckystudio.cozyhome.block.util.enums.ContainsBlock;
import net.luckystudio.cozyhome.block.util.enums.DoubleLongPart;
import net.luckystudio.cozyhome.platform.CozyHomeClientPlatform;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.joml.Matrix4f;

/**
 * Draws the surface of liquids other than water and lava (contains=custom) in sinks, sink counters,
 * bathtubs and fountains, using the fluid's own texture and colour. Water and lava are still drawn by
 * the block models, so they look exactly as before.
 */
public class HeldLiquidRenderer implements BlockEntityRenderer<AbstractWaterHoldingBlockEntity> {

    public HeldLiquidRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(AbstractWaterHoldingBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState state = entity.getCachedState();
        if (!state.contains(ModProperties.CONTAINS) || state.get(ModProperties.CONTAINS) != ContainsBlock.CUSTOM) return;
        Fluid fluid = entity.getStoredFluid();
        World world = entity.getWorld();
        if (fluid == Fluids.EMPTY || world == null) return;

        // Surface rectangle in pixels (model space, before rotating to the block's facing)
        float minX, minZ, maxX, maxZ, height;
        float rotation = 0;
        if (state.getBlock() instanceof AbstractSinkBlock) {
            int level = state.get(AbstractSinkBlock.LEVEL);
            if (level <= 0) return;
            minX = 3; minZ = 3; maxX = 13; maxZ = 13;
            height = 9 + 2 * level;
        } else if (state.getBlock() instanceof BathTubBlock) {
            // The front half draws the surface for the whole tub
            if (state.get(BathTubBlock.PART) != DoubleLongPart.FRONT) return;
            int level = state.get(BathTubBlock.LEVEL);
            if (level <= 0) return;
            minX = 2; minZ = 2; maxX = 14; maxZ = 30;
            height = level == 1 ? 7 : 11;
            rotation = switch (state.get(BathTubBlock.FACING)) {
                case NORTH -> 180;
                case EAST -> 270;
                case WEST -> 90;
                default -> 0;
            };
        } else if (state.getBlock() instanceof FountainBlock) {
            minX = 0; minZ = 0; maxX = 16; maxZ = 16;
            height = 15;
        } else {
            return;
        }

        BlockPos pos = entity.getPos();
        FluidState fluidState = fluid.getDefaultState();
        Sprite sprite = CozyHomeClientPlatform.getStillFluidSprite(fluidState, world, pos);
        int color = CozyHomeClientPlatform.getFluidColor(fluidState, world, pos);
        if ((color >>> 24) == 0) color |= 0xFF000000; // some fluids only give RGB

        int surfaceLight = LiquidHelper.luminance(fluid) > 0
                ? LightmapTextureManager.MAX_LIGHT_COORDINATE
                : brightest(WorldRenderer.getLightmapCoordinates(world, pos), WorldRenderer.getLightmapCoordinates(world, pos.up()));

        matrices.push();
        matrices.translate(0.5, 0, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation));
        matrices.translate(-0.5, 0, -0.5);

        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentCull(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix = entry.getPositionMatrix();
        float y = height / 16f;
        // Tile the texture once per block so long surfaces (bathtubs) aren't stretched
        for (float z0 = minZ; z0 < maxZ; z0 += 16) {
            float z1 = Math.min(maxZ, z0 + 16);
            quad(consumer, entry, matrix, sprite, color, surfaceLight, minX, z0, maxX, z1, y);
        }
        matrices.pop();
    }

    private static void quad(VertexConsumer consumer, MatrixStack.Entry entry, Matrix4f matrix, Sprite sprite, int color, int light,
                             float minX, float minZ, float maxX, float maxZ, float y) {
        float u0 = u(sprite, minX), u1 = u(sprite, maxX);
        float v0 = v(sprite, minZ), v1 = v(sprite, maxZ);
        float x0 = minX / 16f, x1 = maxX / 16f, z0 = minZ / 16f, z1 = maxZ / 16f;
        vertex(consumer, entry, matrix, x0, y, z0, u0, v0, color, light);
        vertex(consumer, entry, matrix, x0, y, z1, u0, v1, color, light);
        vertex(consumer, entry, matrix, x1, y, z1, u1, v1, color, light);
        vertex(consumer, entry, matrix, x1, y, z0, u1, v0, color, light);
    }

    private static void vertex(VertexConsumer consumer, MatrixStack.Entry entry, Matrix4f matrix, float x, float y, float z, float u, float v, int color, int light) {
        consumer.vertex(matrix, x, y, z)
                .color(color)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(entry, 0, 1, 0);
    }

    // Pixel coordinate (wrapped to one block) -> sprite UV
    private static float u(Sprite sprite, float pixel) {
        float local = pixel % 16 == 0 && pixel > 0 ? 16 : pixel % 16;
        return sprite.getMinU() + (sprite.getMaxU() - sprite.getMinU()) * (local / 16f);
    }

    private static float v(Sprite sprite, float pixel) {
        float local = pixel % 16 == 0 && pixel > 0 ? 16 : pixel % 16;
        return sprite.getMinV() + (sprite.getMaxV() - sprite.getMinV()) * (local / 16f);
    }

    private static int brightest(int a, int b) {
        int block = Math.max(LightmapTextureManager.getBlockLightCoordinates(a), LightmapTextureManager.getBlockLightCoordinates(b));
        int sky = Math.max(LightmapTextureManager.getSkyLightCoordinates(a), LightmapTextureManager.getSkyLightCoordinates(b));
        return LightmapTextureManager.pack(block, sky);
    }

    @Override
    public boolean rendersOutsideBoundingBox(AbstractWaterHoldingBlockEntity blockEntity) {
        // Bathtub surfaces reach into the second block
        return blockEntity.getCachedState().getBlock() instanceof BathTubBlock;
    }
}
