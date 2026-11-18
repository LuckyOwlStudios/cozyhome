package net.luckystudio.cozyhome.recipe;

import net.luckystudio.cozyhome.util.ModColorHandler;
import net.luckystudio.cozyhome.util.ModTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * One piece of dyeable furniture (item tag cozyhome:dyeable_furniture) plus exactly one dye gives that
 * furniture in exactly the dye's color. This replaces vanilla's armor dye recipe, which blended colors.
 */
public class FurnitureDyeRecipe extends SpecialCraftingRecipe {

    public FurnitureDyeRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return this.findIngredients(input) != null;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        Ingredients ingredients = this.findIngredients(input);
        if (ingredients == null) {
            return ItemStack.EMPTY;
        }
        ItemStack result = ingredients.furniture().copyWithCount(1);
        result.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(ModColorHandler.getColor(ingredients.dye()), false));
        return result;
    }

    private @Nullable Ingredients findIngredients(CraftingRecipeInput input) {
        ItemStack furniture = ItemStack.EMPTY;
        DyeColor dye = null;
        for (int i = 0; i < input.getSize(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.isIn(ModTags.Items.DYEABLE_FURNITURE)) {
                if (!furniture.isEmpty()) {
                    return null;
                }
                furniture = stack;
            } else if (stack.getItem() instanceof DyeItem dyeItem) {
                if (dye != null) {
                    return null;
                }
                dye = dyeItem.getColor();
            } else {
                return null;
            }
        }
        // Dyeing something the color it already is would only waste the dye
        if (furniture.isEmpty() || dye == null || ModColorHandler.getDyeColor(furniture) == dye) {
            return null;
        }
        return new Ingredients(furniture, dye);
    }

    private record Ingredients(ItemStack furniture, DyeColor dye) {
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FURNITURE_DYE;
    }
}
