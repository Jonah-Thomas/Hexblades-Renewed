package alexthw.hexblades.compat;

import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import elucent.eidolon.api.altar.AltarEntry;
import elucent.eidolon.api.altar.AltarKeys;
import elucent.eidolon.registries.AltarEntries;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.Map;

public class MalumCompat {

    @SuppressWarnings("unchecked")
    public static void altar() {
        try {
            Field entriesField = AltarEntries.class.getDeclaredField("entries");
            entriesField.setAccessible(true);
            Map<Block, AltarEntry> entries = (Map<Block, AltarEntry>) entriesField.get(null);
            ForgeRegistries.BLOCKS.getValues().stream()
                    .filter(b -> b instanceof EtherBrazierBlock)
                    .forEach(b -> entries.put(b, new AltarEntry(AltarKeys.LIGHT_KEY).setPower(1.5).setCapacity(1.5)));
        } catch (Exception e) {
            // Eidolon internals not accessible — skip silently
        }
    }
}
