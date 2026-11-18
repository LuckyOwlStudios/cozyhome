package net.luckystudio.cozyhome.block.util;

import net.luckystudio.cozyhome.block.custom.water_holding_blocks.AbstractWaterHoldingBlockEntity;
import net.luckystudio.cozyhome.block.util.enums.ContainsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * Helpers that let sinks, bathtubs and fountains hold any fluid, including modded ones.
 * <p>
 * Water and lava keep using the {@link ContainsBlock#WATER} / {@link ContainsBlock#LAVA} block states
 * (so their models, biome tint and light level work as before). Every other fluid is stored as
 * {@link ContainsBlock#CUSTOM} with the actual fluid kept in the block entity and drawn by a renderer.
 */
public final class LiquidHelper {
    private LiquidHelper() {}

    /** The still version of a fluid, or {@link Fluids#EMPTY}. */
    public static Fluid still(Fluid fluid) {
        if (fluid instanceof FlowableFluid flowable) return flowable.getStill();
        return fluid == null ? Fluids.EMPTY : fluid;
    }

    /** The fluid inside a filled bucket (vanilla or modded), or {@link Fluids#EMPTY}. */
    public static Fluid fluidInBucket(ItemStack stack) {
        if (stack.getItem() instanceof BucketItem bucket) return still(bucket.fluid);
        return Fluids.EMPTY;
    }

    public static ContainsBlock containsFor(Fluid fluid) {
        fluid = still(fluid);
        if (fluid == Fluids.EMPTY) return ContainsBlock.NONE;
        if (fluid == Fluids.WATER) return ContainsBlock.WATER;
        if (fluid == Fluids.LAVA) return ContainsBlock.LAVA;
        return ContainsBlock.CUSTOM;
    }

    /** The fluid held by a liquid holding block. */
    public static Fluid getFluid(BlockState state, BlockView world, BlockPos pos) {
        if (!state.contains(ModProperties.CONTAINS)) return Fluids.EMPTY;
        return switch (state.get(ModProperties.CONTAINS)) {
            case WATER -> Fluids.WATER;
            case LAVA -> Fluids.LAVA;
            case CUSTOM -> world.getBlockEntity(pos) instanceof AbstractWaterHoldingBlockEntity be ? be.getStoredFluid() : Fluids.EMPTY;
            default -> Fluids.EMPTY;
        };
    }

    /**
     * Stores the fluid in the block entity (if there is one) and returns the state with the matching
     * {@code contains} value. Call this before {@code world.setBlockState} so the client gets both.
     */
    public static BlockState withFluid(World world, BlockPos pos, BlockState state, Fluid fluid) {
        fluid = still(fluid);
        if (world.getBlockEntity(pos) instanceof AbstractWaterHoldingBlockEntity be) {
            be.setStoredFluid(containsFor(fluid) == ContainsBlock.CUSTOM ? fluid : Fluids.EMPTY);
        }
        return state.with(ModProperties.CONTAINS, containsFor(fluid));
    }

    /** True when a block already holding {@code current} can take more of {@code incoming}. */
    public static boolean canMix(Fluid current, Fluid incoming) {
        current = still(current);
        incoming = still(incoming);
        return current == Fluids.EMPTY || current == incoming;
    }

    /** A filled bucket for the fluid, or an empty stack when the fluid has no bucket. */
    public static ItemStack filledBucket(Fluid fluid) {
        Item bucket = still(fluid).getBucketItem();
        return bucket == Items.AIR ? ItemStack.EMPTY : new ItemStack(bucket);
    }

    public static SoundEvent fillSound(Fluid fluid) {
        if (still(fluid) == Fluids.LAVA) return SoundEvents.ITEM_BUCKET_FILL_LAVA;
        return still(fluid).getBucketFillSound().orElse(SoundEvents.ITEM_BUCKET_FILL);
    }

    public static SoundEvent emptySound(Fluid fluid) {
        return still(fluid) == Fluids.LAVA ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
    }

    /**
     * The fluid that can be pulled from a block in the world: fluid sources (any fluid),
     * waterlogged blocks and filled cauldrons. Returns {@link Fluids#EMPTY} if there is none.
     */
    public static Fluid pullableFluid(BlockState state) {
        if (state.isOf(Blocks.WATER_CAULDRON)) return Fluids.WATER;
        if (state.isOf(Blocks.LAVA_CAULDRON)) return Fluids.LAVA;
        if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED)) return Fluids.WATER;
        FluidState fluidState = state.getFluidState();
        if (!fluidState.isEmpty() && fluidState.isStill()) return still(fluidState.getFluid());
        return Fluids.EMPTY;
    }

    /**
     * Takes the pulled fluid away from the world where needed. Water sources stay (they are infinite),
     * every other fluid source is used up. Cauldrons are handled by the blocks themselves.
     */
    public static void drainSource(World world, BlockPos pos, BlockState state, Fluid fluid) {
        if (state.isOf(Blocks.WATER_CAULDRON) || state.isOf(Blocks.LAVA_CAULDRON)) return;
        if (still(fluid) == Fluids.WATER) return;
        if (state.getBlock() instanceof net.minecraft.block.FluidBlock) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    /** Light level for liquid holding blocks, used for custom fluids rendered by the block entity renderer. */
    public static int luminance(Fluid fluid) {
        return still(fluid).getDefaultState().getBlockState().getLuminance();
    }

    public static boolean hasLiquid(BlockState state) {
        return state.contains(ModProperties.CONTAINS) && state.get(ModProperties.CONTAINS) != ContainsBlock.NONE;
    }

}
