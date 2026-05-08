package alexthw.hexblades.deity;

import alexthw.hexblades.Hexblades;
import elucent.eidolon.api.deity.Deity;
import elucent.eidolon.common.deity.Deities;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class HexDeities extends Deities {

    public static Deity HEX_DEITY;
    public static ResourceLocation temp_id;

    public static void registerDeity() {
        temp_id = new ResourceLocation(Hexblades.MODID, "blade");
        HEX_DEITY = Deities.register(new HexDeity(temp_id, 0, 102, 255));
    }

    public static final List<String> DeityNames = Arrays.asList("dark", "blade");

}
