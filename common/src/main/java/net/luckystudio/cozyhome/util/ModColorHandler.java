package net.luckystudio.cozyhome.util;

import net.luckystudio.cozyhome.block.custom.lamps.AbstractLampBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Furniture dyeing works with the 16 vanilla dye colors only. Dyeing a block or item sets it to exactly
 * the dye's color (no blending), stored as a {@link DataComponentTypes#DYED_COLOR} so it carries over
 * between the placed block (block entity components) and the dropped item (copied by the loot table).
 */
public class ModColorHandler {
    /** Tint used by furniture that has never been dyed. */
    public static final int DEFAULT_COLOR = -17170434;

    /** The exact ARGB color a piece of furniture gets when dyed with {@code dye}. */
    public static int getColor(DyeColor dye) {
        return ColorHelper.Argb.fullAlpha(dye.getEntityColor());
    }

    /** The dye whose color is exactly {@code color}, or null for undyed / old blended colors. */
    public static @Nullable DyeColor getDyeColor(int color) {
        for (DyeColor dye : DyeColor.values()) {
            if ((dye.getEntityColor() & 0xFFFFFF) == (color & 0xFFFFFF)) {
                return dye;
            }
        }
        return null;
    }

    public static @Nullable DyeColor getDyeColor(ItemStack stack) {
        DyedColorComponent dyed = stack.get(DataComponentTypes.DYED_COLOR);
        return dyed != null ? getDyeColor(dyed.rgb()) : null;
    }

    public static @Nullable DyeColor getDyeColor(@Nullable BlockEntity entity) {
        if (entity == null) {
            return null;
        }
        DyedColorComponent dyed = entity.getComponents().get(DataComponentTypes.DYED_COLOR);
        return dyed != null ? getDyeColor(dyed.rgb()) : null;
    }

    public static Text getColorName(DyeColor dye) {
        return Text.translatable("cozyhome.color." + dye.getName());
    }

    /**
     * "Red" + "Oak Couch" -> "Red Oak Couch". Undyed items (and items carrying an old blended color
     * from before dyes were exact) keep their plain name.
     */
    public static Text getDyedName(ItemStack stack, Text baseName) {
        DyeColor dye = getDyeColor(stack);
        if (dye == null) {
            return baseName;
        }
        return Text.translatable("cozyhome.dyed_name", getColorName(dye), baseName);
    }

    /** The color a block shows when it has not been dyed. */
    public static int getDefaultColor(Block block) {
        return block instanceof AbstractLampBlock lamp ? lamp.getDefaultColor() : DEFAULT_COLOR;
    }

    public static int getDefaultColor(ItemStack stack) {
        return stack.getItem() instanceof BlockItem blockItem ? getDefaultColor(blockItem.getBlock()) : DEFAULT_COLOR;
    }

    public static int getItemColor(ItemStack stack) {
        return DyedColorComponent.getColor(stack, getDefaultColor(stack));
    }

    public static int getBlockColor(@Nullable BlockEntity entity, int defaultColor) {
        if (entity == null) {
            return defaultColor;
        }
        DyedColorComponent dyedColorComponent = entity.getComponents().get(DataComponentTypes.DYED_COLOR);
        return dyedColorComponent != null ? ColorHelper.Argb.fullAlpha(dyedColorComponent.rgb()) : defaultColor;
    }

    /**
     * Shared right-click-with-dye behaviour: sets the block entity to exactly the dye's color.
     * Returns null when {@code stack} is not a dye, so the caller can carry on with its other interactions.
     */
    public static @Nullable ItemActionResult tryDyeBlock(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, @Nullable BlockEntity entity) {
        if (entity == null || !(stack.getItem() instanceof DyeItem dyeItem)) {
            return null;
        }
        DyeColor dye = dyeItem.getColor();
        if (getDyeColor(entity) == dye) {
            player.sendMessage(Text.translatable("message.cozyhome.same_color"), true);
            return ItemActionResult.SUCCESS;
        }
        ComponentMap components = ComponentMap.builder()
                .addAll(entity.getComponents())
                .add(DataComponentTypes.DYED_COLOR, new DyedColorComponent(getColor(dye), false))
                .build();
        entity.setComponents(components);
        world.playSound(player, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        stack.decrementUnlessCreative(1, player);
        entity.markDirty();
        world.updateListeners(pos, state, state, 0);
        return ItemActionResult.SUCCESS;
    }
}
