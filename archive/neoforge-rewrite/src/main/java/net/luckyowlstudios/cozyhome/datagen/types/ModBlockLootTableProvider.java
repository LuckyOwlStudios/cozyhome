package net.luckyowlstudios.cozyhome.datagen.types;

import net.luckyowlstudios.cozyhome.init.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        // PLANKED WALLS
        this.dropSelf(ModBlocks.OAK_PLANKED_WALL.get());
        this.dropSelf(ModBlocks.SPRUCE_PLANKED_WALL.get());
        this.dropSelf(ModBlocks.BIRCH_PLANKED_WALL.get());
        this.dropSelf(ModBlocks.JUNGLE_PLANKED_WALL.get());
        this.dropSelf(ModBlocks.ACACIA_PLANKED_WALL.get());
        this.dropSelf(ModBlocks.DARK_OAK_PLANKED_WALL.get());
        this.dropSelf(ModBlocks.MANGROVE_PLANKED_WALL.get());
        this.dropSelf(ModBlocks.CHERRY_PLANKED_WALL.get());
        this.dropSelf(ModBlocks.BAMBOO_PLANKED_WALL.get());
        this.dropSelf(ModBlocks.CRIMSON_PLANKED_WALL.get());
        this.dropSelf(ModBlocks.WARPED_PLANKED_WALL.get());

        // COUNTERS
        this.dropSelf(ModBlocks.OAK_COUNTER.get());
        this.dropSelf(ModBlocks.SPRUCE_COUNTER.get());
        this.dropSelf(ModBlocks.BIRCH_COUNTER.get());
        this.dropSelf(ModBlocks.JUNGLE_COUNTER.get());
        this.dropSelf(ModBlocks.ACACIA_COUNTER.get());
        this.dropSelf(ModBlocks.DARK_OAK_COUNTER.get());
        this.dropSelf(ModBlocks.MANGROVE_COUNTER.get());
        this.dropSelf(ModBlocks.CHERRY_COUNTER.get());
        this.dropSelf(ModBlocks.BAMBOO_COUNTER.get());
        this.dropSelf(ModBlocks.CRIMSON_COUNTER.get());
        this.dropSelf(ModBlocks.WARPED_COUNTER.get());

        // STORAGE COUNTERS
        this.dropSelf(ModBlocks.OAK_STORAGE_COUNTER.get());
        this.dropSelf(ModBlocks.SPRUCE_STORAGE_COUNTER.get());
        this.dropSelf(ModBlocks.BIRCH_STORAGE_COUNTER.get());
        this.dropSelf(ModBlocks.JUNGLE_STORAGE_COUNTER.get());
        this.dropSelf(ModBlocks.ACACIA_STORAGE_COUNTER.get());
        this.dropSelf(ModBlocks.DARK_OAK_STORAGE_COUNTER.get());
        this.dropSelf(ModBlocks.MANGROVE_STORAGE_COUNTER.get());
        this.dropSelf(ModBlocks.CHERRY_STORAGE_COUNTER.get());
        this.dropSelf(ModBlocks.BAMBOO_STORAGE_COUNTER.get());
        this.dropSelf(ModBlocks.CRIMSON_STORAGE_COUNTER.get());
        this.dropSelf(ModBlocks.WARPED_STORAGE_COUNTER.get());

        // SINK COUNTERS
        this.dropSelf(ModBlocks.OAK_SINK_COUNTER.get());
        this.dropSelf(ModBlocks.SPRUCE_SINK_COUNTER.get());
        this.dropSelf(ModBlocks.BIRCH_SINK_COUNTER.get());
        this.dropSelf(ModBlocks.JUNGLE_SINK_COUNTER.get());
        this.dropSelf(ModBlocks.ACACIA_SINK_COUNTER.get());
        this.dropSelf(ModBlocks.DARK_OAK_SINK_COUNTER.get());
        this.dropSelf(ModBlocks.MANGROVE_SINK_COUNTER.get());
        this.dropSelf(ModBlocks.CHERRY_SINK_COUNTER.get());
        this.dropSelf(ModBlocks.BAMBOO_SINK_COUNTER.get());
        this.dropSelf(ModBlocks.CRIMSON_SINK_COUNTER.get());
        this.dropSelf(ModBlocks.WARPED_SINK_COUNTER.get());

        // TABLES
        this.dropSelf(ModBlocks.OAK_TABLE.get());
        this.dropSelf(ModBlocks.SPRUCE_TABLE.get());
        this.dropSelf(ModBlocks.BIRCH_TABLE.get());
        this.dropSelf(ModBlocks.JUNGLE_TABLE.get());
        this.dropSelf(ModBlocks.ACACIA_TABLE.get());
        this.dropSelf(ModBlocks.DARK_OAK_TABLE.get());
        this.dropSelf(ModBlocks.MANGROVE_TABLE.get());
        this.dropSelf(ModBlocks.CHERRY_TABLE.get());
        this.dropSelf(ModBlocks.BAMBOO_TABLE.get());
        this.dropSelf(ModBlocks.CRIMSON_TABLE.get());
        this.dropSelf(ModBlocks.WARPED_TABLE.get());

        // SHORT TABLES
        this.dropSelf(ModBlocks.OAK_SHORT_TABLE.get());
        this.dropSelf(ModBlocks.SPRUCE_SHORT_TABLE.get());
        this.dropSelf(ModBlocks.BIRCH_SHORT_TABLE.get());
        this.dropSelf(ModBlocks.JUNGLE_SHORT_TABLE.get());
        this.dropSelf(ModBlocks.ACACIA_SHORT_TABLE.get());
        this.dropSelf(ModBlocks.DARK_OAK_SHORT_TABLE.get());
        this.dropSelf(ModBlocks.MANGROVE_SHORT_TABLE.get());
        this.dropSelf(ModBlocks.CHERRY_SHORT_TABLE.get());
        this.dropSelf(ModBlocks.BAMBOO_SHORT_TABLE.get());
        this.dropSelf(ModBlocks.CRIMSON_SHORT_TABLE.get());
        this.dropSelf(ModBlocks.WARPED_SHORT_TABLE.get());

        // CHIMNEYS
        this.dropSelf(ModBlocks.STONE_BRICK_CHIMNEY.get());
        this.dropSelf(ModBlocks.MOSSY_STONE_BRICK_CHIMNEY.get());
        this.dropSelf(ModBlocks.GRANITE_CHIMNEY.get());
        this.dropSelf(ModBlocks.DIORITE_CHIMNEY.get());
        this.dropSelf(ModBlocks.ANDESITE_CHIMNEY.get());
        this.dropSelf(ModBlocks.DEEPSLATE_CHIMNEY.get());
        this.dropSelf(ModBlocks.CALCITE_CHIMNEY.get());
        this.dropSelf(ModBlocks.TUFF_CHIMNEY.get());
        this.dropSelf(ModBlocks.BRICK_CHIMNEY.get());
        this.dropSelf(ModBlocks.MUD_CHIMNEY.get());
        this.dropSelf(ModBlocks.SANDSTONE_CHIMNEY.get());
        this.dropSelf(ModBlocks.RED_SANDSTONE_CHIMNEY.get());
        this.dropSelf(ModBlocks.PRISMARINE_CHIMNEY.get());
        this.dropSelf(ModBlocks.NETHER_BRICK_CHIMNEY.get());
        this.dropSelf(ModBlocks.RED_NETHER_BRICK_CHIMNEY.get());
        this.dropSelf(ModBlocks.BLACKSTONE_CHIMNEY.get());
        this.dropSelf(ModBlocks.ENDSTONE_CHIMNEY.get());
        this.dropSelf(ModBlocks.PURPUR_CHIMNEY.get());
        this.dropSelf(ModBlocks.IRON_CHIMNEY.get());
        this.dropSelf(ModBlocks.GOLD_CHIMNEY.get());

        // RAILINGS
        this.dropSelf(ModBlocks.OAK_RAILING.get());
        this.dropSelf(ModBlocks.SPRUCE_RAILING.get());
        this.dropSelf(ModBlocks.BIRCH_RAILING.get());
        this.dropSelf(ModBlocks.JUNGLE_RAILING.get());
        this.dropSelf(ModBlocks.ACACIA_RAILING.get());
        this.dropSelf(ModBlocks.DARK_OAK_RAILING.get());
        this.dropSelf(ModBlocks.MANGROVE_RAILING.get());
        this.dropSelf(ModBlocks.CHERRY_RAILING.get());
        this.dropSelf(ModBlocks.BAMBOO_RAILING.get());
        this.dropSelf(ModBlocks.CRIMSON_RAILING.get());
        this.dropSelf(ModBlocks.WARPED_RAILING.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
