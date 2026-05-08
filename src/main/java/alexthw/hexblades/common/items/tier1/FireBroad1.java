package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class FireBroad1 extends HexSwordItem {


    public FireBroad1(Properties props) {
        super(6, -2.7F, props);
        tooltipText = Component.translatable("tooltip.hexblades.flame_sword");
        textColor = ChatFormatting.RED;
    }

    public FireBroad1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
        textColor = ChatFormatting.RED;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            // EntityDamageSource("lava", attacker).bypassArmor() → magic() is the closest armor-bypassing source
            target.hurt(target.damageSources().magic(), getElementalPower(stack));
            target.setSecondsOnFire(4);
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));
        if (awakening) updateElementalPower(weapon, COMMON.SwordED1.get(), devotion);
        setAttackPower(weapon, awakening, devotion / COMMON.SwordDS1.get());
    }

    @Override
    public void talk(Player player) {
        player.sendSystemMessage(Component.translatable(this.getDescriptionId() + ".dialogue." + player.level().getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.fireColor))));
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.literal("Armor piercing damage: " + getElementalPower(stack)));
        tooltip.add(Component.literal("Sets enemies on fire for 4 seconds"));
    }
}
