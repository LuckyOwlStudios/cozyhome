package net.luckystudio.cozyhome.block.custom.counters;

import net.luckystudio.cozyhome.CozyHome;
import net.luckystudio.cozyhome.client.ModEntityModelLayers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

import java.util.HashMap;
import java.util.Map;

/**
 * Draws the storage counter's doors and swings them open while someone is using it.
 * The block model itself only has the open cabinet front.
 */
public class StorageCounterBlockEntityRenderer implements BlockEntityRenderer<StorageCounterBlockEntity> {
    private static final Map<Block, Identifier> TEXTURES = new HashMap<>();
    private final StorageCounterDoorsModel doors;

    public StorageCounterBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.doors = new StorageCounterDoorsModel(ctx.getLayerModelPart(ModEntityModelLayers.STORAGE_COUNTER_DOORS));
    }

    @Override
    public void render(StorageCounterBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState state = entity.getCachedState();
        if (!(state.getBlock() instanceof StorageCounterBlock)) return;
        Direction facing = state.get(StorageCounterBlock.FACING);

        matrices.push();
        matrices.translate(0.5F, 1.5F, 0.5F);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(facing.asRotation()));

        // The counter is a solid block, so take the light from the space in front of the doors.
        int doorLight = light;
        if (entity.getWorld() != null) {
            doorLight = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().offset(facing.getOpposite()));
        }

        this.doors.setOpenProgress(entity.getOpenProgress(tickDelta));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(getTexture(state.getBlock())));
        this.doors.render(matrices, vertexConsumer, doorLight, overlay);
        matrices.pop();
    }

    private static Identifier getTexture(Block block) {
        return TEXTURES.computeIfAbsent(block, b -> {
            String wood = Registries.BLOCK.getId(b).getPath().replace("_storage_counter", "");
            return CozyHome.id("textures/block/counter/" + wood + "_doors.png");
        });
    }
}
