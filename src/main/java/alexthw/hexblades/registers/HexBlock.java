package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.blocks.DecoBlock;
import alexthw.hexblades.common.blocks.EverfullUrnBlock;
import alexthw.hexblades.common.blocks.FirePedestalBlock;
import alexthw.hexblades.common.blocks.SwordStandBlock;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static alexthw.hexblades.registers.HexItem.addTabProp;

public class HexBlock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Hexblades.MODID);

    public static final RegistryObject<Block> SWORD_STAND;
    public static final RegistryObject<Block> EVERFULL_URN;
    public static final RegistryObject<Block> FIRE_PEDESTAL;
    public static final RegistryObject<Block> MAGMA_BRICKS;
    public static DecoBlockPack DARK_POLISH_PLANKS;

    static {
        SWORD_STAND = addBlock("sword_stand", () -> new SwordStandBlock(
                BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .requiresCorrectToolForDrops()
                        .noOcclusion()
        ));

        FIRE_PEDESTAL = addBlock("fire_pedestal", () -> new FirePedestalBlock(
                BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN)
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .requiresCorrectToolForDrops()
                        .noOcclusion().strength(50.0F, 1200.0F)
        ));

        EVERFULL_URN = addBlock("everfull_urn", () -> new EverfullUrnBlock(
                BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .requiresCorrectToolForDrops()
                        .noOcclusion()
        ));

        MAGMA_BRICKS = addBlock("nether_magma_bricks", () -> new Block(
                BlockBehaviour.Properties.of().mapColor(MapColor.NETHER)
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .lightLevel((state) -> 4)
                        .requiresCorrectToolForDrops()
                        .strength(2.0F, 6.0F)
                        .sound(SoundType.NETHER_BRICKS)
        ));

        DARK_POLISH_PLANKS = new DecoBlockPack(BLOCKS, "dark_polished_planks",
                BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK)
                        .sound(SoundType.WOOD)
                        .strength(1.6F, 3.0F))
                .addFence();
    }

    public static class DecoBlockPack {
        DeferredRegister<Block> registry;
        String basename;
        BlockBehaviour.Properties props;
        RegistryObject<Block> full;
        RegistryObject<Block> slab;
        RegistryObject<Block> stair;
        RegistryObject<Block> wall = null;
        RegistryObject<Block> fence = null;
        RegistryObject<Block> fence_gate = null;

        public DecoBlockPack(DeferredRegister<Block> blocks, String basename, BlockBehaviour.Properties props) {
            this.registry = blocks;
            this.basename = basename;
            this.props = props;
            this.full = addBlock(basename, () -> new DecoBlock(props));
            this.slab = addBlock(basename + "_slab", () -> new SlabBlock(props));
            this.stair = addBlock(basename + "_stairs", () -> new StairBlock(
                    () -> this.full.get().defaultBlockState(), props));
        }

        public DecoBlockPack addWall() {
            this.wall = addBlock(this.basename + "_wall", () -> new WallBlock(this.props));
            return this;
        }

        public DecoBlockPack addFence() {
            this.fence = addBlock(this.basename + "_fence", () -> new FenceBlock(this.props));
            this.fence_gate = addBlock(this.basename + "_fence_gate", () -> new FenceGateBlock(this.props, SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
            return this;
        }

        public Block getBlock() { return this.full.get(); }
        public Block getSlab() { return this.slab.get(); }
        public Block getStairs() { return this.stair.get(); }
        public Block getWall() { return this.wall.get(); }
        public Block getFence() { return this.fence.get(); }
        public Block getFenceGate() { return this.fence_gate.get(); }
    }

    static RegistryObject<Block> addBlock(String name, Supplier<? extends Block> blockSupplier) {
        RegistryObject<Block> registeredBlock = BLOCKS.register(name, blockSupplier::get);
        HexItem.ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), addTabProp()));
        return registeredBlock;
    }
}
