package net.luckystudio.cozyhome.item.custom;

import net.luckystudio.cozyhome.util.ModColorHandler;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Block item for dyeable furniture. Its name shows the dye it carries ("Red Oak Couch"),
 * and an undyed one keeps the plain block name.
 */
public class DyedBlockItem extends BlockItem {

    public DyedBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return ModColorHandler.getDyedName(stack, super.getName(stack));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.cozyhome.dyeable").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
