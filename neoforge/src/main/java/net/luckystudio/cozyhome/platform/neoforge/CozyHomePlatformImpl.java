package net.luckystudio.cozyhome.platform.neoforge;

import net.neoforged.fml.ModList;

/** NeoForge implementation of {@link net.luckystudio.cozyhome.platform.CozyHomePlatform}. */
public final class CozyHomePlatformImpl {
    private CozyHomePlatformImpl() {}

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
