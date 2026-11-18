package net.luckystudio.cozyhome.block.custom.counters;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

/**
 * The two cabinet doors on the front of a storage counter. Each door is hinged on its
 * outer edge and swings outwards by up to 90 degrees.
 */
public class StorageCounterDoorsModel extends Model {
    private final ModelPart left;
    private final ModelPart right;

    public StorageCounterDoorsModel(ModelPart root) {
        super(RenderLayer::getEntityCutout);
        this.left = root.getChild("left");
        this.right = root.getChild("right");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        root.addChild("left", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-0.5F, -6.0F, -0.5F, 7.0F, 11.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-6.5F, 18.0F, -6.5F));
        root.addChild("right", ModelPartBuilder.create().uv(0, 12)
                .cuboid(-6.5F, -6.0F, -0.5F, 7.0F, 11.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(6.5F, 18.0F, -6.5F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    /** @param openProgress 0 = closed, 1 = fully open */
    public void setOpenProgress(float openProgress) {
        float angle = openProgress * 90.0F * MathHelper.RADIANS_PER_DEGREE;
        this.left.yaw = angle;
        this.right.yaw = -angle;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.left.render(matrices, vertices, light, overlay, color);
        this.right.render(matrices, vertices, light, overlay, color);
    }
}
