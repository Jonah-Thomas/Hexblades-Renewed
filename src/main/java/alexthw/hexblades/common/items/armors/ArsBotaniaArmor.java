package alexthw.hexblades.common.items.armors;

import alexthw.hexblades.util.Constants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.api.mana.ManaDiscountArmor;

import javax.annotation.Nullable;

public class ArsBotaniaArmor extends NouveauArmor implements ManaDiscountArmor {

    public ArsBotaniaArmor(ArmorItem.Type type, Properties builderIn) {
        super(type, builderIn);
    }

    @Override
    public float getDiscount(ItemStack stack, int slot, Player player, @Nullable ItemStack tool) {
        return getFocus(stack).equals("botania") ? Constants.ArmorCompat.BotaniaDiscount : 0;
    }
}
