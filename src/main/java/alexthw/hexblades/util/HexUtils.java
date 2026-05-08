package alexthw.hexblades.util;

import alexthw.hexblades.Hexblades;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;

import java.util.*;
import java.util.function.Predicate;

public class HexUtils {

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(Hexblades.MODID, path);
    }

    @SafeVarargs
    @SuppressWarnings({"varargs", "SuspiciousMethodCalls"})
    public static <T> Collection<T> takeAll(Collection<? extends T> src, T... items) {
        List<T> ret = Arrays.asList(items);
        for (T item : items) {
            if (!src.contains(item)) {
                return Collections.emptyList();
            }
        }
        if (!src.removeAll(ret)) {
            return Collections.emptyList();
        }
        return ret;
    }

    public static <T> Collection<T> takeAll(Collection<T> src, Predicate<T> pred) {
        List<T> ret = new ArrayList<>();
        Iterator<T> iter = src.iterator();
        while (iter.hasNext()) {
            T item = iter.next();
            if (pred.test(item)) {
                iter.remove();
                ret.add(item);
            }
        }
        if (ret.isEmpty()) {
            return Collections.emptyList();
        }
        return ret;
    }

    public static <T> List<T> getTilesWithinAABB(Class<T> type, Level level, AABB bb) {
        List<T> tileList = new ArrayList<>();
        for (int i = (int) Math.floor(bb.minX); i < (int) Math.ceil(bb.maxX) + 16; i += 16) {
            for (int j = (int) Math.floor(bb.minZ); j < (int) Math.ceil(bb.maxZ) + 16; j += 16) {
                var chunk = level.getChunk(new BlockPos(i, 0, j));
                for (BlockPos p : chunk.getBlockEntitiesPos()) {
                    if (bb.contains(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5)) {
                        BlockEntity t = level.getBlockEntity(p);
                        if (type.isInstance(t)) {
                            tileList.add(type.cast(t));
                        }
                    }
                }
            }
        }
        return tileList;
    }

    public static Vec3 getVector(Level level, Player player) {
        HitResult ray = level.clip(new ClipContext(
                player.getEyePosition(),
                player.getEyePosition().add(player.getLookAngle().scale(4)),
                ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        return (ray.getType() == HitResult.Type.BLOCK) ? ray.getLocation()
                : player.getEyePosition().add(player.getLookAngle().scale(4));
    }

    // Packed ARGB colors
    public static final int fireColor    = packColor(255, 230, 30,  40);
    public static final int iceColor     = packColor(255, 25,  140, 170);
    public static final int waterColor   = packColor(255, 55,  51,  171);
    public static final int earthColor   = packColor(255, 126, 70,  0);
    public static final int thunderColor = packColor(255, 255, 255, 65);

    public static int packColor(int a, int r, int g, int b) {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    public static boolean chance(int c, Level level) {
        return c >= level.random.nextInt(100);
    }
}
