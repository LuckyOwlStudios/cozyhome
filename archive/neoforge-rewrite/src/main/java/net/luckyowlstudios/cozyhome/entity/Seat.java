package net.luckyowlstudios.cozyhome.entity;

import net.luckyowlstudios.cozyhome.init.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class Seat extends Entity {

    public Seat(EntityType<? extends Seat> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    public Seat(Level level, BlockPos pos, LivingEntity rider) {
        super(ModEntityTypes.SEAT.get(), level);
        float xx = pos.getX() + 0.5f;
        float yy = pos.getY();
        float zz = pos.getZ() + 0.5f;
        this.setPos(xx, yy, zz);
        this.xo = xx;
        this.yo = yy;
        this.zo = zz;
        rider.startRiding(this);
    }

    @Override
    public void tick() {
        super.tick();
//        if (this.level().isClientSide()) return;
//        if (this.needsToBeRidden() && this.getControllingPassenger() == null) {
//            this.discard();
//            return;
//        }
//        if (!(this.getControllingPassenger() instanceof LivingEntity livingEntity)) return;
//        this.setRot(livingEntity.getYRot(), livingEntity.getXRot() * 0.5F);
    }

    protected boolean needsToBeRidden() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this instanceof Seat) return false;
        return super.hurt(source, amount);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof LivingEntity livingEntity
                ? livingEntity
                : super.getControllingPassenger();
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (needsToBeRidden()) {
            this.discard();
        }
    }
}
