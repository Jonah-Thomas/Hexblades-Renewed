package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class WaterSaber1 extends HexSwordItem {

    public WaterSaber1(Properties props) {
        super(5, -2.4F, props);
        tooltipText = Component.translatable("tooltip.hexblades.water_saber");
        textColor = ChatFormatting.DARK_AQUA;
    }

    public WaterSaber1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
        textColor = ChatFormatting.DARK_AQUA;
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public void applyHexBonus(Player entity, boolean awakened) {
        if (awakened) entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, false, false));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion / COMMON.SaberDS1.get());
        setShielding(weapon, awakening, (float) (devotion / COMMON.SaberSH1.get()));
    }

    public void setShielding(ItemStack weapon, boolean awakening, float damageReduction) {
        CompoundTag tag = weapon.getOrCreateTag();
        tag.putFloat(Constants.NBT.SHIELDING, awakening ? damageReduction : 0);
    }

    @Override
    public void talk(Player player) {
        player.sendSystemMessage(Component.translatable(this.getDescriptionId() + ".dialogue." + player.level().getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.waterColor))));
    }

    public float getShielding(ItemStack weapon) {
        return weapon.getOrCreateTag().getFloat(Constants.NBT.SHIELDING);
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.literal("Damage reduction: " + getShielding(stack)));
        tooltip.add(Component.literal("Gives water breathing"));
    }
}
