package net.luckyowlstudios.cozyhome.block.types;

import net.luckyowlstudios.cozyhome.block.templates.PlaneConnectingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TableBlock extends PlaneConnectingBlock {

    public static final VoxelShape TABLE_TOP = Block.box(0, 13, 0, 16, 16, 16);
    public static final VoxelShape BOTTOM = Block.box(1, 0, 1, 15, 13, 15);
    public static final VoxelShape TABLE = Shapes.join(TABLE_TOP, BOTTOM, BooleanOp.OR);

    public TableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return TABLE;
    }

    @Override
    protected boolean canConnect(LevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof TableBlock;
    }
}
