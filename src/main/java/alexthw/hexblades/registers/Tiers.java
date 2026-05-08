package alexthw.hexblades.registers;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class Tiers {

    public static class HexiumTier implements Tier {
        public static final HexiumTier INSTANCE = new HexiumTier();

        @Override public int getUses() { return 750; }
        @Override public float getSpeed() { return 7.0F; }
        @Override public float getAttackDamageBonus() { return 3.0F; }
        @Override public int getLevel() { return 2; }
        @Override public int getEnchantmentValue() { return 25; }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(HexItem.HEXIUM_INGOT.get()));
        }
    }

    public static class PatronWeaponTier implements Tier {
        public static final PatronWeaponTier INSTANCE = new PatronWeaponTier();

        @Override public int getUses() { return 1250; }
        @Override public float getSpeed() { return 7.0F; }
        @Override public float getAttackDamageBonus() { return 3.0F; }
        @Override public int getLevel() { return 5; }
        @Override public int getEnchantmentValue() { return 5; }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
    }
}
