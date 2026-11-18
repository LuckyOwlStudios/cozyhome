package net.luckystudio.cozyhome.neoforge;

import net.luckystudio.cozyhome.CozyHome;
import net.luckystudio.cozyhome.CozyHomeClient;
import net.luckystudio.cozyhome.block.custom.counters.StorageCounterScreen;
import net.luckystudio.cozyhome.block.custom.drawers.DrawerScreen;
import net.luckystudio.cozyhome.client.ModRenderLayers;
import net.luckystudio.cozyhome.item.renderer.CozyItemRenderer;
import net.luckystudio.cozyhome.screen.ModScreenHandlers;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@Mod(value = CozyHome.MOD_ID, dist = Dist.CLIENT)
public class CozyHomeNeoForgeClient {
    public CozyHomeNeoForgeClient(IEventBus modBus) {
        modBus.addListener(FMLClientSetupEvent.class, CozyHomeNeoForgeClient::onClientSetup);
        modBus.addListener(RegisterMenuScreensEvent.class, CozyHomeNeoForgeClient::onRegisterScreens);
        modBus.addListener(EntityRenderersEvent.RegisterLayerDefinitions.class, CozyHomeNeoForgeClient::onRegisterLayers);
        modBus.addListener(EntityRenderersEvent.RegisterRenderers.class, CozyHomeNeoForgeClient::onRegisterRenderers);
        modBus.addListener(RegisterColorHandlersEvent.Block.class, CozyHomeNeoForgeClient::onRegisterBlockColors);
        modBus.addListener(RegisterColorHandlersEvent.Item.class, CozyHomeNeoForgeClient::onRegisterItemColors);
        modBus.addListener(RegisterClientExtensionsEvent.class, CozyHomeNeoForgeClient::onRegisterClientExtensions);
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            CozyHomeClient.init();
            ModRenderLayers.registerBlockRenderLayers((layer, blocks) -> {
                for (Block block : blocks) {
                    RenderLayers.setRenderLayer(block, layer);
                }
            });
        });
    }

    private static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(ModScreenHandlers.STORAGE_COUNTER_SCREEN_HANDLER, StorageCounterScreen::new);
        event.register(ModScreenHandlers.DRAWER_SCREEN_HANDLER, DrawerScreen::new);
    }

    private static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CozyHomeClient.registerModelLayers(event::registerLayerDefinition);
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        CozyHomeClient.registerEntityRenderers(new CozyHomeClient.EntityRendererRegistrar() {
            @Override
            public <E extends Entity> void register(EntityType<? extends E> type, EntityRendererFactory<E> factory) {
                event.registerEntityRenderer(type, factory);
            }
        });
        CozyHomeClient.registerBlockEntityRenderers(new CozyHomeClient.BlockEntityRendererRegistrar() {
            @Override
            public <T extends BlockEntity> void register(BlockEntityType<? extends T> type, BlockEntityRendererFactory<T> factory) {
                event.registerBlockEntityRenderer(type, factory);
            }
        });
    }

    private static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        ModRenderLayers.registerColorProviders(event::register, (provider, items) -> {});
    }

    private static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        ModRenderLayers.registerColorProviders((provider, blocks) -> {}, event::register);
    }

    private static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        CozyHomeClient.registerItemRenderers((item, renderer) ->
                event.registerItem(new CustomItemRendererExtension(renderer), item.asItem()));
    }

    /** Hooks a {@link CozyItemRenderer} into NeoForge's custom item rendering ({@code builtin/entity} models). */
    private static final class CustomItemRendererExtension implements IClientItemExtensions {
        private final CozyItemRenderer renderer;
        private BuiltinModelItemRenderer itemRenderer;

        private CustomItemRendererExtension(CozyItemRenderer renderer) {
            this.renderer = renderer;
        }

        @Override
        public BuiltinModelItemRenderer getCustomRenderer() {
            // Created lazily: the client isn't fully constructed yet when extensions are registered.
            if (itemRenderer == null) {
                MinecraftClient client = MinecraftClient.getInstance();
                itemRenderer = new BuiltinModelItemRenderer(client.getBlockEntityRenderDispatcher(), client.getEntityModelLoader()) {
                    @Override
                    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
                        renderer.render(stack, mode, matrices, vertexConsumers, light, overlay);
                    }
                };
            }
            return itemRenderer;
        }
    }
}
