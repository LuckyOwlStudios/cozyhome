package net.luckystudio.cozyhome.screen;

import net.luckystudio.cozyhome.CozyHome;
import net.luckystudio.cozyhome.block.custom.counters.StorageCounterScreenHandler;
import net.luckystudio.cozyhome.block.custom.drawers.DrawerScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;

/**
 * Screen handler types. These used to live in the client initializer, which meant they were never
 * registered on a dedicated server.
 */
public class ModScreenHandlers {
    public static final ScreenHandlerType<StorageCounterScreenHandler> STORAGE_COUNTER_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER, CozyHome.id("storage_counter"), new ScreenHandlerType<>(StorageCounterScreenHandler::new, FeatureSet.empty()));

    public static final ScreenHandlerType<DrawerScreenHandler> DRAWER_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER, CozyHome.id("drawer"), new ScreenHandlerType<>(DrawerScreenHandler::new, FeatureSet.empty()));

    public static void registerScreenHandlers() {
        CozyHome.LOGGER.info("Registering Screen Handlers for " + CozyHome.MOD_ID);
    }
}
