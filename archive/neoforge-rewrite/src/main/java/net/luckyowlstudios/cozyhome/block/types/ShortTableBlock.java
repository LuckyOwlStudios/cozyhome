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

public class ShortTableBlock extends PlaneConnectingBlock {

    public static final VoxelShape TABLE_TOP = Block.box(0, 5, 0, 16, 8, 16);
    public static final VoxelShape BOTTOM = Block.box(1, 0, 1, 15, 5, 15);
    public static final VoxelShape TABLE = Shapes.join(TABLE_TOP, BOTTOM, BooleanOp.OR);

    public ShortTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean canConnect(LevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof ShortTableBlock;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return TABLE;
    }
}
