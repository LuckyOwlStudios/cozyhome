package net.luckystudio.cozyhome.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;

/**
 * Small bridge to loader-specific behaviour.
 * <p>
 * Each method is implemented in {@code net.luckystudio.cozyhome.platform.fabric.CozyHomePlatformImpl}
 * and {@code net.luckystudio.cozyhome.platform.neoforge.CozyHomePlatformImpl}; Architectury wires
 * the right one in at build time.
 */
public final class CozyHomePlatform {
    private CozyHomePlatform() {}

    @ExpectPlatform
    public static boolean isModLoaded(String modId) {
        throw new AssertionError();
    }

    /**
     * Registers a point of interest type for every state of {@code block}. Vanilla keeps a separate
     * blockstate to POI map that each loader fills in its own way, so this can't use Registry.register directly.
     */
    @ExpectPlatform
    public static PointOfInterestType registerPointOfInterest(Identifier id, int ticketCount, int searchDistance, Block block) {
        throw new AssertionError();
    }
}
