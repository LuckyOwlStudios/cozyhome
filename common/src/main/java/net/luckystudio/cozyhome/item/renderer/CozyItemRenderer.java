package net.luckystudio.cozyhome.item.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

/**
 * Loader-independent version of Fabric's {@code DynamicItemRenderer}. Used for items whose
 * model uses {@code builtin/entity}. Fabric hooks it into BuiltinItemRendererRegistry, NeoForge
 * wraps it in a custom item renderer through IClientItemExtensions.
 */
@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface CozyItemRenderer {
    void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay);
}
