package alexthw.hexblades.world;

// In 1.20.1, configured structures are fully data-driven and no longer registered in Java code.
//
// All structure configuration (start pool, size, step, spawn_overrides, terrain_adaptation)
// is defined in JSON datapack files:
//
//   data/hexblades/worldgen/structure/fire_temple.json
//   data/hexblades/worldgen/structure_set/fire_temples.json
//
// Biome tags in data/hexblades/tags/worldgen/biome/ control where structures can spawn.
//
// This class is intentionally empty. The old WorldGenRegistries / FlatGenerationSettings
// registration approach was removed in 1.18 and does not exist in 1.20.1.
public class ConfiguredStructures {
    // No-op — all configured structure registration is handled via JSON datapacks.
}
