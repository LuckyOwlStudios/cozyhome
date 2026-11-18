package net.luckystudio.cozyhome.block.custom.fountains;

import com.mojang.serialization.MapCodec;
import net.luckystudio.cozyhome.block.custom.horizontal_connecting_blocks.AbstractHorizontalConnectingBlock;
import net.luckystudio.cozyhome.block.util.ModProperties;
import net.luckystudio.cozyhome.block.util.enums.ContainsBlock;
import net.luckystudio.cozyhome.block.util.interfaces.AllSidesConnectingBlock;
import net.luckystudio.cozyhome.util.ModScreenTexts;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.luckystudio.cozyhome.block.util.LiquidHelper;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class FountainBlock extends AbstractHorizontalConnectingBlock implements AllSidesConnectingBlock, BlockEntityProvider {
    public static final MapCodec<FountainBlock> CODEC = createCodec(FountainBlock::new);
    public static final EnumProperty<ContainsBlock> CONTAINS = ModProperties.CONTAINS;

    public static final VoxelShape TOP_PIECE = Block.createCuboidShape(0, 10, 0, 16, 16, 16);
    public static final VoxelShape TOP_PIECE_VOID = Block.createCuboidShape(2, 14, 2, 14, 16, 14);
    public static final VoxelShape TOP = VoxelShapes.combine(TOP_PIECE, TOP_PIECE_VOID, BooleanBiFunction.ONLY_FIRST);
    public static final VoxelShape MIDDLE = Block.createCuboidShape(4, 2, 4, 12, 10, 12);
    public static final VoxelShape BASE = Block.createCuboidShape(2, 0, 2, 14, 2, 14);
    public static final VoxelShape SHAPE = VoxelShapes.union(TOP, MIDDLE, BASE);

    public FountainBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(CONTAINS, ContainsBlock.NONE));
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(CONTAINS));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FountainBlockEntity(pos, state);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ContainsBlock contents = state.get(CONTAINS);

        // Pour in any bucket of liquid, vanilla or modded
        Fluid bucketFluid = LiquidHelper.fluidInBucket(stack);
        if (bucketFluid != Fluids.EMPTY) {
            if (LiquidHelper.getFluid(state, world, pos) == bucketFluid) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BUCKET)));
            return changeState(LiquidHelper.withFluid(world, pos, state, bucketFluid), LiquidHelper.emptySound(bucketFluid), world, pos, player);
        }

        // Scoop the liquid back out with an empty bucket
        if (contents != ContainsBlock.NONE && stack.getItem() == Items.BUCKET) {
            Fluid fluid = LiquidHelper.getFluid(state, world, pos);
            ItemStack filledBucket = LiquidHelper.filledBucket(fluid);
            if (filledBucket.isEmpty()) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, filledBucket));
            return changeState(LiquidHelper.withFluid(world, pos, state, Fluids.EMPTY), LiquidHelper.fillSound(fluid), world, pos, player);
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private static ItemActionResult changeState(BlockState newState, SoundEvent soundEvent, World world, BlockPos pos, PlayerEntity player) {
        world.setBlockState(pos, newState, Block.NOTIFY_ALL);
        world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1F, 1f);
        world.emitGameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
        return ItemActionResult.SUCCESS;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // This spawns particles when the block contains lava using the LavaFluid Classes randomDisplayTick method
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(CONTAINS) == ContainsBlock.LAVA) {
            LavaFluid lavaFluid = (LavaFluid) Fluids.LAVA;
            lavaFluid.randomDisplayTick(world, pos, state.getFluidState(), random);
        }
    }

    // Handles entity effects based on the block’s fill state (LAVA, POWDER_SNOW, WATER)
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        ContainsBlock fillState = state.get(CONTAINS);

        // If block contains lava, apply burn damage and set the entity on fire
        if (fillState == ContainsBlock.LAVA && entity instanceof LivingEntity) {
            entity.damage(world.getDamageSources().hotFloor(), 4.0F);
            entity.setOnFireFor(3);
        }

        // Call the superclass method after handling custom effects
        super.onSteppedOn(world, pos, state, entity);
    }

    // So mobs don't stand on it
    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack, context, tooltip, options);
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("tooltip.cozyhome.block.can_hold").formatted(Formatting.GRAY));
        tooltip.add(ModScreenTexts.entry().append(Text.translatable("block.minecraft.water")));
        tooltip.add(ModScreenTexts.entry().append(Text.translatable("block.minecraft.lava")));
        tooltip.add(ModScreenTexts.entry().append(Text.translatable("tooltip.cozyhome.any_other_liquid")));
    }

    @Override
    public void onBroken(WorldAccess worldAccess, BlockPos pos, BlockState state) {
        onBlockDestroyed((World)worldAccess, state, pos);
        super.onBroken(worldAccess, pos, state);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        onBlockDestroyed(world, state, pos);
        return super.onBreak(world, pos, state, player);
    }

    // Spill out liquids when the block is destroyed
    private static void onBlockDestroyed(World world, BlockState state, BlockPos pos) {
        Fluid fluid = LiquidHelper.getFluid(state, world, pos);
        if (fluid instanceof FlowableFluid flowable) {
            world.setBlockState(pos, flowable.getFlowing(4, false).getBlockState());
        }
    }

    @Override
    public boolean isMatchingBlock(BlockState state, BlockState targetState) {
        return targetState.getBlock() instanceof FountainBlock &&
                targetState.contains(CONTAINS) && state.contains(CONTAINS) &&
                targetState.get(CONTAINS).equals(state.get(CONTAINS));
    }
}