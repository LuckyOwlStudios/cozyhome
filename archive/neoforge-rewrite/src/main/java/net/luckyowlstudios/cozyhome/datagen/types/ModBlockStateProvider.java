package net.luckyowlstudios.cozyhome.datagen.types;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.luckyowlstudios.cozyhome.block.templates.PlaneConnectingBlock;
import net.luckyowlstudios.cozyhome.block.types.RailingBlock;
import net.luckyowlstudios.cozyhome.block.util.ModBlockStateProperties;
import net.luckyowlstudios.cozyhome.block.util.enums.VerticalConnection;
import net.luckyowlstudios.cozyhome.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Cozyhome.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        // PLANKED WALLS
        plankedWallBlock(ModBlocks.OAK_PLANKED_WALL.get());
        plankedWallBlock(ModBlocks.SPRUCE_PLANKED_WALL.get());
        plankedWallBlock(ModBlocks.BIRCH_PLANKED_WALL.get());
        plankedWallBlock(ModBlocks.JUNGLE_PLANKED_WALL.get());
        plankedWallBlock(ModBlocks.ACACIA_PLANKED_WALL.get());
        plankedWallBlock(ModBlocks.DARK_OAK_PLANKED_WALL.get());
        plankedWallBlock(ModBlocks.MANGROVE_PLANKED_WALL.get());
        plankedWallBlock(ModBlocks.CHERRY_PLANKED_WALL.get());
        plankedWallBlock(ModBlocks.BAMBOO_PLANKED_WALL.get());
        plankedWallBlock(ModBlocks.CRIMSON_PLANKED_WALL.get());
        plankedWallBlock(ModBlocks.WARPED_PLANKED_WALL.get());

        // COUNTERS
        counterBlock(ModBlocks.OAK_COUNTER.get());
        counterBlock(ModBlocks.SPRUCE_COUNTER.get());
        counterBlock(ModBlocks.BIRCH_COUNTER.get());
        counterBlock(ModBlocks.JUNGLE_COUNTER.get());
        counterBlock(ModBlocks.ACACIA_COUNTER.get());
        counterBlock(ModBlocks.DARK_OAK_COUNTER.get());
        counterBlock(ModBlocks.MANGROVE_COUNTER.get());
        counterBlock(ModBlocks.CHERRY_COUNTER.get());
        counterBlock(ModBlocks.BAMBOO_COUNTER.get());
        counterBlock(ModBlocks.CRIMSON_COUNTER.get());
        counterBlock(ModBlocks.WARPED_COUNTER.get());

        // STORAGE COUNTERS
        storageCounterBlock(ModBlocks.OAK_STORAGE_COUNTER.get());
        storageCounterBlock(ModBlocks.SPRUCE_STORAGE_COUNTER.get());
        storageCounterBlock(ModBlocks.BIRCH_STORAGE_COUNTER.get());
        storageCounterBlock(ModBlocks.JUNGLE_STORAGE_COUNTER.get());
        storageCounterBlock(ModBlocks.ACACIA_STORAGE_COUNTER.get());
        storageCounterBlock(ModBlocks.DARK_OAK_STORAGE_COUNTER.get());
        storageCounterBlock(ModBlocks.MANGROVE_STORAGE_COUNTER.get());
        storageCounterBlock(ModBlocks.CHERRY_STORAGE_COUNTER.get());
        storageCounterBlock(ModBlocks.BAMBOO_STORAGE_COUNTER.get());
        storageCounterBlock(ModBlocks.CRIMSON_STORAGE_COUNTER.get());
        storageCounterBlock(ModBlocks.WARPED_STORAGE_COUNTER.get());

        // SINK COUNTERS
        sinkCounterBlock(ModBlocks.OAK_SINK_COUNTER.get());
        sinkCounterBlock(ModBlocks.SPRUCE_SINK_COUNTER.get());
        sinkCounterBlock(ModBlocks.BIRCH_SINK_COUNTER.get());
        sinkCounterBlock(ModBlocks.JUNGLE_SINK_COUNTER.get());
        sinkCounterBlock(ModBlocks.ACACIA_SINK_COUNTER.get());
        sinkCounterBlock(ModBlocks.DARK_OAK_SINK_COUNTER.get());
        sinkCounterBlock(ModBlocks.MANGROVE_SINK_COUNTER.get());
        sinkCounterBlock(ModBlocks.CHERRY_SINK_COUNTER.get());
        sinkCounterBlock(ModBlocks.BAMBOO_SINK_COUNTER.get());
        sinkCounterBlock(ModBlocks.CRIMSON_SINK_COUNTER.get());
        sinkCounterBlock(ModBlocks.WARPED_SINK_COUNTER.get());

        // TABLES
        tableBlock(ModBlocks.OAK_TABLE.get());
        tableBlock(ModBlocks.SPRUCE_TABLE.get());
        tableBlock(ModBlocks.BIRCH_TABLE.get());
        tableBlock(ModBlocks.JUNGLE_TABLE.get());
        tableBlock(ModBlocks.ACACIA_TABLE.get());
        tableBlock(ModBlocks.DARK_OAK_TABLE.get());
        tableBlock(ModBlocks.MANGROVE_TABLE.get());
        tableBlock(ModBlocks.CHERRY_TABLE.get());
        tableBlock(ModBlocks.BAMBOO_TABLE.get());
        tableBlock(ModBlocks.CRIMSON_TABLE.get());
        tableBlock(ModBlocks.WARPED_TABLE.get());

        // SHORT TABLES
        shortTableBlock(ModBlocks.OAK_SHORT_TABLE.get());
        shortTableBlock(ModBlocks.SPRUCE_SHORT_TABLE.get());
        shortTableBlock(ModBlocks.BIRCH_SHORT_TABLE.get());
        shortTableBlock(ModBlocks.JUNGLE_SHORT_TABLE.get());
        shortTableBlock(ModBlocks.ACACIA_SHORT_TABLE.get());
        shortTableBlock(ModBlocks.DARK_OAK_SHORT_TABLE.get());
        shortTableBlock(ModBlocks.MANGROVE_SHORT_TABLE.get());
        shortTableBlock(ModBlocks.CHERRY_SHORT_TABLE.get());
        shortTableBlock(ModBlocks.BAMBOO_SHORT_TABLE.get());
        shortTableBlock(ModBlocks.CRIMSON_SHORT_TABLE.get());
        shortTableBlock(ModBlocks.WARPED_SHORT_TABLE.get());

        // RAILINGS
        railingBlock(ModBlocks.OAK_RAILING.get(), ResourceLocation.parse("block/oak_planks"));
        railingBlock(ModBlocks.SPRUCE_RAILING.get(), ResourceLocation.parse("block/spruce_planks"));
        railingBlock(ModBlocks.BIRCH_RAILING.get(), ResourceLocation.parse("block/birch_planks"));
        railingBlock(ModBlocks.JUNGLE_RAILING.get(), ResourceLocation.parse("block/jungle_planks"));
        railingBlock(ModBlocks.ACACIA_RAILING.get(), ResourceLocation.parse("block/acacia_planks"));
        railingBlock(ModBlocks.DARK_OAK_RAILING.get(), ResourceLocation.parse("block/dark_oak_planks"));
        railingBlock(ModBlocks.MANGROVE_RAILING.get(), ResourceLocation.parse("block/mangrove_planks"));
        railingBlock(ModBlocks.CHERRY_RAILING.get(), ResourceLocation.parse("block/cherry_planks"));
        railingBlock(ModBlocks.BAMBOO_RAILING.get(), ResourceLocation.parse("block/bamboo_planks"));
        railingBlock(ModBlocks.CRIMSON_RAILING.get(), ResourceLocation.parse("block/crimson_planks"));
        railingBlock(ModBlocks.WARPED_RAILING.get(), ResourceLocation.parse("block/warped_planks"));
    }

    private void counterBlock(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile straight = models().withExistingParent(name, modLoc("block/counter/counter"))
                .texture("top", modLoc("block/counter/" + name + "_top"))
                .texture("side", modLoc("block/counter/" + name + "_side"))
                .texture("bottom", modLoc("block/counter/" + name + "_bottom"))
                .texture("front", modLoc("block/counter/" + name + "_front"))
                .texture("back", modLoc("block/counter/" + name + "_front"))
                .texture("particle", modLoc("block/counter/" + name + "_front"));
        ModelFile inner_corner = models().withExistingParent(name + "_inner", modLoc("block/counter/counter_inner"))
                .texture("top", modLoc("block/counter/" + name + "_inner_top"))
                .texture("side", modLoc("block/counter/" + name + "_side"))
                .texture("bottom", modLoc("block/counter/" + name + "_inner_bottom"))
                .texture("front", modLoc("block/counter/" + name + "_front"))
                .texture("back", modLoc("block/counter/" + name + "_front"))
                .texture("particle", modLoc("block/counter/" + name + "_front"));
        ModelFile outer_corner = models().withExistingParent(name + "_outer", modLoc("block/counter/counter_outer"))
                .texture("top", modLoc("block/counter/" + name + "_outer_top"))
                .texture("side", modLoc("block/counter/" + name + "_side"))
                .texture("bottom", modLoc("block/counter/" + name + "_outer_bottom"))
                .texture("front", modLoc("block/counter/" + name + "_outer_side"))
                .texture("particle", modLoc("block/counter/" + name + "_front"));
        horizontalCornerBlock(block, straight, inner_corner, outer_corner);
        this.itemModels().simpleBlockItem(block);
    }

    private void storageCounterBlock(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile straight = models().withExistingParent(name, modLoc("block/counter/counter"))
                .texture("top", modLoc("block/counter/" + name.replace("_storage", "") + "_top"))
                .texture("side", modLoc("block/counter/" + name.replace("_storage", "") + "_side"))
                .texture("bottom", modLoc("block/counter/" + name.replace("_storage", "") + "_bottom"))
                .texture("front", modLoc("block/counter/" + name.replace("_storage", "") + "_open"))
                .texture("back", modLoc("block/counter/" + name.replace("_storage", "") + "_front"))
                .texture("particle", modLoc("block/counter/" + name.replace("_storage", "") + "_front"));
        this.horizontalBlock(block, straight);
        this.itemModels().simpleBlockItem(block);
    }

    private void sinkCounterBlock(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile modelFile = models().withExistingParent(name, modLoc("block/counter/counter"))
                .texture("top", modLoc("block/counter/" + name.replace("_sink", "") + "_sink_top"))
                .texture("side", modLoc("block/counter/" + name.replace("_sink", "") + "_side"))
                .texture("bottom", modLoc("block/counter/" + name.replace("_sink", "") + "_bottom"))
                .texture("front", modLoc("block/counter/" + name.replace("_sink", "") + "_front"))
                .texture("back", modLoc("block/counter/" + name.replace("_sink", "") + "_back"))
                .texture("inner_side", modLoc("block/counter/" + name.replace("_sink", "") + "_back"))
                .texture("inner_bottom", modLoc("block/counter/sink_inner_side"))
                .texture("faucet", modLoc("block/faucet/iron_faucet"))
                .texture("particle", modLoc("block/counter/sink_inner_bottom"));
        this.horizontalBlock(block, modelFile);
        this.itemModels().simpleBlockItem(block);
    }

    private void railingBlock(Block block, ResourceLocation breakParticle) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile straight = models().withExistingParent(name, modLoc("block/railing/railing"))
                .texture("top", modLoc("block/railing/" + name))
                .texture("side", modLoc("block/railing/" + name + "_parts"))
                .texture("particle", breakParticle)
                .renderType("cutout");
        ModelFile inner_corner = models().withExistingParent(name + "_inner", modLoc("block/railing/inner_railing"))
                .texture("top", modLoc("block/railing/" + name))
                .texture("side", modLoc("block/railing/" + name + "_parts"))
                .texture("particle", breakParticle)
                .renderType("cutout");
        ModelFile outer_corner = models().withExistingParent(name + "_outer", modLoc("block/railing/outer_railing"))
                .texture("top", modLoc("block/railing/" + name))
                .texture("side", modLoc("block/railing/" + name + "_parts"))
                .texture("particle", breakParticle)
                .renderType("cutout");
        ModelFile straight_bottom = models().withExistingParent(name + "_bottom", modLoc("block/railing/railing_bottom"))
                .texture("top", modLoc("block/railing/" + name))
                .texture("side", modLoc("block/railing/" + name + "_parts"))
                .texture("particle", breakParticle)
                .renderType("cutout");
        ModelFile inner_corner_bottom = models().withExistingParent(name + "_inner_bottom", modLoc("block/railing/inner_railing_bottom"))
                .texture("top", modLoc("block/railing/" + name))
                .texture("side", modLoc("block/railing/" + name + "_parts"))
                .texture("particle", breakParticle)
                .renderType("cutout");
        ModelFile outer_corner_bottom = models().withExistingParent(name + "_outer_bottom", modLoc("block/railing/outer_railing_bottom"))
                .texture("top", modLoc("block/railing/" + name))
                .texture("side", modLoc("block/railing/" + name + "_parts"))
                .texture("particle", breakParticle)
                .renderType("cutout");
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            StairsShape stairsShape = state.getValue(BlockStateProperties.STAIRS_SHAPE);
            boolean bottom = state.getValue(RailingBlock.BOTTOM);

            ModelFile selectedModel = switch (stairsShape) {
                case INNER_LEFT, INNER_RIGHT -> bottom ? inner_corner_bottom : inner_corner;
                case OUTER_LEFT, OUTER_RIGHT -> bottom ? outer_corner_bottom : outer_corner;
                default -> bottom ? straight_bottom : straight;
            };
            // Determine if we need UV rotation for right-hand variants
            boolean needsUVRotation = stairsShape == StairsShape.INNER_RIGHT || stairsShape == StairsShape.OUTER_RIGHT;
            return ConfiguredModel.builder()
                    .modelFile(selectedModel)
                    .rotationY((int) ((facing.getOpposite()).toYRot() + (needsUVRotation ? 90F : 0F)))
                    .build();
        });
        this.itemModels().simpleBlockItem(block);
    }

    private void horizontalCornerBlock(Block block, ModelFile straight, ModelFile inner_corner, ModelFile outer_corner) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            StairsShape stairsShape = state.getValue(BlockStateProperties.STAIRS_SHAPE);
            ModelFile selectedModel = switch (stairsShape) {
                case INNER_LEFT, INNER_RIGHT -> inner_corner;
                case OUTER_LEFT, OUTER_RIGHT -> outer_corner;
                default -> straight;
            };
            // Determine if we need UV rotation for right-hand variants
            boolean needsUVRotation = stairsShape == StairsShape.INNER_RIGHT || stairsShape == StairsShape.OUTER_RIGHT;
            return ConfiguredModel.builder()
                    .modelFile(selectedModel)
                    .rotationY((int) ((facing.getOpposite()).toYRot() + (needsUVRotation ? 90F : 0F)))
                    .build();
        });
    }

    private void plankedWallBlock(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile none = models().cubeColumn(name, modLoc("block/planked_wall/" + name), modLoc("block/planked_wall/" + name + "_end"));
        ModelFile top = models().cubeColumn(name + "_top", modLoc("block/planked_wall/" + name + "_top"), modLoc("block/planked_wall/" + name + "_end"));
        ModelFile middle = models().cubeColumn(name + "_middle", modLoc("block/planked_wall/" + name + "_middle"), modLoc("block/planked_wall/" + name + "_end"));
        ModelFile bottom = models().cubeColumn(name + "_bottom", modLoc("block/planked_wall/" + name + "_bottom"), modLoc("block/planked_wall/" + name + "_end"));

        makeVerticalConnection(block, none, top, middle, bottom);
        this.itemModels().simpleBlockItem(block);
    }

    private void chimneyBlock(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile none = models().withExistingParent(name, modLoc("block/chimney/chimney"))
                .texture("top", "block/chimney/" + name + "_top")
                .texture("side", "block/chimney/" + name + "_side")
                .texture("intake_side", "block/chimney/" + name + "_intake_side")
                .texture("intake", "block/chimney/chimney_intake_iron");
        ModelFile top = models().withExistingParent(name + "_top", modLoc("block/chimney/chimney_top"))
                .texture("top", "block/chimney/" + name + "_top")
                .texture("side", "block/chimney/" + name + "_side")
                .texture("intake_side", "block/chimney/" + name + "_intake_side")
                .texture("intake", "block/chimney/chimney_intake_iron");
        ModelFile middle = models().cubeColumn(name + "_middle", modLoc("block/chimney/" + name + "_middle"), modLoc("block/chimney/" + name + "_end"));
        ModelFile bottom = models().cubeColumn(name + "_bottom", modLoc("block/chimney/" + name + "_bottom"), modLoc("block/chimney/" + name + "_end"));

        makeVerticalConnection(block, none, top, middle, bottom);
        this.itemModels().simpleBlockItem(block);
    }

    private void makeVerticalConnection(Block block, ModelFile none, ModelFile top, ModelFile middle, ModelFile bottom) {
        getVariantBuilder(block).forAllStates(state -> {
            VerticalConnection verticalConnection = state.getValue(ModBlockStateProperties.VERTICAL_CONNECTION);
            ModelFile selectedModel = switch (verticalConnection) {
                case TOP -> top;
                case MIDDLE -> middle;
                case BOTTOM -> bottom;
                default -> none;
            };
            return ConfiguredModel.builder()
                    .modelFile(selectedModel)
                    .build();
        });
    }

    private void tableBlock(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile full = models().withExistingParent(name, modLoc("block/table/table"))
                .texture("top", modLoc("block/table/" + name + "_top"))
                .texture("sides", modLoc("block/table/" + name + "_sides"))
                .texture("legs", modLoc("block/table/" + name + "_legs"))
                .renderType("cutout");
        ModelFile middlePiece = models().withExistingParent(name + "_middle_piece", modLoc("block/table/table_middle_piece"))
                .texture("top", modLoc("block/table/" + name + "_top"))
                .texture("sides", modLoc("block/table/" + name + "_sides"))
                .renderType("cutout");
        ModelFile innerCorner = models().withExistingParent(name + "_inner_corner", modLoc("block/table/table_inner_corner"))
                .texture("top", modLoc("block/table/" + name + "_top"))
                .texture("sides", modLoc("block/table/" + name + "_sides"))
                .texture("legs", modLoc("block/table/" + name + "_legs"))
                .renderType("cutout");
        ModelFile outerCorner = models().withExistingParent(name + "_outer_corner", modLoc("block/table/table_outer_corner"))
                .texture("top", modLoc("block/table/" + name + "_top"))
                .texture("sides", modLoc("block/table/" + name + "_sides"))
                .texture("legs", modLoc("block/table/" + name + "_legs"))
                .renderType("cutout");
        ModelFile leftSide = models().withExistingParent(name + "_left_side", modLoc("block/table/table_left_side"))
                .texture("top", modLoc("block/table/" + name + "_top"))
                .texture("sides", modLoc("block/table/" + name + "_sides"))
                .renderType("cutout");
        ModelFile rightSide = models().withExistingParent(name + "_right_side", modLoc("block/table/table_right_side"))
                .texture("top", modLoc("block/table/" + name + "_top"))
                .texture("sides", modLoc("block/table/" + name + "_sides"))
                .renderType("cutout");
        makePlaneConnection(block, full, middlePiece, innerCorner, outerCorner, leftSide, rightSide);
        this.itemModels().simpleBlockItem(block);
    }

    private void shortTableBlock(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile full = models().withExistingParent(name, modLoc("block/short_table/short_table"))
                .texture("top", modLoc("block/table/" + name.replace("_short", "") + "_top"))
                .texture("sides", modLoc("block/table/" + name.replace("_short", "") + "_sides"))
                .texture("legs", modLoc("block/table/" + name.replace("_short", "") + "_legs"))
                .renderType("cutout");
        ModelFile middlePiece = models().withExistingParent(name + "_middle_piece", modLoc("block/short_table/short_table_middle_piece"))
                .texture("top", modLoc("block/table/" + name.replace("_short", "") + "_top"))
                .texture("sides", modLoc("block/table/" + name.replace("_short", "") + "_sides"))
                .renderType("cutout");
        ModelFile innerCorner = models().withExistingParent(name + "_inner_corner", modLoc("block/short_table/short_table_inner_corner"))
                .texture("top", modLoc("block/table/" + name.replace("_short", "") + "_top"))
                .texture("sides", modLoc("block/table/" + name.replace("_short", "") + "_sides"))
                .texture("legs", modLoc("block/table/" + name.replace("_short", "") + "_legs"))
                .renderType("cutout");
        ModelFile outerCorner = models().withExistingParent(name + "_outer_corner", modLoc("block/short_table/short_table_outer_corner"))
                .texture("top", modLoc("block/table/" + name.replace("_short", "") + "_top"))
                .texture("sides", modLoc("block/table/" + name.replace("_short", "") + "_sides"))
                .texture("legs", modLoc("block/table/" + name.replace("_short", "") + "_legs"))
                .renderType("cutout");
        ModelFile leftSide = models().withExistingParent(name + "_left_side", modLoc("block/short_table/short_table_left_side"))
                .texture("top", modLoc("block/table/" + name.replace("_short", "") + "_top"))
                .texture("sides", modLoc("block/table/" + name.replace("_short", "") + "_sides"))
                .renderType("cutout");
        ModelFile rightSide = models().withExistingParent(name + "_right_side", modLoc("block/short_table/short_table_right_side"))
                .texture("top", modLoc("block/table/" + name.replace("_short", "") + "_top"))
                .texture("sides", modLoc("block/table/" + name.replace("_short", "") + "_sides"))
                .renderType("cutout");
        makePlaneConnection(block, full, middlePiece, innerCorner, outerCorner, leftSide, rightSide);
        this.itemModels().simpleBlockItem(block);
    }

    private void makePlaneConnection(Block block, ModelFile full, ModelFile middlePiece, ModelFile innerCorner, ModelFile outerCorner, ModelFile leftSide, ModelFile rightSide) {
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block).part().modelFile(full).addModel()
                .condition(PlaneConnectingBlock.NORTH, false)
                .condition(PlaneConnectingBlock.EAST, false)
                .condition(PlaneConnectingBlock.SOUTH, false)
                .condition(PlaneConnectingBlock.WEST, false)
                .condition(PlaneConnectingBlock.NORTH_EAST, false)
                .condition(PlaneConnectingBlock.NORTH_WEST, false)
                .condition(PlaneConnectingBlock.SOUTH_EAST, false)
                .condition(PlaneConnectingBlock.SOUTH_WEST, false)
                .end();

        // MIDDLE PIECES
        builder.part()
                .modelFile(middlePiece)
                .rotationY(0)
                .addModel()
                .condition(PlaneConnectingBlock.NORTH, true)
                .condition(PlaneConnectingBlock.WEST, true)
                .condition(PlaneConnectingBlock.NORTH_WEST, true)
        ;
        // North-East inner corner (90° rotation)
        // Shows when there are connections to North AND East, but NOT to North-East diagonal
        builder.part()
                .modelFile(middlePiece)
                .rotationY(90)
                .addModel()
                .condition(PlaneConnectingBlock.NORTH, true)
                .condition(PlaneConnectingBlock.EAST, true)
                .condition(PlaneConnectingBlock.NORTH_EAST, true)
        ;
        // South-East inner corner (180° rotation)
        // Shows when there are connections to South AND East, but NOT to South-East diagonal
        builder.part()
                .modelFile(middlePiece)
                .rotationY(180)
                .addModel()
                .condition(PlaneConnectingBlock.SOUTH, true)
                .condition(PlaneConnectingBlock.EAST, true)
                .condition(PlaneConnectingBlock.SOUTH_EAST, true)
        ;
        // South-West inner corner (270° rotation)
        // Shows when there are connections to South AND West, but NOT to South-West diagonal
        builder.part()
                .modelFile(middlePiece)
                .rotationY(270)
                .addModel()
                .condition(PlaneConnectingBlock.SOUTH, true)
                .condition(PlaneConnectingBlock.WEST, true)
                .condition(PlaneConnectingBlock.SOUTH_WEST, true)
        ;

        // INNER CORNERS
        // North-West inner corner (0° rotation)
        // Shows when there are connections to North AND West, but NOT to North-West diagonal
        builder.part()
                .modelFile(innerCorner)
                .rotationY(0)
                .addModel()
                .condition(PlaneConnectingBlock.NORTH, true)
                .condition(PlaneConnectingBlock.WEST, true)
                .condition(PlaneConnectingBlock.NORTH_WEST, false)
        ;
        // North-East inner corner (90° rotation)
        // Shows when there are connections to North AND East, but NOT to North-East diagonal
        builder.part()
                .modelFile(innerCorner)
                .rotationY(90)
                .addModel()
                .condition(PlaneConnectingBlock.NORTH, true)
                .condition(PlaneConnectingBlock.EAST, true)
                .condition(PlaneConnectingBlock.NORTH_EAST, false)
        ;
        // South-East inner corner (180° rotation)
        // Shows when there are connections to South AND East, but NOT to South-East diagonal
        builder.part()
                .modelFile(innerCorner)
                .rotationY(180)
                .addModel()
                .condition(PlaneConnectingBlock.SOUTH, true)
                .condition(PlaneConnectingBlock.EAST, true)
                .condition(PlaneConnectingBlock.SOUTH_EAST, false)
        ;
        // South-West inner corner (270° rotation)
        // Shows when there are connections to South AND West, but NOT to South-West diagonal
        builder.part()
                .modelFile(innerCorner)
                .rotationY(270)
                .addModel()
                .condition(PlaneConnectingBlock.SOUTH, true)
                .condition(PlaneConnectingBlock.WEST, true)
                .condition(PlaneConnectingBlock.SOUTH_WEST, false)
        ;

        // OUTER CORNERS
        // North-West corner (0° rotation)
        builder.part()
                .modelFile(outerCorner)
                .rotationY(0)
                .addModel()
                .condition(PlaneConnectingBlock.NORTH, false)
                .condition(PlaneConnectingBlock.WEST, false)
                .condition(PlaneConnectingBlock.NORTH_WEST, false)
        ;
        // North-East corner (90° rotation)
        builder.part()
                .modelFile(outerCorner)
                .rotationY(90)
                .addModel()
                .condition(PlaneConnectingBlock.NORTH, false)
                .condition(PlaneConnectingBlock.EAST, false)
                .condition(PlaneConnectingBlock.NORTH_EAST, false)
        ;
        // South-East corner (180° rotation)
        builder.part()
                .modelFile(outerCorner)
                .rotationY(180)
                .addModel()
                .condition(PlaneConnectingBlock.SOUTH, false)
                .condition(PlaneConnectingBlock.EAST, false)
                .condition(PlaneConnectingBlock.SOUTH_EAST, false)
        ;
        // South-West corner (270° rotation)
        builder.part()
                .modelFile(outerCorner)
                .rotationY(270)
                .addModel()
                .condition(PlaneConnectingBlock.SOUTH, false)
                .condition(PlaneConnectingBlock.WEST, false)
                .condition(PlaneConnectingBlock.SOUTH_WEST, false);

        // LEFT SIDE (relative to the side)
        // North side - left side (facing north, west is left)
        builder.part()
                .modelFile(leftSide)
                .rotationY(0)
                .addModel()
                .condition(PlaneConnectingBlock.SOUTH, true)
                .condition(PlaneConnectingBlock.EAST, false)
        ;
        // East side - left side (facing east, north is left)
        builder.part()
                .modelFile(leftSide)
                .rotationY(90)
                .addModel()
                .condition(PlaneConnectingBlock.WEST, true)
                .condition(PlaneConnectingBlock.SOUTH, false)
        ;
        // South side - left side (facing south, east is left)
        builder.part()
                .modelFile(leftSide)
                .rotationY(180)
                .addModel()
                .condition(PlaneConnectingBlock.NORTH, true)
                .condition(PlaneConnectingBlock.WEST, false)
        ;
        // West side - left side (facing west, south is left)
        builder.part()
                .modelFile(leftSide)
                .rotationY(270)
                .addModel()
                .condition(PlaneConnectingBlock.EAST, true)
                .condition(PlaneConnectingBlock.NORTH, false)
        ;

        // RIGHT SIDE (relative to the side)
        // North side - right side (facing north, east is right)
        builder.part()
                .modelFile(rightSide)
                .rotationY(0)
                .addModel()
                .condition(PlaneConnectingBlock.SOUTH, true)
                .condition(PlaneConnectingBlock.WEST, false)
        ;
        // East side - right side (facing east, south is right)
        builder.part()
                .modelFile(rightSide)
                .rotationY(90)
                .addModel()
                .condition(PlaneConnectingBlock.WEST, true)
                .condition(PlaneConnectingBlock.NORTH, false)
        ;
        // South side - right side (facing south, west is right)
        builder.part()
                .modelFile(rightSide)
                .rotationY(180)
                .addModel()
                .condition(PlaneConnectingBlock.NORTH, true)
                .condition(PlaneConnectingBlock.EAST, false)
        ;
        // West side - right side (facing west, north is right)
        builder.part()
                .modelFile(rightSide)
                .rotationY(270)
                .addModel()
                .condition(PlaneConnectingBlock.EAST, true)
                .condition(PlaneConnectingBlock.SOUTH, false)
        ;
    }
}
