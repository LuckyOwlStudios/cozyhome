package net.luckystudio.cozyhome.world.structure;

import com.mojang.serialization.MapCodec;
import net.luckystudio.cozyhome.CozyHome;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;

public class ModStructureProcessors {
    public static final StructureProcessorType<VillageFurnitureProcessor> VILLAGE_FURNITURE =
            register("village_furniture", VillageFurnitureProcessor.CODEC);

    private static <P extends StructureProcessor> StructureProcessorType<P> register(String name, MapCodec<P> codec) {
        StructureProcessorType<P> type = () -> codec;
        return Registry.register(Registries.STRUCTURE_PROCESSOR, CozyHome.id(name), type);
    }

    public static void registerStructureProcessors() {
        CozyHome.LOGGER.info("Registering Structure Processors for " + CozyHome.MOD_ID);
    }
}
