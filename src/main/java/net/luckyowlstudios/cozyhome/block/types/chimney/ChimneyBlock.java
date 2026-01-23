package net.luckyowlstudios.cozyhome.block.types.chimney;

import com.mojang.serialization.MapCodec;
import net.luckyowlstudios.cozyhome.block.templates.VerticalConnectingBlock;
import net.luckyowlstudios.cozyhome.block.util.ModBlockStateProperties;
import net.luckyowlstudios.cozyhome.block.util.enums.VerticalConnection;
import net.luckyowlstudios.cozyhome.init.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class ChimneyBlock extends VerticalConnectingBlock implements EntityBlock {

    public static final MapCodec<ChimneyBlock> CODEC = simpleCodec(ChimneyBlock::new);

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ChimneyBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(LIT, Boolean.TRUE)
        );
    }

    @Override
    protected MapCodec<ChimneyBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ChimneyBlockEntity(blockPos, blockState); // Use ChimneyBlockEntity, not CampfireBlockEntity
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() && canEmitParticles(state) ?
                createTickerHelper(blockEntityType, ModBlockEntityTypes.CHIMNEY_BLOCK_ENTITY.get(), ChimneyBlockEntity::particleTick) :
                null;
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>)ticker : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (canEmitParticles(state)) {
            for(int i = 0; i < random.nextInt(1) + 1; ++i) {
                level.addParticle(ParticleTypes.LAVA, (double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, (double)(random.nextFloat() / 2.0F), 5.0E-5, (double)(random.nextFloat() / 2.0F));
            }
        }
    }

    public boolean canEmitParticles(BlockState state) {
        return state.getValue(LIT) && (state.getValue(ModBlockStateProperties.VERTICAL_CONNECTION) == VerticalConnection.NONE
                || state.getValue(ModBlockStateProperties.VERTICAL_CONNECTION) == VerticalConnection.TOP);
    }
}
