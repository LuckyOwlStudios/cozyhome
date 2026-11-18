package net.luckystudio.cozyhome.platform.neoforge;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.FluidState;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public final class CozyHomeClientPlatformImpl {
    private CozyHomeClientPlatformImpl() {}

    public static Sprite getStillFluidSprite(FluidState fluidState, BlockRenderView world, BlockPos pos) {
        Identifier texture = IClientFluidTypeExtensions.of(fluidState).getStillTexture(fluidState, world, pos);
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(texture);
    }

    public static int getFluidColor(FluidState fluidState, BlockRenderView world, BlockPos pos) {
        return IClientFluidTypeExtensions.of(fluidState).getTintColor(fluidState, world, pos);
    }
}
