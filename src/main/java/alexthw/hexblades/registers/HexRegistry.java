package alexthw.hexblades.registers;

import alexthw.hexblades.codex.CodexHexChapters;
import alexthw.hexblades.common.potions.EChargedEffect;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.network.FlameEffectPacket;
import alexthw.hexblades.network.MiningSwitchPacket;
import alexthw.hexblades.network.RefillEffectPacket;
import alexthw.hexblades.network.WeaponAwakenPacket;
import alexthw.hexblades.recipes.TempRecipes;
import alexthw.hexblades.ritual.HexRituals;
import alexthw.hexblades.spells.HexSpells;
import alexthw.hexblades.util.CompatUtil;
import elucent.eidolon.common.tile.CrucibleTileEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.RegistryObject;

import static alexthw.hexblades.Hexblades.MODID;
import static alexthw.hexblades.registers.Registry.POTIONS;
import static alexthw.hexblades.registers.Registry.POTION_TYPES;
import static alexthw.hexblades.util.CompatUtil.isMalumLoaded;

public class HexRegistry {

    public static final String NETWORK_VERSION = "1";
    public static SimpleChannel CHANNEL;

    public static RegistryObject<MobEffect> CHARGED_EFFECT;
    public static RegistryObject<Potion> CHARGED_POTION;
    public static RegistryObject<Potion> WITHER_POTION;

    static {
        CHARGED_EFFECT = POTIONS.register("electro_charged", EChargedEffect::new);
        CHARGED_POTION = POTION_TYPES.register("electro_charged",
                () -> new Potion(new MobEffectInstance(CHARGED_EFFECT.get(), 3600)));
        WITHER_POTION = POTION_TYPES.register("wither",
                () -> new Potion(new MobEffectInstance(MobEffects.WITHER, 100)));
    }

    public static void init() {
        HexDeities.registerDeity();
        HexSpells.RegisterSpells();
        HexSerializers.registerCmdArgTypesSerializers();
    }

    public static void post_init() {
        setupNetwork();
        TempRecipes.init();
        HexRituals.init();
        CompatUtil.check();

        // Restore 1.16 behavior: allow Hexblades-defined tags to extend crucible heat sources.
        CrucibleTileEntity.HOT_BLOCKS.add(b -> b.is(HexTags.Blocks.CRUCIBLE_HOT_BLOCKS));
        CrucibleTileEntity.HOT_BLOCKS.add(b -> b.is(HexTags.Blocks.CRUCIBLE_HOT_BLOCKS_LEGACY));

        if (isMalumLoaded()) {
            try { alexthw.hexblades.compat.MalumCompat.altar(); } catch (Exception e) { alexthw.hexblades.Hexblades.LOGGER.error("Malum compat failed", e); }
        }

        CodexHexChapters.init();

        // TODO: Eidolon-Repraised PotionBrewing mixin — verify if still available
        // The old PotionBrewingMixin.callAddMix needs to be replaced with BrewingRecipeRegistry.addRecipe
        // if Eidolon-Repraised no longer exposes that mixin
        // net.minecraftforge.common.brewing.BrewingRecipeRegistry.addRecipe(
        //     new net.minecraftforge.common.brewing.SimpleBrewingRecipe(...));
    }

    private static void setupNetwork() {
        CHANNEL = NetworkRegistry.newSimpleChannel(
                new net.minecraft.resources.ResourceLocation(MODID, "main"),
                () -> NETWORK_VERSION,
                NETWORK_VERSION::equals,
                NETWORK_VERSION::equals
        );

        int id = 0;
        CHANNEL.registerMessage(id++, FlameEffectPacket.class,
                FlameEffectPacket::encode, FlameEffectPacket::decode, FlameEffectPacket::consume);
        CHANNEL.registerMessage(id++, RefillEffectPacket.class,
                RefillEffectPacket::encode, RefillEffectPacket::decode, RefillEffectPacket::consume);
        CHANNEL.registerMessage(id++, MiningSwitchPacket.class,
                MiningSwitchPacket::encode, MiningSwitchPacket::decode, MiningSwitchPacket::consume);
        CHANNEL.registerMessage(id, WeaponAwakenPacket.class,
                WeaponAwakenPacket::encode, WeaponAwakenPacket::decode, WeaponAwakenPacket::consume);
    }
}
