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
