package net.luckystudio.cozyhome.neoforge;

import net.luckystudio.cozyhome.CozyHome;
import net.luckystudio.cozyhome.datagen.CozyHomeDataGenerator;
import net.luckystudio.cozyhome.util.ModFlammableBlocks;
import net.luckystudio.cozyhome.util.ModFuels;
import net.luckystudio.cozyhome.util.ModTrades;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.item.Item;
import net.minecraft.village.TradeOffers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

@Mod(CozyHome.MOD_ID)
public class CozyHomeNeoForge {
    private static boolean registered = false;
    private static final Map<Item, Integer> FUELS = new IdentityHashMap<>();

    public CozyHomeNeoForge(IEventBus modBus) {
        modBus.addListener(RegisterEvent.class, CozyHomeNeoForge::onRegister);
        modBus.addListener(FMLCommonSetupEvent.class, CozyHomeNeoForge::onCommonSetup);
        modBus.addListener(GatherDataEvent.class, CozyHomeDataGenerator::gatherData);

        NeoForge.EVENT_BUS.addListener(FurnaceFuelBurnTimeEvent.class, CozyHomeNeoForge::onFuelBurnTime);
        NeoForge.EVENT_BUS.addListener(VillagerTradesEvent.class, CozyHomeNeoForge::onVillagerTrades);
        NeoForge.EVENT_BUS.addListener(WandererTradesEvent.class, CozyHomeNeoForge::onWandererTrades);
    }

    /**
     * The common code registers everything with vanilla's Registry.register in static initializers.
     * NeoForge keeps every built-in registry unfrozen while RegisterEvent is being fired, so we run
     * all of it once, during the first RegisterEvent we receive.
     */
    private static void onRegister(RegisterEvent event) {
        if (registered) {
            return;
        }
        registered = true;
        CozyHome.init();
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModFuels.registerFuels((item, burnTime) -> FUELS.put(item.asItem(), burnTime));

            FireBlock fire = (FireBlock) Blocks.FIRE;
            ModFlammableBlocks.registerFlammables(fire::registerFlammableBlock);
        });
    }

    private static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        Integer burnTime = FUELS.get(event.getItemStack().getItem());
        if (burnTime != null) {
            event.setBurnTime(burnTime);
        }
    }

    private static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == ModTrades.TELESCOPE_PROFESSION) {
            // NeoForge pre-fills a mutable list for every level (1 to 5)
            List<TradeOffers.Factory> trades = event.getTrades().get(ModTrades.TELESCOPE_LEVEL);
            if (trades != null) {
                trades.add(ModTrades.TELESCOPE);
            }
        }
    }

    private static void onWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().add(ModTrades.TRADER_CUSHION);
    }
}
