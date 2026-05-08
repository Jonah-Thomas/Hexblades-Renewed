package alexthw.hexblades.world;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexStructures;
import alexthw.hexblades.util.WorldGenUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;

public class FireTempleStructure extends AbstractJigsawStructure {

    public static final Codec<FireTempleStructure> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    StructureSettings.CODEC.fieldOf("structure_settings").forGetter(FireTempleStructure::getStructureSettings),
                    Codec.INT.optionalFieldOf("min_height_limit", 60).forGetter(FireTempleStructure::getMinHeightLimit),
                    Codec.INT.optionalFieldOf("terrain_height_radius", 1).forGetter(FireTempleStructure::getTerrainHeightRadius),
                    Codec.INT.optionalFieldOf("allow_terrain_height_range", 16).forGetter(FireTempleStructure::getAllowTerrainHeightRange),
                    Codec.BOOL.optionalFieldOf("can_spawn_in_water", false).forGetter(FireTempleStructure::canSpawnInWater)
            ).apply(instance, FireTempleStructure::new)
    );

    private static final ResourceKey<StructureTemplatePool> START_POOL = ResourceKey.create(
            Registries.TEMPLATE_POOL,
            new ResourceLocation(Hexblades.MODID, "fire_temple/fire_temple_start")
    );

    public FireTempleStructure(StructureSettings settings, int minHeightLimit, int terrainHeightRadius, int allowTerrainHeightRange, boolean canSpawnInWater) {
        super(settings, minHeightLimit, terrainHeightRadius, allowTerrainHeightRange, canSpawnInWater);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        if (!checkTerrainHeight(context)) {
            return Optional.empty();
        }

        ChunkPos chunkPos = context.chunkPos();
        int x = chunkPos.getMiddleBlockX();
        int z = chunkPos.getMiddleBlockZ();

        BlockPos placementPos = WorldGenUtil.getLowestLand(
                context.chunkGenerator(),
                context.heightAccessor(),
                context.randomState(),
                x, z,
                false
        );

        HolderGetter<StructureTemplatePool> templatePools =
                context.registryAccess().registryOrThrow(Registries.TEMPLATE_POOL).asLookup();

        return JigsawPlacement.addPieces(
                context,
                templatePools.getOrThrow(START_POOL),
                Optional.empty(),
                14,
                placementPos,
                false,
                Optional.empty(),
                128
        );
    }

    @Override
    public StructureType<?> type() {
        return HexStructures.FIRE_TEMPLE.get();
    }
}
