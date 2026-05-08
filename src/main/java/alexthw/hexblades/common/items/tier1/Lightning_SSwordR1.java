package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class Lightning_SSwordR1 extends HexSwordItem {

    public Lightning_SSwordR1(Properties props) {
        super(4, -1.5F, props);
        tooltipText = Component.translatable("tooltip.hexblades.thunder_knives");
        textColor = ChatFormatting.YELLOW;
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);
        if (!hasTwin(player)) {
            setAwakenedState(weapon, false);
        } else if (getAwakened(weapon)) {
            setAwakenedState(weapon, weapon.getDamageValue() <= getMaxDamage(weapon) * 0.9);
        } else {
            setAwakenedState(weapon, true);
        }
        boolean awakening = getAwakened(weapon);
        setAttackPower(weapon, awakening, devotion / COMMON.DualsDS1.get());
        setAttackSpeed(weapon, awakening, devotion / COMMON.DualsAS1.get());
    }

    public boolean hasTwin(Player player) {
        return player.getOffhandItem().getItem() == HexItem.LIGHTNING_DAGGER_L.get();
    }

    @Override
    public void talk(Player player) {
        player.sendSystemMessage(Component.translatable(this.getDescriptionId() + ".dialogue." + player.level().getRandom().nextInt(dialogueLines))
                .withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.thunderColor))));
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.literal("Needs its twin to awaken"));
        tooltip.add(Component.literal("Will electrocute charged enemies for extra damage"));
    }
}
