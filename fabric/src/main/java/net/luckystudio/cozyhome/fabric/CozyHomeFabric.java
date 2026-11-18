package net.luckystudio.cozyhome.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.luckystudio.cozyhome.CozyHome;
import net.luckystudio.cozyhome.util.ModFlammableBlocks;
import net.luckystudio.cozyhome.util.ModFuels;
import net.luckystudio.cozyhome.util.ModTrades;
import net.luckystudio.cozyhome.villager.CarpenterTrades;
import net.luckystudio.cozyhome.villager.ModVillagers;

public class CozyHomeFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		CozyHome.init();

		ModFuels.registerFuels(FuelRegistry.INSTANCE::add);
		ModFlammableBlocks.registerFlammables(FlammableBlockRegistry.getDefaultInstance()::add);

		// Registering Villager Trades
		TradeOfferHelper.registerVillagerOffers(ModTrades.TELESCOPE_PROFESSION, ModTrades.TELESCOPE_LEVEL,
				factories -> factories.add(ModTrades.TELESCOPE));

		// Carpenter villager trades, levels 1 to 5
		CarpenterTrades.TRADES.forEach((level, trades) ->
				TradeOfferHelper.registerVillagerOffers(ModVillagers.CARPENTER, level, factories -> factories.addAll(trades)));

		// Wandering trader now sells trader themed furniture
		TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.add(ModTrades.TRADER_CUSHION));
	}
}
