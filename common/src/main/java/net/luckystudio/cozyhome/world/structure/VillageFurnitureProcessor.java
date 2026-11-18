package net.luckystudio.cozyhome.world.structure;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import net.luckystudio.cozyhome.CozyHome;
import net.luckystudio.cozyhome.block.custom.seatable.chairs.ChairBlock;
import net.luckystudio.cozyhome.block.custom.horizontal_connecting_blocks.TableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Swaps the makeshift furniture in village houses for Cozyhome furniture while the house is being placed.
 * <p>
 * Vanilla villages build tables as a wooden fence with a wooden pressure plate on top, and chairs as
 * bottom-half wooden stairs with their back turned to the table. This processor finds that pattern and
 * replaces it with the matching wood's Cozyhome table and chairs. Nothing on disk is overridden: the
 * structure files and template pools stay vanilla, so other mods that add, replace or edit village houses
 * keep working, and houses without the pattern are left exactly as they are.
 * <p>
 * It is attached to village pieces at runtime by {@code SinglePoolElementMixin}.
 */
public class VillageFurnitureProcessor extends StructureProcessor {
    public static final VillageFurnitureProcessor INSTANCE = new VillageFurnitureProcessor();
    public static final MapCodec<VillageFurnitureProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    private static final Direction[] HORIZONTAL = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    private VillageFurnitureProcessor() {}

    /** True for jigsaw pieces whose structure file lives under {@code village/}, in any namespace. */
    public static boolean appliesTo(Either<Identifier, StructureTemplate> location) {
        return location.left().map(id -> id.getPath().startsWith("village/")).orElse(false);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructureProcessors.VILLAGE_FURNITURE;
    }

    /**
     * Runs once per piece, after every per-block processor, with the whole piece in hand. Positions are
     * matched in the template's own coordinates (the {@code originals} list), and the new states are written
     * unrotated, the same as vanilla: the template rotates and mirrors every state when it places it.
     */
    @Override
    public List<StructureTemplate.StructureBlockInfo> reprocess(ServerWorldAccess world, BlockPos pos, BlockPos pivot,
                                                                List<StructureTemplate.StructureBlockInfo> originals,
                                                                List<StructureTemplate.StructureBlockInfo> current,
                                                                StructurePlacementData data) {
        // The two lists line up index for index. If another mod's processor broke that, leave the piece alone.
        if (originals.size() != current.size()) return current;

        Map<BlockPos, Integer> index = new HashMap<>(originals.size() * 2);
        for (int i = 0; i < originals.size(); i++) {
            index.put(originals.get(i).pos(), i);
        }

        // 1. Find the fence + pressure plate tables.
        Map<BlockPos, BlockState> tables = new LinkedHashMap<>();
        List<Integer> plates = new ArrayList<>();
        for (int i = 0; i < current.size(); i++) {
            BlockState fence = current.get(i).state();
            if (!fence.isIn(BlockTags.WOODEN_FENCES)) continue;

            BlockPos fencePos = originals.get(i).pos();
            BlockState above = stateAt(fencePos.up(), index, current);
            BlockState below = stateAt(fencePos.down(), index, current);
            if (above == null || !above.isIn(BlockTags.WOODEN_PRESSURE_PLATES)) continue;
            if (below != null && below.isIn(BlockTags.FENCES)) continue; // a post, not a table

            Block table = furnitureFor(fence.getBlock(), "_fence", "_table");
            if (!(table instanceof TableBlock)) continue;

            tables.put(fencePos, table.getDefaultState()
                    .with(Properties.WATERLOGGED, fence.contains(Properties.WATERLOGGED) && fence.get(Properties.WATERLOGGED)));
            plates.add(index.get(fencePos.up()));
        }
        if (tables.isEmpty()) return current;

        List<StructureTemplate.StructureBlockInfo> result = new ArrayList<>(current);

        // 2. Place the tables, joined up with any table next to them.
        for (Map.Entry<BlockPos, BlockState> entry : tables.entrySet()) {
            BlockPos tablePos = entry.getKey();
            BlockState table = entry.getValue()
                    .with(TableBlock.NORTH, tables.containsKey(tablePos.north()))
                    .with(TableBlock.EAST, tables.containsKey(tablePos.east()))
                    .with(TableBlock.SOUTH, tables.containsKey(tablePos.south()))
                    .with(TableBlock.WEST, tables.containsKey(tablePos.west()));
            replace(result, index.get(tablePos), table);
        }
        for (int plate : plates) {
            replace(result, plate, Blocks.AIR.getDefaultState());
        }

        // 3. Stairs pulled up to a table become chairs facing it.
        for (Map.Entry<BlockPos, BlockState> entry : tables.entrySet()) {
            BlockPos tablePos = entry.getKey();
            for (Direction outward : HORIZONTAL) {
                Integer seat = index.get(tablePos.offset(outward));
                if (seat == null) continue;

                BlockState stairs = result.get(seat).state();
                if (!isChairStairs(stairs, outward)) continue;

                Block chair = furnitureFor(stairs.getBlock(), "_stairs", "_chair");
                if (!(chair instanceof ChairBlock)) {
                    chair = furnitureFor(entry.getValue().getBlock(), "_table", "_chair");
                }
                if (!(chair instanceof ChairBlock)) continue;

                Integer rotation = rotationFacing(outward.getOpposite());
                if (rotation == null) continue;

                replace(result, seat, chair.getDefaultState()
                        .with(ChairBlock.ROTATION, rotation)
                        .with(ChairBlock.TUCKED, false)
                        .with(Properties.WATERLOGGED, stairs.get(Properties.WATERLOGGED)));
            }
        }
        return result;
    }

    /** Bottom-half straight wooden stairs whose back faces away from the table. */
    private static boolean isChairStairs(BlockState state, Direction awayFromTable) {
        return state.isIn(BlockTags.WOODEN_STAIRS)
                && state.contains(StairsBlock.FACING)
                && state.get(StairsBlock.FACING) == awayFromTable
                && state.get(StairsBlock.HALF) == BlockHalf.BOTTOM
                && state.get(StairsBlock.SHAPE) == StairShape.STRAIGHT;
    }

    /** The chair rotation that {@code TuckableBlock} reads back as facing {@code toward}. */
    @Nullable
    private static Integer rotationFacing(Direction toward) {
        for (int rotation = 0; rotation <= RotationPropertyHelper.getMax(); rotation += 4) {
            if (RotationPropertyHelper.toDirection(rotation).orElse(null) == toward) return rotation;
        }
        return null;
    }

    /** Maps e.g. {@code minecraft:spruce_fence} to {@code cozyhome:spruce_table}, or null if there is none. */
    @Nullable
    private static Block furnitureFor(Block source, String suffix, String furnitureSuffix) {
        String path = Registries.BLOCK.getId(source).getPath();
        if (!path.endsWith(suffix)) return null;
        String wood = path.substring(0, path.length() - suffix.length());
        return Registries.BLOCK.getOrEmpty(CozyHome.id(wood + furnitureSuffix)).orElse(null);
    }

    @Nullable
    private static BlockState stateAt(BlockPos templatePos, Map<BlockPos, Integer> index,
                                      List<StructureTemplate.StructureBlockInfo> current) {
        Integer i = index.get(templatePos);
        return i == null ? null : current.get(i).state();
    }

    private static void replace(List<StructureTemplate.StructureBlockInfo> infos, int i, BlockState state) {
        StructureTemplate.StructureBlockInfo old = infos.get(i);
        infos.set(i, new StructureTemplate.StructureBlockInfo(old.pos(), state, null));
    }
}
