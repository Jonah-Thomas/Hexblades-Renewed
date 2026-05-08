package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.registries.Registry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class IceKatana1 extends HexSwordItem {

    public IceKatana1(Properties props) {
        super(5, -2.5F, props);
        tooltipText = Component.translatable("tooltip.hexblades.ice_katana");
        textColor = ChatFormatting.AQUA;
    }

    public IceKatana1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
        textColor = ChatFormatting.AQUA;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            // Registry.FROST_DAMAGE.source() replaces old EntityDamageSource(Registry.FROST_DAMAGE.getMsgId(), attacker).bypassArmor()
            target.hurt(Registry.FROST_DAMAGE.source(target.level(), attacker), getElementalPower(stack));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0));
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));
        if (awakening) updateElementalPower(weapon, COMMON.KatanaED.get(), devotion);

        setAttackPower(weapon, awakening, devotion / COMMON.KatanaDS1.get());
        setAttackSpeed(weapon, awakening, devotion / COMMON.KatanaAS1.get());
    }

    @Override
    public void talk(Player player) {
        player.sendSystemMessage(Component.translatable(this.getDescriptionId() + ".dialogue." + player.level().getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.iceColor))));
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.literal("Armor piercing damage: " + getElementalPower(stack)));
        tooltip.add(Component.literal("Slows enemies"));
    }
}
