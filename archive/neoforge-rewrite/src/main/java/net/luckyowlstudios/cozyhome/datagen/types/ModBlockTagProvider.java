package net.luckyowlstudios.cozyhome.datagen.types;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Cozyhome.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.OAK_COUNTER.get())
                .add(ModBlocks.SPRUCE_COUNTER.get())
                .add(ModBlocks.BIRCH_COUNTER.get())
                .add(ModBlocks.JUNGLE_COUNTER.get())
                .add(ModBlocks.ACACIA_COUNTER.get())
                .add(ModBlocks.DARK_OAK_COUNTER.get())
                .add(ModBlocks.MANGROVE_COUNTER.get())
                .add(ModBlocks.CHERRY_COUNTER.get())
                .add(ModBlocks.BAMBOO_COUNTER.get())
                .add(ModBlocks.CRIMSON_COUNTER.get())
                .add(ModBlocks.WARPED_COUNTER.get())

                .add(ModBlocks.OAK_STORAGE_COUNTER.get())
                .add(ModBlocks.SPRUCE_STORAGE_COUNTER.get())
                .add(ModBlocks.BIRCH_STORAGE_COUNTER.get())
                .add(ModBlocks.JUNGLE_STORAGE_COUNTER.get())
                .add(ModBlocks.ACACIA_STORAGE_COUNTER.get())
                .add(ModBlocks.DARK_OAK_STORAGE_COUNTER.get())
                .add(ModBlocks.MANGROVE_STORAGE_COUNTER.get())
                .add(ModBlocks.CHERRY_STORAGE_COUNTER.get())
                .add(ModBlocks.BAMBOO_STORAGE_COUNTER.get())
                .add(ModBlocks.CRIMSON_STORAGE_COUNTER.get())
                .add(ModBlocks.WARPED_STORAGE_COUNTER.get())

                .add(ModBlocks.OAK_SINK_COUNTER.get())
                .add(ModBlocks.SPRUCE_SINK_COUNTER.get())
                .add(ModBlocks.BIRCH_SINK_COUNTER.get())
                .add(ModBlocks.JUNGLE_SINK_COUNTER.get())
                .add(ModBlocks.ACACIA_SINK_COUNTER.get())
                .add(ModBlocks.DARK_OAK_SINK_COUNTER.get())
                .add(ModBlocks.MANGROVE_SINK_COUNTER.get())
                .add(ModBlocks.CHERRY_SINK_COUNTER.get())
                .add(ModBlocks.BAMBOO_SINK_COUNTER.get())
                .add(ModBlocks.CRIMSON_SINK_COUNTER.get())
                .add(ModBlocks.WARPED_SINK_COUNTER.get())
        ;

        tag(BlockTags.MINEABLE_WITH_AXE)

                .add(ModBlocks.OAK_PLANKED_WALL.get())
                .add(ModBlocks.SPRUCE_PLANKED_WALL.get())
                .add(ModBlocks.BIRCH_PLANKED_WALL.get())
                .add(ModBlocks.JUNGLE_PLANKED_WALL.get())
                .add(ModBlocks.ACACIA_PLANKED_WALL.get())
                .add(ModBlocks.DARK_OAK_PLANKED_WALL.get())
                .add(ModBlocks.MANGROVE_PLANKED_WALL.get())
                .add(ModBlocks.CHERRY_PLANKED_WALL.get())
                .add(ModBlocks.BAMBOO_PLANKED_WALL.get())
                .add(ModBlocks.CRIMSON_PLANKED_WALL.get())
                .add(ModBlocks.WARPED_PLANKED_WALL.get())

                .add(ModBlocks.OAK_COUNTER.get())
                .add(ModBlocks.SPRUCE_COUNTER.get())
                .add(ModBlocks.BIRCH_COUNTER.get())
                .add(ModBlocks.JUNGLE_COUNTER.get())
                .add(ModBlocks.ACACIA_COUNTER.get())
                .add(ModBlocks.DARK_OAK_COUNTER.get())
                .add(ModBlocks.MANGROVE_COUNTER.get())
                .add(ModBlocks.CHERRY_COUNTER.get())
                .add(ModBlocks.BAMBOO_COUNTER.get())
                .add(ModBlocks.CRIMSON_COUNTER.get())
                .add(ModBlocks.WARPED_COUNTER.get())

                .add(ModBlocks.OAK_STORAGE_COUNTER.get())
                .add(ModBlocks.SPRUCE_STORAGE_COUNTER.get())
                .add(ModBlocks.BIRCH_STORAGE_COUNTER.get())
                .add(ModBlocks.JUNGLE_STORAGE_COUNTER.get())
                .add(ModBlocks.ACACIA_STORAGE_COUNTER.get())
                .add(ModBlocks.DARK_OAK_STORAGE_COUNTER.get())
                .add(ModBlocks.MANGROVE_STORAGE_COUNTER.get())
                .add(ModBlocks.CHERRY_STORAGE_COUNTER.get())
                .add(ModBlocks.BAMBOO_STORAGE_COUNTER.get())
                .add(ModBlocks.CRIMSON_STORAGE_COUNTER.get())
                .add(ModBlocks.WARPED_STORAGE_COUNTER.get())

                .add(ModBlocks.OAK_SINK_COUNTER.get())
                .add(ModBlocks.SPRUCE_SINK_COUNTER.get())
                .add(ModBlocks.BIRCH_SINK_COUNTER.get())
                .add(ModBlocks.JUNGLE_SINK_COUNTER.get())
                .add(ModBlocks.ACACIA_SINK_COUNTER.get())
                .add(ModBlocks.DARK_OAK_SINK_COUNTER.get())
                .add(ModBlocks.MANGROVE_SINK_COUNTER.get())
                .add(ModBlocks.CHERRY_SINK_COUNTER.get())
                .add(ModBlocks.BAMBOO_SINK_COUNTER.get())
                .add(ModBlocks.CRIMSON_SINK_COUNTER.get())
                .add(ModBlocks.WARPED_SINK_COUNTER.get())

                .add(ModBlocks.OAK_RAILING.get())
                .add(ModBlocks.SPRUCE_RAILING.get())
                .add(ModBlocks.BIRCH_RAILING.get())
                .add(ModBlocks.JUNGLE_RAILING.get())
                .add(ModBlocks.ACACIA_RAILING.get())
                .add(ModBlocks.DARK_OAK_RAILING.get())
                .add(ModBlocks.MANGROVE_RAILING.get())
                .add(ModBlocks.CHERRY_RAILING.get())
                .add(ModBlocks.BAMBOO_RAILING.get())
                .add(ModBlocks.CRIMSON_RAILING.get())
                .add(ModBlocks.WARPED_RAILING.get())
        ;
    }
}
