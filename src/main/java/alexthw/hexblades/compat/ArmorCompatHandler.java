package alexthw.hexblades.compat;

import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.util.CompatUtil;
import elucent.eidolon.codex.Page;
import elucent.eidolon.codex.TitlePage;
import elucent.eidolon.codex.WorktablePage;
import elucent.eidolon.registries.Registry;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.codex.CodexHexChapters.makePageKey;
import static alexthw.hexblades.codex.CodexHexChapters.nukeRecipe;

public class ArmorCompatHandler {

    public static HexWArmor makeChest(Item.Properties properties) {
        ArmorItem.Type type = ArmorItem.Type.CHESTPLATE;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(type, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(type, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(type, properties);
        return new HexWArmor(type, properties);
    }

    public static HexWArmor makeHead(Item.Properties properties) {
        ArmorItem.Type type = ArmorItem.Type.HELMET;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(type, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(type, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(type, properties);
        return new HexWArmor(type, properties);
    }

    public static HexWArmor makeFeet(Item.Properties properties) {
        ArmorItem.Type type = ArmorItem.Type.BOOTS;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(type, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(type, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(type, properties);
        return new HexWArmor(type, properties);
    }

    public static HexWArmor makeLegs(Item.Properties properties) {
        ArmorItem.Type type = ArmorItem.Type.LEGGINGS;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(type, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(type, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(type, properties);
        return new HexWArmor(type, properties);
    }

    public static void attachRenderers() {
        // GeckoLib4: armor renderer registration is done via initializeClient() on the item itself
        // Compat armor renderers (Botania/ArsNouveau) handled in their respective compat classes
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded()) {
            ArsBotaniaHandler.renderer();
            return;
        }
        if (CompatUtil.isBotaniaLoaded()) {
            BotaniaCompat.renderer();
        } else if (CompatUtil.isArsNovLoaded()) {
            ArsNouveauCompat.renderer();
        }
    }

    public static Page[] makeCodex() {
        List<Page> pages = new ArrayList<>();

        TitlePage warlock = new TitlePage(makePageKey("warlock_focus"));
        WorktablePage warlockCraft = new WorktablePage(new ItemStack(HexItem.FOCUS_WARLOCK.get(), 1));

        Collections.addAll(pages, warlock, nukeRecipe(COMMON.NUKE_WORKBENCH.get(), warlockCraft));

        if (CompatUtil.isBotaniaLoaded()) {
            Collections.addAll(pages, BotaniaCompat.makeCodex());
        }
        if (CompatUtil.isArsNovLoaded()) {
            Collections.addAll(pages, ArsNouveauCompat.makeCodex());
        }

        return pages.toArray(new Page[0]);
    }
}
