package net.luckystudio.cozyhome.world.structure;

import net.luckystudio.cozyhome.CozyHome;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.block.Block;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * Looks up hand-built furnished copies of structure pieces and works out what changed.
 * <p>
 * A furnished copy of {@code <namespace>:<path>} lives at {@code cozyhome:furnished/<namespace>/<path>}, for
 * example {@code data/cozyhome/structure/furnished/minecraft/village/plains/houses/plains_small_house_1.nbt}.
 * It is the original piece, loaded with a structure block, furnished by hand and saved again. Only the blocks
 * that differ from the original are ever placed, and only when the original piece has not been overridden by
 * a datapack or another mod, so a house that someone else changed is never touched.
 * <p>
 * Zombie village houses with no furnished copy of their own fall back to the normal house's copy, as long as
 * both are the same size.
 */
public final class FurnishedTemplates {
    public static final String FOLDER = "furnished";

    /** One block to place, in the template's own coordinates. */
    public record Change(BlockPos pos, BlockState state, @Nullable NbtCompound nbt) {}

    /** The changes for one piece, remembered against the exact template objects they were worked out from. */
    private record Overlay(StructureTemplate piece, StructureTemplate base, List<Change> changes) {}

    // Keyed by the furnished template object. The template manager makes new objects on /reload,
    // so stale entries drop out on their own.
    private static final Map<StructureTemplate, Overlay> CACHE = Collections.synchronizedMap(new WeakHashMap<>());

    private FurnishedTemplates() {}

    /** The blocks to change for the piece at {@code location}, or an empty list if there is nothing to do. */
    public static List<Change> changesFor(MinecraftServer server, Identifier location) {
        StructureTemplateManager templates = server.getStructureTemplateManager();
        Optional<StructureTemplate> piece = templates.getTemplate(location);
        if (piece.isEmpty()) return List.of();

        // The piece's own furnished copy, or for a zombie house, the normal house's.
        Identifier base = location;
        Optional<StructureTemplate> furnished = templates.getTemplate(furnishedId(location));
        if (furnished.isEmpty() && location.getPath().contains("/zombie/")) {
            base = Identifier.of(location.getNamespace(), location.getPath().replace("/zombie/", "/"));
            furnished = templates.getTemplate(furnishedId(base));
        }
        if (furnished.isEmpty()) return List.of();

        Optional<StructureTemplate> baseTemplate = base.equals(location) ? piece : templates.getTemplate(base);
        if (baseTemplate.isEmpty()) return List.of();

        Overlay cached = CACHE.get(furnished.get());
        if (cached != null && cached.piece() == piece.get() && cached.base() == baseTemplate.get()) {
            return cached.changes();
        }

        List<Change> changes = List.of();
        if (!isOverridden(server.getResourceManager(), location) && !isOverridden(server.getResourceManager(), base)) {
            changes = diff(location, baseTemplate.get(), piece.get(), furnished.get());
        }
        CACHE.put(furnished.get(), new Overlay(piece.get(), baseTemplate.get(), changes));
        return changes;
    }

    public static Identifier furnishedId(Identifier location) {
        return CozyHome.id(FOLDER + "/" + location.getNamespace() + "/" + location.getPath());
    }

    /**
     * True if more than one data pack provides this structure file: the original (vanilla or the mod that
     * adds it) plus a datapack or mod replacing it. A replaced house gets no furniture.
     */
    private static boolean isOverridden(ResourceManager resources, Identifier location) {
        Identifier file = Identifier.of(location.getNamespace(), "structure/" + location.getPath() + ".nbt");
        return resources.getAllResources(file).size() > 1;
    }

    private static List<Change> diff(Identifier location, StructureTemplate base, StructureTemplate piece,
                                     StructureTemplate furnished) {
        if (!base.getSize().equals(furnished.getSize()) || !piece.getSize().equals(furnished.getSize())) {
            CozyHome.LOGGER.warn("Furnished copy of {} is {}, but the original is {}; skipping it",
                    location, furnished.getSize(), base.getSize());
            return List.of();
        }
        Map<BlockPos, Change> original = read(base);
        List<Change> changes = new ArrayList<>();
        for (Change block : read(furnished).values()) {
            if (isStructureBlock(block.state())) continue;
            Change before = original.get(block.pos());
            if (before != null && before.state() == block.state() && Objects.equals(before.nbt(), block.nbt())) continue;
            if (before != null && isStructureBlock(before.state())) continue; // never touch jigsaws
            changes.add(block);
        }
        return List.copyOf(changes);
    }

    private static boolean isStructureBlock(BlockState state) {
        return state.isOf(Blocks.JIGSAW) || state.isOf(Blocks.STRUCTURE_BLOCK) || state.isOf(Blocks.STRUCTURE_VOID);
    }

    /** Every block in a template, by position. Reads the saved form so it needs no access to private fields. */
    private static Map<BlockPos, Change> read(StructureTemplate template) {
        NbtCompound nbt = template.writeNbt(new NbtCompound());
        RegistryEntryLookup<Block> blocks = Registries.BLOCK.getReadOnlyWrapper();

        NbtList paletteNbt = nbt.getList("palette", NbtElement.COMPOUND_TYPE);
        if (paletteNbt.isEmpty()) {
            NbtList palettes = nbt.getList("palettes", NbtElement.LIST_TYPE);
            if (!palettes.isEmpty()) paletteNbt = palettes.getList(0);
        }
        List<BlockState> palette = new ArrayList<>(paletteNbt.size());
        for (int i = 0; i < paletteNbt.size(); i++) {
            palette.add(NbtHelper.toBlockState(blocks, paletteNbt.getCompound(i)));
        }

        Map<BlockPos, Change> result = new HashMap<>();
        NbtList blockList = nbt.getList("blocks", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < blockList.size(); i++) {
            NbtCompound block = blockList.getCompound(i);
            NbtList pos = block.getList("pos", NbtElement.INT_TYPE);
            int state = block.getInt("state");
            if (pos.size() != 3 || state < 0 || state >= palette.size()) continue;
            BlockPos blockPos = new BlockPos(pos.getInt(0), pos.getInt(1), pos.getInt(2));
            NbtCompound blockEntity = block.contains("nbt", NbtElement.COMPOUND_TYPE) ? block.getCompound("nbt") : null;
            result.put(blockPos, new Change(blockPos, palette.get(state), blockEntity));
        }
        return result;
    }
}
