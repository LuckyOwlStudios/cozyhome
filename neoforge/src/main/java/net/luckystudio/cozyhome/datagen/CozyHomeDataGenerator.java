package net.luckystudio.cozyhome.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.loottable.LootTableProvider;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.RegistryWrapper;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * NeoForge data generation. Run with {@code ./gradlew :neoforge:runData}; output goes to
 * {@code common/src/main/generated}, which both the Fabric and NeoForge jars ship.
 */
public class CozyHomeDataGenerator {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        DataOutput output = generator.getPackOutput();
        CompletableFuture<RegistryWrapper.WrapperLookup> lookup = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // Assets
        generator.addProvider(event.includeClient(), new ModModelProvider(output));

        // Data
        ModBlockTagProvider blockTags = generator.addProvider(event.includeServer(),
                new ModBlockTagProvider(output, lookup, existingFileHelper));
        generator.addProvider(event.includeServer(),
                new ModItemTagProvider(output, lookup, blockTags.getTagLookupFuture(), existingFileHelper));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(),
                List.of(new LootTableProvider.LootTypeGenerator(ModLootTableProvider::new, LootContextTypes.BLOCK)), lookup));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, lookup));
    }
}
