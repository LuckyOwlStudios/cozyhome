package net.luckystudio.cozyhome.platform.neoforge;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;
import net.neoforged.fml.ModList;

/** NeoForge implementation of {@link net.luckystudio.cozyhome.platform.CozyHomePlatform}. */
public final class CozyHomePlatformImpl {
    private CozyHomePlatformImpl() {}

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static PointOfInterestType registerPointOfInterest(Identifier id, int ticketCount, int searchDistance, Block block) {
        // NeoForge fills its blockstate to POI map from a registry add callback, so a plain register is enough
        return Registry.register(Registries.POINT_OF_INTEREST_TYPE, id,
                new PointOfInterestType(ImmutableSet.copyOf(block.getStateManager().getStates()), ticketCount, searchDistance));
    }
}
