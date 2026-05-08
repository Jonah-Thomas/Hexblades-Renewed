package alexthw.hexblades.compat.jei;

import alexthw.hexblades.common.items.armors.DyebleWarlockArmor;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.recipes.WarlockArmorDye;
import com.google.common.collect.ImmutableList;
import elucent.eidolon.common.item.WarlockRobesItem;
import elucent.eidolon.registries.Registry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;
import java.util.List;

public class WarlockArmorDyeWrapper implements ICraftingCategoryExtension {

    private final ResourceLocation name;

    public WarlockArmorDyeWrapper(WarlockArmorDye recipe) {
        this.name = recipe.getId();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
        ImmutableList.Builder<ItemStack> armors = ImmutableList.builder();
        armors.add(new ItemStack(HexItem.DYE_WARLOCK_H.get()));
        armors.add(new ItemStack(HexItem.DYE_WARLOCK_C.get()));
        armors.add(new ItemStack(HexItem.DYE_WARLOCK_F.get()));
        armors.add(new ItemStack(Registry.WARLOCK_HAT.get()));
        armors.add(new ItemStack(Registry.WARLOCK_CLOAK.get()));
        armors.add(new ItemStack(Registry.WARLOCK_BOOTS.get()));

        ImmutableList.Builder<ItemStack> dyes = ImmutableList.builder();
        dyes.add(new ItemStack(Items.WHITE_DYE));
        dyes.add(new ItemStack(Items.ORANGE_DYE));
        dyes.add(new ItemStack(Items.MAGENTA_DYE));
        dyes.add(new ItemStack(Items.LIGHT_BLUE_DYE));
        dyes.add(new ItemStack(Items.YELLOW_DYE));
        dyes.add(new ItemStack(Items.LIME_DYE));
        dyes.add(new ItemStack(Items.PINK_DYE));
        dyes.add(new ItemStack(Items.CYAN_DYE));
        dyes.add(new ItemStack(Items.PURPLE_DYE));
        dyes.add(new ItemStack(Items.BLUE_DYE));
        dyes.add(new ItemStack(Items.BROWN_DYE));
        dyes.add(new ItemStack(Items.GREEN_DYE));
        dyes.add(new ItemStack(Items.RED_DYE));
        dyes.add(new ItemStack(Items.BLACK_DYE));

        List<List<ItemStack>> inputs = ImmutableList.of(armors.build(), dyes.build());
        craftingGridHelper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK, inputs, 0, 0);

        ImmutableList.Builder<ItemStack> outputs = ImmutableList.builder();
        outputs.add(new ItemStack(HexItem.DYE_WARLOCK_H.get()));
        outputs.add(new ItemStack(HexItem.DYE_WARLOCK_C.get()));
        outputs.add(new ItemStack(HexItem.DYE_WARLOCK_F.get()));
        craftingGridHelper.createAndSetOutputs(builder, VanillaTypes.ITEM_STACK, outputs.build());
    }

    private List<ItemStack> getColorsOnPiece(Item item) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();

        if (item instanceof WarlockRobesItem robesItem) {
            if (!(item instanceof DyebleWarlockArmor)) {
                item = switch (robesItem.getType().getSlot()) {
                    case HEAD -> HexItem.DYE_WARLOCK_H.get();
                    case CHEST -> HexItem.DYE_WARLOCK_C.get();
                    case FEET -> HexItem.DYE_WARLOCK_F.get();
                    default -> item;
                };
            }
            for (int i = 0; i < 16; i++) {
                if (i != 8 && i != 7) {
                    ItemStack stack = new ItemStack(item);
                    stack.getOrCreateTag().putInt("color", i);
                    builder.add(stack);
                }
            }
            return builder.build();
        } else return ImmutableList.of();
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return name;
    }
}
