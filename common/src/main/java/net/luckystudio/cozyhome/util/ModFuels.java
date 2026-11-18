package net.luckystudio.cozyhome.util;

import net.luckystudio.cozyhome.block.ModBlocks;
import net.minecraft.item.ItemConvertible;

public class ModFuels {
    /** Implemented by each loader: Fabric uses FuelRegistry, NeoForge uses the furnace fuel burn time event. */
    @FunctionalInterface
    public interface FuelRegistrar {
        void add(ItemConvertible item, int burnTime);
    }

    public static void registerFuels(FuelRegistrar fuels) {
        fuels.add(ModBlocks.OAK_TABLE, 300);
        fuels.add(ModBlocks.SPRUCE_TABLE, 300);
        fuels.add(ModBlocks.BIRCH_TABLE, 300);
        fuels.add(ModBlocks.JUNGLE_TABLE, 300);
        fuels.add(ModBlocks.ACACIA_TABLE, 300);
        fuels.add(ModBlocks.DARK_OAK_TABLE, 300);
        fuels.add(ModBlocks.MANGROVE_TABLE, 300);
        fuels.add(ModBlocks.CHERRY_TABLE, 300);
        fuels.add(ModBlocks.BAMBOO_TABLE, 300);

        fuels.add(ModBlocks.OAK_CHAIR, 150);
        fuels.add(ModBlocks.SPRUCE_CHAIR, 150);
        fuels.add(ModBlocks.BIRCH_CHAIR, 150);
        fuels.add(ModBlocks.JUNGLE_CHAIR, 150);
        fuels.add(ModBlocks.ACACIA_CHAIR, 150);
        fuels.add(ModBlocks.DARK_OAK_CHAIR, 150);
        fuels.add(ModBlocks.MANGROVE_CHAIR, 150);
        fuels.add(ModBlocks.CHERRY_CHAIR, 150);
        fuels.add(ModBlocks.BAMBOO_CHAIR, 150);

        fuels.add(ModBlocks.OAK_WALL_CLOCK, 60);
        fuels.add(ModBlocks.SPRUCE_WALL_CLOCK, 60);
        fuels.add(ModBlocks.BIRCH_WALL_CLOCK, 60);
        fuels.add(ModBlocks.JUNGLE_WALL_CLOCK, 60);
        fuels.add(ModBlocks.ACACIA_WALL_CLOCK, 60);
        fuels.add(ModBlocks.DARK_OAK_WALL_CLOCK, 60);
        fuels.add(ModBlocks.MANGROVE_WALL_CLOCK, 60);
        fuels.add(ModBlocks.CHERRY_WALL_CLOCK, 60);
        fuels.add(ModBlocks.BAMBOO_WALL_CLOCK, 60);

        fuels.add(ModBlocks.OAK_GRANDFATHER_CLOCK, 900);
        fuels.add(ModBlocks.SPRUCE_GRANDFATHER_CLOCK, 900);
        fuels.add(ModBlocks.BIRCH_GRANDFATHER_CLOCK, 900);
        fuels.add(ModBlocks.JUNGLE_GRANDFATHER_CLOCK, 900);
        fuels.add(ModBlocks.ACACIA_GRANDFATHER_CLOCK, 900);
        fuels.add(ModBlocks.DARK_OAK_GRANDFATHER_CLOCK, 900);
        fuels.add(ModBlocks.MANGROVE_GRANDFATHER_CLOCK, 900);
        fuels.add(ModBlocks.CHERRY_GRANDFATHER_CLOCK, 900);
        fuels.add(ModBlocks.BAMBOO_GRANDFATHER_CLOCK, 900);

        fuels.add(ModBlocks.OAK_SOFA, 600);
        fuels.add(ModBlocks.SPRUCE_SOFA, 600);
        fuels.add(ModBlocks.BIRCH_SOFA, 600);
        fuels.add(ModBlocks.JUNGLE_SOFA, 600);
        fuels.add(ModBlocks.ACACIA_SOFA, 600);
        fuels.add(ModBlocks.DARK_OAK_SOFA, 600);
        fuels.add(ModBlocks.MANGROVE_SOFA, 600);
        fuels.add(ModBlocks.CHERRY_SOFA, 600);
        fuels.add(ModBlocks.BAMBOO_SOFA, 600);

        fuels.add(ModBlocks.OAK_DESK, 300);
        fuels.add(ModBlocks.SPRUCE_DESK, 300);
        fuels.add(ModBlocks.BIRCH_DESK, 300);
        fuels.add(ModBlocks.JUNGLE_DESK, 300);
        fuels.add(ModBlocks.ACACIA_DESK, 300);
        fuels.add(ModBlocks.DARK_OAK_DESK, 300);
        fuels.add(ModBlocks.MANGROVE_DESK, 300);
        fuels.add(ModBlocks.CHERRY_DESK, 300);
        fuels.add(ModBlocks.BAMBOO_DESK, 300);

        fuels.add(ModBlocks.OAK_DRAWER, 300);
        fuels.add(ModBlocks.SPRUCE_DRAWER, 300);
        fuels.add(ModBlocks.BIRCH_DRAWER, 300);
        fuels.add(ModBlocks.JUNGLE_DRAWER, 300);
        fuels.add(ModBlocks.ACACIA_DRAWER, 300);
        fuels.add(ModBlocks.DARK_OAK_DRAWER, 300);
        fuels.add(ModBlocks.MANGROVE_DRAWER, 300);
        fuels.add(ModBlocks.CHERRY_DRAWER, 300);
        fuels.add(ModBlocks.BAMBOO_DRAWER, 300);

        fuels.add(ModBlocks.OAK_WALL_MIRROR, 100);
        fuels.add(ModBlocks.SPRUCE_WALL_MIRROR, 100);
        fuels.add(ModBlocks.BIRCH_WALL_MIRROR, 100);
        fuels.add(ModBlocks.JUNGLE_WALL_MIRROR, 100);
        fuels.add(ModBlocks.ACACIA_WALL_MIRROR, 100);
        fuels.add(ModBlocks.DARK_OAK_WALL_MIRROR, 100);
        fuels.add(ModBlocks.MANGROVE_WALL_MIRROR, 100);
        fuels.add(ModBlocks.CHERRY_WALL_MIRROR, 100);
        fuels.add(ModBlocks.BAMBOO_WALL_MIRROR, 100);

        fuels.add(ModBlocks.OAK_LARGE_STUMP, 100);
        fuels.add(ModBlocks.SPRUCE_LARGE_STUMP, 100);
        fuels.add(ModBlocks.BIRCH_LARGE_STUMP, 100);
        fuels.add(ModBlocks.JUNGLE_LARGE_STUMP, 100);
        fuels.add(ModBlocks.ACACIA_LARGE_STUMP, 100);
        fuels.add(ModBlocks.DARK_OAK_LARGE_STUMP, 100);
        fuels.add(ModBlocks.MANGROVE_LARGE_STUMP, 100);
        fuels.add(ModBlocks.CHERRY_LARGE_STUMP, 100);
        fuels.add(ModBlocks.BAMBOO_LARGE_STUMP, 100);
    }
}
