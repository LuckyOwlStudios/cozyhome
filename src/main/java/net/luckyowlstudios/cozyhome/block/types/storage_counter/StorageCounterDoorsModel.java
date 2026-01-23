package net.luckyowlstudios.cozyhome.block.types.storage_counter;// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.luckyowlstudios.cozyhome.Cozyhome;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class StorageCounterDoorsModel extends Model {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Cozyhome.id( "storage_counter_doors"), "main");
	private final ModelPart left;
	private final ModelPart right;

	public StorageCounterDoorsModel(ModelPart root) {
		super(RenderType::entityCutout);
        this.left = root.getChild("left");
		this.right = root.getChild("right");
	}

	public static LayerDefinition createDoors() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition left = partdefinition.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -6.0F, -0.5F, 7.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 18.0F, -6.5F));

		PartDefinition right = partdefinition.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 12).addBox(-6.5F, -6.0F, -0.5F, 7.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 18.0F, -6.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		left.render(poseStack, buffer, packedLight, packedOverlay, color);
		right.render(poseStack, buffer, packedLight, packedOverlay, color);
	}

	public void setupAnim(float openProgress, float partialTick) {
		// Smooth animation based on progress (0.0 = closed, 1.0 = open)
		float openAngle = openProgress * 90.0F; // 0 to 90 degrees

		// Apply rotation with smooth interpolation
		this.left.yRot = Mth.DEG_TO_RAD * openAngle;
		this.right.yRot = Mth.DEG_TO_RAD * -openAngle; // Right door opens the opposite way
	}
}