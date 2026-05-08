package alexthw.hexblades.common.items.armors;

import alexthw.hexblades.client.render.entity.ArmorRenderer;
import alexthw.hexblades.registers.HexItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

import static alexthw.hexblades.util.Constants.ArmorCompat.FOCUS_TAG;

public class HexWArmor extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HexWArmor(ArmorItem.Type type, Properties builderIn) {
        super(HexWArmor.Material.INSTANCE, type, builderIn);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new ArmorRenderer();
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    public static String getFocus(ItemStack stack) {
        String focus = stack.getOrCreateTag().getString(FOCUS_TAG);
        return focus.equals("") ? "none" : focus;
    }

    public static void setFocus(ItemStack stack, String focus) {
        if (!(stack.getItem() instanceof HexWArmor)) return;
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString(FOCUS_TAG, focus);
        stack.setTag(tag);
    }

    public static int getFocusId(ItemStack stack) {
        return switch (stack.getOrCreateTag().getString(FOCUS_TAG)) {
            case "eidolon" -> 1;
            case "botania" -> 2;
            case "ars nouveau" -> 3;
            default -> 0;
        };
    }

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(Component.literal("Focus: " + getFocus(pStack)));
    }

    public static class Material implements ArmorMaterial {
        public static final HexWArmor.Material INSTANCE = new HexWArmor.Material();

        public Material() {
        }

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            EquipmentSlot slot = type.getSlot();
            return HexWArmor.MAX_DAMAGE_ARRAY[slot.getIndex()] * 30;
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return switch (type) {
                case CHESTPLATE -> 8;
                case LEGGINGS -> 6;
                case HELMET, BOOTS -> 3;
                default -> 0;
            };
        }

        @Override
        public int getEnchantmentValue() {
            return 25;
        }

        @Override
        public SoundEvent getEquipSound() {
            return ArmorMaterials.GOLD.getEquipSound();
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(HexItem.HEXED_INGOT.get()));
        }

        @Override
        public String getName() {
            return "hexblades:hex_armor";
        }

        @Override
        public float getToughness() {
            return 2.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.075F;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // no animations needed
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
