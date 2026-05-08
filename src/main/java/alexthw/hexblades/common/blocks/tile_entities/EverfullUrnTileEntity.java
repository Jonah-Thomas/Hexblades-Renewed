package alexthw.hexblades.common.blocks.tile_entities;

import alexthw.hexblades.network.RefillEffectPacket;
import alexthw.hexblades.registers.HexBlockEntityType;
import elucent.eidolon.common.tile.TileEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.util.CompatUtil.isBotaniaLoaded;

public class EverfullUrnTileEntity extends TileEntityBase {

    public EverfullUrnTileEntity(BlockPos pos, BlockState state) {
        this(HexBlockEntityType.EVERFULL_URN_TILE_ENTITY.get(), pos, state);
    }

    public EverfullUrnTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EverfullUrnTileEntity be) {
        if (level == null) return;
        if (!level.isClientSide() && level.getGameTime() % COMMON.UrnTickRate.get() == 0) {
            fillCauldrons(level, pos);
            // TODO: Eidolon-Repraised — CrucibleBlockEntity hasWater refill
            // The old ObfuscationReflectionHelper approach to set hasWater is removed.
            // Verify Eidolon-Repraised CrucibleBlockEntity API for water-filling.
            // fillCrucibles(level, pos);

            // TODO: Botania compat — re-enable once Botania 1.20.1 is confirmed available
            // if (isBotaniaLoaded()) BotaniaCompat.refillApotecaries(level, pos);

            // Send refill effect packet to clients
            // TODO: port RefillEffectPacket to Forge 47 — done in Phase 8
        }
    }

    private static void fillCauldrons(Level level, BlockPos origin) {
        AABB bb = new AABB(origin.offset(-2, -1, -2), origin.offset(3, 2, 3));
        for (int i = (int) Math.floor(bb.minX); i < (int) Math.ceil(bb.maxX); i++) {
            for (int j = (int) Math.floor(bb.minZ); j < (int) Math.ceil(bb.maxZ); j++) {
                for (int k = (int) Math.floor(bb.minY); k < (int) Math.ceil(bb.maxY); k++) {
                    BlockPos scan = new BlockPos(i, k, j);
                    BlockState scanState = level.getBlockState(scan);
                    Block block = scanState.getBlock();
                    if (block instanceof LayeredCauldronBlock
                            && scanState.hasProperty(BlockStateProperties.LEVEL_CAULDRON)
                            && scanState.getValue(BlockStateProperties.LEVEL_CAULDRON) < 3) {
                        level.setBlock(scan, scanState.setValue(BlockStateProperties.LEVEL_CAULDRON, 3), Block.UPDATE_ALL);
                    }
                }
            }
        }
    }
}
