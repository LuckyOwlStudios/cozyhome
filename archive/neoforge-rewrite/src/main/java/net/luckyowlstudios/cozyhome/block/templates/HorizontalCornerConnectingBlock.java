package net.luckyowlstudios.cozyhome.block.templates;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
import org.jetbrains.annotations.Nullable;

// This block connects similar to how stairs connect, though do not flip upside down like how stairs do.
public class HorizontalCornerConnectingBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<HorizontalCornerConnectingBlock> CODEC = simpleCodec(HorizontalCornerConnectingBlock::new);
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;

    protected HorizontalCornerConnectingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SHAPE, StairsShape.STRAIGHT)
        );
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, SHAPE);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
        return blockState.setValue(SHAPE, setStairShapeNoFlip(blockState, context.getLevel(), blockPos));
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return direction.getAxis().isHorizontal()
                ? state.setValue(SHAPE, setStairShapeNoFlip(state, (Level) level, pos))
                : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    public StairsShape setStairShapeNoFlip(BlockState state, Level level, BlockPos pos) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        BlockState blockState = level.getBlockState(pos.relative(direction));
            if (blockState.getBlock() == this) {
                Direction direction2 = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING);
                if (direction2.getAxis() != state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis()) {
                    if (direction2 == direction.getCounterClockWise()) {
                        return StairsShape.INNER_LEFT;
                    }

                    return StairsShape.INNER_RIGHT;
                }
            }
            BlockState blockState2 = level.getBlockState(pos.relative(direction.getOpposite()));
            if (blockState2.getBlock() == this) {
                Direction direction3 = blockState2.getValue(BlockStateProperties.HORIZONTAL_FACING);
                if (direction3.getAxis() != state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis()) {
                    if (direction3 == direction.getCounterClockWise()) {
                        return StairsShape.OUTER_LEFT;
                    }
                    return StairsShape.OUTER_RIGHT;
                }
            }
        return StairsShape.STRAIGHT;
    }
}
