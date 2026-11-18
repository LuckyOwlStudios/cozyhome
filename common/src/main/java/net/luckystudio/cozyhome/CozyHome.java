package net.luckystudio.cozyhome;

import net.luckystudio.cozyhome.block.ModBlocks;
import net.luckystudio.cozyhome.block.util.ModBlockEntityTypes;
import net.luckystudio.cozyhome.components.ModDataComponents;
import net.luckystudio.cozyhome.entity.ModEntities;
import net.luckystudio.cozyhome.item.ModItemGroups;
import net.luckystudio.cozyhome.item.ModItems;
import net.luckystudio.cozyhome.recipe.ModRecipes;
import net.luckystudio.cozyhome.screen.ModScreenHandlers;
import net.luckystudio.cozyhome.util.ModSoundEvents;
import net.luckystudio.cozyhome.world.structure.ModStructureProcessors;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loader-independent entry point. Fabric calls {@link #init()} from its ModInitializer,
 * NeoForge calls it while registries are open for registration.
 * <p>
 * Fuels, flammable blocks and trades use loader APIs, so each loader registers them from
 * {@link net.luckystudio.cozyhome.util.ModFuels}, {@link net.luckystudio.cozyhome.util.ModFlammableBlocks}
 * and {@link net.luckystudio.cozyhome.util.ModTrades}.
 */
public class CozyHome {
	public static final String MOD_ID = "cozyhome";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void init() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		ModBlockEntityTypes.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
		ModItemGroups.registerModItemGroups();
		ModSoundEvents.registerSounds();
		ModDataComponents.registerModDataComponents();
		ModRecipes.registerRecipes();
		ModStructureProcessors.registerStructureProcessors();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
