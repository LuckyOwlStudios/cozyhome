package net.luckystudio.cozyhome.villager;

import net.luckystudio.cozyhome.CozyHome;
import net.luckystudio.cozyhome.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Trades for the Carpenter villager, by level (1 novice to 5 master).
 * <p>
 * The Carpenter <b>buys</b> wooden furniture made from its own village's wood (oak in plains, spruce in
 * taiga and snowy villages, and so on), which turns spare logs into emeralds with a crafting step in between.
 * It <b>sells</b> what a village can't easily give you: logs and stems from far away biomes, the paint brush,
 * and at the top levels the undead and ominous furniture, which has no crafting recipe.
 * <p>
 * Like every vanilla profession, a villager picks two trades from each level's list.
 */
public final class CarpenterTrades {
    private CarpenterTrades() {}

    // Standard vanilla price multipliers: 0.05 for everyday trades, 0.2 for valuable ones
    private static final float LOW = 0.05f;
    private static final float HIGH = 0.2f;

    /** Each villager type's home wood. Types from other mods fall back to oak. */
    private static final Map<VillagerType, String> HOME_WOOD = Map.of(
            VillagerType.PLAINS, "oak",
            VillagerType.TAIGA, "spruce",
            VillagerType.SNOW, "spruce",
            VillagerType.DESERT, "birch",
            VillagerType.SAVANNA, "acacia",
            VillagerType.JUNGLE, "jungle",
            VillagerType.SWAMP, "mangrove");

    /** Overworld woods the carpenter can sell logs of (bamboo is sold as blocks of bamboo). */
    private static final List<String> OVERWORLD_WOODS = List.of(
            "oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo");

    /** Furniture with no crafting recipe, sold at expert and master level. */
    private static final List<String> RARE_FURNITURE = List.of(
            "chair", "table", "lamp", "wall_clock", "grandfather_clock");

    public static final Map<Integer, List<TradeOffers.Factory>> TRADES = Map.of(
            1, List.of(
                    new BuyFurniture("chair", 6, 16, 2),
                    new BuyFurniture("table", 6, 16, 2),
                    sell(ModItems.PAINT_BRUSH, 1, 3, 12, 1, LOW)),
            2, List.of(
                    new BuyFurniture("desk", 4, 12, 10),
                    new BuyFurniture("large_stump", 4, 12, 10),
                    new SellForeignLogs(8, 2, 12, 5)),
            3, List.of(
                    new BuyFurniture("drawer", 2, 12, 20),
                    new SellNetherStems(8, 4, 12, 10)),
            4, List.of(
                    new BuyFurniture("grandfather_clock", 1, 12, 30),
                    new SellRareFurniture("undead", 12, 3, 15)),
            5, List.of(
                    new SellRareFurniture("ominous", 20, 3, 30),
                    new SellRareFurniture("undead", 12, 3, 30)));

    /** The wood this villager's village is built from. */
    public static String homeWood(Entity entity) {
        if (entity instanceof VillagerDataContainer villager) {
            return HOME_WOOD.getOrDefault(villager.getVillagerData().getType(), "oak");
        }
        return "oak";
    }

    private static Item cozyItem(String path) {
        return Registries.ITEM.get(CozyHome.id(path));
    }

    private static TradeOffers.Factory sell(Item item, int count, int emeralds, int maxUses, int experience, float multiplier) {
        return (entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, emeralds), new ItemStack(item, count), maxUses, experience, multiplier);
    }

    /** Buys {@code count} pieces of furniture made from the villager's home wood for one emerald. */
    record BuyFurniture(String furniture, int count, int maxUses, int experience) implements TradeOffers.Factory {
        @Override
        public @Nullable TradeOffer create(Entity entity, Random random) {
            Item item = cozyItem(homeWood(entity) + "_" + furniture);
            if (item == Items.AIR) {
                return null;
            }
            return new TradeOffer(new TradedItem(item, count), new ItemStack(Items.EMERALD), maxUses, experience, LOW);
        }
    }

    /** Sells logs of an overworld wood that isn't this village's own, e.g. cherry logs in a plains village. */
    record SellForeignLogs(int count, int emeralds, int maxUses, int experience) implements TradeOffers.Factory {
        @Override
        public TradeOffer create(Entity entity, Random random) {
            String home = homeWood(entity);
            List<String> woods = OVERWORLD_WOODS.stream().filter(wood -> !wood.equals(home)).toList();
            String wood = woods.get(random.nextInt(woods.size()));
            Item log = Registries.ITEM.get(Identifier.ofVanilla(wood.equals("bamboo") ? "bamboo_block" : wood + "_log"));
            return new TradeOffer(new TradedItem(Items.EMERALD, emeralds), new ItemStack(log, count), maxUses, experience, LOW);
        }
    }

    /** Sells crimson or warped stems, so nether wood furniture doesn't have to wait for the Nether. */
    record SellNetherStems(int count, int emeralds, int maxUses, int experience) implements TradeOffers.Factory {
        @Override
        public TradeOffer create(Entity entity, Random random) {
            Item stem = random.nextBoolean() ? Items.CRIMSON_STEM : Items.WARPED_STEM;
            return new TradeOffer(new TradedItem(Items.EMERALD, emeralds), new ItemStack(stem, count), maxUses, experience, LOW);
        }
    }

    /** Sells one random piece of undead or ominous furniture, which can't be crafted. */
    record SellRareFurniture(String style, int emeralds, int maxUses, int experience) implements TradeOffers.Factory {
        @Override
        public @Nullable TradeOffer create(Entity entity, Random random) {
            String furniture = RARE_FURNITURE.get(random.nextInt(RARE_FURNITURE.size()));
            Item item = cozyItem(style + "_" + furniture);
            if (item == Items.AIR) {
                return null;
            }
            return new TradeOffer(new TradedItem(Items.EMERALD, emeralds), new ItemStack(item), maxUses, experience, HIGH);
        }
    }
}
