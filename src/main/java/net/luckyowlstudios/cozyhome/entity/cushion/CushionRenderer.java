package net.luckyowlstudios.cozyhome.entity.cushion;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.util.ThemedStyle;
import net.luckyowlstudios.cozyhome.init.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class CushionRenderer <T extends Cushion> extends EntityRenderer<T> {

    // Dyes
    public static final ResourceLocation WHITE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/white_cushion.png");
    public static final ResourceLocation LIGHT_GRAY_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/light_gray_cushion.png");
    public static final ResourceLocation GRAY_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/gray_cushion.png");
    public static final ResourceLocation BLACK_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/black_cushion.png");
    public static final ResourceLocation BROWN_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/brown_cushion.png");
    public static final ResourceLocation RED_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/red_cushion.png");
    public static final ResourceLocation ORANGE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/orange_cushion.png");
    public static final ResourceLocation YELLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/yellow_cushion.png");
    public static final ResourceLocation LIME_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/lime_cushion.png");
    public static final ResourceLocation GREEN_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/green_cushion.png");
    public static final ResourceLocation CYAN_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/cyan_cushion.png");
    public static final ResourceLocation LIGHT_BLUE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/light_blue_cushion.png");
    public static final ResourceLocation BLUE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/blue_cushion.png");
    public static final ResourceLocation PURPLE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/purple_cushion.png");
    public static final ResourceLocation MAGENTA_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/magenta_cushion.png");
    public static final ResourceLocation PINK_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/pink_cushion.png");

    // Styles
    public static final ResourceLocation HAY_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/hay_cushion.png");
    public static final ResourceLocation TRADER_TEXTURE = ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, "textures/entity/cushion/trader_cushion.png");

    private final CushionModel model;

    public CushionRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CushionModel(context.bakeLayer(ModModelLayers.CUSHION));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        DyeColor dyeColor = entity.getColor();
        ThemedStyle themedStyle = entity.getStyle();
        if (themedStyle != ThemedStyle.NONE) {
            return switch (themedStyle) {
                case HAY -> HAY_TEXTURE;
                case TRADER -> TRADER_TEXTURE;
                default -> WHITE_TEXTURE;
            };
        } else if (dyeColor != null) {
            return switch (dyeColor) {
                case LIGHT_GRAY -> LIGHT_GRAY_TEXTURE;
                case GRAY -> GRAY_TEXTURE;
                case BLACK -> BLACK_TEXTURE;
                case BROWN -> BROWN_TEXTURE;
                case RED -> RED_TEXTURE;
                case ORANGE -> ORANGE_TEXTURE;
                case YELLOW -> YELLOW_TEXTURE;
                case LIME -> LIME_TEXTURE;
                case GREEN -> GREEN_TEXTURE;
                case CYAN -> CYAN_TEXTURE;
                case LIGHT_BLUE -> LIGHT_BLUE_TEXTURE;
                case BLUE -> BLUE_TEXTURE;
                case PURPLE -> PURPLE_TEXTURE;
                case MAGENTA -> MAGENTA_TEXTURE;
                case PINK -> PINK_TEXTURE;
                default -> WHITE_TEXTURE;
            };
        }
        return WHITE_TEXTURE;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.pushPose();

        // Calculate squish scale based on impact
        float squishScale = calculateSquishScale(entity, partialTick);

        // Apply squish scaling - wider on X/Z, shorter on Y when impacting
        poseStack.scale(squishScale, 1.0f / squishScale, squishScale);

        poseStack.translate(0, 1.5F, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    private float calculateSquishScale(T entity, float partialTick) {
        if (entity.getImpactAnimation() > 0) {
            float animationProgress = (entity.getImpactAnimation() - partialTick) / 10.0f; // 10 tick animation
            float intensity = entity.getLastImpactVelocity() * 0.5F; // Stronger intensity

            // Single smooth squish using a sine curve (0 to π for one complete cycle)
            float squishAmount = (float) Math.sin(animationProgress * Math.PI) * intensity * 0.8f; // Max 80% squish

            return 1.0f + squishAmount;
        }

        return 1.0f;
    }
}
