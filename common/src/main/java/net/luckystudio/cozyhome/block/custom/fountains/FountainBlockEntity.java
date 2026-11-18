package net.luckystudio.cozyhome.block.custom.fountains;

import net.luckystudio.cozyhome.block.custom.water_holding_blocks.AbstractWaterHoldingBlockEntity;
import net.luckystudio.cozyhome.block.util.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

/** Only stores which fluid a fountain holds when it isn't water or lava. Fountains don't tick. */
public class FountainBlockEntity extends AbstractWaterHoldingBlockEntity {
    public FountainBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.FOUNTAIN_BLOCK_ENTITY, pos, state);
    }
}
