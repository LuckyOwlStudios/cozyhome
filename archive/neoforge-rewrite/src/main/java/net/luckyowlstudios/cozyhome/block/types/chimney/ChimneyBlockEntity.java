package net.luckyowlstudios.cozyhome.block.types.chimney;

import net.luckyowlstudios.cozyhome.init.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// Copied from the CampfireBlockEntity class
public class ChimneyBlockEntity extends BlockEntity {

    public ChimneyBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.CHIMNEY_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static void particleTick(Level level, BlockPos pos, BlockState state, ChimneyBlockEntity blockEntity) {
        RandomSource randomsource = level.random;
        if (randomsource.nextFloat() < 0.11F) {
            for(int i = 0; i < randomsource.nextInt(2) + 2; ++i) {
                makeParticles(level, pos);
            }
        }
    }

    public static void makeParticles(Level level, BlockPos pos) {
        RandomSource randomsource = level.getRandom();
        SimpleParticleType simpleparticletype = ParticleTypes.CAMPFIRE_SIGNAL_SMOKE;
        level.addAlwaysVisibleParticle(
                simpleparticletype,
                true,
                (double)pos.getX() + 0.5 + randomsource.nextDouble() / 3.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                (double)pos.getY() + randomsource.nextDouble() + randomsource.nextDouble(),
                (double)pos.getZ() + 0.5 + randomsource.nextDouble() / 3.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                0.0,
                0.07,
                0.0
        );
        level.addParticle(
                ParticleTypes.SMOKE,
                (double)pos.getX() + 0.5 + randomsource.nextDouble() / 4.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                (double)pos.getY() + 0.4,
                (double)pos.getZ() + 0.5 + randomsource.nextDouble() / 4.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                0.0,
                0.005,
                0.0
        );
    }
}
