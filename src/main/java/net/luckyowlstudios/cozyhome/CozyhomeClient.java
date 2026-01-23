package net.luckyowlstudios.cozyhome;

import net.luckyowlstudios.cozyhome.block.types.storage_counter.StorageCounterBlockEntityRenderer;
import net.luckyowlstudios.cozyhome.block.types.storage_counter.StorageCounterBlockMenu;
import net.luckyowlstudios.cozyhome.block.types.storage_counter.StorageCounterBlockScreen;
import net.luckyowlstudios.cozyhome.block.types.storage_counter.StorageCounterDoorsModel;
import net.luckyowlstudios.cozyhome.entity.cushion.CushionModel;
import net.luckyowlstudios.cozyhome.entity.cushion.CushionRenderer;
import net.luckyowlstudios.cozyhome.init.*;
import net.luckyowlstudios.cozyhome.item.CushionItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = Cozyhome.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Cozyhome.MOD_ID, value = Dist.CLIENT)
public class CozyhomeClient {
    public CozyhomeClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.CUSHION, CushionModel::createCushionLayer);
        event.registerLayerDefinition(ModModelLayers.STORAGE_COUNTER_DOORS, StorageCounterDoorsModel::createDoors);
    }

    @SubscribeEvent
    public static void registerER(EntityRenderersEvent.RegisterRenderers event) {
        // Entities
        event.registerEntityRenderer(ModEntityTypes.SEAT.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CUSHION.get(), CushionRenderer::new);

        // Blocks
        event.registerBlockEntityRenderer(ModBlockEntityTypes.STORAGE_COUNTER_BLOCK_ENTITY.get(), StorageCounterBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.STORAGE_COUNTER_BLOCK_MENU.get(), StorageCounterBlockScreen::new);
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
                    Block block = state.getBlock();
//                    if (block instanceof CushionBlock cushionBlock) {
//                        // Get the RGB value from the dye color (removes alpha channel)
//                        return cushionBlock.getColor().getTextureDiffuseColor() | 0xFF000000;
//                    }
                    return 0xFFFFFFFF; // Default white if not a cushion block
                }
        );
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new CushionItemRenderer(
                        Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                        Minecraft.getInstance().getEntityModels() // This provides EntityModelSet
                );
            }
        },
                ModItems.WHITE_CUSHION.get(),
                ModItems.LIGHT_GRAY_CUSHION.get(),
                ModItems.GRAY_CUSHION.get(),
                ModItems.BLACK_CUSHION.get(),
                ModItems.BROWN_CUSHION.get(),
                ModItems.RED_CUSHION.get(),
                ModItems.ORANGE_CUSHION.get(),
                ModItems.YELLOW_CUSHION.get(),
                ModItems.LIME_CUSHION.get(),
                ModItems.GREEN_CUSHION.get(),
                ModItems.CYAN_CUSHION.get(),
                ModItems.LIGHT_BLUE_CUSHION.get(),
                ModItems.BLUE_CUSHION.get(),
                ModItems.PURPLE_CUSHION.get(),
                ModItems.MAGENTA_CUSHION.get(),
                ModItems.PINK_CUSHION.get(),
                ModItems.HAY_CUSHION.asItem(),
                ModItems.TRADER_CUSHION.asItem()
        ); // Replace with your actual cushion item
    }
}
