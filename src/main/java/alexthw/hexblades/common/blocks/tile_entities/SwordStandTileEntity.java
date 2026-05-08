package alexthw.hexblades.common.blocks.tile_entities;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.registers.HexBlockEntityType;
import elucent.eidolon.common.tile.TileEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SwordStandTileEntity extends TileEntityBase implements GeoBlockEntity {

    public ItemStack stack = ItemStack.EMPTY;
    long previous = -1L;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SwordStandTileEntity(BlockPos pos, BlockState state) {
        this(HexBlockEntityType.SWORD_STAND_TILE_ENTITY.get(), pos, state);
    }

    public SwordStandTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onDestroyed(BlockState state, BlockPos pos) {
        if (!this.stack.isEmpty() && this.level != null) {
            net.minecraft.world.Containers.dropItemStack(this.level,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, this.stack);
        }
    }

    @Override
    public InteractionResult onActivated(BlockState state, BlockPos pos, Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            ItemStack itemHand = player.getItemInHand(hand);
            if (itemHand.isEmpty() && !this.stack.isEmpty()) {
                player.addItem(this.stack);
                this.stack = ItemStack.EMPTY;
                if (this.level != null && !this.level.isClientSide) this.sync();
                return InteractionResult.SUCCESS;
            }
            if (!itemHand.isEmpty() && this.stack.isEmpty()
                    && (itemHand.getItem() instanceof IHexblade || itemHand.getItem() instanceof SwordItem)) {
                this.stack = itemHand.copy();
                this.stack.setCount(1);
                itemHand.shrink(1);
                if (player.getItemInHand(hand).isEmpty()) player.setItemInHand(hand, ItemStack.EMPTY);
                if (this.level != null && !this.level.isClientSide) this.sync();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.stack = ItemStack.of(tag.getCompound("stack"));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("stack", this.stack.save(new CompoundTag()));
    }

    public boolean ready() { return true; }

    public void pray() {
        if (this.level != null && !this.level.isClientSide) {
            this.previous = this.level.getGameTime();
            this.sync();
        }
    }

    public ItemStack provide() { return this.stack.copy(); }

    public void take() {
        this.stack = ItemStack.EMPTY;
        if (this.level != null && !this.level.isClientSide) this.sync();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            event.getController().setAnimation(RawAnimation.begin()
                    .thenLoop("animation.sword_stand.cubes"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
