package net.luckyowlstudios.cozyhome.datagen;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.datagen.types.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Cozyhome.MOD_ID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> baseLookupProvider = event.getLookupProvider();

        // ✅ Register ModDataPackProvider FIRST
        ModDataPackProvider datapackProvider = new ModDataPackProvider(packOutput, baseLookupProvider);
        generator.addProvider(event.includeServer(), datapackProvider);

        // ✅ Then use its enriched provider for tag-related things
        CompletableFuture<HolderLookup.Provider> enrichedLookupProvider = datapackProvider.getRegistryProvider();

        generator.addProvider(event.includeServer(), new ModDamageTypeTagsProvider(packOutput, enrichedLookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModDataMapProvider(packOutput, enrichedLookupProvider));

        // 🎯 ADD YOUR BANNER PATTERN TAG PROVIDER HERE:
        generator.addProvider(event.includeServer(), new ModBannerPatternTagsProvider(packOutput, enrichedLookupProvider, existingFileHelper));

        // All your other providers
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), baseLookupProvider));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, baseLookupProvider));
        BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(packOutput, baseLookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, baseLookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(packOutput, baseLookupProvider, Cozyhome.MOD_ID, existingFileHelper));

        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
    }
}
