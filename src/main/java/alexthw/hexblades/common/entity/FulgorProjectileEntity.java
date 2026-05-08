package alexthw.hexblades.common.entity;

import alexthw.hexblades.registers.HexRegistry;
import elucent.eidolon.common.entity.SpellProjectileEntity;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.client.particle.Particles;
import elucent.eidolon.registries.EidolonParticles;
import elucent.eidolon.registries.EidolonSounds;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import static alexthw.hexblades.registers.HexParticles.FULGOR_PARTICLE;

public class FulgorProjectileEntity extends SpellProjectileEntity {

    public FulgorProjectileEntity(EntityType<? extends FulgorProjectileEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public Entity shoot(double x, double y, double z, double vx, double vy, double vz, Entity caster) {
        return super.shoot(x, y, z, vx, vy, vz, caster, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motion = this.getDeltaMovement();
        Vec3 pos = this.position();
        Vec3 norm = motion.normalize().scale(0.02500000037252903D);

        for (int i = 0; i < 8; ++i) {
            double lerpX = Mth.lerp((double) ((float) i / 8.0F), this.xo, pos.x);
            double lerpY = Mth.lerp((double) ((float) i / 8.0F), this.yo, pos.y);
            double lerpZ = Mth.lerp((double) ((float) i / 8.0F), this.zo, pos.z);
            Particles.create(FULGOR_PARTICLE).addVelocity(-norm.x, -norm.y, -norm.z).setAlpha(0.0825F, 0.0F).setScale(0.5F, 0.0F).setColor(1.0F, 0.875F, 0.25F, 0.75F, 0.375F, 0.25F).setLifetime(5).spawn(this.level(), lerpX, lerpY, lerpZ);
            Particles.create(EidolonParticles.SPARKLE_PARTICLE).addVelocity(-norm.x, -norm.y, -norm.z).setAlpha(0.521F, 0.0F).setScale(0.15F).setColor(1.0F, 0.875F, 0.25F, 0.75F, 0.375F, 0.25F).setLifetime(7).spawn(this.level(), lerpX, lerpY, lerpZ);
        }
    }

    @Override
    protected void onImpact(HitResult ray, Entity target) {
        Entity owner = this.getOwner();
        target.hurt(this.damageSources().indirectMagic(this, owner), 3.0F);
        if (target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(HexRegistry.CHARGED_EFFECT.get(), 100, 0));
        }
        this.onImpact(ray);
    }

    @Override
    protected void onImpact(HitResult ray) {
        this.discard();
        if (!this.level().isClientSide) {
            Vec3 pos = ray.getLocation();
            this.level().playSound(null, pos.x, pos.y, pos.z, EidolonSounds.SPLASH_SOULFIRE_EVENT.get(), net.minecraft.sounds.SoundSource.NEUTRAL, 0.5F, this.random.nextFloat() * 0.2F + 0.9F);
            Networking.sendToTracking(this.level(), this.blockPosition(), new MagicBurstEffectPacket(pos.x, pos.y, pos.z, ColorUtil.packColor(255, 255, 255, 72), ColorUtil.packColor(255, 255, 235, 102)));
        }
    }
}
