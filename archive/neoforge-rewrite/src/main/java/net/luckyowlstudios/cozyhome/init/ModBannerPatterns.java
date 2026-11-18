package net.luckyowlstudios.cozyhome.init;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BannerPattern;

public class ModBannerPatterns {

    public static final ResourceKey<BannerPattern> HOME = ResourceKey.create(Registries.BANNER_PATTERN, ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "home"));

    public static void bootstrap(BootstrapContext<BannerPattern> context) {
        // Register the banner pattern
        // The string parameter is the pattern name that will be used in the texture files
        context.register(HOME, new BannerPattern(Cozyhome.id("home"), "block.cozyhome.banner." + HOME.location().toShortLanguageKey()));
    }
}
