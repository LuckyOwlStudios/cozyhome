package net.luckystudio.cozyhome;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.luckystudio.cozyhome.block.util.ModBlockEntityTypes;
import net.luckystudio.cozyhome.block.ModBlocks;
import net.luckystudio.cozyhome.block.custom.seatable.chairs.ChairBlockEntityRenderer;
import net.luckystudio.cozyhome.block.custom.seatable.chairs.ChairModel;
import net.luckystudio.cozyhome.block.custom.clocks.grandfather_clock.GrandfatherClockBlockEntityRenderer;
import net.luckystudio.cozyhome.block.custom.clocks.grandfather_clock.GrandfatherClockModel;
import net.luckystudio.cozyhome.block.custom.clocks.wall_clock.WallClockBlockEntityRenderer;
import net.luckystudio.cozyhome.block.custom.clocks.wall_clock.WallClockModel;
import net.luckystudio.cozyhome.block.custom.seatable.couches.CouchBlockEntityRenderer;
import net.luckystudio.cozyhome.block.custom.seatable.couches.CouchCushionModel;
import net.luckystudio.cozyhome.block.custom.seatable.sofas.SofaBlockEntityRenderer;
import net.luckystudio.cozyhome.block.custom.seatable.sofas.SofaCushionModel;
import net.luckystudio.cozyhome.block.custom.seatable.sofas.SofaModel;
import net.luckystudio.cozyhome.block.custom.telescope.TelescopeBlockEntityRenderer;
import net.luckystudio.cozyhome.block.custom.telescope.TelescopeModel;
import net.luckystudio.cozyhome.block.custom.counters.StorageCounterBlockEntityRenderer;
import net.luckystudio.cozyhome.block.custom.counters.StorageCounterDoorsModel;
import net.luckystudio.cozyhome.client.ModEntityModelLayers;
import net.luckystudio.cozyhome.client.renderer.HeldLiquidRenderer;
import net.luckystudio.cozyhome.block.custom.water_holding_blocks.AbstractWaterHoldingBlockEntity;
import net.luckystudio.cozyhome.entity.ModEntities;
import net.luckystudio.cozyhome.entity.custom.SeatRenderer;
import net.luckystudio.cozyhome.entity.model.*;
import net.luckystudio.cozyhome.item.renderer.BathtubItemRenderer;
import net.luckystudio.cozyhome.item.renderer.ChairItemRenderer;
import net.luckystudio.cozyhome.item.renderer.CozyItemRenderer;
import net.luckystudio.cozyhome.item.renderer.SofaItemRenderer;
import net.luckystudio.cozyhome.item.renderer.WallClockItemRenderer;
import net.luckystudio.cozyhome.util.ModModelPredicates;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemConvertible;

import java.util.function.Supplier;

/**
 * Loader-independent client setup. Each loader calls these methods from the right place
 * (Fabric: ClientModInitializer, NeoForge: the matching mod bus events) and passes in its
 * own registration hooks. Menu screens are registered directly by each loader.
 */
@Environment(EnvType.CLIENT)
public class CozyHomeClient {

    @FunctionalInterface
    public interface ModelLayerRegistrar {
        void register(EntityModelLayer layer, Supplier<TexturedModelData> modelData);
    }

    public interface EntityRendererRegistrar {
        <E extends Entity> void register(EntityType<? extends E> type, EntityRendererFactory<E> factory);
    }

    public interface BlockEntityRendererRegistrar {
        <T extends BlockEntity> void register(BlockEntityType<? extends T> type, BlockEntityRendererFactory<T> factory);
    }

    @FunctionalInterface
    public interface ItemRendererRegistrar {
        void register(ItemConvertible item, CozyItemRenderer renderer);
    }

    /** Anything that doesn't need a loader hook. */
    public static void init() {
        ModEntityModelLayers.registerEntityModelLayers();
        ModModelPredicates.registerModelPredicates();
    }

    public static void registerModelLayers(ModelLayerRegistrar layers) {
        layers.register(ModEntityModelLayers.SEAT, SeatEntityModel::getTexturedModelData);
        layers.register(ModEntityModelLayers.TELESCOPE, TelescopeModel::getTexturedModelData);
        layers.register(ModEntityModelLayers.SOFA, SofaModel::getTexturedModelData);
        layers.register(ModEntityModelLayers.SOFA_CUSHION, SofaCushionModel::getTexturedModelData);
        layers.register(ModEntityModelLayers.COUCH_CUSHION, CouchCushionModel::getTexturedModelData);
        layers.register(ModEntityModelLayers.CHAIR, ChairModel::getTexturedModelData);
        layers.register(ModEntityModelLayers.CUSHION, CushionModel::getTexturedModelData);
        layers.register(ModEntityModelLayers.GRANDFATHER_CLOCK, GrandfatherClockModel::getTexturedModelData);
        layers.register(ModEntityModelLayers.WALL_CLOCK, WallClockModel::getTexturedModelData);
        layers.register(ModEntityModelLayers.STORAGE_COUNTER_DOORS, StorageCounterDoorsModel::getTexturedModelData);
    }

    public static void registerEntityRenderers(EntityRendererRegistrar renderers) {
        renderers.register(ModEntities.SEAT_ENTITY, SeatRenderer::new);
    }

    public static void registerBlockEntityRenderers(BlockEntityRendererRegistrar renderers) {
        renderers.register(ModBlockEntityTypes.TELESCOPE_BLOCK_ENTITY, TelescopeBlockEntityRenderer::new);
        renderers.register(ModBlockEntityTypes.SOFA_BLOCK_ENTITY, SofaBlockEntityRenderer::new);
        renderers.register(ModBlockEntityTypes.COUCH_BLOCK_ENTITY, CouchBlockEntityRenderer::new);
        renderers.register(ModBlockEntityTypes.CHAIR_BLOCK_ENTITY, ChairBlockEntityRenderer::new);
        renderers.register(ModBlockEntityTypes.GRANDFATHER_CLOCK_BLOCK_ENTITY, GrandfatherClockBlockEntityRenderer::new);
        renderers.register(ModBlockEntityTypes.WALL_CLOCK_BLOCK_ENTITY, WallClockBlockEntityRenderer::new);
        renderers.register(ModBlockEntityTypes.STORAGE_COUNTER_BLOCK_ENTITY, StorageCounterBlockEntityRenderer::new);
        renderers.<AbstractWaterHoldingBlockEntity>register(ModBlockEntityTypes.SINK_BLOCK_ENTITY, HeldLiquidRenderer::new);
        renderers.<AbstractWaterHoldingBlockEntity>register(ModBlockEntityTypes.BATHTUB_BLOCK_ENTITY, HeldLiquidRenderer::new);
        renderers.<AbstractWaterHoldingBlockEntity>register(ModBlockEntityTypes.FOUNTAIN_BLOCK_ENTITY, HeldLiquidRenderer::new);
    }

    /** Items with a {@code builtin/entity} model that are drawn with a custom renderer. */
    public static void registerItemRenderers(ItemRendererRegistrar itemRenderers) {
        ItemConvertible[] chairItems = {
                ModBlocks.OAK_CHAIR,
                ModBlocks.SPRUCE_CHAIR,
                ModBlocks.BIRCH_CHAIR,
                ModBlocks.JUNGLE_CHAIR,
                ModBlocks.ACACIA_CHAIR,
                ModBlocks.DARK_OAK_CHAIR,
                ModBlocks.MANGROVE_CHAIR,
                ModBlocks.CHERRY_CHAIR,
                ModBlocks.BAMBOO_CHAIR,
                ModBlocks.CRIMSON_CHAIR,
                ModBlocks.WARPED_CHAIR,
                ModBlocks.IRON_CHAIR,
                ModBlocks.GLASS_CHAIR,
                ModBlocks.UNDEAD_CHAIR,
                ModBlocks.OMINOUS_CHAIR
        };
        for (ItemConvertible chair : chairItems) {
            itemRenderers.register(chair, new ChairItemRenderer());
        }

        ItemConvertible[] sofaItems = {
                ModBlocks.OAK_SOFA,
                ModBlocks.SPRUCE_SOFA,
                ModBlocks.BIRCH_SOFA,
                ModBlocks.JUNGLE_SOFA,
                ModBlocks.ACACIA_SOFA,
                ModBlocks.DARK_OAK_SOFA,
                ModBlocks.MANGROVE_SOFA,
                ModBlocks.CHERRY_SOFA,
                ModBlocks.BAMBOO_SOFA,
                ModBlocks.CRIMSON_SOFA,
                ModBlocks.WARPED_SOFA
        };
        for (ItemConvertible sofa : sofaItems) {
            itemRenderers.register(sofa, new SofaItemRenderer());
        }

        ItemConvertible[] wallClockItems = {
                ModBlocks.OAK_WALL_CLOCK,
                ModBlocks.SPRUCE_WALL_CLOCK,
                ModBlocks.BIRCH_WALL_CLOCK,
                ModBlocks.JUNGLE_WALL_CLOCK,
                ModBlocks.ACACIA_WALL_CLOCK,
                ModBlocks.DARK_OAK_WALL_CLOCK,
                ModBlocks.MANGROVE_WALL_CLOCK,
                ModBlocks.CHERRY_WALL_CLOCK,
                ModBlocks.BAMBOO_WALL_CLOCK,
                ModBlocks.CRIMSON_WALL_CLOCK,
                ModBlocks.WARPED_WALL_CLOCK,
                ModBlocks.IRON_WALL_CLOCK,
                ModBlocks.GLASS_WALL_CLOCK,
                ModBlocks.UNDEAD_WALL_CLOCK,
                ModBlocks.OMINOUS_WALL_CLOCK
        };
        for (ItemConvertible wallClock : wallClockItems) {
            itemRenderers.register(wallClock, new WallClockItemRenderer());
        }

        ItemConvertible[] bathtubsItems = {
                ModBlocks.STONE_BRICK_BATHTUB,
                ModBlocks.MOSSY_STONE_BRICK_BATHTUB,
                ModBlocks.GRANITE_BATHTUB,
                ModBlocks.DIORITE_BATHTUB,
                ModBlocks.ANDESITE_BATHTUB,
                ModBlocks.DEEPSLATE_BATHTUB,
                ModBlocks.CALCITE_BATHTUB,
                ModBlocks.TUFF_BATHTUB,
                ModBlocks.BRICK_BATHTUB,
                ModBlocks.MUD_BATHTUB,
                ModBlocks.SANDSTONE_BATHTUB,
                ModBlocks.RED_SANDSTONE_BATHTUB,
                ModBlocks.PRISMARINE_BATHTUB,
                ModBlocks.NETHER_BRICK_BATHTUB,
                ModBlocks.RED_NETHER_BRICK_BATHTUB,
                ModBlocks.BLACKSTONE_BATHTUB,
                ModBlocks.ENDSTONE_BATHTUB,
                ModBlocks.PURPUR_BATHTUB,
                ModBlocks.IRON_BATHTUB,
                ModBlocks.GOLD_BATHTUB,
        };
        for (ItemConvertible bathtubs : bathtubsItems) {
            itemRenderers.register(bathtubs, new BathtubItemRenderer());
        }
    }
}
