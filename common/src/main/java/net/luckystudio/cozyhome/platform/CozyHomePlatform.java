package net.luckystudio.cozyhome.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

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
}
