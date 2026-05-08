package alexthw.hexblades.recipes;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.compat.ArsNouveauCompat;
import alexthw.hexblades.compat.BotaniaCompat;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.recipe.CrucibleRecipe;
import elucent.eidolon.recipe.CrucibleRegistry;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import elucent.eidolon.registries.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.util.CompatUtil.isArsNovLoaded;
import static alexthw.hexblades.util.CompatUtil.isBotaniaLoaded;

public class TempRecipes {

    public static void init() {
        if (!COMMON.NUKE_CRUCIBLE.get()) {
            // HEXIUM INGOT: step1 = lead+gold (0 stirs), step2 = soul_shard+enchanted_ash (1 stir)
            CrucibleRegistry.register(new CrucibleRecipe(List.of(
                    new CrucibleRecipe.Step(0, List.of(
                            Ingredient.of(ItemTags.create(new ResourceLocation("forge", "ingots/lead"))),
                            Ingredient.of(Tags.Items.INGOTS_GOLD)
                    )),
                    new CrucibleRecipe.Step(1, List.of(
                            Ingredient.of(Registry.SOUL_SHARD.get()),
                            Ingredient.of(Registry.ENCHANTED_ASH.get())
                    ))
            ), new ItemStack(HexItem.HEXIUM_INGOT.get(), 2))
                    .setRegistryName(new ResourceLocation(Hexblades.MODID, "hexium_ingot")));

            // HEXED INGOT: step1 = hexium+arcane_gold (0 stirs), step2 = death_essence (1 stir)
            CrucibleRegistry.register(new CrucibleRecipe(List.of(
                    new CrucibleRecipe.Step(0, List.of(
                            Ingredient.of(HexItem.HEXIUM_INGOT.get()),
                            Ingredient.of(Registry.ARCANE_GOLD_INGOT.get())
                    )),
                    new CrucibleRecipe.Step(1, List.of(
                            Ingredient.of(Registry.DEATH_ESSENCE.get())
                    ))
            ), new ItemStack(HexItem.HEXED_INGOT.get(), 1))
                    .setRegistryName(new ResourceLocation(Hexblades.MODID, "hexed_ingot")));

            // SOUL CANDY: step1 = soul_shard (0 stirs), step2 = honey_bottle+sugar (1 stir)
            CrucibleRegistry.register(new CrucibleRecipe(List.of(
                    new CrucibleRecipe.Step(0, List.of(
                            Ingredient.of(Registry.SOUL_SHARD.get())
                    )),
                    new CrucibleRecipe.Step(1, List.of(
                            Ingredient.of(Items.HONEY_BOTTLE),
                            Ingredient.of(Items.SUGAR)
                    ))
            ), new ItemStack(HexItem.SOUL_CANDY.get(), 4))
                    .setRegistryName(new ResourceLocation(Hexblades.MODID, "soul_candy")));
        }
        if (!COMMON.NUKE_WORKBENCH.get()) {
            // ARMOR FOCUS
            WorktableRegistry.register(new WorktableRecipe(
                    new Ingredient[]{
                            Ingredient.of(ItemStack.EMPTY), Ingredient.of(HexItem.HEXIUM_INGOT.get()), Ingredient.of(ItemStack.EMPTY),
                            Ingredient.of(HexItem.HEXIUM_INGOT.get()), Ingredient.of(Registry.LESSER_SOUL_GEM.get()), Ingredient.of(HexItem.HEXIUM_INGOT.get()),
                            Ingredient.of(ItemStack.EMPTY), Ingredient.of(HexItem.HEXIUM_INGOT.get()), Ingredient.of(ItemStack.EMPTY)
                    },
                    new Ingredient[]{
                            Ingredient.of(Registry.UNHOLY_SYMBOL.get()),
                            Ingredient.of(Registry.GOLD_INLAY.get()),
                            Ingredient.of(Registry.WICKED_WEAVE.get()),
                            Ingredient.of(Registry.GOLD_INLAY.get())
                    },
                    new ItemStack(HexItem.FOCUS_BASE.get(), 1)
            ).setRegistryName(Hexblades.MODID, "blank_focus"));

            // WARLOCK FOCUS
            WorktableRegistry.register(new WorktableRecipe(
                    new Ingredient[]{
                            Ingredient.of(ItemStack.EMPTY), Ingredient.of(Registry.WICKED_WEAVE.get()), Ingredient.of(ItemStack.EMPTY),
                            Ingredient.of(Registry.WICKED_WEAVE.get()), Ingredient.of(HexItem.FOCUS_BASE.get()), Ingredient.of(Registry.WICKED_WEAVE.get()),
                            Ingredient.of(ItemStack.EMPTY), Ingredient.of(Registry.WICKED_WEAVE.get()), Ingredient.of(ItemStack.EMPTY)
                    },
                    new Ingredient[]{
                            Ingredient.of(Registry.ARCANE_GOLD_NUGGET.get()),
                            Ingredient.of(Registry.ARCANE_GOLD_NUGGET.get()),
                            Ingredient.of(Registry.ARCANE_GOLD_NUGGET.get()),
                            Ingredient.of(Registry.ARCANE_GOLD_NUGGET.get())
                    },
                    new ItemStack(HexItem.FOCUS_WARLOCK.get(), 1)
            ).setRegistryName(Hexblades.MODID, "eidolon_focus"));

            if (isBotaniaLoaded()) {
                BotaniaCompat.addRecipes();
            }
            if (isArsNovLoaded()) {
                ArsNouveauCompat.addRecipes();
            }
        }
    }
}
