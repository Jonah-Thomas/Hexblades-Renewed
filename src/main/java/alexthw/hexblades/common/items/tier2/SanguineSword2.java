package alexthw.hexblades.common.items.tier2;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class SanguineSword2 extends SanguineSword {

    public SanguineSword2(Properties builderIn) {
        super(builderIn);
        tooltipText = "tooltip.hexblades.sanguine_sword2";
        setLore(tooltipText);
    }

    @Override
    public void applyHexBonus(Player user, boolean awakened) {
        if (awakened) {
            user.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false));
            user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 0, false, false));
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);
        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));
        setAttackPower(weapon, awakening, devotion / Math.max(1.0D, COMMON.BloodDS.get() * 0.8D));
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        float before = target.getHealth();
        float witherDamage = awakened ? (float) (3.0F + getDevotion(attacker) / Math.max(1.0D, COMMON.BloodED.get() * 0.85D)) : 2.5F;
        target.hurt(target.damageSources().wither(), witherDamage);
        float healing = before - target.getHealth();
        if (healing > 0.0F) {
            attacker.heal(healing);
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item.hexblades.blood_sword_2");
    }
}
