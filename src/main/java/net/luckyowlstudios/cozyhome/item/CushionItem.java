package net.luckyowlstudios.cozyhome.item;

import net.luckyowlstudios.cozyhome.util.ThemedStyle;
import net.luckyowlstudios.cozyhome.entity.cushion.Cushion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.IBakedModelExtension;

public class CushionItem extends Item implements IBakedModelExtension {

    ThemedStyle style;
    DyeColor color;

    public CushionItem(DyeColor dyeColor, Properties properties) {
        super(properties);
        this.color = dyeColor;
    }

    public CushionItem(ThemedStyle themedStyle, Properties properties) {
        super(properties);
        this.style = themedStyle;
    }

    public ThemedStyle getStyle() {
        return this.style;
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockPos placePos = clickedPos.relative(context.getClickedFace()); // Position to place the cushion
        BlockState blockstate = level.getBlockState(placePos);

        if (blockstate.canBeReplaced()) {
            Cushion cushion;
            if (this.style != null) {
                cushion = new Cushion(level, placePos, this.style); // Use placePos, not clickedPos
            } else {
                cushion = new Cushion(level, placePos, this.color);
            }
            level.addFreshEntity(cushion);

            if (!context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1); // Consume the item
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }
}
