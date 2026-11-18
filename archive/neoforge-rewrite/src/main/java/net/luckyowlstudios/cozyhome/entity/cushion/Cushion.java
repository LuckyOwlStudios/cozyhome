package net.luckyowlstudios.cozyhome.entity.cushion;

import net.luckyowlstudios.cozyhome.util.ThemedStyle;
import net.luckyowlstudios.cozyhome.entity.Seat;
import net.luckyowlstudios.cozyhome.init.ModEntityTypes;
import net.luckyowlstudios.cozyhome.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

// Copied a lot from FallingBlockEntity
public class Cushion extends Seat {
    protected static final EntityDataAccessor<Integer> DATA_COLOR = SynchedEntityData.defineId(Cushion.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_STYLE = SynchedEntityData.defineId(Cushion.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(Cushion.class, EntityDataSerializers.BLOCK_POS);
    protected static final EntityDataAccessor<Float> DATA_IMPACT_ANIMATION = SynchedEntityData.defineId(Cushion.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> DATA_LAST_IMPACT_VELOCITY = SynchedEntityData.defineId(Cushion.class, EntityDataSerializers.FLOAT);
    public boolean forceTickAfterTeleportToDuplicate;
    public int time;

    // Animation
    public float impactAnimation = 0.0f;
    public float lastImpactVelocity = 0.0f;

    public Cushion(EntityType<Cushion> cushionEntityType, Level level) {
        super(cushionEntityType, level);
        this.setDeltaMovement(Vec3.ZERO);
    }

    public Cushion(Level level, BlockPos pos, DyeColor dyeColor) {
        super(ModEntityTypes.CUSHION.get(), level);
        this.setup(pos);
        this.setColor(dyeColor); // Set the synced color
    }

    public Cushion(Level level, BlockPos pos, ThemedStyle themedStyle) {
        super(ModEntityTypes.CUSHION.get(), level);
        this.setup(pos);
        this.setStyle(themedStyle); // Set the synced color
    }

    private void setup(BlockPos pos) {
        this.setPos(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = pos.getX();
        this.yo = pos.getY();
        this.zo = pos.getZ();
        this.setStartPos(this.blockPosition());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_START_POS, BlockPos.ZERO);
        builder.define(DATA_COLOR, DyeColor.WHITE.getId());
        builder.define(DATA_STYLE, ThemedStyle.NONE.getId());
        builder.define(DATA_IMPACT_ANIMATION, 0.0f);
        builder.define(DATA_LAST_IMPACT_VELOCITY, 0.0f);
    }

    public BlockPos getStartPos() {
        return (BlockPos)this.entityData.get(DATA_START_POS);
    }

    public void setStartPos(BlockPos startPos) {
        this.entityData.set(DATA_START_POS, startPos);
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLOR));
    }

    public void setColor(DyeColor color) {
        this.entityData.set(DATA_COLOR, color.getId());
    }

    public ThemedStyle getStyle() {
        return ThemedStyle.byId(this.entityData.get(DATA_STYLE));
    }

    public void setStyle(ThemedStyle color) {
        this.entityData.set(DATA_STYLE, color.getId());
    }

    public float getImpactAnimation() {
        return this.entityData.get(DATA_IMPACT_ANIMATION);
    }

    public void setImpactAnimation(float animation) {
        this.entityData.set(DATA_IMPACT_ANIMATION, animation);
    }

    public float getLastImpactVelocity() {
        return this.entityData.get(DATA_LAST_IMPACT_VELOCITY);
    }

    public void setLastImpactVelocity(float velocity) {
        this.entityData.set(DATA_LAST_IMPACT_VELOCITY, velocity);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    public boolean isPickable() {
        return true;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        DyeColor dyeColor = this.getColor();
        ThemedStyle themedStyle = this.getStyle();
        if (themedStyle != ThemedStyle.NONE) {
            return switch (themedStyle) {
                case HAY -> ModItems.HAY_CUSHION.toStack();
                case TRADER -> ModItems.TRADER_CUSHION.toStack();
                default -> ModItems.WHITE_CUSHION.toStack();
            };
        } else if (dyeColor != null) {
            return switch (dyeColor) {
                case LIGHT_GRAY -> ModItems.LIGHT_GRAY_CUSHION.toStack();
                case GRAY -> ModItems.GRAY_CUSHION.toStack();
                case BLACK -> ModItems.BLACK_CUSHION.toStack();
                case BROWN -> ModItems.BROWN_CUSHION.toStack();
                case RED -> ModItems.RED_CUSHION.toStack();
                case ORANGE -> ModItems.ORANGE_CUSHION.toStack();
                case YELLOW -> ModItems.YELLOW_CUSHION.toStack();
                case LIME -> ModItems.LIME_CUSHION.toStack();
                case GREEN -> ModItems.GREEN_CUSHION.toStack();
                case CYAN -> ModItems.CYAN_CUSHION.toStack();
                case LIGHT_BLUE -> ModItems.LIGHT_BLUE_CUSHION.toStack();
                case BLUE -> ModItems.BLUE_CUSHION.toStack();
                case PURPLE -> ModItems.PURPLE_CUSHION.toStack();
                case MAGENTA -> ModItems.MAGENTA_CUSHION.toStack();
                case PINK -> ModItems.PINK_CUSHION.toStack();
                default -> ModItems.WHITE_CUSHION.toStack();
            };
        }
        return null;
    }

    @Override
    public boolean shouldRiderSit() {
        return true;
    }

    @Override
    protected boolean needsToBeRidden() {
        return false;
    }

    @Override
    public void tick() {
        ++this.time;

        // Store previous velocity and on-ground state
        Vec3 previousVelocity = this.getDeltaMovement();
        boolean wasOnGround = this.onGround();

        this.applyGravity();
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.handlePortal();

        // Check if we just landed (wasn't on ground before, now is on ground)
        if (!this.level().isClientSide && !wasOnGround && this.onGround()) {
            // Play landing sound based on impact velocity
            double impactVelocity = Math.abs(previousVelocity.y);
            if (impactVelocity > 0.1) { // Only play sound if impact was significant

                // Animaions
                if (this.getImpactAnimation() <= 0) {
                    this.setLastImpactVelocity((float) impactVelocity);
                    this.setImpactAnimation(10.0f); // Shorter animation - 10 ticks instead of 20
                }

                float volume = Math.min(1.0F, (float) impactVelocity * 2.0F); // Scale volume with impact
                float pitch = 0.8F + (float) (Math.random() * 0.4F); // Random pitch variation
                this.playSound(SoundEvents.WOOL_PLACE, volume, pitch);
            }
        }

        // Decrease impact animation over time
        if (this.getImpactAnimation() > 0) {
            this.setImpactAnimation(this.getImpactAnimation() - 1.0f);
        }

        if (!this.level().isClientSide && (this.isAlive() || this.forceTickAfterTeleportToDuplicate)) {
            BlockPos blockpos = this.blockPosition();
            BlockState blockstate = this.level().getBlockState(blockpos);

            // Handle landing logic
            Vec3 velocity = this.getDeltaMovement();
            if (this.onGround()) {
                // Stop movement when on ground
                this.setDeltaMovement(velocity.multiply(0.7, 0, 0.7));
            } else {
                // Normal drag while falling
                this.setDeltaMovement(velocity.multiply(0.98, 0.98, 0.98));
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Time", this.time);
        compoundTag.putInt("Color", this.getColor().getId());
        compoundTag.putInt("Style", this.getStyle().getId());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.time = compoundTag.getInt("Time");
        if (compoundTag.contains("Color")) {
            this.setColor(DyeColor.byId(compoundTag.getInt("Color")));
        }
        if (compoundTag.contains("Style")) {
            this.setStyle(ThemedStyle.byId(compoundTag.getInt("Style")));
        }
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        double d0 = packet.getX();
        double d1 = packet.getY();
        double d2 = packet.getZ();
        this.setPos(d0, d1, d2);
        this.setStartPos(this.blockPosition());
    }

    @Override
    public void move(MoverType type, Vec3 deltaMovement) {
        // Store the current block position before movement
        BlockPos currentBlockPos = this.blockPosition();
        double snapX = currentBlockPos.getX() + 0.5;
        double snapZ = currentBlockPos.getZ() + 0.5;

        // Only allow vertical movement
        Vec3 verticalOnly = new Vec3(0, deltaMovement.y, 0);

        // Perform the movement with only vertical delta
        super.move(type, verticalOnly);

        // After movement, ensure we stay horizontally centered on the same block
        // Only adjust position if we've drifted from center
        double currentX = this.getX();
        double currentZ = this.getZ();

        if (Math.abs(currentX - snapX) > 0.001 || Math.abs(currentZ - snapZ) > 0.001) {
            this.setPosRaw(snapX, this.getY(), snapZ);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!player.isSecondaryUseActive()) {
            if (!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.interact(player, hand);
    }

    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.BLOCKS;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.isRemoved() && !this.level().isClientSide) {
                // Remove any passengers first
                this.ejectPassengers();

                // Play a break sound
                this.playSound(SoundEvents.WOOL_BREAK, 1.0F, 1.0F);

                // Drop
                ItemStack itemStack = getPickResult();
                if (itemStack != null && !source.isCreativePlayer()) {
                    this.spawnAtLocation(getPickResult());
                }

                // Mark as hurt and discard the entity
                this.markHurt();
                this.discard();
            }
            return true;
        }
    }
}
