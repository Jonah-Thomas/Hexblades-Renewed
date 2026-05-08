package alexthw.hexblades.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;

/**
 * World-generation utility helpers.
 *
 * Note: {@code haveCategories(BiomeLoadingEvent, Biome.Category...)} was removed.
 * {@code Biome.Category} no longer exists in 1.20.1; use biome tags
 * (e.g. {@code BiomeTags.IS_NETHER}) together with {@code Holder<Biome>.is(TagKey)} instead.
 */
public class WorldGenUtil {

    /**
     * Scans downward from near the world ceiling and returns the highest solid land
     * position that has at least 3 blocks of air above it.
     *
     * @param chunkGenerator the chunk generator
     * @param heightAccessor level height accessor for Y bounds
     * @param randomState    the random state for column lookups
     * @param x              block X coordinate
     * @param z              block Z coordinate
     * @param canBeOnLiquid  if {@code false}, only occluding (fully solid) blocks count as ground
     * @return an immutable {@link BlockPos} at the highest suitable surface, or wherever
     *         the scan ended when no surface was found
     */
    public static BlockPos getHighestLand(ChunkGenerator chunkGenerator, LevelHeightAccessor heightAccessor,
                                          RandomState randomState, int x, int z, boolean canBeOnLiquid) {
        int startY = heightAccessor.getMinBuildHeight() + chunkGenerator.getGenDepth() - 20;
        var blockColumn = chunkGenerator.getBaseColumn(x, z, heightAccessor, randomState);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(x, startY, z);

        while (mutable.getY() > chunkGenerator.getSeaLevel()) {
            BlockState current = blockColumn.getBlock(mutable.getY());
            if (!current.canOcclude()) {
                mutable.move(Direction.DOWN);
                continue;
            }
            BlockState threeAbove = blockColumn.getBlock(mutable.getY() + 3);
            if (threeAbove.isAir() && (canBeOnLiquid ? !current.isAir() : current.canOcclude())) {
                return mutable.immutable();
            }
            mutable.move(Direction.DOWN);
        }

        return mutable.immutable();
    }

    /**
     * Scans upward from sea level and returns the lowest solid land position that
     * has at least 5 blocks of air above it, offset one block upward (i.e. the first
     * air block above the surface).
     *
     * @param chunkGenerator the chunk generator
     * @param heightAccessor level height accessor for Y bounds
     * @param randomState    the random state for column lookups
     * @param x              block X coordinate
     * @param z              block Z coordinate
     * @param canBeOnLiquid  if {@code false}, only occluding (fully solid) blocks count as ground
     * @return an immutable {@link BlockPos} one block above the lowest suitable surface,
     *         or sea level if none is found
     */
    public static BlockPos getLowestLand(ChunkGenerator chunkGenerator, LevelHeightAccessor heightAccessor,
                                         RandomState randomState, int x, int z, boolean canBeOnLiquid) {
        int ceilingY = heightAccessor.getMinBuildHeight() + chunkGenerator.getGenDepth() - 20;
        var blockColumn = chunkGenerator.getBaseColumn(x, z, heightAccessor, randomState);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(x, chunkGenerator.getSeaLevel() + 1, z);
        BlockState current = blockColumn.getBlock(mutable.getY());

        while (mutable.getY() <= ceilingY) {
            boolean isGround = canBeOnLiquid ? !current.isAir() : current.canOcclude();
            if (isGround
                    && blockColumn.getBlock(mutable.getY() + 1).isAir()
                    && blockColumn.getBlock(mutable.getY() + 5).isAir()) {
                mutable.move(Direction.UP);
                return mutable.immutable();
            }
            mutable.move(Direction.UP);
            current = blockColumn.getBlock(mutable.getY());
        }

        return new BlockPos(x, chunkGenerator.getSeaLevel(), z);
    }
}
