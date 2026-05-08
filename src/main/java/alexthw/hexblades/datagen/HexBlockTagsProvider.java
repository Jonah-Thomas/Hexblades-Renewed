package alexthw.hexblades.datagen;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexBlock;
import alexthw.hexblades.registers.HexTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import javax.annotation.Nonnull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static alexthw.hexblades.registers.HexBlock.BLOCKS;
import static net.minecraft.tags.BlockTags.*;

public class HexBlockTagsProvider extends BlockTagsProvider {

    public HexBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Hexblades.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(PLANKS).add(HexBlock.DARK_POLISH_PLANKS.getBlock());
        tag(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        tag(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairBlock));
        tag(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        tag(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        tag(WOODEN_SLABS).add(HexBlock.DARK_POLISH_PLANKS.getSlab());
        tag(WOODEN_STAIRS).add(HexBlock.DARK_POLISH_PLANKS.getStairs());
        tag(WOODEN_FENCES).add(HexBlock.DARK_POLISH_PLANKS.getFence());

        tag(HexTags.Blocks.CRUCIBLE_HOT_BLOCKS).add(Blocks.MAGMA_BLOCK, Blocks.FIRE, Blocks.SOUL_FIRE, Blocks.LAVA);

        // Malum ether blocks — add to crucible hot blocks
        ForgeRegistries.BLOCKS.getValues().stream()
            .filter(b -> b instanceof com.sammy.malum.common.block.ether.EtherBlock
                      || b instanceof com.sammy.malum.common.block.ether.EtherBrazierBlock)
            .forEach(this::addToCrucible);
        // TODO: Occultism and Druidcraft not yet available for 1.20.1
        // tag(HexTags.Blocks.CRUCIBLE_HOT_BLOCKS).addOptional(OccultismBlocks.SPIRIT_FIRE.getId());
    }

    private void addToCrucible(Block block) {
        tag(HexTags.Blocks.CRUCIBLE_HOT_BLOCKS).addOptional(ForgeRegistries.BLOCKS.getKey(block));
    }

    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        BLOCKS.getEntries().stream()
                .filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }

    @Override
    public String getName() {
        return "HexBlades Block Tags";
    }
}
