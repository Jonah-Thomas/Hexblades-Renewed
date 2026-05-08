package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.FireBroad1;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class FireBroad2 extends FireBroad1 {

    public FireBroad2(Properties props) {
        super(7, -2.7F, props);
        tooltipText = Component.translatable("tooltip.hexblades.flame_sword2");
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public void applyHexBonus(Player user, boolean awakened) {
        user.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0, false, false));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);
        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));
        if (awakening) updateElementalPower(weapon, COMMON.SwordED2.get(), devotion);
        setAttackPower(weapon, awakening, devotion / COMMON.SwordDS2.get());
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            // magic() bypasses armor in 1.20.1
            target.hurt(target.damageSources().magic(), getElementalPower(stack));
        }
        target.setSecondsOnFire(6);
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.literal("Armor piercing damage: " + getElementalPower(stack)));
        tooltip.add(Component.literal("Sets enemies on fire for 6 seconds"));
    }
}
