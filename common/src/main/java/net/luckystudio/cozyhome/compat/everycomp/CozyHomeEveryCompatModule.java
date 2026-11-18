package net.luckystudio.cozyhome.compat.everycomp;

import net.luckystudio.cozyhome.CozyHome;
import net.luckystudio.cozyhome.block.ModBlocks;
import net.luckystudio.cozyhome.block.custom.wall_mirrors.WallMirrorBlock;
import net.luckystudio.cozyhome.block.util.ModBlockEntityTypes;
import net.luckystudio.cozyhome.item.ModItemGroups;
import net.luckystudio.cozyhome.item.custom.DyedBlockItem;
import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.api.RenderLayer;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

/**
 * Every Compat ("Wood Good") module. When Every Compat is installed it clones these
 * Cozyhome blocks for every modded wood type it detects, recoloring the oak textures
 * to match each wood.
 * <p>
 * Only furniture drawn with plain JSON models is listed here. Chairs, clocks, sofas
 * and storage counters render (part of) themselves with block entity renderers that
 * pick a fixed texture per wood type, so Every Compat can't recolor them yet.
 * <p>
 * This class is only ever loaded by Every Compat (see {@link CozyHomeEveryCompatPlugin}),
 * so Cozyhome runs fine without it.
 */
public class CozyHomeEveryCompatModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, Block> counters;
    public final SimpleEntrySet<WoodType, Block> sinkCounters;
    public final SimpleEntrySet<WoodType, Block> tables;
    public final SimpleEntrySet<WoodType, Block> desks;
    public final SimpleEntrySet<WoodType, Block> drawers;
    public final SimpleEntrySet<WoodType, Block> wallMirrors;
    public final SimpleEntrySet<WoodType, Block> couches;
    public final SimpleEntrySet<WoodType, Block> largeStumps;

    public CozyHomeEveryCompatModule(String modId) {
        // Blocks are registered under Every Compat's namespace, like every other EC module.
        super(modId, "ch", EveryCompat.MOD_ID);

        counters = SimpleEntrySet.builder(WoodType.class, "counter",
                        () -> ModBlocks.OAK_COUNTER, () -> VanillaWoodTypes.OAK,
                        w -> ModBlocks.createCounterBlock(w.planks, false, true))
                .addTexture(tex("break/oak_furniture"))
                .addTexture(tex("counter/oak_counter_back"))
                .addTexture(tex("counter/oak_counter_bottom"))
                .addTexture(tex("counter/oak_counter_front"))
                .addTexture(tex("counter/oak_counter_side"))
                .addTexture(tex("counter/oak_counter_top"))
                .addTexture(tex("counter/oak_counter_inner_bottom"))
                .addTexture(tex("counter/oak_counter_inner_top"))
                .addTexture(tex("counter/oak_counter_outer_bottom"))
                .addTexture(tex("counter/oak_counter_outer_side"))
                .addTexture(tex("counter/oak_counter_outer_top"))
                .addTag(BlockTags.AXE_MINEABLE, RegistryKeys.BLOCK)
                .addTag(BlockTags.PICKAXE_MINEABLE, RegistryKeys.BLOCK)
                .setTab(() -> ModItemGroups.COZY_HOME)
                .defaultRecipe()
                .build();
        this.addEntry(counters);

        sinkCounters = SimpleEntrySet.builder(WoodType.class, "sink_counter",
                        () -> ModBlocks.OAK_SINK_COUNTER, () -> VanillaWoodTypes.OAK,
                        w -> ModBlocks.createSinkCounterBlock(w.planks))
                .addTexture(tex("break/oak_furniture"))
                .addTexture(tex("counter/oak_counter_back"))
                .addTexture(tex("counter/oak_counter_bottom"))
                .addTexture(tex("counter/oak_counter_front"))
                .addTexture(tex("counter/oak_counter_side"))
                .addTexture(tex("counter/oak_counter_sink_top"))
                .addTile(() -> ModBlockEntityTypes.SINK_BLOCK_ENTITY)
                .addTag(BlockTags.AXE_MINEABLE, RegistryKeys.BLOCK)
                .addTag(BlockTags.PICKAXE_MINEABLE, RegistryKeys.BLOCK)
                .copyParentTint() // liquid color in the basin
                .setRenderType(RenderLayer.CUTOUT)
                .setTab(() -> ModItemGroups.COZY_HOME)
                .defaultRecipe()
                .build();
        this.addEntry(sinkCounters);

        tables = SimpleEntrySet.builder(WoodType.class, "table",
                        () -> ModBlocks.OAK_TABLE, () -> VanillaWoodTypes.OAK,
                        w -> ModBlocks.createTable(w.planks))
                .requiresChildren("slab") // recipe
                .addTexture(tex("table/oak_table_legs"))
                .addTexture(tex("table/oak_table_sides"))
                .addTexture(tex("table/oak_table_top"))
                .addTag(BlockTags.AXE_MINEABLE, RegistryKeys.BLOCK)
                .addTag(Identifier.of(CozyHome.MOD_ID, "can_tuck_under"), RegistryKeys.BLOCK)
                .setRenderType(RenderLayer.CUTOUT)
                .setTab(() -> ModItemGroups.COZY_HOME)
                .defaultRecipe()
                .build();
        this.addEntry(tables);

        desks = SimpleEntrySet.builder(WoodType.class, "desk",
                        () -> ModBlocks.OAK_DESK, () -> VanillaWoodTypes.OAK,
                        w -> ModBlocks.createDesk(w.planks))
                .requiresChildren("slab") // recipe
                .addTexture(tex("drawer/oak_drawer_back_middle"))
                .addTexture(tex("drawer/oak_drawer_back_side"))
                .addTexture(tex("drawer/oak_drawer_bottom"))
                .addTexture(tex("drawer/oak_drawer_bottom_middle"))
                .addTexture(tex("drawer/oak_drawer_bottom_side"))
                .addTexture(tex("drawer/oak_drawer_side"))
                .addTexture(tex("drawer/oak_drawer_side_inner"))
                .addTexture(tex("drawer/oak_drawer_top"))
                .addTexture(tex("drawer/oak_drawer_top_middle"))
                .addTexture(tex("drawer/oak_drawer_top_side"))
                .addTag(BlockTags.AXE_MINEABLE, RegistryKeys.BLOCK)
                .setTab(() -> ModItemGroups.COZY_HOME)
                .defaultRecipe()
                .build();
        this.addEntry(desks);

        drawers = SimpleEntrySet.builder(WoodType.class, "drawer",
                        () -> ModBlocks.OAK_DRAWER, () -> VanillaWoodTypes.OAK,
                        w -> ModBlocks.createDrawer(w.planks))
                .requiresChildren("slab") // recipe
                .addTexture(tex("drawer/oak_drawer_back_middle"))
                .addTexture(tex("drawer/oak_drawer_back_side"))
                .addTexture(tex("drawer/oak_drawer_bottom"))
                .addTexture(tex("drawer/oak_drawer_bottom_middle"))
                .addTexture(tex("drawer/oak_drawer_bottom_side"))
                .addTexture(tex("drawer/oak_drawer_front"))
                .addTexture(tex("drawer/oak_drawer_front_middle"))
                .addTexture(tex("drawer/oak_drawer_front_side"))
                .addTexture(tex("drawer/oak_drawer_front_side_right"))
                .addTexture(tex("drawer/oak_drawer_side"))
                .addTexture(tex("drawer/oak_drawer_side_inner"))
                .addTexture(tex("drawer/oak_drawer_top"))
                .addTexture(tex("drawer/oak_drawer_top_middle"))
                .addTexture(tex("drawer/oak_drawer_top_side"))
                .addTile(() -> ModBlockEntityTypes.DRAWER_BLOCK_ENTITY)
                .addTag(BlockTags.AXE_MINEABLE, RegistryKeys.BLOCK)
                .setTab(() -> ModItemGroups.COZY_HOME)
                .defaultRecipe()
                .build();
        this.addEntry(drawers);

        wallMirrors = SimpleEntrySet.builder(WoodType.class, "wall_mirror",
                        () -> ModBlocks.OAK_WALL_MIRROR, () -> VanillaWoodTypes.OAK,
                        w -> new WallMirrorBlock(AbstractBlock.Settings.copy(Blocks.GLASS)))
                .addTexture(tex("shared/oak_board"))
                .addTexture(tex("shared/oak_board_bottom"))
                .addTexture(tex("shared/oak_board_middle"))
                .addTexture(tex("shared/oak_board_top"))
                .addTexture(tex("shared/oak_frame"))
                .addTexture(tex("shared/oak_frame_bottom"))
                .addTexture(tex("shared/oak_frame_middle"))
                .addTexture(tex("shared/oak_frame_top"))
                .setRenderType(RenderLayer.CUTOUT)
                .setTab(() -> ModItemGroups.COZY_HOME)
                .defaultRecipe()
                .build();
        this.addEntry(wallMirrors);

        couches = SimpleEntrySet.builder(WoodType.class, "couch",
                        () -> ModBlocks.OAK_COUCH, () -> VanillaWoodTypes.OAK,
                        w -> ModBlocks.createCouch(w.planks))
                .requiresChildren("slab") // recipe
                .addTexture(tex("couch/oak_couch"))
                .addTile(() -> ModBlockEntityTypes.COUCH_BLOCK_ENTITY)
                .addCustomItem((w, block, settings) -> new DyedBlockItem(block, settings))
                .addTag(BlockTags.AXE_MINEABLE, RegistryKeys.BLOCK)
                .addTag(Identifier.of(CozyHome.MOD_ID, "dyeable_furniture"), RegistryKeys.ITEM)
                .copyParentTint() // dyed cushion color
                .setTab(() -> ModItemGroups.COZY_HOME)
                .defaultRecipe()
                .build();
        this.addEntry(couches);

        largeStumps = SimpleEntrySet.builder(WoodType.class, "large_stump",
                        () -> ModBlocks.OAK_LARGE_STUMP, () -> VanillaWoodTypes.OAK,
                        w -> ModBlocks.createLargeStump(w.getSound()))
                .createPaletteFromChild("log")
                .addTexture(tex("large_stump/oak_large_stump_corner_inner_top"))
                .addTexture(tex("large_stump/oak_large_stump_corner_top"))
                .addTexture(tex("large_stump/oak_large_stump_double_top"))
                .addTexture(tex("large_stump/oak_large_stump_middle_top"))
                .addTag(BlockTags.AXE_MINEABLE, RegistryKeys.BLOCK)
                .setTab(() -> ModItemGroups.COZY_HOME)
                .defaultRecipe()
                .build();
        this.addEntry(largeStumps);
    }

    private static Identifier tex(String path) {
        return Identifier.of(CozyHome.MOD_ID, "block/" + path);
    }
}
