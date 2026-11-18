**DESCRIPTION**
This mod adds furniture into the game

## Project layout

Cozy Home is built for both **Fabric** and **NeoForge** (Minecraft 1.21.1) using
[Architectury Loom](https://docs.architectury.dev/). The code still uses Yarn mappings everywhere.

| Folder | What goes there |
| --- | --- |
| `common/` | Almost everything: blocks, items, block entities, renderers, models, assets and data. No Fabric API or NeoForge classes here. |
| `fabric/` | Fabric entrypoints and `fabric.mod.json`. |
| `neoforge/` | NeoForge entrypoints, `META-INF/neoforge.mods.toml`, and the data generators. |

Things that need a loader API (fuels, flammable blocks, villager trades, client renderer registration,
`isModLoaded`) are written once in `common/` and handed a small registrar by each loader:
see `ModFuels`, `ModFlammableBlocks`, `ModTrades`, `ModRenderLayers`, `CozyHomeClient` and `CozyHomePlatform`.

## Building

- `./gradlew build` builds both jars, into `fabric/build/libs/` and `neoforge/build/libs/`.
- `./gradlew :fabric:runClient` / `./gradlew :neoforge:runClient` start a dev client for each loader.
- `./gradlew :neoforge:runData` regenerates models, recipes, loot tables and tags into
  `common/src/main/generated/`, which both loaders ship.

## Furnished village houses

Cozyhome can add hand-built furniture to houses from vanilla villages (or any other jigsaw structure) without
overriding the original files. For each house you furnish, a copy of it lives at
`common/src/main/resources/data/cozyhome/structure/furnished/<namespace>/<path>.nbt`. While the house
generates, only the blocks that differ from the original are placed on top of it. If a datapack or another mod
replaces that house file, the furniture is skipped and their version is left as it is.

To furnish a house:

1. In a creative world with cheats on, place a structure block, set it to **Load** mode and load the house, e.g.
   `minecraft:village/plains/houses/plains_small_house_1`. Turn on **Show Bounding Box**, then press **Load**
   twice to place it.
2. Furnish it by hand. Any block can be changed or added (including air to remove something), but keep it
   inside the same bounding box and leave the jigsaw blocks alone. Block entity contents, such as chair cushions
   or chest items, are kept.
3. Switch the structure block to **Save** mode, keep the same position and size, and name it
   `cozyhome:furnished/minecraft/village/plains/houses/plains_small_house_1`, then press **Save**.
4. Run `/reload`. New villages in that world now use your furniture, so you can test it straight away.
5. Copy the saved file from `<world>/generated/cozyhome/structures/furnished/...` into
   `common/src/main/resources/data/cozyhome/structure/furnished/...` so it ships with the mod.

Zombie village houses with no furnished copy of their own reuse the normal house's furniture.
