package net.luckystudio.cozyhome.block.custom.water_holding_blocks;

import net.luckystudio.cozyhome.block.custom.water_holding_blocks.sink.AbstractSinkBlock;
import net.luckystudio.cozyhome.block.util.ModProperties;
import net.luckystudio.cozyhome.block.util.enums.ContainsBlock;
import net.luckystudio.cozyhome.block.util.interfaces.WaterHoldingBlock;
import net.luckystudio.cozyhome.block.util.LiquidHelper;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AbstractWaterHoldingBlockEntity extends BlockEntity {

    public int timer;
    public int soupTime;
    // Only used for fluids other than water and lava (contains=custom); see LiquidHelper
    private Fluid storedFluid = Fluids.EMPTY;

    public AbstractWaterHoldingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.timer = 0;
        this.soupTime = 0;
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, AbstractWaterHoldingBlockEntity blockEntity) {

        // Handle soup time
        if (blockEntity.soupTime > 0) {
            if (state.get(ModProperties.CONTAINS) != ContainsBlock.WATER) blockEntity.soupTime = 0;
            blockEntity.soupTime--;
        }

        // If the block is not triggered, or if it's not a water holding block, return to stop the tick
        if (!state.get(Properties.TRIGGERED) && !(state.getBlock() instanceof WaterHoldingBlock)) return;

        WaterHoldingBlock waterHoldingBlock = (WaterHoldingBlock) state.getBlock();

        Direction pullDirection = waterHoldingBlock.pullingDirection(state, world, blockPos);

        // Turn off the trigger if the block has no liquid to pull or the block is full
        if (pullDirection == null || waterHoldingBlock.isFull(state)) {
            world.setBlockState(blockPos, state.with(Properties.TRIGGERED, false), 3);
            return;
        }

        // Determine the liquid we should be pulling
        BlockPos pullPos = blockPos.offset(pullDirection);
        BlockState pullState = world.getBlockState(pullPos);
        Fluid pullingFluid = LiquidHelper.pullableFluid(pullState);

        // Choose the correct particles
        ParticleEffect dripParticle;
        ParticleEffect splashParticle;

        if (pullingFluid == Fluids.WATER) {
            dripParticle = ParticleTypes.FALLING_DRIPSTONE_WATER;
            splashParticle = ParticleTypes.SPLASH;
        } else if (pullingFluid == Fluids.LAVA) {
            dripParticle = ParticleTypes.FALLING_LAVA;
            splashParticle = ParticleTypes.LANDING_LAVA;
        } else {
            BlockState fluidBlock = pullingFluid.getDefaultState().getBlockState();
            dripParticle = new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, fluidBlock);
            splashParticle = new BlockStateParticleEffect(ParticleTypes.BLOCK, fluidBlock);
        }

        // Particle positions
        double faucetHeight = state.getBlock() instanceof AbstractSinkBlock ? 0.6 : 0.4875; // Adjust this value to change the height of the faucet
        Vec3d centerTop = Vec3d.ofCenter(blockPos).add(0.0, faucetHeight, 0.0);
        Vec3d center = new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + waterHoldingBlock.getLiquidLevelHeight(state), blockPos.getZ() + 0.5);

        // Spawn particles
        world.addParticle(dripParticle, centerTop.x, centerTop.y, centerTop.z, 0.0, 0.0, 0.0);
        world.addParticle(splashParticle, center.x, center.y, center.z, 0.0, 0.0, 0.0);

        // Update timer and level
        blockEntity.timer++;

        if (blockEntity.timer < 20) return;
        waterHoldingBlock.addLiquid(state, world, blockPos, pullState, pullDirection);
        blockEntity.timer = 0; // Reset timer
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("timer", this.timer);
        nbt.putInt("soupTime", this.soupTime);
        if (this.storedFluid != Fluids.EMPTY) {
            nbt.putString("fluid", Registries.FLUID.getId(this.storedFluid).toString());
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.timer = nbt.getInt("timer");
        this.soupTime = nbt.getInt("soupTime");
        Identifier fluidId = nbt.contains("fluid") ? Identifier.tryParse(nbt.getString("fluid")) : null;
        this.storedFluid = fluidId == null ? Fluids.EMPTY : Registries.FLUID.get(fluidId);
    }

    public Fluid getStoredFluid() {
        return this.storedFluid;
    }

    public void setStoredFluid(Fluid fluid) {
        this.storedFluid = fluid == null ? Fluids.EMPTY : fluid;
        this.markDirty();
        if (this.world != null && !this.world.isClient) {
            BlockState state = this.getCachedState();
            this.world.updateListeners(this.pos, state, state, 3);
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    // This Syncs the Client and Server
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
