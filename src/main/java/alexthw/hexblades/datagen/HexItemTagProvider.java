package alexthw.hexblades.datagen;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.registers.HexTags;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.CompletableFuture;

public class HexItemTagProvider extends ItemTagsProvider {

    public HexItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Hexblades.MODID, existingFileHelper);
    }

    public static net.minecraft.tags.TagKey<Item> makeWrapperTag(String id) {
        return ItemTags.create(HexUtils.prefix(id));
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);

        tag(HexTags.Items.HEXIUM_INGOT).add(HexItem.HEXIUM_INGOT.get());
        HexItem.ITEMS.getEntries().stream().filter(c -> c.get() instanceof IHexblade).forEach(this::hexbladeTag);
        tag(Tags.Items.INGOTS).addTag(HexTags.Items.HEXIUM_INGOT);
    }

    private void hexbladeTag(RegistryObject<Item> item) {
        tag(HexTags.Items.HEX_BLADE).add(item.get());
    }

    @Override
    public String getName() {
        return "HexBlades Item Tags";
    }
}
