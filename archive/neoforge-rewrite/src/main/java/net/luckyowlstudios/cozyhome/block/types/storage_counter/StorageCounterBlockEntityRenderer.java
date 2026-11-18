package net.luckyowlstudios.cozyhome.block.types.storage_counter;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.init.ModModelLayers;
import net.luckyowlstudios.cozyhome.util.MaterialTypes;
import net.minecraft.Util;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Map;

public class StorageCounterBlockEntityRenderer implements BlockEntityRenderer<StorageCounterBlockEntity> {

    private static final Map<MaterialTypes, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), map -> {
        map.put(MaterialTypes.OAK, Cozyhome.id("textures/block/counter/oak_doors.png"));
        map.put(MaterialTypes.SPRUCE, Cozyhome.id("textures/block/counter/spruce_doors.png"));
        map.put(MaterialTypes.BIRCH, Cozyhome.id("textures/block/counter/birch_doors.png"));
        map.put(MaterialTypes.JUNGLE, Cozyhome.id("textures/block/counter/jungle_doors.png"));
        map.put(MaterialTypes.ACACIA, Cozyhome.id("textures/block/counter/acacia_doors.png"));
        map.put(MaterialTypes.DARK_OAK, Cozyhome.id("textures/block/counter/dark_oak_doors.png"));
        map.put(MaterialTypes.MANGROVE, Cozyhome.id("textures/block/counter/mangrove_doors.png"));
        map.put(MaterialTypes.CHERRY, Cozyhome.id("textures/block/counter/cherry_doors.png"));
        map.put(MaterialTypes.BAMBOO, Cozyhome.id("textures/block/counter/bamboo_doors.png"));
        map.put(MaterialTypes.CRIMSON, Cozyhome.id("textures/block/counter/crimson_doors.png"));
        map.put(MaterialTypes.WARPED, Cozyhome.id("textures/block/counter/warped_doors.png"));
        map.put(MaterialTypes.PRINCESS, Cozyhome.id("textures/block/counter/princess_doors.png"));
        map.put(MaterialTypes.IRON, Cozyhome.id("textures/block/counter/iron_doors.png"));
        map.put(MaterialTypes.GLASS, Cozyhome.id("textures/block/counter/glass_doors.png"));
        map.put(MaterialTypes.UNDEAD, Cozyhome.id("textures/block/counter/undead_doors.png"));
        map.put(MaterialTypes.OMINOUS, Cozyhome.id("textures/block/counter/ominous_doors.png"));
    });
    private final StorageCounterDoorsModel doors;

    public StorageCounterBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.doors = new StorageCounterDoorsModel(context.bakeLayer(ModModelLayers.STORAGE_COUNTER_DOORS));
    }

    @Override
    public void render(StorageCounterBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockState = blockEntity.getBlockState();
        Direction direction = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING);

        // Get smooth animation progress
        float openProgress = blockEntity.getOpenProgress(partialTick);

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));

        // Get proper lighting for the block position
        int combinedLight = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos());

        // Setup animation with smooth progress
        doors.setupAnim(openProgress, partialTick);

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(blockEntity)));
        doors.renderToBuffer(poseStack, vertexConsumer, combinedLight, packedOverlay);
        poseStack.popPose();
    }

    public static ResourceLocation getTextureLocation(StorageCounterBlockEntity blockEntity) {
        BlockState blockState = blockEntity.getBlockState();
        StorageCounterBlock storageCounterBlock = (StorageCounterBlock) blockState.getBlock();
        return TEXTURES.get(storageCounterBlock.getMaterial());
    }
}