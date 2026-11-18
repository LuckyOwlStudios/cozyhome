package net.luckystudio.cozyhome.util;

import net.luckystudio.cozyhome.block.ModBlocks;
import net.luckystudio.cozyhome.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

/**
 * Villager and wandering trader trades. Each loader adds these through its own trade API.
 */
public class ModTrades {
    /** Cartographers sell a telescope at this level (master). */
    public static final VillagerProfession TELESCOPE_PROFESSION = VillagerProfession.CARTOGRAPHER;
    public static final int TELESCOPE_LEVEL = 5;

    public static final TradeOffers.Factory TELESCOPE = (entity, random) -> new TradeOffer(
            new TradedItem(Items.EMERALD, 26),
            new ItemStack(ModBlocks.TELESCOPE, 1),
            2,
            5,
            0.5f);

    /** Wandering trader now sells trader themed furniture (common trade pool). */
    public static final TradeOffers.Factory TRADER_CUSHION = (entity, random) -> new TradeOffer(
            new TradedItem(Items.EMERALD, 6),
            new ItemStack(ModItems.TRADER_CUSHION, 1),
            8,
            5,
            0.5f);
}
