package net.luckyowlstudios.cozyhome.init;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.util.ThemedStyle;
import net.luckyowlstudios.cozyhome.item.CushionItem;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Cozyhome.MOD_ID);

    public static final DeferredItem<Item> WHITE_CUSHION = ITEMS.register("white_cushion",
            () -> new CushionItem(DyeColor.WHITE, new Item.Properties()));
    public static final DeferredItem<Item> LIGHT_GRAY_CUSHION = ITEMS.register("light_gray_cushion",
            () -> new CushionItem(DyeColor.LIGHT_GRAY, new Item.Properties()));
    public static final DeferredItem<Item> GRAY_CUSHION = ITEMS.register("gray_cushion",
            () -> new CushionItem(DyeColor.GRAY, new Item.Properties()));
    public static final DeferredItem<Item> BLACK_CUSHION = ITEMS.register("black_cushion",
            () -> new CushionItem(DyeColor.BLACK, new Item.Properties()));
    public static final DeferredItem<Item> BROWN_CUSHION = ITEMS.register("brown_cushion",
            () -> new CushionItem(DyeColor.BROWN, new Item.Properties()));
    public static final DeferredItem<Item> RED_CUSHION = ITEMS.register("red_cushion",
            () -> new CushionItem(DyeColor.RED, new Item.Properties()));
    public static final DeferredItem<Item> ORANGE_CUSHION = ITEMS.register("orange_cushion",
            () -> new CushionItem(DyeColor.ORANGE, new Item.Properties()));
    public static final DeferredItem<Item> YELLOW_CUSHION = ITEMS.register("yellow_cushion",
            () -> new CushionItem(DyeColor.YELLOW, new Item.Properties()));
    public static final DeferredItem<Item> LIME_CUSHION = ITEMS.register("lime_cushion",
            () -> new CushionItem(DyeColor.LIME, new Item.Properties()));
    public static final DeferredItem<Item> GREEN_CUSHION = ITEMS.register("green_cushion",
            () -> new CushionItem(DyeColor.GREEN, new Item.Properties()));
    public static final DeferredItem<Item> CYAN_CUSHION = ITEMS.register("cyan_cushion",
            () -> new CushionItem(DyeColor.CYAN, new Item.Properties()));
    public static final DeferredItem<Item> LIGHT_BLUE_CUSHION = ITEMS.register("light_blue_cushion",
            () -> new CushionItem(DyeColor.LIGHT_BLUE, new Item.Properties()));
    public static final DeferredItem<Item> BLUE_CUSHION = ITEMS.register("blue_cushion",
            () -> new CushionItem(DyeColor.BLUE, new Item.Properties()));
    public static final DeferredItem<Item> PURPLE_CUSHION = ITEMS.register("purple_cushion",
            () -> new CushionItem(DyeColor.PURPLE, new Item.Properties()));
    public static final DeferredItem<Item> MAGENTA_CUSHION = ITEMS.register("magenta_cushion",
            () -> new CushionItem(DyeColor.MAGENTA, new Item.Properties()));
    public static final DeferredItem<Item> PINK_CUSHION = ITEMS.register("pink_cushion",
            () -> new CushionItem(DyeColor.PINK, new Item.Properties()));
    public static final DeferredItem<Item> HAY_CUSHION = ITEMS.register("hay_cushion",
            () -> new CushionItem(ThemedStyle.HAY, new Item.Properties()));
    public static final DeferredItem<Item> TRADER_CUSHION = ITEMS.register("trader_cushion",
            () -> new CushionItem(ThemedStyle.TRADER, new Item.Properties()));

    public static final DeferredItem<Item> FORT_BANNER_PATTERN = ITEMS.register("home_banner_pattern",
            () -> new BannerPatternItem(ModTags.PATTERN_ITEM_HOME, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
