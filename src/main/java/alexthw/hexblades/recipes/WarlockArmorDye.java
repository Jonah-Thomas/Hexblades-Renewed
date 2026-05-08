package alexthw.hexblades.recipes;

import alexthw.hexblades.common.items.armors.DyebleWarlockArmor;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.common.item.WarlockRobesItem;
// Note: WarlockRobesItem extends ArmorItem; getEquipmentSlot() inherited from ArmorItem
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;

public class WarlockArmorDye extends CustomRecipe {

    public static final SimpleCraftingRecipeSerializer<WarlockArmorDye> SERIALIZER = new SimpleCraftingRecipeSerializer<>(WarlockArmorDye::new);

    public WarlockArmorDye(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param inv    grid
     * @param pLevel world
     */
    @Override
    public boolean matches(CraftingContainer inv, Level pLevel) {
        boolean foundDye = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof DyeItem && !foundDye) {
                    int color = ((DyeItem) stack.getItem()).getDyeColor().getId();
                    if (color == 7 || color == 8) return false;
                    foundDye = true;
                } else if (!foundItem) {
                    if (stack.getItem() instanceof WarlockRobesItem) {
                        foundItem = true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return foundDye && foundItem;
    }

    /**
     * Returns an Item that is the result of this recipe
     *
     * @param inv      grid
     * @param registry registry access
     */
    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess registry) {
        ItemStack item = ItemStack.EMPTY;
        DyeColor dye = null;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof WarlockRobesItem && item.isEmpty()) {
                    item = stack;
                } else {
                    dye = ((DyeItem) stack.getItem()).getDyeColor();
                }
            }
        }

        ItemStack dyed = item.copy();

        assert dye != null;

        if (item.getItem() instanceof DyebleWarlockArmor) {

            dyed.getOrCreateTag().putInt("color", dye.getId());

        } else {
            CompoundTag tag = item.getOrCreateTag();
            tag.putInt("color", dye.getId());
            WarlockRobesItem robes = (WarlockRobesItem) item.getItem();
            EquipmentSlot slot = robes.getEquipmentSlot();
            if (slot == EquipmentSlot.HEAD) {
                dyed = new ItemStack(HexItem.DYE_WARLOCK_H.get(), 1);
                dyed.setTag(tag);
            } else if (slot == EquipmentSlot.CHEST) {
                dyed = new ItemStack(HexItem.DYE_WARLOCK_C.get(), 1);
                dyed.setTag(tag);
            } else if (slot == EquipmentSlot.FEET) {
                dyed = new ItemStack(HexItem.DYE_WARLOCK_F.get(), 1);
                dyed.setTag(tag);
            }
        }

        return dyed;
    }


    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth > 1 || pHeight > 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
