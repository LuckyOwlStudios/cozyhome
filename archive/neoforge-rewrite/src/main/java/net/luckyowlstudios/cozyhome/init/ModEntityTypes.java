package net.luckyowlstudios.cozyhome.init;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.entity.Seat;
import net.luckyowlstudios.cozyhome.entity.cushion.Cushion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Cozyhome.MOD_ID);

    public static final Supplier<EntityType<Seat>> SEAT =
            ENTITY_TYPES.register("seat", () -> EntityType.Builder.<Seat>of(Seat::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(4)
                    .noSummon()
                    .build("seat"));

    public static final Supplier<EntityType<Cushion>> CUSHION =
            ENTITY_TYPES.register("cushion", () -> EntityType.Builder.<Cushion>of(Cushion::new, MobCategory.MISC)
                    .sized(0.95F, 0.25F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("cushion"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
