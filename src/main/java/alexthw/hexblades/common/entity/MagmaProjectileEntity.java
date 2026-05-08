package alexthw.hexblades.common.entity;

import elucent.eidolon.common.entity.SpellProjectileEntity;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.client.particle.Particles;
import elucent.eidolon.registries.EidolonParticles;
import elucent.eidolon.registries.EidolonSounds;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class MagmaProjectileEntity extends SpellProjectileEntity {

    FireElementalEntity FEE;

    public Entity shoot(double x, double y, double z, double vx, double vy, double vz, FireElementalEntity firenando) {
        FEE = firenando;
        return super.shoot(x, y, z, vx, vy, vz, firenando, ItemStack.EMPTY);
    }

    public MagmaProjectileEntity(EntityType<? extends Projectile> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 pos = this.position();

        for (int i = 0; i < 8; ++i) {
            double lerpX = Mth.lerp((float) i / 8.0F, this.xo, pos.x);
            double lerpY = Mth.lerp((float) i / 8.0F, this.yo, pos.y);
            double lerpZ = Mth.lerp((float) i / 8.0F, this.zo, pos.z);
            Particles.create(EidolonParticles.FLAME_PARTICLE).setAlpha(0.5F, 0.0F).setScale(0.375F, 0.0F).setColor(1.0F, 0.5F, 0.15F, 0.65F, 0.25F, 0.075F).setLifetime(3).spawn(this.level(), lerpX, lerpY, lerpZ);
        }
    }

    @Override
    protected void onImpact(HitResult ray, Entity target) {
        target.hurt(this.damageSources().indirectMagic(FEE, this.getOwner()), 7.0F);
        this.onImpact(ray);
    }

    @Override
    protected void onImpact(HitResult ray) {
        this.discard();
        if (!this.level().isClientSide) {
            Vec3 pos = ray.getLocation();
            this.level().playSound(null, pos.x, pos.y, pos.z, EidolonSounds.SPLASH_SOULFIRE_EVENT.get(), net.minecraft.sounds.SoundSource.NEUTRAL, 0.6F, this.random.nextFloat() * 0.2F + 0.9F);
            Networking.sendToTracking(this.level(), this.blockPosition(), new MagicBurstEffectPacket(pos.x, pos.y, pos.z, ColorUtil.packColor(255, 255, 229, 125), ColorUtil.packColor(255, 124, 57, 247)));
        }
    }
}
