package net.luckystudio.cozyhome.platform.fabric;

import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;

/** Fabric implementation of {@link net.luckystudio.cozyhome.platform.CozyHomePlatform}. */
public final class CozyHomePlatformImpl {
    private CozyHomePlatformImpl() {}

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static PointOfInterestType registerPointOfInterest(Identifier id, int ticketCount, int searchDistance, Block block) {
        // Fabric API also adds the block's states to vanilla's blockstate to POI map
        return PointOfInterestHelper.register(id, ticketCount, searchDistance, block);
    }
}
