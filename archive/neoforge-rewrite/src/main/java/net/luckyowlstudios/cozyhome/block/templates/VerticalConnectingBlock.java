package net.luckyowlstudios.cozyhome.block.templates;

import net.luckyowlstudios.cozyhome.block.util.ModBlockStateProperties;
import net.luckyowlstudios.cozyhome.block.util.enums.VerticalConnection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class VerticalConnectingBlock extends Block {

    public static final EnumProperty<VerticalConnection> VERTICAL_CONNECTION = ModBlockStateProperties.VERTICAL_CONNECTION;

    public VerticalConnectingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(VERTICAL_CONNECTION, VerticalConnection.NONE)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VERTICAL_CONNECTION);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = this.defaultBlockState();
        return blockState.setValue(VERTICAL_CONNECTION, setVerticalConnection(context.getLevel(), blockPos));
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        // Update when vertical neighbors change
        if (direction == Direction.UP || direction == Direction.DOWN) {
            return state.setValue(VERTICAL_CONNECTION, setVerticalConnection(level, pos));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    public static VerticalConnection setVerticalConnection(LevelAccessor level, BlockPos pos) {
        boolean isMatchingBlockAbove = level.getBlockState(pos.above()).getBlock() instanceof VerticalConnectingBlock;
        boolean isMatchingBlockBelow = level.getBlockState(pos.below()).getBlock() instanceof VerticalConnectingBlock;

        if (isMatchingBlockAbove && isMatchingBlockBelow) return VerticalConnection.MIDDLE;
        if (isMatchingBlockAbove) return VerticalConnection.BOTTOM;
        if (isMatchingBlockBelow) return VerticalConnection.TOP;
        return VerticalConnection.NONE;
    }
}
