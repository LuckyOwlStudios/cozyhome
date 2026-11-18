package net.luckystudio.cozyhome.recipe;

import net.luckystudio.cozyhome.CozyHome;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {

    public static final RecipeSerializer<FurnitureDyeRecipe> FURNITURE_DYE = Registry.register(
            Registries.RECIPE_SERIALIZER, CozyHome.id("furniture_dye"), new SpecialRecipeSerializer<>(FurnitureDyeRecipe::new));

    public static void registerRecipes() {
        CozyHome.LOGGER.info("Registering Recipes for " + CozyHome.MOD_ID);
    }
}
