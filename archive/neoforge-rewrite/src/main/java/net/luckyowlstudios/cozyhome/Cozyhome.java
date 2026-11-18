package net.luckyowlstudios.cozyhome;

import net.luckyowlstudios.cozyhome.init.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.FireBlock;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// TEAL IS MADE FROM MOVING THE HUE TO 96 ON ANY GREEN TEXTURE!!!
// CERISE IS MADE FROM MOVING THE HUE -32 ON ANY RED TEXTURE!!!
// NAVY IS MADE FROM MOVING THE HUE 32 ON ANY TEAL TEXTURE!!!

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Cozyhome.MOD_ID)
public class Cozyhome {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "cozyhome";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Cozyhome(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Cozyhome) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModMenuTypes.register(modEventBus);
//        ModSoundEvents.register(modEventBus);
//        ModVillagerProfessions.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(this::registerFlammables);
    }

    private void registerFlammables() {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;

        // Register your wooden blocks as flammable
        // Parameters: (block, encouragement, flammability)
        // encouragement = how easily fire spreads to this block (higher = spreads easier)
        // flammability = how quickly the block burns (higher = burns faster)

        // PLANKED WALLS
        fireBlock.setFlammable(ModBlocks.OAK_PLANKED_WALL.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.SPRUCE_PLANKED_WALL.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.BIRCH_PLANKED_WALL.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.JUNGLE_PLANKED_WALL.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.ACACIA_PLANKED_WALL.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.DARK_OAK_PLANKED_WALL.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.MANGROVE_PLANKED_WALL.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.CHERRY_PLANKED_WALL.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.BAMBOO_PLANKED_WALL.get(), 5, 20);

        // COUNTERS
        fireBlock.setFlammable(ModBlocks.OAK_COUNTER.get(), 5, 10);
        fireBlock.setFlammable(ModBlocks.SPRUCE_COUNTER.get(), 5, 10);
        fireBlock.setFlammable(ModBlocks.BIRCH_COUNTER.get(), 5, 10);
        fireBlock.setFlammable(ModBlocks.JUNGLE_COUNTER.get(), 5, 10);
        fireBlock.setFlammable(ModBlocks.ACACIA_COUNTER.get(), 5, 10);
        fireBlock.setFlammable(ModBlocks.DARK_OAK_COUNTER.get(), 5, 10);
        fireBlock.setFlammable(ModBlocks.MANGROVE_COUNTER.get(), 5, 10);
        fireBlock.setFlammable(ModBlocks.CHERRY_COUNTER.get(), 5, 10);
        fireBlock.setFlammable(ModBlocks.BAMBOO_COUNTER.get(), 5, 10);

        // RAILINGS
        fireBlock.setFlammable(ModBlocks.OAK_RAILING.get(), 5, 40);
        fireBlock.setFlammable(ModBlocks.SPRUCE_RAILING.get(), 5, 40);
        fireBlock.setFlammable(ModBlocks.BIRCH_RAILING.get(), 5, 40);
        fireBlock.setFlammable(ModBlocks.JUNGLE_RAILING.get(), 5, 40);
        fireBlock.setFlammable(ModBlocks.ACACIA_RAILING.get(), 5, 40);
        fireBlock.setFlammable(ModBlocks.DARK_OAK_RAILING.get(), 5, 40);
        fireBlock.setFlammable(ModBlocks.MANGROVE_RAILING.get(), 5, 40);
        fireBlock.setFlammable(ModBlocks.CHERRY_RAILING.get(), 5, 40);
        fireBlock.setFlammable(ModBlocks.BAMBOO_RAILING.get(), 5, 40);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
