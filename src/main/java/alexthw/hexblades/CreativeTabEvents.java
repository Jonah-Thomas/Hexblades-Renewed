package alexthw.hexblades;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.common.items.tier1.WaterSaber1;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.network.FlameEffectPacket;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.registers.HexRegistry;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.capability.IReputation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static alexthw.hexblades.util.Constants.NBT.*;

public class CreativeTabEvents {

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        var level = entity.level();

        if (!level.isClientSide()) {
            if (entity instanceof Drowned) {
                LivingEntity source = (event.getSource().getEntity() instanceof LivingEntity le) ? le : null;
                int looting = ForgeHooks.getLootingLevel(entity, source, event.getSource());
                boolean doDrop = entity.level().random.nextInt(10) == 1;
                for (int i = 0; i < looting && !doDrop; i++) {
                    if (entity.level().random.nextInt(20) == 1) doDrop = true;
                }
                if (doDrop) {
                    ItemStack stack = new ItemStack(HexItem.DROWNED_HEART.get());
                    ItemEntity drop = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), stack);
                    event.getDrops().add(drop);
                }
            } else if (entity instanceof Player player) {
                if ((player instanceof FakePlayer) || player.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                    return;
                }
                List<ItemEntity> keeps = new ArrayList<>();
                for (ItemEntity item : event.getDrops()) {
                    ItemStack stack = item.getItem();
                    if (!stack.isEmpty() && stack.getItem() instanceof IHexblade) {
                        keeps.add(item);
                    }
                }

                if (!keeps.isEmpty()) {
                    event.getDrops().removeAll(keeps);

                    CompoundTag cmp = new CompoundTag();
                    cmp.putInt(TAG_HW_DROP_COUNT, keeps.size());

                    int i = 0;
                    for (ItemEntity keep : keeps) {
                        ItemStack stack = keep.getItem();
                        CompoundTag cmp1 = stack.save(new CompoundTag());
                        cmp.put(TAG_HW_DROP_PREFIX + i, cmp1);
                        i++;
                    }

                    CompoundTag data = player.getPersistentData();
                    if (!data.contains(Player.PERSISTED_NBT_TAG)) {
                        data.put(Player.PERSISTED_NBT_TAG, new CompoundTag());
                    }
                    data.getCompound(Player.PERSISTED_NBT_TAG).put(TAG_HW_KEEP, cmp);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnHW(PlayerEvent.PlayerRespawnEvent event) {
        CompoundTag data = event.getEntity().getPersistentData();
        if (data.contains(Player.PERSISTED_NBT_TAG)) {
            CompoundTag cmp = data.getCompound(Player.PERSISTED_NBT_TAG);
            CompoundTag cmp1 = cmp.getCompound(TAG_HW_KEEP);

            int count = cmp1.getInt(TAG_HW_DROP_COUNT);
            for (int i = 0; i < count; i++) {
                CompoundTag cmp2 = cmp1.getCompound(TAG_HW_DROP_PREFIX + i);
                ItemStack stack = ItemStack.of(cmp2);
                if (!stack.isEmpty()) {
                    if (!event.getEntity().getInventory().add(stack.copy())) {
                        event.getEntity().spawnAtLocation(stack.copy());
                    }
                }
            }
            cmp.remove(TAG_HW_KEEP);
        }
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent event) {
        LivingEntity damaged = event.getEntity();
        if (damaged instanceof Player) {
            ItemStack item = damaged.getMainHandItem();
            if (item.getItem() instanceof WaterSaber1 saber) {
                float shield = saber.getShielding(item);
                event.setAmount(Math.min(1, event.getAmount() - shield));
            }
        }
    }

    @SubscribeEvent
    public void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player
                && event.getEntity() instanceof Monster
                && !player.level().isClientSide()) {
            Item item = player.getMainHandItem().getItem();
            if (item instanceof HexSwordItem sword) {
                if (HexUtils.chance((int) (5 + (event.getEntity().getMaxHealth() / 2)), event.getEntity().level())) {
                    player.level().getCapability(IReputation.INSTANCE).ifPresent(rep ->
                            rep.addReputation(player, HexDeities.HEX_DEITY.getId(), 1.0D));
                    HexRegistry.CHANNEL.send(
                            net.minecraftforge.network.PacketDistributor.TRACKING_ENTITY.with(event::getEntity),
                            new FlameEffectPacket(event.getEntity().blockPosition()));
                    sword.talk(player);
                }
            }
        }
    }

    @SubscribeEvent
    public void onApplyPotion(MobEffectEvent.Applicable event) {
        if (event.getEffectInstance() != null
                && event.getEffectInstance().getEffect() == MobEffects.MOVEMENT_SLOWDOWN
                && event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof HexWArmor) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        var source = event.getSource();
        if (!(source.getEntity() instanceof Player player)) return;

        boolean isWither = source.is(DamageTypes.WITHER);
        boolean isMagic  = source.is(DamageTypeTags.IS_PROJECTILE) || source.getMsgId().equals("magic");

        if (isWither || isMagic) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);
            if (stack.getItem() instanceof HexWArmor) {
                float multiplier = HexWArmor.getFocusId(stack) == 1 ? 1.5F : 1.25F;
                event.setAmount(event.getAmount() * multiplier);
                if (isWither) player.heal(event.getAmount() / 3.0F);
            }

            ItemStack chest = event.getEntity().getItemBySlot(EquipmentSlot.CHEST);
            if (chest.getItem() instanceof HexWArmor) {
                float multiplier = HexWArmor.getFocusId(chest) == 1 ? 0.5F : 0.75F;
                event.setAmount(event.getAmount() * multiplier);
            }
        } else if (source.is(DamageTypes.FALL)
            || source.is(DamageTypes.FALLING_ANVIL)
                || source.is(DamageTypes.HOT_FLOOR)
                || source.is(DamageTypes.EXPLOSION)
                || source.is(DamageTypes.PLAYER_EXPLOSION)) {
            ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
            if (legs.getItem() instanceof HexWArmor) {
                float multiplier = HexWArmor.getFocusId(legs) == 1 ? 0.5F : 0.75F;
                event.setAmount(event.getAmount() * multiplier);
            }
        } else if (source.is(DamageTypes.SWEET_BERRY_BUSH)) {
            ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
            if (boots.getItem() instanceof HexWArmor && event.isCancelable()) {
                event.setCanceled(true);
            }
        }
    }

    // SpeedFactorEvent is Eidolon-specific — TODO: verify Eidolon-Repraised equivalent event
    // @SubscribeEvent
    // public void onGetSpeedFactor(SpeedFactorEvent event) { ... }
}
