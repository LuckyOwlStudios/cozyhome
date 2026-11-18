package net.luckyowlstudios.cozyhome.block.types;

import net.luckyowlstudios.cozyhome.block.templates.HorizontalCornerConnectingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CounterBlock extends HorizontalCornerConnectingBlock {

    // Setting the pieces of the block
    public static final VoxelShape COUNTER_TOP = Block.box(0, 12, 0, 16, 16, 16);
    public static final VoxelShape NORTH_EAST_INNER = Block.box(0, 0, 0, 14, 12, 16);
    public static final VoxelShape NORTH_WEST_INNER = Block.box(2, 0, 0, 16, 12, 16);
    public static final VoxelShape SOUTH_EAST_INNER = Block.box(2, 0, 0, 16, 12, 16);
    public static final VoxelShape SOUTH_WEST_INNER = Block.box(0, 0, 0, 14, 12, 16);
    public static final VoxelShape NORTH_EAST_OUTER = Block.box(2, 0, 0, 16, 12, 14);
    public static final VoxelShape NORTH_WEST_OUTER = Block.box(0, 0, 0, 14, 12, 14);
    public static final VoxelShape SOUTH_EAST_OUTER = Block.box(2, 0, 2, 16, 12, 16);
    public static final VoxelShape SOUTH_WEST_OUTER = Block.box(0, 0, 2, 14, 12, 16);

    // Final Shapes
    public static final VoxelShape SOUTH_STRAIGHT = Shapes.join(COUNTER_TOP, Block.box(0, 0, 0, 16, 12, 14), BooleanOp.OR);
    public static final VoxelShape NORTH_INNER_LEFT = Shapes.join(COUNTER_TOP, NORTH_EAST_INNER, BooleanOp.OR);
    public static final VoxelShape NORTH_INNER_RIGHT = Shapes.join(COUNTER_TOP, NORTH_WEST_INNER, BooleanOp.OR);
    public static final VoxelShape NORTH_OUTER_LEFT = Shapes.join(COUNTER_TOP, NORTH_WEST_OUTER, BooleanOp.OR);
    public static final VoxelShape NORTH_OUTER_RIGHT = Shapes.join(COUNTER_TOP, NORTH_EAST_OUTER, BooleanOp.OR);
    public static final VoxelShape WEST_STRAIGHT = Shapes.join(COUNTER_TOP, Block.box(2, 0, 0, 16, 12, 16), BooleanOp.OR);
    public static final VoxelShape EAST_INNER_LEFT = Shapes.join(COUNTER_TOP, NORTH_WEST_INNER, BooleanOp.OR);
    public static final VoxelShape EAST_INNER_RIGHT = Shapes.join(COUNTER_TOP, SOUTH_EAST_INNER, BooleanOp.OR);
    public static final VoxelShape EAST_OUTER_LEFT = Shapes.join(COUNTER_TOP, NORTH_EAST_OUTER, BooleanOp.OR);
    public static final VoxelShape EAST_OUTER_RIGHT = Shapes.join(COUNTER_TOP, SOUTH_EAST_OUTER, BooleanOp.OR);
    public static final VoxelShape NORTH_STRAIGHT = Shapes.join(COUNTER_TOP, Block.box(0, 0, 2, 16, 12, 16), BooleanOp.OR);
    public static final VoxelShape SOUTH_INNER_LEFT = Shapes.join(COUNTER_TOP, SOUTH_EAST_INNER, BooleanOp.OR);
    public static final VoxelShape SOUTH_INNER_RIGHT = Shapes.join(COUNTER_TOP, SOUTH_WEST_INNER, BooleanOp.OR);
    public static final VoxelShape SOUTH_OUTER_LEFT = Shapes.join(COUNTER_TOP, SOUTH_EAST_OUTER, BooleanOp.OR);
    public static final VoxelShape SOUTH_OUTER_RIGHT = Shapes.join(COUNTER_TOP, SOUTH_WEST_OUTER, BooleanOp.OR);
    public static final VoxelShape EAST_STRAIGHT = Shapes.join(COUNTER_TOP, Block.box(0, 0, 0, 14, 12, 16), BooleanOp.OR);
    public static final VoxelShape WEST_INNER_LEFT = Shapes.join(COUNTER_TOP, SOUTH_WEST_INNER, BooleanOp.OR);
    public static final VoxelShape WEST_INNER_RIGHT = Shapes.join(COUNTER_TOP, NORTH_EAST_INNER, BooleanOp.OR);
    public static final VoxelShape WEST_OUTER_LEFT = Shapes.join(COUNTER_TOP, SOUTH_WEST_OUTER, BooleanOp.OR);
    public static final VoxelShape WEST_OUTER_RIGHT = Shapes.join(COUNTER_TOP, NORTH_WEST_OUTER, BooleanOp.OR);
    
    public CounterBlock(Properties properties) {
        super(properties);
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
                case STRAIGHT -> NORTH_STRAIGHT;
                case INNER_LEFT -> NORTH_INNER_LEFT;
                case INNER_RIGHT -> NORTH_INNER_RIGHT;
                case OUTER_LEFT -> NORTH_OUTER_LEFT;
                case OUTER_RIGHT -> NORTH_OUTER_RIGHT;
            };
            case EAST -> switch (shape) {
                case STRAIGHT -> EAST_STRAIGHT;
                case INNER_LEFT -> EAST_INNER_LEFT;
                case INNER_RIGHT -> EAST_INNER_RIGHT;
                case OUTER_LEFT -> EAST_OUTER_LEFT;
                case OUTER_RIGHT -> EAST_OUTER_RIGHT;
            };
            case SOUTH -> switch (shape) {
                case STRAIGHT -> SOUTH_STRAIGHT;
                case INNER_LEFT -> SOUTH_INNER_LEFT;
                case INNER_RIGHT -> SOUTH_INNER_RIGHT;
                case OUTER_LEFT -> SOUTH_OUTER_LEFT;
                case OUTER_RIGHT -> SOUTH_OUTER_RIGHT;
            };
            case WEST -> switch (shape) {
                case STRAIGHT -> WEST_STRAIGHT;
                case INNER_LEFT -> WEST_INNER_LEFT;
                case INNER_RIGHT -> WEST_INNER_RIGHT;
                case OUTER_LEFT -> WEST_OUTER_LEFT;
                case OUTER_RIGHT -> WEST_OUTER_RIGHT;
            };
            default -> throw new IllegalStateException("Unexpected value: " + FACING);
        };
    }
}
