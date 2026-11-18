package net.luckystudio.cozyhome.mixin;

import com.mojang.datafixers.util.Either;
import net.luckystudio.cozyhome.world.structure.VillageFurnitureProcessor;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Adds {@link VillageFurnitureProcessor} to village pieces as they are placed. This only appends a processor
 * to the placement settings; the piece's own processor list, structure file and pool are untouched.
 */
@Mixin(SinglePoolElement.class)
public abstract class SinglePoolElementMixin {
    @Shadow
    @Final
    protected Either<Identifier, StructureTemplate> location;

    @Inject(method = "createPlacementData", at = @At("RETURN"))
    private void cozyhome$addVillageFurniture(BlockRotation rotation, BlockBox box, StructureLiquidSettings liquidSettings,
                                              boolean keepJigsaws, CallbackInfoReturnable<StructurePlacementData> cir) {
        if (VillageFurnitureProcessor.appliesTo(this.location)) {
            cir.getReturnValue().addProcessor(VillageFurnitureProcessor.INSTANCE);
        }
    }
}
