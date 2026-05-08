package alexthw.hexblades.datagen;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexBlock;
import com.google.common.collect.ImmutableSet;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static alexthw.hexblades.registers.HexBlock.BLOCKS;
import static alexthw.hexblades.util.HexUtils.takeAll;

public class HexLootTableProvider extends LootTableProvider {

    public HexLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(HexBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {
        // Validation handled by parent
    }

    public static class HexBlockLootTables extends BlockLootSubProvider {

        private static final Set<Item> IMMUNE_TO_EXPLOSIONS = Stream.of(HexBlock.SWORD_STAND)
                .map(c -> c.get().asItem())
                .collect(ImmutableSet.toImmutableSet());

        protected HexBlockLootTables() {
            super(IMMUNE_TO_EXPLOSIONS, FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
            takeAll(blocks, b -> true).forEach(b -> {
                Hexblades.LOGGER.info("Registering loot table for block: {}", b.get());
                dropSelf(b.get());
            });
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
        }
    }
}
