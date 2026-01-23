package net.luckyowlstudios.cozyhome.block.types;

import net.luckyowlstudios.cozyhome.block.templates.HorizontalCornerConnectingBlock;
import net.luckyowlstudios.cozyhome.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RailingBlock extends HorizontalCornerConnectingBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape NORTH_SIDE = Block.box(0, 0, 14, 16, 16, 16);
    public static final VoxelShape EAST_SIDE = Block.box(0, 0, 0, 2, 16, 16);
    public static final VoxelShape SOUTH_SIDE = Block.box(0, 0, 0, 16, 16, 2);
    public static final VoxelShape WEST_SIDE = Block.box(14, 0, 0, 16, 16, 16);

    public static final VoxelShape NORTHWEST_SIDE = Block.box(0, 0, 0, 2, 16, 2);
    public static final VoxelShape NORTHEAST_SIDE = Block.box(14, 0, 0, 16, 16, 2);
    public static final VoxelShape SOUTHWEST_SIDE = Block.box(14, 0, 14, 16, 16, 16);
    public static final VoxelShape SOUTHEAST_SIDE = Block.box(0, 0, 14, 2, 16, 16);

    public RailingBlock(Properties properties) {
        super(properties.noOcclusion().isViewBlocking(ModBlocks::never));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SHAPE, StairsShape.STRAIGHT)
                .setValue(BOTTOM, false)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BOTTOM, WATERLOGGED);
    }

    @Override
    protected VoxelShape getVisualShape(BlockState p_309057_, BlockGetter p_308936_, BlockPos p_308956_, CollisionContext p_309006_) {
        return Shapes.empty();
    }

    @Override
    protected float getShadeBrightness(BlockState p_308911_, BlockGetter p_308952_, BlockPos p_308918_) {
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState p_309084_, BlockGetter p_309133_, BlockPos p_309097_) {
        return true;
    }

    @Override
    public @Nullable PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return PathType.BLOCKED;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state);
    }

    private VoxelShape getShape(BlockState state) {
        Direction direction = state.getValue(FACING);
        StairsShape shape = state.getValue(SHAPE);
        return switch (direction) {
            case NORTH -> switch (shape) {
                case STRAIGHT -> NORTH_SIDE;
                case INNER_LEFT -> Shapes.join(NORTH_SIDE, WEST_SIDE, BooleanOp.OR);
                case INNER_RIGHT -> Shapes.join(NORTH_SIDE, EAST_SIDE, BooleanOp.OR);
                case OUTER_LEFT -> SOUTHWEST_SIDE;
                case OUTER_RIGHT -> SOUTHEAST_SIDE;
            };
            case EAST -> switch (shape) {
                case STRAIGHT -> EAST_SIDE;
                case INNER_LEFT -> Shapes.join(EAST_SIDE, NORTH_SIDE, BooleanOp.OR);
                case INNER_RIGHT -> Shapes.join(EAST_SIDE, SOUTH_SIDE, BooleanOp.OR);
                case OUTER_LEFT -> SOUTHEAST_SIDE;
                case OUTER_RIGHT -> NORTHWEST_SIDE;
            };
            case SOUTH -> switch (shape) {
                case STRAIGHT -> SOUTH_SIDE;
                case INNER_LEFT -> Shapes.join(SOUTH_SIDE, EAST_SIDE, BooleanOp.OR);
                case INNER_RIGHT -> Shapes.join(SOUTH_SIDE, WEST_SIDE, BooleanOp.OR);
                case OUTER_LEFT -> NORTHWEST_SIDE;
                case OUTER_RIGHT -> NORTHEAST_SIDE;
            };
            case WEST -> switch (shape) {
                case STRAIGHT -> WEST_SIDE;
                case INNER_LEFT -> Shapes.join(WEST_SIDE, SOUTH_SIDE, BooleanOp.OR);
                case INNER_RIGHT -> Shapes.join(WEST_SIDE, NORTH_SIDE, BooleanOp.OR);
                case OUTER_LEFT -> NORTHEAST_SIDE;
                case OUTER_RIGHT -> SOUTHWEST_SIDE;
            };
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos posBelow = context.getClickedPos().below();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return super.getStateForPlacement(context)
                .setValue(BOTTOM, !level.getBlockState(posBelow).isFaceSturdy(level, posBelow, Direction.UP))
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            FluidState fluidstate = level.getFluidState(pos);
            return super.updateShape(state, direction, neighborState, level, pos, neighborPos)
                    .setValue(BOTTOM, !neighborState.isFaceSturdy(level, neighborPos, Direction.UP))
                    .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
