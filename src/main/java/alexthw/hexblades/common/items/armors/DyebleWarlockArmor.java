package alexthw.hexblades.common.items.armors;

import elucent.eidolon.common.item.WarlockRobesItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class DyebleWarlockArmor extends WarlockRobesItem {

    public DyebleWarlockArmor(ArmorItem.Type slot, Properties builderIn) {
        super(slot, builderIn);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        int color = getColor(stack);
        return switch (color) {
            case 0  -> "hexblades:textures/entity/warlock_robes/white.png";
            case 1  -> "hexblades:textures/entity/warlock_robes/orange.png";
            case 2  -> "hexblades:textures/entity/warlock_robes/magenta.png";
            case 3  -> "hexblades:textures/entity/warlock_robes/light_blue.png";
            case 4  -> "hexblades:textures/entity/warlock_robes/yellow.png";
            case 5  -> "hexblades:textures/entity/warlock_robes/lime.png";
            case 6  -> "hexblades:textures/entity/warlock_robes/pink.png";
            case 9  -> "hexblades:textures/entity/warlock_robes/cyan.png";
            case 10 -> "hexblades:textures/entity/warlock_robes/purple.png";
            case 12 -> "hexblades:textures/entity/warlock_robes/brown.png";
            case 13 -> "hexblades:textures/entity/warlock_robes/green.png";
            case 14 -> "hexblades:textures/entity/warlock_robes/red.png";
            case 15 -> "hexblades:textures/entity/warlock_robes/black.png";
            default -> super.getArmorTexture(stack, entity, slot, type);
        };
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(Component.literal(getColorName(getColor(pStack))));
    }

    private int getColor(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt("color");
    }

    private String getColorName(int index) {
        return switch (index) {
            case 0  -> "White";
            case 1  -> "Orange";
            case 2  -> "Magenta";
            case 3  -> "Light_blue";
            case 4  -> "Yellow";
            case 5  -> "Lime";
            case 6  -> "Pink";
            case 9  -> "Cyan";
            case 10 -> "Purple";
            case 12 -> "Brown";
            case 13 -> "Green";
            case 14 -> "Red";
            case 15 -> "Black";
            default -> "Blue";
        };
    }
}
