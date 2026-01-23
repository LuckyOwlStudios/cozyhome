package net.luckyowlstudios.cozyhome.init;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.block.types.storage_counter.StorageCounterBlockMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Cozyhome.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<StorageCounterBlockMenu>> STORAGE_COUNTER_BLOCK_MENU =
            registerMenuType("storage_counter_block_menu", StorageCounterBlockMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
