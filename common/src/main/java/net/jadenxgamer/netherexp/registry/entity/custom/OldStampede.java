package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class OldStampede extends Monster implements NeutralMob {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();

    private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
    private int angerTime;
    private UUID angryAt;
    private int eating = 0;
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = (item) -> !item.hasPickUpDelay() && item.isAlive() && item.getItem().is(Items.BONE);
    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(OldStampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(OldStampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> AGITATED = SynchedEntityData.defineId(OldStampede.class, EntityDataSerializers.INT);

    public OldStampede(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setCanPickUpLoot(true);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.isInLava()) {
                swimAnimationState.startIfStopped(this.tickCount);
            }
            else swimAnimationState.stop();
            if (this.isMoving()) {
                if (this.isAngry()) {
                    runAnimationState.startIfStopped(this.tickCount);
                    idleAnimationState.stop();
                    walkAnimationState.stop();
                }
                else {
                    runAnimationState.stop();
                    walkAnimationState.startIfStopped(this.tickCount);
                    idleAnimationState.stop();
                }
            }
            else {
                runAnimationState.stop();
                walkAnimationState.stop();
                idleAnimationState.startIfStopped(this.tickCount);
            }
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.01F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.MOVEMENT_SPEED, 0.36)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.2, 32.0f));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new PickupItemGoal());
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Stray.class, true));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ItemStack item = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!item.isEmpty()) {
                ++this.eating;
                this.playEatingAnimation();
                this.setAngry(true);
                this.setEating(true);
                if (this.eating > 100) {
                    this.setItemSlot(EquipmentSlot.MAINHAND, Items.AIR.getDefaultInstance());
                    this.heal(10);
                    this.eating = 0;
                    this.setAngry(false);
                    this.setEating(false);
                }
            }
            else this.setAngry(this.getTarget() != null);
        }
        if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
            damageLivingEntities(this.level().getEntities(this, this.getBoundingBox(), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
        }

        super.aiStep();
    }

    private void playEatingAnimation() {
        if (this.eating % 5 == 0) {
            this.playSound(SoundEvents.GENERIC_EAT, 0.5F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            for (int i = 0; i < 6; ++i) {
                Vec3 vec3 = new Vec3(((double) this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double) this.random.nextFloat() - 0.5) * 0.1);
                vec3 = vec3.xRot(-this.getXRot() * 0.017453292F);
                vec3 = vec3.yRot(-this.getYRot() * 0.017453292F);
                double d = (double) (-this.random.nextFloat()) * 0.6 - 0.3;
                Vec3 vec32 = new Vec3(((double) this.random.nextFloat() - 0.5) * 0.8, d, 1.0 + ((double) this.random.nextFloat() - 0.5) * 0.4);
                vec32 = vec32.yRot(-this.yBodyRot * 0.017453292F);
                vec32 = vec32.add(this.getX(), this.getEyeY() + 1.0, this.getZ());
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItemBySlot(EquipmentSlot.MAINHAND)), vec32.x, vec32.y, vec32.z, vec3.x, vec3.y + 0.05, vec3.z);
            }
        }
    }

    private void damageLivingEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                entity.hurt(this.damageSources().source(JNEDamageSources.STAMPEDE_CRUSH, this), 10);
            }
        }
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty() && stack.is(Items.BONE);
    }

    @Override
    public void setLastHurtMob(Entity target) {
        super.setLastHurtMob(target);
        int i = random.nextInt(5);
        if (i == 0) {
            this.dropStridite();
        }
    }

    private void dropStridite() {
        int a = random.nextInt(3) + 1;
        for (int d = 0; d < a; ++d) {
            this.spawnAtLocation(JNEItems.STRIDITE.get());
        }
    }

    /////////
    // NBT //
    /////////

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ANGRY, false);
        this.getEntityData().define(EATING, false);
        this.getEntityData().define(AGITATED, 0);
    }

    public boolean isAngry() {
        return getEntityData().get(ANGRY);
    }

    public void setAngry(boolean angry) {
        getEntityData().set(ANGRY, angry);
    }

    public boolean isEating() {
        return getEntityData().get(EATING);
    }

    public void setEating(boolean eating) {
        getEntityData().set(EATING, eating);
    }

    ////////
    // AI //
    ////////

    class PickupItemGoal extends Goal {
        public PickupItemGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!OldStampede.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (OldStampede.this.getTarget() == null) {
                if (OldStampede.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = getItems();
                    return !list.isEmpty() && OldStampede.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        @Override
        public void tick() {
            List<ItemEntity> list = getItems();
            ItemStack itemStack = OldStampede.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemStack.isEmpty() && !list.isEmpty()) {
                OldStampede.this.getNavigation().moveTo(list.get(0), 1.2000000476837158);
            }
        }

        @Override
        public void start() {
            List<ItemEntity> list = getItems();
            if (!list.isEmpty()) {
                OldStampede.this.getNavigation().moveTo(list.get(0), 1.2000000476837158);
            }
        }

        private List<ItemEntity> getItems() {
            return OldStampede.this.level().getEntitiesOfClass(ItemEntity.class, OldStampede.this.getBoundingBox().inflate(8.0, 8.0, 8.0), OldStampede.PICKABLE_DROP_FILTER);
        }
    }


    ///////////
    // ANGER //
    ///////////

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.angryAt;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(this.random));
    }
}
