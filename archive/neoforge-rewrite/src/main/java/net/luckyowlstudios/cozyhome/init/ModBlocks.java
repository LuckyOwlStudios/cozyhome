package net.luckyowlstudios.cozyhome.init;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.block.templates.VerticalConnectingBlock;
import net.luckyowlstudios.cozyhome.block.types.*;
import net.luckyowlstudios.cozyhome.block.types.chimney.ChimneyBlock;
import net.luckyowlstudios.cozyhome.block.types.sink_counter.SinkCounterBlock;
import net.luckyowlstudios.cozyhome.block.types.storage_counter.StorageCounterBlock;
import net.luckyowlstudios.cozyhome.util.MaterialTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Cozyhome.MOD_ID);

    // PLANKED WALLS
    public static final DeferredBlock<Block> OAK_PLANKED_WALL = registerBlock("oak_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_PLANKED_WALL = registerBlock("spruce_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_PLANKED_WALL = registerBlock("birch_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_PLANKED_WALL = registerBlock("jungle_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_PLANKED_WALL = registerBlock("acacia_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_PLANKED_WALL = registerBlock("dark_oak_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_PLANKED_WALL = registerBlock("mangrove_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_PLANKED_WALL = registerBlock("cherry_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> BAMBOO_PLANKED_WALL = registerBlock("bamboo_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_PLANKED_WALL = registerBlock("crimson_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_PLANKED_WALL = registerBlock("warped_planked_wall",
            () -> new VerticalConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));

    // COUNTERS
    public static final DeferredBlock<Block> OAK_COUNTER = registerBlock("oak_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_COUNTER = registerBlock("spruce_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_COUNTER = registerBlock("birch_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_COUNTER = registerBlock("jungle_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_COUNTER = registerBlock("acacia_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_COUNTER = registerBlock("dark_oak_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_COUNTER = registerBlock("mangrove_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_COUNTER = registerBlock("cherry_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> BAMBOO_COUNTER = registerBlock("bamboo_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_COUNTER = registerBlock("crimson_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_COUNTER = registerBlock("warped_counter",
            () -> new CounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));

    // STORAGE COUNTERS
    public static final DeferredBlock<Block> OAK_STORAGE_COUNTER = registerBlock("oak_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_STORAGE_COUNTER = registerBlock("spruce_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.SPRUCE, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_STORAGE_COUNTER = registerBlock("birch_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.BIRCH, BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_STORAGE_COUNTER = registerBlock("jungle_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.JUNGLE, BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_STORAGE_COUNTER = registerBlock("acacia_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.ACACIA, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_STORAGE_COUNTER = registerBlock("dark_oak_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.DARK_OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_STORAGE_COUNTER = registerBlock("mangrove_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.MANGROVE, BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_STORAGE_COUNTER = registerBlock("cherry_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.CHERRY, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> BAMBOO_STORAGE_COUNTER = registerBlock("bamboo_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.BAMBOO, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_STORAGE_COUNTER = registerBlock("crimson_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.CRIMSON, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_STORAGE_COUNTER = registerBlock("warped_storage_counter",
            () -> new StorageCounterBlock(MaterialTypes.WARPED, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));

    // SINK COUNTERS
    public static final DeferredBlock<Block> OAK_SINK_COUNTER = registerBlock("oak_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_SINK_COUNTER = registerBlock("spruce_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_SINK_COUNTER = registerBlock("birch_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_SINK_COUNTER = registerBlock("jungle_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_SINK_COUNTER = registerBlock("acacia_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_SINK_COUNTER = registerBlock("dark_oak_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_SINK_COUNTER = registerBlock("mangrove_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_SINK_COUNTER = registerBlock("cherry_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> BAMBOO_SINK_COUNTER = registerBlock("bamboo_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_SINK_COUNTER = registerBlock("crimson_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_SINK_COUNTER = registerBlock("warped_sink_counter",
            () -> new SinkCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));

    // TABLES
    public static final DeferredBlock<Block> OAK_TABLE = registerBlock("oak_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_TABLE = registerBlock("spruce_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_TABLE = registerBlock("birch_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_TABLE = registerBlock("jungle_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_TABLE = registerBlock("acacia_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_TABLE = registerBlock("dark_oak_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_TABLE = registerBlock("mangrove_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_TABLE = registerBlock("cherry_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> BAMBOO_TABLE = registerBlock("bamboo_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_TABLE = registerBlock("crimson_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_TABLE = registerBlock("warped_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));

    // SHORT TABLES
    public static final DeferredBlock<Block> OAK_SHORT_TABLE = registerBlock("oak_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_SHORT_TABLE = registerBlock("spruce_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_SHORT_TABLE = registerBlock("birch_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_SHORT_TABLE = registerBlock("jungle_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_SHORT_TABLE = registerBlock("acacia_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_SHORT_TABLE = registerBlock("dark_oak_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_SHORT_TABLE = registerBlock("mangrove_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_SHORT_TABLE = registerBlock("cherry_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> BAMBOO_SHORT_TABLE = registerBlock("bamboo_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_SHORT_TABLE = registerBlock("crimson_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_SHORT_TABLE = registerBlock("warped_short_table",
            () -> new ShortTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));

    // CHIMNEYS
    public static final DeferredBlock<Block> STONE_BRICK_CHIMNEY = registerBlock("stone_brick_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final DeferredBlock<Block> MOSSY_STONE_BRICK_CHIMNEY = registerBlock("mossy_stone_brick_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSSY_STONE_BRICKS)));
    public static final DeferredBlock<Block> GRANITE_CHIMNEY = registerBlock("granite_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRANITE)));
    public static final DeferredBlock<Block> DIORITE_CHIMNEY = registerBlock("diorite_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIORITE)));
    public static final DeferredBlock<Block> ANDESITE_CHIMNEY = registerBlock("andesite_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ANDESITE)));
    public static final DeferredBlock<Block> DEEPSLATE_CHIMNEY = registerBlock("deepslate_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS)));
    public static final DeferredBlock<Block> CALCITE_CHIMNEY = registerBlock("calcite_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE)));
    public static final DeferredBlock<Block> TUFF_CHIMNEY = registerBlock("tuff_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_TUFF)));
    public static final DeferredBlock<Block> BRICK_CHIMNEY = registerBlock("brick_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
    public static final DeferredBlock<Block> MUD_CHIMNEY = registerBlock("mud_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS)));
    public static final DeferredBlock<Block> SANDSTONE_CHIMNEY = registerBlock("sandstone_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE)));
    public static final DeferredBlock<Block> RED_SANDSTONE_CHIMNEY = registerBlock("red_sandstone_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_SANDSTONE)));
    public static final DeferredBlock<Block> PRISMARINE_CHIMNEY = registerBlock("prismarine_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE)));
    public static final DeferredBlock<Block> NETHER_BRICK_CHIMNEY = registerBlock("nether_brick_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS)));
    public static final DeferredBlock<Block> RED_NETHER_BRICK_CHIMNEY = registerBlock("red_nether_brick_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_NETHER_BRICKS)));
    public static final DeferredBlock<Block> BLACKSTONE_CHIMNEY = registerBlock("blackstone_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE)));
    public static final DeferredBlock<Block> ENDSTONE_CHIMNEY = registerBlock("endstone_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE)));
    public static final DeferredBlock<Block> PURPUR_CHIMNEY = registerBlock("purpur_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPUR_BLOCK)));
    public static final DeferredBlock<Block> IRON_CHIMNEY = registerBlock("iron_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> GOLD_CHIMNEY = registerBlock("gold_chimney",
            () -> new ChimneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)));

    // RAILINGS
    public static final DeferredBlock<Block> OAK_RAILING = registerBlock("oak_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_RAILING = registerBlock("spruce_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_RAILING = registerBlock("birch_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_RAILING = registerBlock("jungle_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_RAILING = registerBlock("acacia_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_RAILING = registerBlock("dark_oak_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_RAILING = registerBlock("mangrove_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_RAILING = registerBlock("cherry_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> BAMBOO_RAILING = registerBlock("bamboo_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_RAILING = registerBlock("crimson_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_RAILING = registerBlock("warped_railing",
            () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));

    public static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}