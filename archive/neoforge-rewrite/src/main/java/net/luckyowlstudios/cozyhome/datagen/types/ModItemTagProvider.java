package net.luckyowlstudios.cozyhome.datagen.types;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Cozyhome.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.DYED)
                .add(
                        ModItems.WHITE_CUSHION.get(),
                        ModItems.LIGHT_GRAY_CUSHION.get(),
                        ModItems.GRAY_CUSHION.get(),
                        ModItems.BLACK_CUSHION.get(),
                        ModItems.BROWN_CUSHION.get(),
                        ModItems.RED_CUSHION.get(),
                        ModItems.ORANGE_CUSHION.get(),
                        ModItems.YELLOW_CUSHION.get(),
                        ModItems.LIME_CUSHION.get(),
                        ModItems.GREEN_CUSHION.get(),
                        ModItems.CYAN_CUSHION.get(),
                        ModItems.LIGHT_BLUE_CUSHION.get(),
                        ModItems.BLUE_CUSHION.get(),
                        ModItems.PURPLE_CUSHION.get(),
                        ModItems.MAGENTA_CUSHION.get(),
                        ModItems.PINK_CUSHION.get()
                );

        tag(Tags.Items.DYED_WHITE)
                .add(ModItems.WHITE_CUSHION.get());

        tag(Tags.Items.DYED_LIGHT_GRAY)
                .add(ModItems.LIGHT_GRAY_CUSHION.get());

        tag(Tags.Items.DYED_GRAY)
                .add(ModItems.GRAY_CUSHION.get());

        tag(Tags.Items.DYED_BLACK)
                .add(ModItems.BLACK_CUSHION.get());

        tag(Tags.Items.DYED_BROWN)
                .add(ModItems.BROWN_CUSHION.get());

        tag(Tags.Items.DYED_RED)
                .add(ModItems.RED_CUSHION.get());

        tag(Tags.Items.DYED_ORANGE)
                .add(ModItems.ORANGE_CUSHION.get());

        tag(Tags.Items.DYED_YELLOW)
                .add(ModItems.YELLOW_CUSHION.get());

        tag(Tags.Items.DYED_LIME)
                .add(ModItems.LIME_CUSHION.get());

        tag(Tags.Items.DYED_GREEN)
                .add(ModItems.GREEN_CUSHION.get());

        tag(Tags.Items.DYED_CYAN)
                .add(ModItems.CYAN_CUSHION.get());

        tag(Tags.Items.DYED_LIGHT_BLUE)
                .add(ModItems.LIGHT_BLUE_CUSHION.get());

        tag(Tags.Items.DYED_BLUE)
                .add(ModItems.BLUE_CUSHION.get());

        tag(Tags.Items.DYED_PURPLE)
                .add(ModItems.PURPLE_CUSHION.get());

        tag(Tags.Items.DYED_MAGENTA)
                .add(ModItems.MAGENTA_CUSHION.get());

        tag(Tags.Items.DYED_PINK)
                .add(ModItems.PINK_CUSHION.get());
    }
}
