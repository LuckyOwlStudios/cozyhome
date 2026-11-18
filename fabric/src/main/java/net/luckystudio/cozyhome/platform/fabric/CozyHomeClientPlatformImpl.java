package net.luckystudio.cozyhome.platform.fabric;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.FluidState;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public final class CozyHomeClientPlatformImpl {
    private CozyHomeClientPlatformImpl() {}

    public static Sprite getStillFluidSprite(FluidState fluidState, BlockRenderView world, BlockPos pos) {
        FluidRenderHandler handler = FluidRenderHandlerRegistry.INSTANCE.get(fluidState.getFluid());
        if (handler != null) {
            Sprite[] sprites = handler.getFluidSprites(world, pos, fluidState);
            if (sprites != null && sprites.length > 0 && sprites[0] != null) return sprites[0];
        }
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(MissingSprite.getMissingSpriteId());
    }

    public static int getFluidColor(FluidState fluidState, BlockRenderView world, BlockPos pos) {
        FluidRenderHandler handler = FluidRenderHandlerRegistry.INSTANCE.get(fluidState.getFluid());
        return handler == null ? -1 : handler.getFluidColor(world, pos, fluidState);
    }
}
