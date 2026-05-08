package alexthw.hexblades.compat;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.common.items.armors.NouveauArmor;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.codex.Page;
import elucent.eidolon.codex.TitlePage;
import elucent.eidolon.codex.WorktablePage;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import elucent.eidolon.registries.Registry;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.codex.CodexHexChapters.makePageKey;
import static alexthw.hexblades.codex.CodexHexChapters.nukeRecipe;

public class ArsNouveauCompat {

    public static HexWArmor makeArmor(ArmorItem.Type type, Item.Properties properties) {
        return new NouveauArmor(type, properties);
    }

    public static boolean spellbookInOffHand(Player player) {
        return (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof com.hollingsworth.arsnouveau.common.items.SpellBook);
    }

    public static void addRecipes() {
        WorktableRegistry.register(new WorktableRecipe(
                new Ingredient[]{
                        Ingredient.of(ItemStack.EMPTY), Ingredient.of(com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry.MAGE_FIBER.get()), Ingredient.of(ItemStack.EMPTY),
                        Ingredient.of(com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry.MAGE_FIBER.get()), Ingredient.of(HexItem.FOCUS_BASE.get()), Ingredient.of(com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry.MAGE_FIBER.get()),
                        Ingredient.of(ItemStack.EMPTY), Ingredient.of(com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry.MAGE_FIBER.get()), Ingredient.of(ItemStack.EMPTY)
                },
                new Ingredient[]{
                        Ingredient.of(com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry.SOURCE_GEM.get()),
                        Ingredient.of(elucent.eidolon.registries.Registry.ENDER_CALX.get()),
                        Ingredient.of(com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry.SOURCE_GEM.get()),
                        Ingredient.of(elucent.eidolon.registries.Registry.ENDER_CALX.get())
                },
                new ItemStack(HexItem.FOCUS_NOUVEAU.get(), 1)
            ).setRegistryName("hexblades", "ars_nouveau_focus"));
    }

    public static void renderer() {
        // GeckoLib4: armor renderer is registered via initializeClient() on NouveauArmor itself
    }

    public static Page[] makeCodex() {
        TitlePage titled = new TitlePage(makePageKey("ars_focus"));
        WorktablePage focusCraft = new WorktablePage(new ItemStack(HexItem.FOCUS_NOUVEAU.get(), 1));
        return new Page[]{titled, nukeRecipe(COMMON.NUKE_WORKBENCH.get(), focusCraft)};
    }
}
