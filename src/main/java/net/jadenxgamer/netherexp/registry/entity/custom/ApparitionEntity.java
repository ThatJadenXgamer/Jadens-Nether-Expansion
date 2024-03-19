package net.jadenxgamer.netherexp.registry.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

public class ApparitionEntity extends HostileEntity implements Flutterer, GeoEntity {
    @SuppressWarnings("all")
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private static final TrackedData<Integer> PREFERENCE = DataTracker.registerData(ApparitionEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ApparitionEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        fallDistance = 0;
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public boolean isInAir() {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return PassiveEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.8)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(2, new ApparitionWanderAroundGoal());
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (spawnReason != SpawnReason.CONVERSION) {
            setPreference(MathHelper.nextInt(world.getRandom(),1, 4));
        }
        if (world instanceof ServerWorld) {
            this.activePreferredTargetGoal();
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    private void activePreferredTargetGoal() {
        int p = this.getPreference();
        if (p == 1) {
            this.targetSelector.add(4, new ActiveTargetGoal<>(this, SkeletonEntity.class, true));
            this.targetSelector.add(4, new ActiveTargetGoal<>(this, StrayEntity.class, true));
            this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        }
        else if (p == 2) {
            this.targetSelector.add(4, new ActiveTargetGoal<>(this, StriderEntity.class, true));
        }
        else if (p == 3) {
            this.targetSelector.add(4, new ActiveTargetGoal<>(this, MagmaCubeEntity.class, true));
            this.targetSelector.add(6, new ActiveTargetGoal<>(this, SlimeEntity.class, true));
            this.targetSelector.add(5, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        }
        else if (p == 4) {
            this.targetSelector.add(4, new ActiveTargetGoal<>(this, BlazeEntity.class, true));
            this.targetSelector.add(5, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        }
    }

    /////////
    // NBT //
    /////////

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(PREFERENCE, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Preference", this.getPreference());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setPreference(nbt.getInt("Preference"));
    }

    public int getPreference() {
        return this.dataTracker.get(PREFERENCE);
    }

    public void setPreference(int preference) {
        this.dataTracker.set(PREFERENCE, preference);
    }

    ////////////////////
    // GeckoLib Stuff //
    ////////////////////

    protected static final RawAnimation IDLE_1_ANIM = RawAnimation.begin().thenLoop("animation.apparition.idle1");
    protected static final RawAnimation IDLE_2_ANIM = RawAnimation.begin().thenLoop("animation.apparition.idle2");
    protected static final RawAnimation IDLE_3_ANIM = RawAnimation.begin().thenLoop("animation.apparition.idle3");
    protected static final RawAnimation IDLE_4_ANIM = RawAnimation.begin().thenLoop("animation.apparition.idle4");

    @SuppressWarnings("all")
    private PlayState movePredicate(AnimationState event) {
        int p = this.getPreference();
        if (p == 1) {
            return event.setAndContinue(IDLE_1_ANIM);
        }
        else if (p == 2) {
            return event.setAndContinue(IDLE_2_ANIM);
        }
        else if (p == 3) {
            return event.setAndContinue(IDLE_3_ANIM);
        }
        else return event.setAndContinue(IDLE_4_ANIM);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "moveController",
                15, this::movePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    ////////
    // AI //
    ////////

    class ApparitionWanderAroundGoal extends Goal {
        ApparitionWanderAroundGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            return ApparitionEntity.this.navigation.isIdle() && ApparitionEntity.this.random.nextInt(10) == 0;
        }

        public boolean shouldContinue() {
            return ApparitionEntity.this.navigation.isFollowingPath();
        }

        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                ApparitionEntity.this.navigation.startMovingAlong(ApparitionEntity.this.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }

        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2;
            vec3d2 = ApparitionEntity.this.getRotationVec(0.0F);

            Vec3d vec3d3 = AboveGroundTargeting.find(ApparitionEntity.this, 8, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
            return vec3d3 != null ? vec3d3 : NoPenaltySolidTargeting.find(ApparitionEntity.this, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }
}
