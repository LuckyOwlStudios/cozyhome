package net.luckystudio.cozyhome.world.structure;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Places the hand-built furniture from a piece's furnished copy (see {@link FurnishedTemplates}) on top of
 * the piece as it generates. Nothing on disk is overridden: the original structure file, its pool and its
 * processor list stay as they are, and the furniture is skipped when another mod or datapack has replaced
 * the piece.
 * <p>
 * It is attached to jigsaw pieces at runtime by {@code SinglePoolElementMixin}, and can also be put in a
 * datapack's processor list as {@code cozyhome:furnished} with a {@code location}.
 */
public class FurnishedStructureProcessor extends StructureProcessor {
    public static final MapCodec<FurnishedStructureProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("location").forGetter(processor -> processor.location)
    ).apply(instance, FurnishedStructureProcessor::new));

    private final Identifier location;

    public FurnishedStructureProcessor(Identifier location) {
        this.location = location;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructureProcessors.FURNISHED;
    }

    /**
     * Runs once per piece, after every other processor on it. Changes are in the template's own coordinates;
     * blocks already in the piece are matched through the {@code originals} list, and states are written
     * unrotated, the same as vanilla, because the template rotates and mirrors every state as it places it.
     */
    @Override
    public List<StructureTemplate.StructureBlockInfo> reprocess(ServerWorldAccess world, BlockPos pos, BlockPos pivot,
                                                                List<StructureTemplate.StructureBlockInfo> originals,
                                                                List<StructureTemplate.StructureBlockInfo> current,
                                                                StructurePlacementData data) {
        // The two lists line up index for index. If another mod's processor broke that, leave the piece alone.
        if (originals.size() != current.size()) return current;

        MinecraftServer server = world.toServerWorld().getServer();
        List<FurnishedTemplates.Change> changes = FurnishedTemplates.changesFor(server, this.location);
        if (changes.isEmpty()) return current;

        Map<BlockPos, Integer> index = new HashMap<>(originals.size() * 2);
        for (int i = 0; i < originals.size(); i++) {
            index.put(originals.get(i).pos(), i);
        }

        List<StructureTemplate.StructureBlockInfo> result = new ArrayList<>(current);
        for (FurnishedTemplates.Change change : changes) {
            Integer i = index.get(change.pos());
            BlockPos worldPos = i != null ? result.get(i).pos() : StructureTemplate.transform(data, change.pos()).add(pos);
            StructureTemplate.StructureBlockInfo info = new StructureTemplate.StructureBlockInfo(
                    worldPos, change.state(), change.nbt() == null ? null : change.nbt().copy());
            if (i != null) {
                result.set(i, info);
            } else {
                result.add(info);
            }
        }
        return result;
    }
}
