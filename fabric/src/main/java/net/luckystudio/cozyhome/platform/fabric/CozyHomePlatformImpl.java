package net.luckystudio.cozyhome.platform.fabric;

import net.fabricmc.loader.api.FabricLoader;

/** Fabric implementation of {@link net.luckystudio.cozyhome.platform.CozyHomePlatform}. */
public final class CozyHomePlatformImpl {
    private CozyHomePlatformImpl() {}

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
