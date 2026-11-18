package net.luckystudio.cozyhome.block.custom;

import com.mojang.serialization.MapCodec;
import net.luckystudio.cozyhome.block.util.enums.VerticalLinearConnectionBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

/**
 * A wall of planks that places like a log (along the X, Y or Z axis) and joins up with planked walls
 * of the same axis on either end, so tall or long runs show as one continuous piece.
 * <p>
 * {@link #CONNECTION}: {@code single} (alone), {@code tail} (start of a run), {@code middle},
 * {@code head} (end of a run). "Forward" along an axis is the way the model's top points after the
 * blockstate rotation: up for Y, north for Z and east for X.
 */
public class PlankedWallBlock extends PillarBlock {
    public static final MapCodec<PlankedWallBlock> CODEC = createCodec(PlankedWallBlock::new);
    public static final EnumProperty<VerticalLinearConnectionBlock> CONNECTION = EnumProperty.of("connection", VerticalLinearConnectionBlock.class);

    public PlankedWallBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(AXIS, Direction.Axis.Y)
                .with(CONNECTION, VerticalLinearConnectionBlock.SINGLE));
    }

    @Override
    public MapCodec<? extends PlankedWallBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CONNECTION);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        if (state == null) return null;
        return state.with(CONNECTION, getConnection(state.get(AXIS), ctx.getWorld(), ctx.getBlockPos()));
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        Direction.Axis axis = state.get(AXIS);
        if (direction.getAxis() == axis) {
            return state.with(CONNECTION, getConnection(axis, world, pos));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        BlockState rotated = super.rotate(state, rotation);
        // If the run's "forward" end now points backwards along its new axis, swap head and tail
        Direction turned = rotation.rotate(forward(state.get(AXIS)));
        return turned == forward(turned.getAxis()) ? rotated : rotated.with(CONNECTION, flip(rotated.get(CONNECTION)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        Direction mirrored = mirror.apply(forward(state.get(AXIS)));
        return mirrored == forward(mirrored.getAxis()) ? state : state.with(CONNECTION, flip(state.get(CONNECTION)));
    }

    private static VerticalLinearConnectionBlock flip(VerticalLinearConnectionBlock connection) {
        return switch (connection) {
            case HEAD -> VerticalLinearConnectionBlock.TAIL;
            case TAIL -> VerticalLinearConnectionBlock.HEAD;
            default -> connection;
        };
    }

    /** The direction the model's top end points for each axis. */
    public static Direction forward(Direction.Axis axis) {
        return switch (axis) {
            case X -> Direction.EAST;
            case Z -> Direction.NORTH;
            default -> Direction.UP;
        };
    }

    private static boolean connects(Direction.Axis axis, WorldAccess world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof PlankedWallBlock && state.get(AXIS) == axis;
    }

    public static VerticalLinearConnectionBlock getConnection(Direction.Axis axis, WorldAccess world, BlockPos pos) {
        Direction forward = forward(axis);
        boolean ahead = connects(axis, world, pos.offset(forward));
        boolean behind = connects(axis, world, pos.offset(forward.getOpposite()));
        if (ahead && behind) return VerticalLinearConnectionBlock.MIDDLE;
        if (ahead) return VerticalLinearConnectionBlock.TAIL;
        if (behind) return VerticalLinearConnectionBlock.HEAD;
        return VerticalLinearConnectionBlock.SINGLE;
    }
}
