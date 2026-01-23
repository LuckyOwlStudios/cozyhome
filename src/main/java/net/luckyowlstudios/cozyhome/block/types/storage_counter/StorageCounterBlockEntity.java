package net.luckyowlstudios.cozyhome.block.types.storage_counter;

import net.luckyowlstudios.cozyhome.init.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StorageCounterBlockEntity extends RandomizableContainerBlockEntity {
    protected NonNullList<ItemStack> items = NonNullList.withSize(24, ItemStack.EMPTY);

    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
            StorageCounterBlockEntity.this.playSound(blockState, SoundEvents.BARREL_OPEN);
            StorageCounterBlockEntity.this.updateBlockState(blockState, true);
        }

        @Override
        protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
            StorageCounterBlockEntity.this.playSound(blockState, SoundEvents.BARREL_CLOSE);
            StorageCounterBlockEntity.this.updateBlockState(blockState, false);
        }

        @Override
        protected void openerCountChanged(Level p_155066_, BlockPos p_155067_, BlockState p_155068_, int p_155069_, int p_155070_) {
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof StorageCounterBlockMenu) {
                StorageCounterBlockMenu menu = (StorageCounterBlockMenu) player.containerMenu;
                return menu.blockEntity == StorageCounterBlockEntity.this;
            }
            return false;
        }
    };

    // Add these fields for animation
    private float openProgress = 0.0F;
    private float prevOpenProgress = 0.0F;
    private int ticksSinceSync = 0;

    public StorageCounterBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.STORAGE_COUNTER_BLOCK_ENTITY.get(), pos, blockState);
        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
    }

    // Add this method to get smooth animation progress
    public float getOpenProgress(float partialTick) {
        return Mth.lerp(partialTick, this.prevOpenProgress, this.openProgress);
    }

    // Add client-side tick method for animation
    public static void clientTick(Level level, BlockPos pos, BlockState state, StorageCounterBlockEntity storageCounterBlockEntity) {
        storageCounterBlockEntity.prevOpenProgress = storageCounterBlockEntity.openProgress;
        boolean isOpen = storageCounterBlockEntity.getBlockState().getValue(StorageCounterBlock.OPEN);

        if (isOpen && storageCounterBlockEntity.openProgress < 1.0F) {
            // Change 0.1F to a higher value for faster animation
            storageCounterBlockEntity.openProgress = Math.min(1.0F, storageCounterBlockEntity.openProgress + 0.25F); // 5 ticks = 0.25 seconds
        } else if (!isOpen && storageCounterBlockEntity.openProgress > 0.0F) {
            storageCounterBlockEntity.openProgress = Math.max(0.0F, storageCounterBlockEntity.openProgress - 0.25F); // 5 ticks = 0.25 seconds
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items, registries);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag) && tag.contains("Items", 9)) {
            ContainerHelper.loadAllItems(tag, this.items, registries);
        }
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    void playSound(BlockState state, SoundEvent sound) {
        Vec3i vec3i = state.getValue(StorageCounterBlock.FACING).getNormal();
        double d0 = (double)this.worldPosition.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double d1 = (double)this.worldPosition.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double d2 = (double)this.worldPosition.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
        this.level.playSound(null, d0, d1, d2, sound, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    void updateBlockState(BlockState state, boolean open) {
        this.level.setBlock(this.getBlockPos(), state.setValue(StorageCounterBlock.OPEN, Boolean.valueOf(open)), 3);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.cozyhome.storage_counter");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }

    @Override
    public int getContainerSize() {
        return 24;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        return this.saveWithFullMetadata(lookupProvider);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new StorageCounterBlockMenu(containerId, inventory, this);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new StorageCounterBlockMenu(containerId, playerInventory, this);
    }
}
