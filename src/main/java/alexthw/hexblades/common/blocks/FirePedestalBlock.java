package alexthw.hexblades.common.blocks;

import alexthw.hexblades.common.blocks.tile_entities.FirePedestalTileEntity;
import alexthw.hexblades.registers.HexBlockEntityType;
import elucent.eidolon.common.block.HorizontalBlockBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FirePedestalBlock extends HorizontalBlockBase implements EntityBlock {

    public FirePedestalBlock(Properties properties) {
        super(properties);
        this.setShape(makeShape());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FirePedestalTileEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return type == HexBlockEntityType.FIRE_PEDESTAL_TILE_ENTITY.get()
                ? (lvl, pos, st, be) -> FirePedestalTileEntity.tick(lvl, pos, st, (FirePedestalTileEntity) be)
                : null;
    }

    public VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.0625, 0.8125, -0.0625, 1.0625, 1.1875, 1.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.25, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875), BooleanOp.OR);
        return shape;
    }
}
