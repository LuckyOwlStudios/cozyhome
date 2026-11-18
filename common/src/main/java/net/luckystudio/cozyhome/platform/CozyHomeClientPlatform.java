package net.luckystudio.cozyhome.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

/**
 * Client-only loader bridge. Implemented in {@code platform.fabric.CozyHomeClientPlatformImpl}
 * and {@code platform.neoforge.CozyHomeClientPlatformImpl}. Only call this from client code.
 */
public final class CozyHomeClientPlatform {
    private CozyHomeClientPlatform() {}

    /** The still texture of any fluid, including modded ones. */
    @ExpectPlatform
    public static Sprite getStillFluidSprite(FluidState fluidState, BlockRenderView world, BlockPos pos) {
        throw new AssertionError();
    }

    /** The fluid's tint as ARGB (water uses the biome colour). */
    @ExpectPlatform
    public static int getFluidColor(FluidState fluidState, BlockRenderView world, BlockPos pos) {
        throw new AssertionError();
    }
}
