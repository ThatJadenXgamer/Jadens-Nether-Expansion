package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.GargoyleStatueBlock;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class Apparition extends Monster implements FlyingAnimal {

    public int cooldown = 0;
    private static final EntityDataAccessor<Integer> PREFERENCE = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.INT);

    public final AnimationState idle1AnimationState = new AnimationState();
    public final AnimationState walk1AnimationState = new AnimationState();
    public final AnimationState idle2AnimationState = new AnimationState();
    public final AnimationState walk2AnimationState = new AnimationState();
    public final AnimationState idle3AnimationState = new AnimationState();
    public final AnimationState walk3AnimationState = new AnimationState();
    public final AnimationState idle4AnimationState = new AnimationState();
    public final AnimationState walk4AnimationState = new AnimationState();

    public Apparition(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, 1.0F);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            AnimationState idle;
            AnimationState walk;

            switch (this.getPreference()) {
                default: {
                    idle = this.idle1AnimationState;
                    walk = this.walk1AnimationState;
                    break;
                }
                case 2: {
                    idle = this.idle2AnimationState;
                    walk = this.walk2AnimationState;
                    break;
                }
                case 3: {
                    idle = this.idle3AnimationState;
                    walk = this.walk3AnimationState;
                    break;
                }
                case 4: {
                    idle = this.idle4AnimationState;
                    walk = this.walk4AnimationState;
                    break;
                }
            }

            if (this.isMoving()) {
                idle.stop();
                walk.startIfStopped(this.tickCount);
            }
            else {
                walk.stop();
                idle.startIfStopped(this.tickCount);
            }
        }
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.01F;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level) {
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(false);
        flyingPathNavigation.setCanPassDoors(true);
        return flyingPathNavigation;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        fallDistance = 0;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FLYING_SPEED, 0.8)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide() && this.getCooldown() > 0) {
            --this.cooldown;
        }
        if (this.random.nextInt(30) == 0) {
            this.playSound(JNESoundEvents.ENTITY_APPARITION_FLY.get(), 0.15F, 1.0F);
        }
        super.aiStep();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PossessGargoyleStatueGoal(this, 12));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(2, new ApparitionWanderAroundGoal());
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (this.getPreference() == 0) {
            setPreference(Mth.nextInt(level.getRandom(),1, 4));
        }
        if (level instanceof ServerLevel) {
            this.activePreferredTargetGoal();
        }
        return super.finalizeSpawn(level, difficulty, mobSpawnType, spawnGroupData, compoundTag);
    }

    private void activePreferredTargetGoal() {
        int p = this.getPreference();
        if (p == 1) {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Stray.class, true));
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Piglin.class, true));
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }
        else if (p == 2) {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Strider.class, true));
        }
        else if (p == 3) {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, MagmaCube.class, true));
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Piglin.class, true));
            this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }
        else if (p == 4) {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Blaze.class, true));
            this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Piglin.class, true));
            this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }
    }

    ////////////////
    // POSSESSION //
    ////////////////

    private boolean hurtWithCleanWater(ThrownPotion thrownPotion) {
        ItemStack itemStack = thrownPotion.getItem();
        Potion potion = PotionUtils.getPotion(itemStack);
        List<MobEffectInstance> list = PotionUtils.getMobEffects(itemStack);
        return potion == Potions.WATER && list.isEmpty();
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getDirectEntity() instanceof ThrownPotion thrownPotion && hurtWithCleanWater(thrownPotion)) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.ENTITY_APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
            this.discard();
        }
        return super.hurt(damageSource, f);
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        for (int i = 0; i < this.level().getRandom().nextInt(2) + 1; i++) {
            Wisp wisp = JNEEntityType.WISP.get().create(this.level());
            if (wisp != null) {
                wisp.setBoredDelay(0);
                wisp.setPos(this.getX(), this.getY(), this.getZ());
                this.level().addFreshEntity(wisp);
            }
        }
    }

    @Override
    public boolean killedEntity(ServerLevel serverLevel, LivingEntity livingEntity) {
        if (livingEntity instanceof Skeleton skeleton) {
            Vessel vessel = skeleton.convertTo(JNEEntityType.VESSEL.get(), false);
            if (vessel != null) {
                vessel.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(vessel.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, false), null);
                if (skeleton.hasCustomName()) {
                    vessel.setCustomName(skeleton.getCustomName());
                }
                this.discard();
                return false;
            }
        }
        else if (livingEntity instanceof MagmaCube magmaCube) {
            EctoSlab ectoSlab = JNEEntityType.ECTO_SLAB.get().create(this.level());
            if (ectoSlab != null) {
                ectoSlab.setSize(magmaCube.getSize(), true);
                ectoSlab.setPos(this.position());
                if (magmaCube.hasCustomName()) {
                    ectoSlab.setCustomName(magmaCube.getCustomName());
                }
                this.level().addFreshEntity(ectoSlab);
                this.discard();
                magmaCube.discard();
                return false;
            }
        }
        else if (livingEntity instanceof Blaze blaze) {
            Banshee banshee = blaze.convertTo(JNEEntityType.BANSHEE.get(), false);
            if (banshee != null) {
                banshee.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(banshee.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, false), null);
                if (blaze.hasCustomName()) {
                    banshee.setCustomName(blaze.getCustomName());
                }
                this.discard();
                return false;
            }
        }
        return super.killedEntity(serverLevel, livingEntity);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.playSound(JNESoundEvents.ENTITY_APPARITION_ATTACK.get(), 1.0f, 1.0f);
        return super.doHurtTarget(entity);
    }

    /////////
    // NBT //
    /////////

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PREFERENCE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Preference", this.getPreference());
        nbt.putInt("Cooldown", this.cooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setPreference(nbt.getInt("Preference"));
        this.cooldown = nbt.getInt("Cooldown");
    }

    public int getPreference() {
        return this.entityData.get(PREFERENCE);
    }

    public void setPreference(int preference) {
        this.entityData.set(PREFERENCE, preference);
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    ////////////
    // SOUNDS //
    ////////////

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return JNESoundEvents.ENTITY_APPARITION_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JNESoundEvents.ENTITY_APPARITION_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return JNESoundEvents.ENTITY_APPARITION_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(JNESoundEvents.ENTITY_APPARITION_FLY.get(), 0.15F, 1.0F);
    }

    ////////
    // AI //
    ////////

    class ApparitionWanderAroundGoal extends Goal {
        ApparitionWanderAroundGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return Apparition.this.navigation.isDone() && Apparition.this.random.nextInt(10) == 0;
        }

        public boolean canContinueToUse() {
            return Apparition.this.navigation.isInProgress();
        }

        public void start() {
            Vec3 vec3 = this.getRandomLocation();
            if (vec3 != null) {
                Apparition.this.navigation.moveTo(Apparition.this.navigation.createPath(BlockPos.containing(vec3), 1), 1.0);
            }

        }

        @Nullable
        private Vec3 getRandomLocation() {
            Vec3 vec3d2;
            vec3d2 = Apparition.this.getViewVector(0.0F);

            Vec3 vec3d3 = HoverRandomPos.getPos(Apparition.this, 8, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
            return vec3d3 != null ? vec3d3 : AirAndWaterRandomPos.getPos(Apparition.this, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }

    static class PossessGargoyleStatueGoal extends MoveToBlockGoal {
        private final Apparition apparition;

        public PossessGargoyleStatueGoal(Apparition entity, int range) {
            super(entity, 1.0F, range, range);
            apparition = entity;
        }

        @Override
        public double acceptedDistance() {
            return 2.0;
        }

        @Override
        protected boolean isValidTarget(LevelReader level, BlockPos pos) {
            BlockState blockState = level.getBlockState(pos);
            return blockState.is(JNETags.Blocks.POSSESSABLE_GARGOYLE_STATUES) && !blockState.getValue(GargoyleStatueBlock.SALTED);
        }

        @Override
        protected @NotNull BlockPos getMoveToTarget() {
            return this.blockPos;
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos target = getMoveToTarget();
            Level level = apparition.level();
            RandomSource random = level.random;
            if (this.isReachedTarget()) {
                if (level.getBlockState(target).is(JNEBlocks.PHASE_GARGOYLE_STATUE.get())) {
                    EctoSlab ectoSlab = JNEEntityType.ECTO_SLAB.get().create(level);
                    if (ectoSlab != null) {
                        if (level instanceof ServerLevel serverLevel) {
                            ectoSlab.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(this.blockPos), MobSpawnType.MOB_SUMMONED, null, null);
                        }
                        ectoSlab.setPos(apparition.getX(), apparition.getY(), apparition.getZ());
                        ectoSlab.setSize(4, true);
                        ectoSlab.setChangeType(0);
                        if (apparition.hasCustomName()) {
                            ectoSlab.setCustomName(apparition.getCustomName());
                        }
                        level.addFreshEntity(ectoSlab);
                    }
                }
                else if (level.getBlockState(target).is(JNEBlocks.GHOUL_GARGOYLE_STATUE.get())) {
                    Banshee banshee = JNEEntityType.BANSHEE.get().create(level);
                    if (banshee != null) {
                        if (level instanceof ServerLevel serverLevel) {
                            banshee.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(this.blockPos), MobSpawnType.MOB_SUMMONED, null, null);
                        }
                        banshee.setPos(apparition.getX(), apparition.getY(), apparition.getZ());
                        banshee.setChangeType(0);
                        if (apparition.hasCustomName()) {
                            banshee.setCustomName(apparition.getCustomName());
                        }
                        level.addFreshEntity(banshee);
                    }
                }
                else {
                    /* Fallback in case the tag was tampered with and included a statue which doesn't have a hardcoded transformation
                     * this also deals with Ossified Statue's transformation
                     */
                    Vessel vessel = JNEEntityType.VESSEL.get().create(level);
                    if (vessel != null) {
                        if (level instanceof ServerLevel serverLevel) {
                            vessel.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(this.blockPos), MobSpawnType.MOB_SUMMONED, null, null);
                        }
                        vessel.setPos(apparition.getX(), apparition.getY(), apparition.getZ());
                        vessel.setChangeType(0);
                        if (apparition.hasCustomName()) {
                            vessel.setCustomName(apparition.getCustomName());
                        }
                        level.addFreshEntity(vessel);
                    }
                }
                apparition.remove(RemovalReason.DISCARDED);
                Direction[] var5 = Direction.values();
                for (Direction direction : var5) {
                    BlockPos directionPos = blockPos.relative(direction);
                    if (!level.getBlockState(directionPos).isSolidRender(level, directionPos)) {
                        Direction.Axis axis = direction.getAxis();
                        double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat();
                        double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat();
                        double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat();
                        level.addParticle(ParticleTypes.SOUL, target.getX() + e, target.getY() + f, target.getZ() + g, 0.0, 0.0, 0.0);
                    }
                }
            }
        }

        @Override
        public boolean canUse() {
            return apparition.getCooldown() <= 0 && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            BlockPos target = getMoveToTarget();
            BlockState blockState = apparition.level().getBlockState(target);
            return blockState.is(JNETags.Blocks.POSSESSABLE_GARGOYLE_STATUES) && !blockState.getValue(GargoyleStatueBlock.SALTED) && super.canContinueToUse();
        }

        @Override
        public void start() {
            super.start();
        }

        @Override
        public void stop() {
            super.stop();
        }
    }
}
