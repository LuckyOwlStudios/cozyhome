package net.luckyowlstudios.cozyhome.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public abstract class PlaneConnectingBlock extends Block {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty NORTH_EAST = BooleanProperty.create("north_east");
    public static final BooleanProperty NORTH_WEST = BooleanProperty.create("north_west");
    public static final BooleanProperty SOUTH_EAST = BooleanProperty.create("south_east");
    public static final BooleanProperty SOUTH_WEST = BooleanProperty.create("south_west");

    public PlaneConnectingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(NORTH_EAST, false)
                .setValue(NORTH_WEST, false)
                .setValue(SOUTH_EAST, false)
                .setValue(SOUTH_WEST, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        return this.defaultBlockState()
                .setValue(NORTH, canConnect(level, blockPos.north()))
                .setValue(EAST, canConnect(level, blockPos.east()))
                .setValue(SOUTH, canConnect(level, blockPos.south()))
                .setValue(WEST, canConnect(level, blockPos.west()))
                .setValue(NORTH_EAST, canConnectDiagonal(level, blockPos.north().east(), canConnect(level, blockPos.north()), canConnect(level, blockPos.east())))
                .setValue(NORTH_WEST, canConnectDiagonal(level, blockPos.north().west(), canConnect(level, blockPos.north()), canConnect(level, blockPos.west())))
                .setValue(SOUTH_EAST, canConnectDiagonal(level, blockPos.south().east(), canConnect(level, blockPos.south()), canConnect(level, blockPos.east())))
                .setValue(SOUTH_WEST, canConnectDiagonal(level, blockPos.south().west(), canConnect(level, blockPos.south()), canConnect(level, blockPos.west())))
                ;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        // Update when vertical neighbors change
        return state
                .setValue(NORTH, canConnect(level, pos.north()))
                .setValue(EAST, canConnect(level, pos.east()))
                .setValue(SOUTH, canConnect(level, pos.south()))
                .setValue(WEST, canConnect(level, pos.west()))
                .setValue(NORTH_EAST, canConnectDiagonal(level, pos.north().east(), canConnect(level, pos.north()), canConnect(level, pos.east())))
                .setValue(NORTH_WEST, canConnectDiagonal(level, pos.north().west(), canConnect(level, pos.north()), canConnect(level, pos.west())))
                .setValue(SOUTH_EAST, canConnectDiagonal(level, pos.south().east(), canConnect(level, pos.south()), canConnect(level, pos.east())))
                .setValue(SOUTH_WEST, canConnectDiagonal(level, pos.south().west(), canConnect(level, pos.south()), canConnect(level, pos.west())))
                ;
    }

    protected abstract boolean canConnect(LevelAccessor level, BlockPos pos);

    public static boolean canConnectDiagonal(LevelAccessor level, BlockPos pos, boolean direction1, boolean direction2) {
        return direction1 && direction2 && level.getBlockState(pos).getBlock() instanceof PlaneConnectingBlock;
    }
}
