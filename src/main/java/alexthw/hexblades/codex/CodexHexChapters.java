package alexthw.hexblades.codex;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.compat.ArmorCompatHandler;
import alexthw.hexblades.deity.HexFacts;
import alexthw.hexblades.registers.HexBlock;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.util.CompatUtil;
import alexthw.hexblades.spells.HexSpells;
import com.sammy.malum.registry.common.item.ItemRegistry;
import elucent.eidolon.codex.*;
import elucent.eidolon.Eidolon;
import elucent.eidolon.api.spells.Sign;
import elucent.eidolon.registries.Signs;
import elucent.eidolon.util.ColorUtil;
import elucent.eidolon.util.KnowledgeUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class CodexHexChapters {

    static Category HEXBLADES;
    static Category HEXBLADES_WEAPONS;
    static Chapter ICE_KATANA;
    static Chapter FLAME_SWORD;
    static Chapter WATER_SABER;
    static Chapter EARTH_HAMMER;
    static Chapter THUNDER_DUALS;
    static Chapter SANGUINE_UPGRADE;
    static Chapter HEX_SUMMONING;
    static Chapter HEX_EVOLUTION;
    static Chapter HEX_CORE;
    static Chapter HEX_PRAY;
    static Chapter HEX_ALLOY;
    static Chapter BLACK_PLANKS;
    static Chapter URN_OF_WATERS;
    static Chapter COMPAT;
    static Chapter MISC;
    static Chapter TEMPLES;
    static Chapter ARMORS;
    static Index HEXBLADES_INDEX;
    static Index HEXBLADES_WINDEX;

    static String prefix = Hexblades.MODID + ".codex.";

    public static String makePageKey(String path) {
        return prefix + "page." + path;
    }

    public static String makeChapterKey(String path) {
        return prefix + "chapter." + path;
    }

    public static void init() {
        //Main Index - page 1
        {
            BLACK_PLANKS = new Chapter(makeChapterKey("planks"), new TitlePage(makePageKey("dark_planks")),
                    new CraftingPage(new ItemStack(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem(), 8))
            );

            URN_OF_WATERS = new Chapter(makeChapterKey("urn"), new TitlePage(makePageKey("everfull_urn")),
                    new CraftingPage(new ItemStack(HexBlock.EVERFULL_URN.get()))
            );

            MISC = new Chapter(makeChapterKey("misc"),
                    new TitlePage(makePageKey("misc.soul_candy")),
                    nukeRecipe(COMMON.NUKE_CRUCIBLE.get(), new CruciblePage(new ItemStack(HexItem.SOUL_CANDY.get(), 4))),
                    new ChantPage(makePageKey("misc.fire_touch"), HexSpells.FIRE_TOUCH)
            );

            TEMPLES = new Chapter(makeChapterKey("structures"),
                    new TitlePage(makePageKey("fire_temple")),
                    new CraftingPage(new ItemStack(HexBlock.MAGMA_BRICKS.get(), 4))
            );

            if (CompatUtil.isMalumLoaded()) {
            COMPAT = new Chapter(makeChapterKey("compats"), new TitlePage(makePageKey("compats")), new TitlePage(makePageKey("compats.malum")), new ListPage(makePageKey("altar_ether"), new ListPage.ListEntry("brazier", new ItemStack(ItemRegistry.TAINTED_ETHER_BRAZIER.get()))));
        } else {
            COMPAT = new Chapter(makeChapterKey("compats"), new TitlePage(makePageKey("compats")));
        }

            ARMORS = new Chapter(makeChapterKey("armors"), margeArmorPages(getArmorPages(), ArmorCompatHandler.makeCodex()));
        }

        //Hex Theurgy - page 2
        {
            HEX_PRAY = new Chapter(makeChapterKey("hex_pray"),
                    new ChantPage(makePageKey("hex_pray.0"), HexSpells.HEX_PRAY),
                    new TextPage(makePageKey("hex_pray.1"))
            );

            HEX_CORE = new Chapter(makeChapterKey("hex_core"),
                    new TextPage(makePageKey("hex_core.0")),
                    new TextPage(makePageKey("hex_core.1"))
            );

            HEX_SUMMONING = new Chapter(makeChapterKey("hex_summon"),
                    new TextPage(makePageKey("hex_summon.0")),
                    new ChantPage(makePageKey("hex_summon.1"), HexSpells.HEX_SUMMON),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/make_fire_core")))
            );

            HEX_EVOLUTION = new Chapter(makeChapterKey("evolution"),
                    //evolve katana
                    new TitlePage(makePageKey("evolve_ice_katana")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/evolve_ice_katana"))),
                    //evolve sword
                    new TitlePage(makePageKey("evolve_flame_sword")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/evolve_flame_sword"))),
                    //evolve saber
                    new TitlePage(makePageKey("evolve_water_saber")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/evolve_water_saber"))),
                    //evolve hammer
                    new TitlePage(makePageKey("evolve_earth_hammer")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/evolve_earth_hammer"))),
                    //evolve dual
                    new TitlePage(makePageKey("evolve_duals")),
                                        nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/evolve_duals"))),
                                        //evolve blood sword
                                        new TitlePage(makePageKey("evolve_blood_sword")),
                                        nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/evolve_sapping")))
            );
        }

        ///Dull Weapons
        {
            HEX_ALLOY = new Chapter(makeChapterKey("hex_metal"), new TitlePage(makePageKey("hex_metal.0")),
                    //ingot
                    nukeRecipe(COMMON.NUKE_CRUCIBLE.get(), new CruciblePage(new ItemStack(HexItem.HEXIUM_INGOT.get(), 2))),
                    new TextPage(makePageKey("hex_metal.1")),
                    //katana
                    new CraftingPage(new ItemStack(HexItem.DULL_KATANA.get())),
                    //sword
                    new CraftingPage(new ItemStack(HexItem.DULL_BROADSWORD.get())),
                    //cutlass
                    new CraftingPage(new ItemStack(HexItem.DULL_SABER.get())),
                    //hammer
                    new CraftingPage(new ItemStack(HexItem.DULL_HAMMER.get())),
                    //dagger
                    new CraftingPage(new ItemStack(HexItem.DULL_DAGGER.get())),
                    //hexed ingot
                    new TitlePage(makePageKey("hexed_metal")),
                    nukeRecipe(COMMON.NUKE_CRUCIBLE.get(), new CruciblePage(new ItemStack(HexItem.HEXED_INGOT.get(), 1)))
            );
        }

        //HexBlades
        {
            FLAME_SWORD = new Chapter(makeChapterKey("flame_sword"),
                    new TitlePage(makePageKey("flame_sword")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/awake_flame_sword"))),
                    new TextPage(makePageKey("flame_sword.powers"))
            );

            ICE_KATANA = new Chapter(makeChapterKey("ice_katana"),
                    new TitlePage(makePageKey("ice_katana")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/awake_ice_katana"))),
                    new TextPage(makePageKey("ice_katana.powers"))
            );

            WATER_SABER = new Chapter(makeChapterKey("water_saber"),
                    new TitlePage(makePageKey("water_saber")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/awake_water_saber"))),
                    new TextPage(makePageKey("water_saber.powers"))
            );

            EARTH_HAMMER = new Chapter(makeChapterKey("earth_hammer"),
                    new TitlePage(makePageKey("earth_hammer")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/awake_earth_hammer"))),
                    new TextPage(makePageKey("earth_hammer.powers")),
                    new TextPage(makePageKey("earth_hammer.powers_mine"))
            );

            THUNDER_DUALS = new Chapter(makeChapterKey("thunder_duals"),
                    new TitlePage(makePageKey("thunder_duals")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/awake_lightning_dagger"))),
                    new TextPage(makePageKey("thunder_duals.powers"))
            );

            SANGUINE_UPGRADE = new Chapter(makeChapterKey("sanguine_upgrade"),
                    new TitlePage(makePageKey("sanguine_wither")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/awake_sapping"))),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(new ResourceLocation(Hexblades.MODID, "rituals/evolve_sapping")))
            );
        }

        makeIndexes();
    }

    private static Page[] margeArmorPages(Page[] armorPages, Page[] focusPages) {

        List<Page> list = new ArrayList<>();

        TitlePage blank = new TitlePage(makePageKey("blank_focus"));
        WorktablePage blankCraft = new WorktablePage(new ItemStack(HexItem.FOCUS_BASE.get(), 1));

        Collections.addAll(list, blank, nukeRecipe(COMMON.NUKE_WORKBENCH.get(), blankCraft));
        Collections.addAll(list, armorPages);
        Collections.addAll(list, focusPages);

        return list.toArray(new Page[0]);
    }

    static IndexPage.IndexEntry getCompatsPage() {
        if (CompatUtil.isMalumLoaded()) {
            return new IndexPage.IndexEntry(COMPAT, new ItemStack(ItemRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get()));
        } else {
            return new IndexPage.IndexEntry(COMPAT, new ItemStack(Items.CAMPFIRE));
        }
    }

    static Page[] getArmorPages() {
        CraftingPage helmCraft = new CraftingPage(new ItemStack(HexItem.HEX_ARMOR_H.get(), 1));
        CraftingPage chestCraft = new CraftingPage(new ItemStack(HexItem.HEX_ARMOR_C.get(), 1));
        CraftingPage legsCraft = new CraftingPage(new ItemStack(HexItem.HEX_ARMOR_L.get(), 1));
        CraftingPage bootsCraft = new CraftingPage(new ItemStack(HexItem.HEX_ARMOR_B.get(), 1));
        return new Page[]{
                new TextPage(makePageKey("circlet")),
                helmCraft,
                new TextPage(makePageKey("chestplate")),
                chestCraft,
                new TextPage(makePageKey("leggings")),
                legsCraft,
                new TextPage(makePageKey("boots")),
                bootsCraft
        };
    }

    static void makeIndexes() {
        HEXBLADES_INDEX = new Index(makeChapterKey("hex_index"),
                new TitledIndexPage("hexblades.codex.hex_index.0",
                        new IndexPage.IndexEntry(BLACK_PLANKS, new ItemStack(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())),
                        new IndexPage.IndexEntry(URN_OF_WATERS, new ItemStack(HexBlock.EVERFULL_URN.get().asItem())),
                        getCompatsPage(),
                        new IndexPage.IndexEntry(MISC, new ItemStack(HexBlock.SWORD_STAND.get().asItem())),
                        new IndexPage.IndexEntry(TEMPLES, new ItemStack(HexBlock.MAGMA_BRICKS.get().asItem())),
                        new IndexPage.IndexEntry(ARMORS, new ItemStack(HexItem.HEX_ARMOR_C.get()))
                ),
                new IndexPage(
                        signLockedVisibleSlot(HEX_PRAY, new ItemStack(HexItem.DEV_SWORD.get()), Signs.SOUL_SIGN),
                        factLockedVisibleSlot(HEX_CORE, new ItemStack(HexItem.ELEMENTAL_CORE.get()), HexFacts.AWAKENING_RITUAL),
                        factLockedVisibleSlot(HEX_SUMMONING, new ItemStack(HexItem.FIRE_CORE.get()), HexFacts.ELEMENTAL_SUMMON),
                        factLockedVisibleSlot(HEX_EVOLUTION, new ItemStack(HexItem.PATRON_SOUL2.get()), HexFacts.EVOLVE_RITUAL)
                )
        );

        HEXBLADES_WINDEX = new Index(makeChapterKey("weapon_index"),
                new TitledIndexPage("hexblades.codex.weapon_index.0",
                        new IndexPage.IndexEntry(HEX_ALLOY, new ItemStack(HexItem.HEXIUM_INGOT.get())),
                        factLockedVisibleSlot(ICE_KATANA, new ItemStack(HexItem.FROST_RAZOR.get()), HexFacts.AWAKENING_RITUAL),
                        factLockedVisibleSlot(FLAME_SWORD, new ItemStack(HexItem.FIRE_BRAND.get()), HexFacts.AWAKENING_RITUAL),
                        factLockedVisibleSlot(WATER_SABER, new ItemStack(HexItem.WATER_SABER.get()), HexFacts.AWAKENING_RITUAL),
                        factLockedVisibleSlot(EARTH_HAMMER, new ItemStack(HexItem.EARTH_HAMMER.get()), HexFacts.AWAKENING_RITUAL),
                        factLockedVisibleSlot(THUNDER_DUALS, new ItemStack(HexItem.LIGHTNING_DAGGER_L.get()), HexFacts.AWAKENING_RITUAL)
                ),
                new IndexPage(
                        factLockedVisibleSlot(SANGUINE_UPGRADE, new ItemStack(HexItem.BLOOD_SWORD.get()), HexFacts.AWAKENING_RITUAL)
                )
        );

        HEXBLADES = new Category(Hexblades.MODID, new ItemStack(HexItem.PATRON_SOUL.get()), ColorUtil.packColor(220, 0, 30, 66), HEXBLADES_INDEX);
        HEXBLADES_WEAPONS = new Category("hexblades_weapons", new ItemStack(HexItem.DEV_SWORD.get()), ColorUtil.packColor(220, 0, 0, 46), HEXBLADES_WINDEX);
    }

    @SubscribeEvent
    public static void onCodexPreInit(CodexEvents.PreInit event) {
        if (HEXBLADES != null) CodexChapters.categories.add(HEXBLADES);
        if (HEXBLADES_WEAPONS != null) CodexChapters.categories.add(HEXBLADES_WEAPONS);
    }

    public static Page nukeRecipe(boolean flag, Page page) {
        return flag ? disabled : page;
    }

        private static IndexPage.IndexEntry signLockedVisibleSlot(Chapter chapter, ItemStack icon, Sign... signs) {
                return new IndexPage.IndexEntry(chapter, icon, true) {
                        @Override
                        public boolean isUnlocked() {
                                for (Sign sign : signs) {
                                        if (!KnowledgeUtil.knowsSign(Eidolon.proxy.getPlayer(), sign)) {
                                                return false;
                                        }
                                }
                                return true;
                        }
                };
        }

        private static IndexPage.IndexEntry factLockedVisibleSlot(Chapter chapter, ItemStack icon, ResourceLocation... facts) {
                return new IndexPage.IndexEntry(chapter, icon, true) {
                        @Override
                        public boolean isUnlocked() {
                                for (ResourceLocation fact : facts) {
                                        if (!KnowledgeUtil.knowsFact(Eidolon.proxy.getPlayer(), fact)) {
                                                return false;
                                        }
                                }
                                return true;
                        }
                };
        }

    static TextPage disabled = new TextPage("hexblades.codex.disabled");

}
