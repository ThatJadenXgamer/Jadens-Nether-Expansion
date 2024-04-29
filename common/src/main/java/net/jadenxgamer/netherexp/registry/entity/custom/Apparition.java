package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Apparition extends Monster implements FlyingAnimal {
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
            AnimationState i;
            AnimationState w;

            switch (this.getPreference()) {
                default: {
                    i = this.idle1AnimationState;
                    w = this.walk1AnimationState;
                    break;
                }
                case 2: {
                    i = this.idle2AnimationState;
                    w = this.walk2AnimationState;
                    break;
                }
                case 3: {
                    i = this.idle3AnimationState;
                    w = this.walk3AnimationState;
                    break;
                }
                case 4: {
                    i = this.idle4AnimationState;
                    w = this.walk4AnimationState;
                    break;
                }
            }

            if (this.isMoving()) {
                i.stop();
                w.startIfStopped(this.tickCount);
            }
            else {
                w.stop();
                i.startIfStopped(this.tickCount);
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
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(2, new ApparitionWanderAroundGoal());
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (mobSpawnType != MobSpawnType.CONVERSION) {
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
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }
        else if (p == 2) {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Strider.class, true));
        }
        else if (p == 3) {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, MagmaCube.class, true));
            this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Slime.class, true));
            this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }
        else if (p == 4) {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Blaze.class, true));
            this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }
    }

    ////////////////
    // POSSESSION //
    ////////////////

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
                vessel.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(vessel.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), null);
                if (skeleton.hasCustomName()) {
                    vessel.setCustomName(skeleton.getCustomName());
                }
                this.discard();
                return false;
            }
        }
        return super.killedEntity(serverLevel, livingEntity);
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
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setPreference(nbt.getInt("Preference"));
    }

    public int getPreference() {
        return this.entityData.get(PREFERENCE);
    }

    public void setPreference(int preference) {
        this.entityData.set(PREFERENCE, preference);
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
}
