package alexthw.hexblades.ritual;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.registers.HexRegistry;
import elucent.eidolon.api.ritual.ItemRequirement;
import elucent.eidolon.api.ritual.Ritual;
import elucent.eidolon.registries.Registry;
import elucent.eidolon.registries.RitualRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.util.HexUtils.*;
import static elucent.eidolon.registries.Registry.CRIMSON_ESSENCE;
import static elucent.eidolon.registries.EidolonPotions.CHILLED_POTION;

public class HexRituals {

    public static Ritual EVOLVE_SAPPING;
    public static Ritual AWAKE_SWORD;
    public static Ritual AWAKE_KATANA;
    public static Ritual AWAKE_SABER;
    public static Ritual AWAKE_HAMMER;
    public static Ritual AWAKE_DAGGER;
    public static Ritual AWAKE_SAPPING;

    public static Ritual EVOLVE_SWORD;
    public static Ritual EVOLVE_KATANA;
    public static Ritual EVOLVE_SABER;
    public static Ritual EVOLVE_HAMMER;
    public static Ritual EVOLVE_DAGGERS;

    public static Ritual SUMMON_FIRE;

    // TODO: In Eidolon-Repraised, the sacrifice/focus item linking (first arg of old register)
    // is handled via JSON data recipes (ritual_brazier recipe type), not code.
    // These rituals are registered by ResourceLocation only. Add the corresponding
    // data recipes in src/main/resources/data/hexblades/recipes/ to link the focus items.

    public static void init() {

        if (COMMON.NUKE_RITUALS.get()) return;

        //awake
        AWAKE_SWORD = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "awake_flame_sword"),
                new AwakenRitual(new ItemStack(HexItem.FIRE_BRAND.get()), fireColor)
                        .addRequirement(new ItemRequirement(Items.NETHERITE_SCRAP))
                        .addRequirement(new ItemRequirement(Items.NETHERITE_SCRAP))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(Items.MAGMA_CREAM))
        );

        AWAKE_KATANA = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "awake_ice_katana"),
                new AwakenRitual(new ItemStack(HexItem.FROST_RAZOR.get()), iceColor)
                        .addRequirement(new ItemRequirement(Items.BLUE_ICE))
                        .addRequirement(new ItemRequirement(Items.BLUE_ICE))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(Registry.WRAITH_HEART.get()))
                        .addRequirement(new ItemRequirement(Registry.TATTERED_CLOTH.get()))
        );

        AWAKE_SABER = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "awake_water_saber"),
                new AwakenRitual(new ItemStack(HexItem.WATER_SABER.get()), waterColor)
                        .addRequirement(new ItemRequirement(Items.PRISMARINE_SHARD))
                        .addRequirement(new ItemRequirement(Items.PRISMARINE_SHARD))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(Items.PRISMARINE_SHARD))
                        .addRequirement(new ItemRequirement(HexItem.DROWNED_HEART.get()))
        );

        AWAKE_HAMMER = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "awake_earth_hammer"),
                new AwakenRitual(new ItemStack(HexItem.EARTH_HAMMER.get()), earthColor)
                        .addRequirement(new ItemRequirement(Items.OBSIDIAN))
                        .addRequirement(new ItemRequirement(Items.OBSIDIAN))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(Registry.LEAD_BLOCK.get()))
                        .addRequirement(new ItemRequirement(Registry.LEAD_BLOCK.get()))
        );

        AWAKE_DAGGER = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "awake_lightning_dagger"),
                new AwakenRitual(new ItemStack(HexItem.LIGHTNING_DAGGER_L.get()), thunderColor)
                        .addRequirement(new ItemRequirement(Registry.ARCANE_GOLD_INGOT.get()))
                        .addRequirement(new ItemRequirement(Registry.ARCANE_GOLD_INGOT.get()))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(Registry.PEWTER_INGOT.get()))
                        .addRequirement(new ItemRequirement(Registry.PEWTER_INGOT.get()))
        );

        AWAKE_SAPPING = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "awake_sapping"),
                new AwakenRitual(new ItemStack(HexItem.BLOOD_SWORD.get()), fireColor)
                        .addRequirement(new ItemRequirement(Registry.LESSER_SOUL_GEM.get()))
                        .addRequirement(new ItemRequirement(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING)))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(PotionUtils.setPotion(new ItemStack(Items.POTION), HexRegistry.WITHER_POTION.get())))
                        .addRequirement(new ItemRequirement(Registry.LESSER_SOUL_GEM.get()))
        );

        //evolve
        EVOLVE_SWORD = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "evolve_flame_sword"),
                new EvolveRitual(new ItemStack(HexItem.FIRE_BRAND1.get()), fireColor)
                        .addRequirement(new ItemRequirement(Registry.SHADOW_GEM.get()))
                        .addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get()))
                        .addRequirement(new ItemRequirement(CRIMSON_ESSENCE.get()))
                        .addRequirement(new ItemRequirement(CRIMSON_ESSENCE.get()))
        );

        EVOLVE_KATANA = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "evolve_ice_katana"),
                new EvolveRitual(new ItemStack(HexItem.FROST_RAZOR1.get()), iceColor)
                        .addRequirement(new ItemRequirement(Items.QUARTZ))
                        .addRequirement(new ItemRequirement(PotionUtils.setPotion(new ItemStack(Items.POTION), CHILLED_POTION.get())))
                        .addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get()))
                        .addRequirement(new ItemRequirement(Registry.WRAITH_HEART.get()))
                        .addRequirement(new ItemRequirement(Items.QUARTZ))
        );

        EVOLVE_SABER = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "evolve_water_saber"),
                new EvolveRitual(new ItemStack(HexItem.WATER_SABER1.get()), waterColor)
                        .addRequirement(new ItemRequirement(Items.PRISMARINE_CRYSTALS))
                        .addRequirement(new ItemRequirement(Items.HEART_OF_THE_SEA))
                        .addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get()))
                        .addRequirement(new ItemRequirement(HexItem.DROWNED_HEART.get()))
                        .addRequirement(new ItemRequirement(Items.PRISMARINE_CRYSTALS))
        );

        EVOLVE_HAMMER = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "evolve_earth_hammer"),
                new EvolveRitual(new ItemStack(HexItem.EARTH_HAMMER1.get()), earthColor)
                        .addRequirement(new ItemRequirement(Registry.ARCANE_GOLD_BLOCK.get()))
                        .addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get()))
                        .addRequirement(new ItemRequirement(Registry.ARCANE_GOLD_BLOCK.get()))
        );

        EVOLVE_DAGGERS = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "evolve_duals"),
                new EvolveRitual(new ItemStack(HexItem.LIGHTNING_SSWORD_L.get()), thunderColor)
                        .addRequirement(new ItemRequirement(Items.REDSTONE))
                        .addRequirement(new ItemRequirement(Items.PHANTOM_MEMBRANE))
                        .addRequirement(new ItemRequirement(HexItem.LIGHTNING_DAGGER_L.get()))
                        .addRequirement(new ItemRequirement(HexItem.LIGHTNING_DAGGER_R.get()))
                        .addRequirement(new ItemRequirement(Items.PHANTOM_MEMBRANE))
                        .addRequirement(new ItemRequirement(Items.REDSTONE))
        );

        EVOLVE_SAPPING = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "evolve_sapping"),
                new EvolveRitual(new ItemStack(HexItem.BLOOD_SWORD1.get()), fireColor)
                        .addRequirement(new ItemRequirement(Registry.LESSER_SOUL_GEM.get()))
                        .addRequirement(new ItemRequirement(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING)))
                        .addRequirement(new ItemRequirement(Items.NETHER_STAR))
                        .addRequirement(new ItemRequirement(PotionUtils.setPotion(new ItemStack(Items.POTION), HexRegistry.WITHER_POTION.get())))
                        .addRequirement(new ItemRequirement(Registry.LESSER_SOUL_GEM.get()))
        );

        //make summoning cores

        SUMMON_FIRE = RitualRegistry.register(new ResourceLocation(Hexblades.MODID, "make_fire_core"),
                new InfusionRitual(new ItemStack(HexItem.FIRE_CORE.get()), fireColor)
                        .addRequirement(new ItemRequirement(CRIMSON_ESSENCE.get()))
                        .addRequirement(new ItemRequirement(Items.NETHER_BRICK))
                        .addRequirement(new ItemRequirement(Items.LAVA_BUCKET))
                        .addRequirement(new ItemRequirement(Items.QUARTZ))
                        .addRequirement(new ItemRequirement(Items.NETHER_BRICK))
                        .addRequirement(new ItemRequirement(CRIMSON_ESSENCE.get()))
        );
    }

}
