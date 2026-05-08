package alexthw.hexblades.compat.jei;

import alexthw.hexblades.common.items.ArmorFocus;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.recipes.ArmorFocusRecipe;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class ArmorFocusRecipeWrapper implements ICraftingCategoryExtension {

    private final ResourceLocation name;

    public ArmorFocusRecipeWrapper(ArmorFocusRecipe recipe) {
        this.name = recipe.getId();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
        ImmutableList.Builder<ItemStack> armors = ImmutableList.builder();
        armors.add(new ItemStack(HexItem.HEX_ARMOR_H.get()));
        armors.add(new ItemStack(HexItem.HEX_ARMOR_C.get()));
        armors.add(new ItemStack(HexItem.HEX_ARMOR_L.get()));
        armors.add(new ItemStack(HexItem.HEX_ARMOR_B.get()));

        ImmutableList.Builder<ItemStack> focus = ImmutableList.builder();
        focus.add(new ItemStack(HexItem.FOCUS_WARLOCK.get()));
        focus.add(new ItemStack(HexItem.FOCUS_BOTANIA.get()));
        focus.add(new ItemStack(HexItem.FOCUS_NOUVEAU.get()));

        List<List<ItemStack>> inputs = ImmutableList.of(armors.build(), focus.build());
        craftingGridHelper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK, inputs, 0, 0);

        List<ItemStack> outputs = armors.build();
        craftingGridHelper.createAndSetOutputs(builder, VanillaTypes.ITEM_STACK, outputs);
    }

    private List<ItemStack> getFociOnPiece(Item item) {
        if (item instanceof HexWArmor) {
            ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
            for (String type : ArmorFocus.foci) {
                ItemStack stack = new ItemStack(item);
                HexWArmor.setFocus(stack, type);
                builder.add(stack);
            }
            return builder.build();
        }
        return ImmutableList.of();
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return name;
    }
}
