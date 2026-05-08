package alexthw.hexblades.common.blocks;

import alexthw.hexblades.common.blocks.tile_entities.EverfullUrnTileEntity;
import alexthw.hexblades.registers.HexBlockEntityType;
import elucent.eidolon.common.block.HorizontalWaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EverfullUrnBlock extends HorizontalWaterloggableBlock implements EntityBlock {

    static final VoxelShape VSHAPE = Shapes.join(
            Block.box(3, 0, 3, 13, 9, 13),
            Block.box(5.5, 9, 5.5, 10.5, 13, 10.5),
            BooleanOp.OR);

    public EverfullUrnBlock(Properties properties) {
        super(properties);
        this.setShape(VSHAPE);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return VSHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EverfullUrnTileEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return type == HexBlockEntityType.EVERFULL_URN_TILE_ENTITY.get()
                ? (lvl, pos, st, be) -> EverfullUrnTileEntity.tick(lvl, pos, st, (EverfullUrnTileEntity) be)
                : null;
    }
}
