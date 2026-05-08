package alexthw.hexblades.common.potions;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.items.tier1.Lightning_SSwordR1;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EChargedEffect extends MobEffect {

    public EChargedEffect() {
        super(MobEffectCategory.HARMFUL, HexUtils.thunderColor);
        MinecraftForge.EVENT_BUS.addListener(this::shock);
    }

    protected static final ResourceLocation EFFECT_TEXTURE = new ResourceLocation(Hexblades.MODID, "textures/mob_effect/electro_charged.png");

    public void shock(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity source) {
            if (!event.getSource().is(net.minecraft.world.damagesource.DamageTypes.LIGHTNING_BOLT)) {
                if ((source.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof Lightning_SSwordR1) || event.getEntity().isInWaterOrRain()) {
                    if (event.getEntity().hasEffect(this)) {
                        event.setAmount(event.getAmount() + 2.0F);
                        event.getEntity().removeEffect(this);
                    }
                }
            }
        }
    }
}
