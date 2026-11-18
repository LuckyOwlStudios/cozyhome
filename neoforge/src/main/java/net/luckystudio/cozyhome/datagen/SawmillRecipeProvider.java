package net.luckystudio.cozyhome.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.luckystudio.cozyhome.CozyHome;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Optional compat with Universal Sawmill (mod id {@code sawmill}): {@code sawmill:woodcutting} recipes that turn
 * planks or logs into Cozyhome's wooden furniture. Every file carries both a NeoForge and a Fabric load condition,
 * so the recipes are skipped entirely when the sawmill isn't installed.
 *
 * <p>Sawmill already generates recipes on its own for anything whose crafting recipe is made purely of wood
 * (chairs, tables, desks, drawers, large stumps). Those are left to Sawmill so the GUI doesn't show duplicates;
 * this provider covers the furniture that also needs a non-wood part (bricks, wool, a clock, ...), which
 * Sawmill's generator skips.
 *
 * <p>Costs are worked out from the crafting recipe: planks count as 1, slabs as 1/2, a chest as 8, and every
 * non-wood ingredient as 1 plank, so the sawmill stays roughly as expensive as the crafting table.
 */
public class SawmillRecipeProvider implements DataProvider {
    private static final String SAWMILL = "sawmill";

    /** Wood type -> [planks item, log item tag, planks per log]. */
    private record Wood(String name, String planks, String logTag, int planksPerLog) {}

    private static final List<Wood> WOODS = List.of(
            new Wood("oak", "minecraft:oak_planks", "minecraft:oak_logs", 4),
            new Wood("spruce", "minecraft:spruce_planks", "minecraft:spruce_logs", 4),
            new Wood("birch", "minecraft:birch_planks", "minecraft:birch_logs", 4),
            new Wood("jungle", "minecraft:jungle_planks", "minecraft:jungle_logs", 4),
            new Wood("acacia", "minecraft:acacia_planks", "minecraft:acacia_logs", 4),
            new Wood("dark_oak", "minecraft:dark_oak_planks", "minecraft:dark_oak_logs", 4),
            new Wood("mangrove", "minecraft:mangrove_planks", "minecraft:mangrove_logs", 4),
            new Wood("cherry", "minecraft:cherry_planks", "minecraft:cherry_logs", 4),
            new Wood("bamboo", "minecraft:bamboo_planks", "minecraft:bamboo_blocks", 2),
            new Wood("crimson", "minecraft:crimson_planks", "minecraft:crimson_stems", 4),
            new Wood("warped", "minecraft:warped_planks", "minecraft:warped_stems", 4)
    );

    /** Furniture suffix -> cost in planks for ONE item, derived from its crafting recipe. */
    private static final Map<String, Double> COSTS = new LinkedHashMap<>();

    static {
        // 5 planks + 3 counter-top blocks -> 3
        COSTS.put("counter", 8 / 3.0);
        // 5 planks + chest (8 planks) + 3 counter-top blocks -> 3
        COSTS.put("storage_counter", 16 / 3.0);
        // 5 planks + 3 counter-top blocks + cauldron -> 3
        COSTS.put("sink_counter", 9 / 3.0);
        // 4 slabs + clock -> 3
        COSTS.put("wall_clock", 3 / 3.0);
        // 2 planks + wall clock (1) -> 3
        COSTS.put("grandfather_clock", 3 / 3.0);
        // 8 planks + amethyst shard -> 3
        COSTS.put("wall_mirror", 9 / 3.0);
        // 1 plank + wool + lantern -> 1
        COSTS.put("lamp", 3.0);
        // 2 slabs + 4 wool -> 1
        COSTS.put("sofa", 5.0);
        // 2 slabs + 2 wool -> 1
        COSTS.put("couch", 3.0);
    }

    private final DataOutput.PathResolver recipePathResolver;

    public SawmillRecipeProvider(DataOutput output) {
        this.recipePathResolver = output.getResolver(DataOutput.OutputType.DATA_PACK, "recipe");
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (Wood wood : WOODS) {
            for (Map.Entry<String, Double> entry : COSTS.entrySet()) {
                String path = wood.name() + "_" + entry.getKey();
                Identifier result = CozyHome.id(path);
                if (!Registries.ITEM.containsId(result)) {
                    throw new IllegalStateException("Sawmill recipe result " + result + " is not a registered item");
                }
                double cost = entry.getValue();

                JsonObject planks = new JsonObject();
                planks.addProperty("item", wood.planks());
                futures.add(write(writer, "sawmill/" + path + "_from_planks", planks, 1, result, cost));

                JsonObject logs = new JsonObject();
                logs.addProperty("tag", wood.logTag());
                futures.add(write(writer, "sawmill/" + path + "_from_logs", logs, wood.planksPerLog(), result, cost));
            }
        }
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    /**
     * @param inputValue how many planks one input item is worth
     * @param cost       planks needed for one result item
     */
    private CompletableFuture<?> write(DataWriter writer, String id, JsonObject ingredient, int inputValue,
                                       Identifier result, double cost) {
        int inputCount;
        int outputCount;
        double perInput = inputValue / cost;
        if (perInput >= 1) {
            inputCount = 1;
            outputCount = (int) Math.round(perInput);
        } else {
            inputCount = (int) Math.ceil(cost / inputValue);
            outputCount = 1;
        }

        JsonObject json = new JsonObject();
        json.addProperty("type", SAWMILL + ":woodcutting");
        json.add("ingredient", ingredient);
        json.addProperty("ingredient_count", inputCount);
        JsonObject resultJson = new JsonObject();
        resultJson.addProperty("count", outputCount);
        resultJson.addProperty("id", result.toString());
        json.add("result", resultJson);

        // NeoForge: skip unless the sawmill mod is loaded
        JsonObject neoCondition = new JsonObject();
        neoCondition.addProperty("type", "neoforge:mod_loaded");
        neoCondition.addProperty("modid", SAWMILL);
        JsonArray neoConditions = new JsonArray();
        neoConditions.add(neoCondition);
        json.add("neoforge:conditions", neoConditions);

        // Fabric: same, via Fabric API resource conditions
        JsonObject fabricCondition = new JsonObject();
        fabricCondition.addProperty("condition", "fabric:all_mods_loaded");
        JsonArray mods = new JsonArray();
        mods.add(SAWMILL);
        fabricCondition.add("values", mods);
        JsonArray fabricConditions = new JsonArray();
        fabricConditions.add(fabricCondition);
        json.add("fabric:load_conditions", fabricConditions);

        return DataProvider.writeToPath(writer, json, recipePathResolver.resolveJson(CozyHome.id(id)));
    }

    @Override
    public String getName() {
        return "Universal Sawmill recipes";
    }
}
