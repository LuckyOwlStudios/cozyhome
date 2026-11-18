package net.luckyowlstudios.cozyhome.block.types.sink_counter;

import net.luckyowlstudios.cozyhome.init.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SinkCounterBlockEntity extends BlockEntity {

    public SinkCounterBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.SINK_COUNTER_BLOCK_ENTITY.get(), pos, blockState);
    }
}
