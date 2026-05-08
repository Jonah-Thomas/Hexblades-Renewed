package alexthw.hexblades.spells;

import alexthw.hexblades.common.blocks.tile_entities.FirePedestalTileEntity;
import elucent.eidolon.api.spells.Sign;
import elucent.eidolon.common.spell.StaticSpell;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

import static alexthw.hexblades.util.HexUtils.getTilesWithinAABB;

public class HexSummonerSpell extends StaticSpell {

    public HexSummonerSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);
    }

    @Override
    public boolean canCast(Level world, BlockPos blockPos, Player player) {
        Vec3 v = getVector(world, player);
        List<FirePedestalTileEntity> pedestals = getTilesWithinAABB(FirePedestalTileEntity.class, world, new AABB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
        return pedestals.stream().anyMatch((f) -> f.hasCore);
    }

    @Override
    public void cast(Level world, BlockPos blockPos, Player player) {

        if (!world.isClientSide) {
            Vec3 v = getVector(world, player);

            List<FirePedestalTileEntity> pedestals = getTilesWithinAABB(FirePedestalTileEntity.class, world, new AABB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));

            if (pedestals.size() > 0) {

                FirePedestalTileEntity fb = pedestals.stream().filter((f) -> f.hasCore).min(Comparator.comparingDouble((e) -> e.getBlockPos().distSqr(blockPos))).get();

                fb.startSummoning();

            }
        }
    }
}
