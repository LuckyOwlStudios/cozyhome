package net.luckystudio.cozyhome.block.custom.horizontal_connecting_blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/**
 * The classic table look (separate top, apron and leg textures with chunky 3x3 legs).
 * Same connection logic as {@link TableBlock}; only the shape differs to match the model.
 */
public class ClassicTableBlock extends TableBlock {

    public static final MapCodec<ClassicTableBlock> CODEC = createCodec(ClassicTableBlock::new);

    public static final VoxelShape CLASSIC_TOP = Block.createCuboidShape(0, 13, 0, 16, 16, 16);
    public static final VoxelShape CLASSIC_NORTH_WEST_LEG = Block.createCuboidShape(1, 0, 1, 4, 13, 4);
    public static final VoxelShape CLASSIC_NORTH_EAST_LEG = Block.createCuboidShape(12, 0, 1, 15, 13, 4);
    public static final VoxelShape CLASSIC_SOUTH_WEST_LEG = Block.createCuboidShape(1, 0, 12, 4, 13, 15);
    public static final VoxelShape CLASSIC_SOUTH_EAST_LEG = Block.createCuboidShape(12, 0, 12, 15, 13, 15);

    public ClassicTableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<ClassicTableBlock> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        boolean north = state.get(NORTH);
        boolean east = state.get(EAST);
        boolean south = state.get(SOUTH);
        boolean west = state.get(WEST);

        VoxelShape shape = CLASSIC_TOP;
        if (north && west && !state.get(NORTH_WEST) || !north && !west) shape = VoxelShapes.union(shape, CLASSIC_NORTH_WEST_LEG);
        if (north && east && !state.get(NORTH_EAST) || !north && !east) shape = VoxelShapes.union(shape, CLASSIC_NORTH_EAST_LEG);
        if (south && west && !state.get(SOUTH_WEST) || !south && !west) shape = VoxelShapes.union(shape, CLASSIC_SOUTH_WEST_LEG);
        if (south && east && !state.get(SOUTH_EAST) || !south && !east) shape = VoxelShapes.union(shape, CLASSIC_SOUTH_EAST_LEG);
        return shape;
    }
}
