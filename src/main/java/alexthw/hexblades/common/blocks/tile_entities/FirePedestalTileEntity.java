package alexthw.hexblades.common.blocks.tile_entities;

import alexthw.hexblades.common.entity.FireElementalEntity;
import alexthw.hexblades.registers.HexBlockEntityType;
import alexthw.hexblades.registers.HexEntityType;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.common.tile.TileEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FirePedestalTileEntity extends TileEntityBase implements GeoBlockEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean isSummoning = false;
    public boolean hasCore = false;
    private int tickCounter = 0;
    private int animationState = -1;

    public FirePedestalTileEntity(BlockPos pos, BlockState state) {
        this(HexBlockEntityType.FIRE_PEDESTAL_TILE_ENTITY.get(), pos, state);
    }

    public FirePedestalTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void tick(net.minecraft.world.level.Level level, BlockPos pos, BlockState state, FirePedestalTileEntity be) {
        if (!be.isSummoning || level == null) return;
        if (!level.isClientSide()) {
            if (be.tickCounter == 100) {
                FireElementalEntity fe = HexEntityType.FIRE_ELEMENTAL.get().create(level);
                if (fe != null) {
                    fe.setPos(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
                    level.addFreshEntity(fe);
                }
                be.isSummoning = false;
                be.animationState = -1;
                be.sync();
            }
        }
        be.tickCounter++;
    }

    @Override
    public InteractionResult onActivated(BlockState state, BlockPos pos, Player player, InteractionHand hand) {
        if (level != null && !level.isClientSide() && hand == InteractionHand.MAIN_HAND && canUse()) {
            ItemStack itemHand = player.getItemInHand(hand);
            if (!itemHand.isEmpty() && !hasCore && itemHand.getItem() == HexItem.FIRE_CORE.get()) {
                itemHand.shrink(1);
                if (player.getItemInHand(hand).isEmpty()) player.setItemInHand(hand, ItemStack.EMPTY);
                hasCore = true;
                animationState = 0;
                this.sync();
                return InteractionResult.SUCCESS;
            } else if (itemHand.isEmpty() && hasCore) {
                player.addItem(new ItemStack(HexItem.FIRE_CORE.get()));
                hasCore = false;
                animationState = -1;
                this.sync();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public void startSummoning() {
        animationState = 1;
        tickCounter = 0;
        hasCore = false;
        isSummoning = true;
        this.sync();
    }

    private boolean canUse() { return !isSummoning; }
    public int getAnimationState() { return animationState; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            var anim = switch (getAnimationState()) {
                case 1 -> RawAnimation.begin().thenPlay("animation.fire_pedestal_start");
                case 0 -> RawAnimation.begin().thenPlay("animation.core_placed");
                default -> RawAnimation.begin().thenPlay("animation.fire_pedestal_stop");
            };
            event.getController().setAnimation(anim);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("isSummoning", isSummoning);
        tag.putBoolean("hasCore", hasCore);
        tag.putInt("tickCounter", tickCounter);
        tag.putInt("animation", animationState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.isSummoning = tag.getBoolean("isSummoning");
        this.hasCore = tag.getBoolean("hasCore");
        this.tickCounter = tag.getInt("tickCounter");
        this.animationState = tag.getInt("animation");
    }
}
