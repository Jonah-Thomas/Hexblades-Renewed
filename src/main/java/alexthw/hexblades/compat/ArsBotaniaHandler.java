package alexthw.hexblades.compat;

import alexthw.hexblades.common.items.armors.ArsBotaniaArmor;
import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class ArsBotaniaHandler {
    public static HexWArmor makeArmor(ArmorItem.Type type, Item.Properties properties) {
        return new ArsBotaniaArmor(type, properties);
    }

    public static void renderer() {
        // GeckoLib4: armor renderer is registered via initializeClient() on ArsBotaniaArmor itself
    }
}
