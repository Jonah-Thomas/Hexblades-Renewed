package alexthw.hexblades.common.entity;

import alexthw.hexblades.common.entity.ai.fe.FEMeleeGoal;
import alexthw.hexblades.common.entity.ai.fe.FireCannonAttackGoal;
import alexthw.hexblades.common.entity.ai.fe.FireSpinAttackGoal;
import alexthw.hexblades.registers.HexEntityType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;

public class FireElementalEntity extends BaseElementalEntity implements NeutralMob {

    private static final EntityDataAccessor<Integer> ANIMATIONSTATE = SynchedEntityData.defineId(FireElementalEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FIRECHARGE = SynchedEntityData.defineId(FireElementalEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LOADING = SynchedEntityData.defineId(FireElementalEntity.class, EntityDataSerializers.BOOLEAN);

    private static final RawAnimation IDLE_BODY = RawAnimation.begin().thenLoop("animation.hexblades.fe.idle.body");
    private static final RawAnimation IDLE_ARMS = RawAnimation.begin().thenLoop("animation.hexblades.fe.idle.arms");
    private static final RawAnimation ATTACK_SHOOT = RawAnimation.begin().thenLoop("animation.hexblades.fe.attacks.shoot2");
    private static final RawAnimation ATTACK_MELEE = RawAnimation.begin().then("animation.hexblades.fe.attacks.melee", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation ATTACK_SPIN = RawAnimation.begin().thenLoop("animation.hexblades.fe.attacks.spin");

    public FireElementalEntity(EntityType<FireElementalEntity> type, Level worldIn) {
        super(type, worldIn);
        bossEvent = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        bossEvent.setDarkenScreen(true);
        this.registerGoals();
        this.navigation.canFloat();
    }

    @Override
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Nullable
    @Override
    protected net.minecraft.sounds.SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource p_184601_1_) {
        return net.minecraft.sounds.SoundEvents.BLAZE_HURT;
    }

    @Nullable
    @Override
    protected net.minecraft.sounds.SoundEvent getAmbientSound() {
        return net.minecraft.sounds.SoundEvents.FIRE_AMBIENT;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getDeathSound() {
        return net.minecraft.sounds.SoundEvents.BLAZE_DEATH;
    }

    public float getBrightness() {
        return 10.0F;
    }

    @Override
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_, net.minecraft.world.damagesource.DamageSource src) {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.applyEntityAI();
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Zoglin.class, true));
    }

    public static AttributeSupplier createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .build();
    }

    @Override
    public void aiStep() {
        net.minecraft.world.level.material.FluidState below = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getFluidState();
        Vec3 motion;
        if (!below.isEmpty()) {
            motion = this.getDeltaMovement();
            this.setOnGround(true);
            if (this.getY() + motion.y < (double) ((float) this.getBlockPosBelowThatAffectsMyMovement().getY() + below.getOwnHeight())) {
                this.setNoGravity(true);
                if (motion.y < 0.0D) {
                    this.setDeltaMovement(motion.multiply(1.0D, 0.0D, 1.0D));
                }
                this.setPos(this.getX(), (float) this.getBlockPosBelowThatAffectsMyMovement().getY() + below.getOwnHeight(), this.getZ());
            }
        } else {
            this.setNoGravity(false);
        }

        this.fallDistance = 0.0F;
        motion = this.getDeltaMovement();
        if (!this.onGround() && motion.y < 0.0D) {
            this.setDeltaMovement(motion.multiply(1.0D, 0.6D, 1.0D));
        }

        super.aiStep();
    }

    protected void applyEntityAI() {
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, new FireCannonAttackGoal(this, 1.0D, 40, 20.0F));
        this.goalSelector.addGoal(4, new FEMeleeGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new FireSpinAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public int getAnimationState() {
        return this.entityData.get(ANIMATIONSTATE);
    }

    public int getFireCharge() {
        return this.entityData.get(FIRECHARGE);
    }

    public boolean isCannonLoaded() {
        return this.entityData.get(LOADING);
    }

    public void setAnimationState(int anim) {
        this.entityData.set(ANIMATIONSTATE, anim);
    }

    public void addFireCharge(int charge) {
        this.entityData.set(FIRECHARGE, Math.max(0, getFireCharge() + charge));
    }

    public void loadCannon(boolean b) {
        this.entityData.set(LOADING, b);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATIONSTATE, 0);
        this.entityData.define(FIRECHARGE, 0);
        this.entityData.define(LOADING, false);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
            new AnimationController<>(this, "attack_controller", 5, this::attackPredicate),
            new AnimationController<>(this, "idle", 0, this::idleP)
        );
    }

    private PlayState idleP(AnimationState<FireElementalEntity> event) {
        event.getController().setAnimation(IDLE_BODY);
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationState<FireElementalEntity> event) {
        AnimationController<?> controller = event.getController();
        switch (this.getAnimationState()) {
            case 0 -> controller.setAnimation(IDLE_ARMS);
            case 1 -> controller.setAnimation(ATTACK_SHOOT);
            case 2 -> controller.setAnimation(ATTACK_MELEE);
            case 3 -> controller.setAnimation(ATTACK_SPIN);
            default -> { return PlayState.STOP; }
        }
        return PlayState.CONTINUE;
    }

    public void performRangedAttack(LivingEntity target, float pDistanceFactor) {
        this.lookAt(target, 360, 360);

        Vec3 vel = getEyePosition().add(getLookAngle().scale(40.0D)).subtract(this.position()).scale(0.05D);
        Vec3 pos = new Vec3(getX() + Math.cos(Math.toRadians(yBodyRot + 90)), getY() + 2.3F, getZ() + Math.sin(Math.toRadians(yBodyRot + 90)));
        loadCannon(false);

        level().addFreshEntity(
            (new MagmaProjectileEntity(HexEntityType.MAGMA_PROJECTILE.get(), level())).shoot(pos.x, pos.y, pos.z, vel.x * 0.9, vel.y, vel.z * 0.9, this)
        );
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);

        boolean flag = target.hurt(this.damageSources().mobAttack(this), f);
        if (flag) {
            if (target.invulnerableTime > 0) {
                target.invulnerableTime = 0;
            }
            target.hurt(this.damageSources().onFire(), 2.0F + (float) this.level().getDifficulty().getId() / 2);
            this.setLastHurtMob(target);
        }

        return flag;
    }

    // NeutralMob stubs — fire elemental doesn't use anger system but must implement
    @Override public int getRemainingPersistentAngerTime() { return 0; }
    @Override public void setRemainingPersistentAngerTime(int i) {}
    @Override public java.util.UUID getPersistentAngerTarget() { return null; }
    @Override public void setPersistentAngerTarget(@Nullable java.util.UUID uuid) {}
    @Override public void startPersistentAngerTimer() {}
}
