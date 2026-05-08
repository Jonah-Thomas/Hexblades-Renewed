package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.WaterSaber1;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class WaterSaber2 extends WaterSaber1 {

    public WaterSaber2(Properties props) {
        super(6, -2.4F, props);
        tooltipText = Component.translatable("tooltip.hexblades.water_saber2");
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            float bonus_dmg = getElementalPower(stack);
            if (target.getMobType() == MobType.WATER) {
                // magic() bypasses armor in 1.20.1
                target.hurt(target.damageSources().magic(), bonus_dmg);
            } else {
                target.hurt(target.damageSources().drown(), bonus_dmg);
            }
        }
    }

    @Override
    public void applyHexBonus(Player entity, boolean awakened) {
        if (awakened) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 200, 0, false, false));
        } else {
            entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200));
        }
        entity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 200));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);
        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));
        if (awakening) updateElementalPower(weapon, COMMON.SaberED2.get(), devotion);
        setAttackPower(weapon, awakening, devotion / COMMON.SaberDS2.get());
        setShielding(weapon, awakening, (float) (devotion / COMMON.SaberSH2.get()));
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.literal("Armor piercing damage: " + getElementalPower(stack)));
        tooltip.add(Component.literal("Damage reduction: " + getShielding(stack)));
        tooltip.add(Component.literal("Gives conduit power"));
    }
}
