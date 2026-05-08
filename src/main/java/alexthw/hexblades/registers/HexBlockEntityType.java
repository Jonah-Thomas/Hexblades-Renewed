package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.blocks.tile_entities.EverfullUrnTileEntity;
import alexthw.hexblades.common.blocks.tile_entities.FirePedestalTileEntity;
import alexthw.hexblades.common.blocks.tile_entities.SwordStandTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HexBlockEntityType {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Hexblades.MODID);

    public static final RegistryObject<BlockEntityType<SwordStandTileEntity>> SWORD_STAND_TILE_ENTITY =
            BLOCK_ENTITIES.register("sword_stand_tile",
                    () -> BlockEntityType.Builder.of(SwordStandTileEntity::new, HexBlock.SWORD_STAND.get()).build(null));

    public static final RegistryObject<BlockEntityType<EverfullUrnTileEntity>> EVERFULL_URN_TILE_ENTITY =
            BLOCK_ENTITIES.register("everfull_urn_tile",
                    () -> BlockEntityType.Builder.of(EverfullUrnTileEntity::new, HexBlock.EVERFULL_URN.get()).build(null));

    public static final RegistryObject<BlockEntityType<FirePedestalTileEntity>> FIRE_PEDESTAL_TILE_ENTITY =
            BLOCK_ENTITIES.register("fire_pedestal_tile",
                    () -> BlockEntityType.Builder.of(FirePedestalTileEntity::new, HexBlock.FIRE_PEDESTAL.get()).build(null));

}
