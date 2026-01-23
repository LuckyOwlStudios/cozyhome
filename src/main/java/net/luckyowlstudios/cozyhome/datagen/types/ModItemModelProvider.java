package net.luckyowlstudios.cozyhome.datagen.types;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Cozyhome.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(ModItems.FORT_BANNER_PATTERN.get());

        // Generate cushion item model using your template
        this.cushionModel(ModItems.WHITE_CUSHION.get(), mcLoc("block/white_wool"));
        this.cushionModel(ModItems.LIGHT_GRAY_CUSHION.get(), mcLoc("block/light_gray_wool"));
        this.cushionModel(ModItems.GRAY_CUSHION.get(), mcLoc("block/gray_wool"));
        this.cushionModel(ModItems.BLACK_CUSHION.get(), mcLoc("block/black_wool"));
        this.cushionModel(ModItems.BROWN_CUSHION.get(), mcLoc("block/brown_wool"));
        this.cushionModel(ModItems.RED_CUSHION.get(), mcLoc("block/red_wool"));
        this.cushionModel(ModItems.ORANGE_CUSHION.get(), mcLoc("block/orange_wool"));
        this.cushionModel(ModItems.YELLOW_CUSHION.get(), mcLoc("block/yellow_wool"));
        this.cushionModel(ModItems.LIME_CUSHION.get(), mcLoc("block/lime_wool"));
        this.cushionModel(ModItems.GREEN_CUSHION.get(), mcLoc("block/green_wool"));
        this.cushionModel(ModItems.CYAN_CUSHION.get(), mcLoc("block/cyan_wool"));
        this.cushionModel(ModItems.LIGHT_BLUE_CUSHION.get(), mcLoc("block/light_blue_wool"));
        this.cushionModel(ModItems.BLUE_CUSHION.get(), mcLoc("block/blue_wool"));
        this.cushionModel(ModItems.PURPLE_CUSHION.get(), mcLoc("block/purple_wool"));
        this.cushionModel(ModItems.MAGENTA_CUSHION.get(), mcLoc("block/magenta_wool"));
        this.cushionModel(ModItems.PINK_CUSHION.get(), mcLoc("block/pink_wool"));

        this.cushionModel(ModItems.HAY_CUSHION.get(), mcLoc("block/hay_block_side"));
        this.cushionModel(ModItems.TRADER_CUSHION.get(), mcLoc("block/hay_block_side"));
    }

    private void cushionModel(Item item, ResourceLocation particle) {
        this.withExistingParent(item.toString(),
                        modLoc("item/template_cushion"))
                .texture("particle", particle);
    }
}
