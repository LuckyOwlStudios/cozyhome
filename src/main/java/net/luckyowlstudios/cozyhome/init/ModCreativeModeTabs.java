package net.luckyowlstudios.cozyhome.init;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Cozyhome.MOD_ID);

    public static final Supplier<CreativeModeTab> COZYHOME_CREATIVE_TAB = CREATIVE_MODE_TAB.register("cozyhome_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.OAK_TABLE))
                    .title(Component.translatable("itemGroup.cozyhome"))
                    .displayItems((itemDisplayParameters, output) -> {

                        // PLANKED WALLS
                        output.accept(ModBlocks.OAK_PLANKED_WALL);
                        output.accept(ModBlocks.SPRUCE_PLANKED_WALL);
                        output.accept(ModBlocks.BIRCH_PLANKED_WALL);
                        output.accept(ModBlocks.JUNGLE_PLANKED_WALL);
                        output.accept(ModBlocks.ACACIA_PLANKED_WALL);
                        output.accept(ModBlocks.DARK_OAK_PLANKED_WALL);
                        output.accept(ModBlocks.MANGROVE_PLANKED_WALL);
                        output.accept(ModBlocks.CHERRY_PLANKED_WALL);
                        output.accept(ModBlocks.BAMBOO_PLANKED_WALL);
                        output.accept(ModBlocks.CRIMSON_PLANKED_WALL);
                        output.accept(ModBlocks.WARPED_PLANKED_WALL);

                        // Farmer's Delight Cabinets
                        if (ModList.get().isLoaded("farmersdelight")) {
                            addBlockByResourceLocation(output, "farmersdelight:oak_cabinet");
                            addBlockByResourceLocation(output, "farmersdelight:birch_cabinet");
                            addBlockByResourceLocation(output, "farmersdelight:spruce_cabinet");
                            addBlockByResourceLocation(output, "farmersdelight:jungle_cabinet");
                            addBlockByResourceLocation(output, "farmersdelight:acacia_cabinet");
                            addBlockByResourceLocation(output, "farmersdelight:dark_oak_cabinet");
                            addBlockByResourceLocation(output, "farmersdelight:mangrove_cabinet");
                            addBlockByResourceLocation(output, "farmersdelight:cherry_cabinet");
                            addBlockByResourceLocation(output, "farmersdelight:bamboo_cabinet");
                            addBlockByResourceLocation(output, "farmersdelight:crimson_cabinet");
                            addBlockByResourceLocation(output, "farmersdelight:warped_cabinet");
                        }

                        // COUNTERS
                        output.accept(ModBlocks.OAK_COUNTER);
                        output.accept(ModBlocks.SPRUCE_COUNTER);
                        output.accept(ModBlocks.BIRCH_COUNTER);
                        output.accept(ModBlocks.JUNGLE_COUNTER);
                        output.accept(ModBlocks.ACACIA_COUNTER);
                        output.accept(ModBlocks.DARK_OAK_COUNTER);
                        output.accept(ModBlocks.MANGROVE_COUNTER);
                        output.accept(ModBlocks.CHERRY_COUNTER);
                        output.accept(ModBlocks.BAMBOO_COUNTER);
                        output.accept(ModBlocks.CRIMSON_COUNTER);
                        output.accept(ModBlocks.WARPED_COUNTER);

                        // STORAGE COUNTERS
                        output.accept(ModBlocks.OAK_STORAGE_COUNTER);
                        output.accept(ModBlocks.SPRUCE_STORAGE_COUNTER);
                        output.accept(ModBlocks.BIRCH_STORAGE_COUNTER);
                        output.accept(ModBlocks.JUNGLE_STORAGE_COUNTER);
                        output.accept(ModBlocks.ACACIA_STORAGE_COUNTER);
                        output.accept(ModBlocks.DARK_OAK_STORAGE_COUNTER);
                        output.accept(ModBlocks.MANGROVE_STORAGE_COUNTER);
                        output.accept(ModBlocks.CHERRY_STORAGE_COUNTER);
                        output.accept(ModBlocks.BAMBOO_STORAGE_COUNTER);
                        output.accept(ModBlocks.CRIMSON_STORAGE_COUNTER);
                        output.accept(ModBlocks.WARPED_STORAGE_COUNTER);

                        // TABLES
                        output.accept(ModBlocks.OAK_TABLE);
                        output.accept(ModBlocks.SPRUCE_TABLE);
                        output.accept(ModBlocks.BIRCH_TABLE);
                        output.accept(ModBlocks.JUNGLE_TABLE);
                        output.accept(ModBlocks.ACACIA_TABLE);
                        output.accept(ModBlocks.DARK_OAK_TABLE);
                        output.accept(ModBlocks.MANGROVE_TABLE);
                        output.accept(ModBlocks.CHERRY_TABLE);
                        output.accept(ModBlocks.BAMBOO_TABLE);
                        output.accept(ModBlocks.CRIMSON_TABLE);
                        output.accept(ModBlocks.WARPED_TABLE);

                        // SHORT TABLES
                        output.accept(ModBlocks.OAK_SHORT_TABLE);
                        output.accept(ModBlocks.SPRUCE_SHORT_TABLE);
                        output.accept(ModBlocks.BIRCH_SHORT_TABLE);
                        output.accept(ModBlocks.JUNGLE_SHORT_TABLE);
                        output.accept(ModBlocks.ACACIA_SHORT_TABLE);
                        output.accept(ModBlocks.DARK_OAK_SHORT_TABLE);
                        output.accept(ModBlocks.MANGROVE_SHORT_TABLE);
                        output.accept(ModBlocks.CHERRY_SHORT_TABLE);
                        output.accept(ModBlocks.BAMBOO_SHORT_TABLE);
                        output.accept(ModBlocks.CRIMSON_SHORT_TABLE);
                        output.accept(ModBlocks.WARPED_SHORT_TABLE);

                        // CUSHIONS
                        output.accept(ModItems.WHITE_CUSHION);
                        output.accept(ModItems.LIGHT_GRAY_CUSHION);
                        output.accept(ModItems.GRAY_CUSHION);
                        output.accept(ModItems.BLACK_CUSHION);
                        output.accept(ModItems.BROWN_CUSHION);
                        output.accept(ModItems.RED_CUSHION);
                        output.accept(ModItems.ORANGE_CUSHION);
                        output.accept(ModItems.YELLOW_CUSHION);
                        output.accept(ModItems.LIME_CUSHION);
                        output.accept(ModItems.GREEN_CUSHION);
                        output.accept(ModItems.CYAN_CUSHION);
                        output.accept(ModItems.LIGHT_BLUE_CUSHION);
                        output.accept(ModItems.BLUE_CUSHION);
                        output.accept(ModItems.PURPLE_CUSHION);
                        output.accept(ModItems.MAGENTA_CUSHION);
                        output.accept(ModItems.PINK_CUSHION);
                        output.accept(ModItems.HAY_CUSHION);
                        output.accept(ModItems.TRADER_CUSHION);

                        // BEDS
                        output.accept(Blocks.WHITE_BED);
                        output.accept(Blocks.LIGHT_GRAY_BED);
                        output.accept(Blocks.GRAY_BED);
                        output.accept(Blocks.BLACK_BED);
                        output.accept(Blocks.BROWN_BED);
                        output.accept(Blocks.RED_BED);
                        output.accept(Blocks.ORANGE_BED);
                        output.accept(Blocks.YELLOW_BED);
                        output.accept(Blocks.LIME_BED);
                        output.accept(Blocks.GREEN_BED);
                        output.accept(Blocks.CYAN_BED);
                        output.accept(Blocks.LIGHT_BLUE_BED);
                        output.accept(Blocks.BLUE_BED);
                        output.accept(Blocks.PURPLE_BED);
                        output.accept(Blocks.MAGENTA_BED);
                        output.accept(Blocks.PINK_BED);

                        // RAILINGS
                        output.accept(ModBlocks.OAK_RAILING);
                        output.accept(ModBlocks.SPRUCE_RAILING);
                        output.accept(ModBlocks.BIRCH_RAILING);
                        output.accept(ModBlocks.JUNGLE_RAILING);
                        output.accept(ModBlocks.ACACIA_RAILING);
                        output.accept(ModBlocks.DARK_OAK_RAILING);
                        output.accept(ModBlocks.MANGROVE_RAILING);
                        output.accept(ModBlocks.CHERRY_RAILING);
                        output.accept(ModBlocks.BAMBOO_RAILING);
                        output.accept(ModBlocks.CRIMSON_RAILING);
                        output.accept(ModBlocks.WARPED_RAILING);
                    }).build());

//    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
//            event.insertBefore(Items.OAK_PLANKS.getDefaultInstance(), ModBlocks.OAK_PLANKED_WALL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//        }
//    }

    // Helper method to safely add blocks by ResourceLocation
    private static void addBlockByResourceLocation(CreativeModeTab.Output output, String blockId) {
        ResourceLocation location = ResourceLocation.parse(blockId);
        Block block = BuiltInRegistries.BLOCK.get(location);

        // Check if the block exists and isn't the default "air" block
        if (block != Blocks.AIR) {
            output.accept(block.asItem());
        }
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
