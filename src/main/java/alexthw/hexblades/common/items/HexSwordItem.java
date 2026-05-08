package alexthw.hexblades.common.items;

import alexthw.hexblades.compat.ArsNouveauCompat;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import static alexthw.hexblades.util.CompatUtil.isArsNovLoaded;

public class HexSwordItem extends SwordItem implements IHexblade {

    protected final double baseAttack;
    protected final double baseSpeed;
    protected ChatFormatting textColor = ChatFormatting.GOLD;

    protected MutableComponent tooltipText = Component.translatable("The Dev Sword, you shouldn't read this");
    protected int rechargeTick = 5;
    protected final int dialogueLines = 3;

    public HexSwordItem(int attackDamage, float attackSpeed, Properties properties) {
        super(Tiers.PatronWeaponTier.INSTANCE, attackDamage, attackSpeed, properties);
        baseAttack = attackDamage;
        baseSpeed = attackSpeed;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return shouldCauseReequipAnimation(oldStack, newStack);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        this.inventoryTick(pStack, pLevel, pEntity);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return this.hurtEnemy(pStack, pTarget, pAttacker, true);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide()) {
            if (!(player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ShieldItem)) {
                if (isArsNovLoaded()) {
                    if (ArsNouveauCompat.spellbookInOffHand(player)) return super.use(world, player, hand);
                }
                recalculatePowers(player.getItemInHand(hand), world, player);
            }
        }
        return super.use(world, player, hand);
    }

    @Override
    public void applyHexBonus(Player user, boolean awakened) {
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
    }

    public int getRechargeTicks() {
        return rechargeTick;
    }

    public int getEnergyLeft(ItemStack stack) {
        return (getMaxDamage(stack) - stack.getDamageValue());
    }

    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);
        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));
        setAttackPower(weapon, awakening, devotion);
        setAttackSpeed(weapon, awakening, devotion);
    }

    public void setAttackPower(ItemStack weapon, boolean awakening, double extradamage) {
        CompoundTag tag = weapon.getOrCreateTag();
        if (awakening) {
            tag.putDouble(Constants.NBT.EXTRA_DAMAGE, baseAttack + extradamage);
        } else {
            tag.putDouble(Constants.NBT.EXTRA_DAMAGE, baseAttack);
        }
    }

    public void setAttackSpeed(ItemStack weapon, boolean awakening, double extraspeed) {
        CompoundTag tag = weapon.getOrCreateTag();
        if (awakening) {
            tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, baseSpeed + extraspeed);
        } else {
            tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, baseSpeed);
        }
    }

    public double getAttackPower(ItemStack weapon) {
        double AP = weapon.getOrCreateTag().getDouble(Constants.NBT.EXTRA_DAMAGE);
        return AP > 0 ? AP : baseAttack;
    }

    public double getAttackSpeed(ItemStack weapon) {
        double AS = weapon.getOrCreateTag().getDouble(Constants.NBT.EXTRA_ATTACK_SPEED);
        return AS != 0 ? AS : baseSpeed;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(tooltipText.withStyle(ChatFormatting.ITALIC).withStyle(textColor));
        tooltip.add(Component.literal(""));
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.hexblades.awakened").withStyle(ChatFormatting.BLUE));
            addShiftTooltip(stack, tooltip);
        } else {
            tooltip.add(Component.translatable("tooltip.hexblades.shift"));
        }
    }

    protected void addShiftTooltip(ItemStack stack, List<Component> tooltip) {
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getAttackPower(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }

    public void talk(Player player) {
        player.sendSystemMessage(Component.translatable(this.getDescriptionId() + ".dialogue." + player.level().getRandom().nextInt(dialogueLines)).withStyle(s -> s.withItalic(true)));
    }
}
