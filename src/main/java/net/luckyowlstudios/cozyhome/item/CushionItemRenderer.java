package net.luckyowlstudios.cozyhome.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.luckyowlstudios.cozyhome.util.ThemedStyle;
import net.luckyowlstudios.cozyhome.entity.cushion.Cushion;
import net.luckyowlstudios.cozyhome.init.ModEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;

public class CushionItemRenderer extends BlockEntityWithoutLevelRenderer {

    public CushionItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher,
                               EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        // Create a new cushion for each render call to ensure proper color
        Cushion cushion = new Cushion(ModEntityTypes.CUSHION.get(), mc.level);
        cushion.setPos(0, 0, 0);

        // Set the color/style based on the item
        if (stack.getItem() instanceof CushionItem cushionItem) {
            DyeColor dyeColor = cushionItem.getColor();
            ThemedStyle themedStyle = cushionItem.getStyle();

            if (dyeColor != null) {
                cushion.setColor(dyeColor); // Make sure your Cushion entity has this method
            }
            if (themedStyle != null) {
                cushion.setStyle(themedStyle); // Make sure your Cushion entity has this method
            }
        }

        poseStack.pushPose();

        // Adjust positioning and scale based on display context
        switch (displayContext) {
            case GUI:
                poseStack.translate(0.5, 0.25, 0.5);
                break;
            case FIRST_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND:
                poseStack.translate(0.5, 0.5, 0.5);
                break;
            case THIRD_PERSON_RIGHT_HAND, THIRD_PERSON_LEFT_HAND:
                poseStack.translate(0.5, 0.25, 0.5);
                break;
            case GROUND:
                poseStack.translate(0.5, 0.1, 0.5);
                break;
            case FIXED:
                poseStack.translate(0.5, 0.3, 0.5);
                poseStack.scale(0.7f, 0.7f, 0.7f);
                break;
        }

        EntityRenderDispatcher entityRenderer = mc.getEntityRenderDispatcher();
        entityRenderer.render(cushion, 0, 0, 0, 0, 1.0f, poseStack, buffer, packedLight);

        poseStack.popPose();
    }
}