package net.luckyowlstudios.cozyhome.init;

import com.google.common.collect.Sets;
import net.luckyowlstudios.cozyhome.Cozyhome;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class ModModelLayers {
    private static final Set<ModelLayerLocation> ALL_MODELS = Sets.newHashSet();
    public static final ModelLayerLocation STORAGE_COUNTER_DOORS = register("storage_counter_doors");
    public static final ModelLayerLocation CUSHION = register("cushion");

    private static ModelLayerLocation register(String path) {
        return register(path, "main");
    }

    private static ModelLayerLocation register(String path, String model) {
        ModelLayerLocation modellayerlocation = createLocation(path, model);
        if (!ALL_MODELS.add(modellayerlocation)) {
            throw new IllegalStateException("Duplicate registration for " + modellayerlocation);
        } else {
            return modellayerlocation;
        }
    }

    private static ModelLayerLocation createLocation(String path, String model) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Cozyhome.MOD_ID, path), model);
    }
}
