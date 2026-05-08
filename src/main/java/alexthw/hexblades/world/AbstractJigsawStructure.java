package alexthw.hexblades.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public abstract class AbstractJigsawStructure extends Structure {

    private final StructureSettings structureSettings;
    protected int minHeightLimit;
    protected int terrainHeightRadius;
    protected int allowTerrainHeightRange;
    protected boolean canSpawnInWater;

    public StructureSettings getStructureSettings() {
        return structureSettings;
    }

    public int getMinHeightLimit() {
        return minHeightLimit;
    }

    public int getTerrainHeightRadius() {
        return terrainHeightRadius;
    }

    public int getAllowTerrainHeightRange() {
        return allowTerrainHeightRange;
    }

    public boolean canSpawnInWater() {
        return canSpawnInWater;
    }

    public AbstractJigsawStructure(StructureSettings settings, int minHeightLimit, int terrainHeightRadius, int allowTerrainHeightRange, boolean canSpawnInWater) {
        super(settings);
        this.structureSettings = settings;
        this.allowTerrainHeightRange = allowTerrainHeightRange;
        this.minHeightLimit = minHeightLimit;
        this.terrainHeightRadius = terrainHeightRadius;
        this.canSpawnInWater = canSpawnInWater;
    }

    /**
     * Subclasses override this to provide actual jigsaw placement.
     * Returns Optional.empty() by default.
     */
    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return Optional.empty();
    }

    /**
     * Checks whether the terrain height variation around the given chunk position
     * is within the allowed range and above the minimum height limit.
     *
     * @return true if the terrain is suitable for spawning
     */
    protected boolean checkTerrainHeight(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        int chunkX = chunkPos.x;
        int chunkZ = chunkPos.z;
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        RandomState randomState = context.randomState();

        if (allowTerrainHeightRange != -1) {
            int maxTerrainHeight = Integer.MIN_VALUE;
            int minTerrainHeight = Integer.MAX_VALUE;
            for (int curChunkX = chunkX - terrainHeightRadius; curChunkX <= chunkX + terrainHeightRadius; curChunkX++) {
                for (int curChunkZ = chunkZ - terrainHeightRadius; curChunkZ <= chunkZ + terrainHeightRadius; curChunkZ++) {
                    int height = chunkGenerator.getBaseHeight(
                            curChunkX * 16, curChunkZ * 16,
                            Heightmap.Types.WORLD_SURFACE_WG,
                            context.heightAccessor(),
                            randomState);
                    maxTerrainHeight = Math.max(maxTerrainHeight, height);
                    minTerrainHeight = Math.min(minTerrainHeight, height);
                    if (minTerrainHeight < this.minHeightLimit) return false;
                }
            }
            return maxTerrainHeight - minTerrainHeight <= allowTerrainHeightRange;
        }

        if (!canSpawnInWater) {
            int x = chunkX * 16;
            int z = chunkZ * 16;
            int landHeight = chunkGenerator.getFirstOccupiedHeight(
                    x, z,
                    Heightmap.Types.WORLD_SURFACE_WG,
                    context.heightAccessor(),
                    randomState);
            BlockPos surfacePos = new BlockPos(x, landHeight, z);
            BlockState topBlock = chunkGenerator.getBaseColumn(x, z, context.heightAccessor(), randomState)
                    .getBlock(surfacePos.getY());
            return topBlock.getFluidState().isEmpty();
        }

        return true;
    }

    @Override
    public abstract StructureType<?> type();
}
