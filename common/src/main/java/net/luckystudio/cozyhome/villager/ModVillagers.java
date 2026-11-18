package net.luckystudio.cozyhome.villager;

import com.google.common.collect.ImmutableSet;
import net.luckystudio.cozyhome.CozyHome;
import net.luckystudio.cozyhome.block.ModBlocks;
import net.luckystudio.cozyhome.platform.CozyHomePlatform;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

/**
 * Cozy Home villager professions.
 * <p>
 * The Carpenter works at a {@link ModBlocks#CARPENTRY_TABLE}. It buys wooden furniture made from its
 * village's wood and sells wood that is hard to get nearby, plus the rare undead and ominous furniture.
 * Its trades live in {@link CarpenterTrades} and each loader adds them through its own trade API.
 * <p>
 * Villagers only claim job sites listed in the {@code minecraft:acquirable_job_site} POI tag, so the
 * carpenter POI is also added to that tag in {@code data/minecraft/tags/point_of_interest_type}.
 */
public final class ModVillagers {
    private ModVillagers() {}

    public static final RegistryKey<PointOfInterestType> CARPENTER_POI_KEY =
            RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, CozyHome.id("carpenter"));

    public static final PointOfInterestType CARPENTER_POI =
            CozyHomePlatform.registerPointOfInterest(CARPENTER_POI_KEY.getValue(), 1, 1, ModBlocks.CARPENTRY_TABLE);

    public static final VillagerProfession CARPENTER = Registry.register(Registries.VILLAGER_PROFESSION, CozyHome.id("carpenter"),
            new VillagerProfession(
                    "carpenter",
                    entry -> entry.matchesKey(CARPENTER_POI_KEY),
                    entry -> entry.matchesKey(CARPENTER_POI_KEY),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.ENTITY_VILLAGER_WORK_FLETCHER));

    public static void registerVillagers() {
        CozyHome.LOGGER.info("Registering villager professions for " + CozyHome.MOD_ID);
    }
}
