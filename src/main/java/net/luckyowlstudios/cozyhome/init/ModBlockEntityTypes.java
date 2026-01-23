package net.luckyowlstudios.cozyhome.init;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.block.types.chimney.ChimneyBlockEntity;
import net.luckyowlstudios.cozyhome.block.types.storage_counter.StorageCounterBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Cozyhome.MOD_ID);

    public static final Supplier<BlockEntityType<StorageCounterBlockEntity>> STORAGE_COUNTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("storage_counter_block_entity", () -> BlockEntityType.Builder.of(
                    StorageCounterBlockEntity::new,
                    ModBlocks.OAK_STORAGE_COUNTER.get(),
                    ModBlocks.SPRUCE_STORAGE_COUNTER.get(),
                    ModBlocks.BIRCH_STORAGE_COUNTER.get(),
                    ModBlocks.JUNGLE_STORAGE_COUNTER.get(),
                    ModBlocks.ACACIA_STORAGE_COUNTER.get(),
                    ModBlocks.DARK_OAK_STORAGE_COUNTER.get(),
                    ModBlocks.MANGROVE_STORAGE_COUNTER.get(),
                    ModBlocks.CHERRY_STORAGE_COUNTER.get(),
                    ModBlocks.BAMBOO_STORAGE_COUNTER.get(),
                    ModBlocks.CRIMSON_STORAGE_COUNTER.get(),
                    ModBlocks.WARPED_STORAGE_COUNTER.get()
            ).build(null));

    public static final Supplier<BlockEntityType<StorageCounterBlockEntity>> SINK_COUNTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sink_counter_block_entity", () -> BlockEntityType.Builder.of(
                    StorageCounterBlockEntity::new,
                    ModBlocks.OAK_SINK_COUNTER.get(),
                    ModBlocks.SPRUCE_SINK_COUNTER.get(),
                    ModBlocks.BIRCH_SINK_COUNTER.get(),
                    ModBlocks.JUNGLE_SINK_COUNTER.get(),
                    ModBlocks.ACACIA_SINK_COUNTER.get(),
                    ModBlocks.DARK_OAK_SINK_COUNTER.get(),
                    ModBlocks.MANGROVE_SINK_COUNTER.get(),
                    ModBlocks.CHERRY_SINK_COUNTER.get(),
                    ModBlocks.BAMBOO_SINK_COUNTER.get(),
                    ModBlocks.CRIMSON_SINK_COUNTER.get(),
                    ModBlocks.WARPED_SINK_COUNTER.get()
            ).build(null));

    public static final Supplier<BlockEntityType<ChimneyBlockEntity>> CHIMNEY_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("chimney_block_entity", () -> BlockEntityType.Builder.of(
                    ChimneyBlockEntity::new,
                    ModBlocks.STONE_BRICK_CHIMNEY.get(),
                    ModBlocks.MOSSY_STONE_BRICK_CHIMNEY.get(),
                    ModBlocks.GRANITE_CHIMNEY.get(),
                    ModBlocks.DIORITE_CHIMNEY.get(),
                    ModBlocks.ANDESITE_CHIMNEY.get(),
                    ModBlocks.DEEPSLATE_CHIMNEY.get(),
                    ModBlocks.CALCITE_CHIMNEY.get(),
                    ModBlocks.TUFF_CHIMNEY.get(),
                    ModBlocks.BRICK_CHIMNEY.get(),
                    ModBlocks.MUD_CHIMNEY.get(),
                    ModBlocks.SANDSTONE_CHIMNEY.get(),
                    ModBlocks.RED_SANDSTONE_CHIMNEY.get(),
                    ModBlocks.PRISMARINE_CHIMNEY.get(),
                    ModBlocks.NETHER_BRICK_CHIMNEY.get(),
                    ModBlocks.RED_NETHER_BRICK_CHIMNEY.get(),
                    ModBlocks.BLACKSTONE_CHIMNEY.get(),
                    ModBlocks.ENDSTONE_CHIMNEY.get(),
                    ModBlocks.PURPUR_CHIMNEY.get(),
                    ModBlocks.IRON_CHIMNEY.get(),
                    ModBlocks.GOLD_CHIMNEY.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
