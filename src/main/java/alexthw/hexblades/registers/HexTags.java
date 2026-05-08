package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class HexTags {

    public static final class Blocks {
        public static final TagKey<Block> CRUCIBLE_HOT_BLOCKS = makeWrapperTag("crucible_heat_source");
        // Keep compatibility with older docs/configs that used the plural form.
        public static final TagKey<Block> CRUCIBLE_HOT_BLOCKS_LEGACY = makeWrapperTag("crucible_heat_sources");

        private static TagKey<Block> forge(String path) {
            return BlockTags.create(new ResourceLocation("forge", path));
        }

        private static TagKey<Block> mod(String path) {
            return BlockTags.create(new ResourceLocation(Hexblades.MODID, path));
        }
    }

    public static final class Items {
        public static final TagKey<Item> HEXIUM_INGOT = forge("ingot/hexium");
        public static final TagKey<Item> HEX_BLADE = mod("hexblade");

        public static TagKey<Item> forge(String path) {
            return ItemTags.create(new ResourceLocation("forge", path));
        }

        private static TagKey<Item> mod(String path) {
            return ItemTags.create(new ResourceLocation(Hexblades.MODID, path));
        }
    }

    public static TagKey<Block> makeWrapperTag(String id) {
        return BlockTags.create(HexUtils.prefix(id));
    }
}
