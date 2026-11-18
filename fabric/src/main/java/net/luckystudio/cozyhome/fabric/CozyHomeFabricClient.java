package net.luckystudio.cozyhome.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.luckystudio.cozyhome.CozyHomeClient;
import net.luckystudio.cozyhome.block.custom.counters.StorageCounterScreen;
import net.luckystudio.cozyhome.block.custom.drawers.DrawerScreen;
import net.luckystudio.cozyhome.client.ModRenderLayers;
import net.luckystudio.cozyhome.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

@Environment(EnvType.CLIENT)
public class CozyHomeFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CozyHomeClient.init();

        HandledScreens.register(ModScreenHandlers.STORAGE_COUNTER_SCREEN_HANDLER, StorageCounterScreen::new);
        HandledScreens.register(ModScreenHandlers.DRAWER_SCREEN_HANDLER, DrawerScreen::new);

        CozyHomeClient.registerModelLayers((layer, modelData) ->
                EntityModelLayerRegistry.registerModelLayer(layer, modelData::get));

        CozyHomeClient.registerEntityRenderers(new CozyHomeClient.EntityRendererRegistrar() {
            @Override
            public <E extends Entity> void register(EntityType<? extends E> type, EntityRendererFactory<E> factory) {
                EntityRendererRegistry.register(type, factory);
            }
        });

        CozyHomeClient.registerBlockEntityRenderers(new CozyHomeClient.BlockEntityRendererRegistrar() {
            @Override
            public <T extends BlockEntity> void register(BlockEntityType<? extends T> type, BlockEntityRendererFactory<T> factory) {
                BlockEntityRendererFactories.register(type, factory);
            }
        });

        CozyHomeClient.registerItemRenderers((item, renderer) ->
                BuiltinItemRendererRegistry.INSTANCE.register(item, (BuiltinItemRendererRegistry.DynamicItemRenderer) renderer::render));

        ModRenderLayers.registerBlockRenderLayers(BlockRenderLayerMap.INSTANCE::putBlocks);
        ModRenderLayers.registerColorProviders(ColorProviderRegistry.BLOCK::register, ColorProviderRegistry.ITEM::register);
    }
}
