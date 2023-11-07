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
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.UUID;

public class ApparitionEntity
extends HostileEntity
implements GeoEntity, Angerable, Flutterer {
    @SuppressWarnings("all")
    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    private static final UniformIntProvider ANGER_TIME_RANGE;
    private int angerTime;
    private UUID angryAt;

    public ApparitionEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

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

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
        .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.7000000238418579)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10.0)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
    }


    //TODO: Endermite is temporary replace it with Vessel
    @Override
    public boolean onKilledOther(ServerWorld world, LivingEntity other) {
        boolean bl = super.onKilledOther(world, other);
        if (other instanceof SkeletonEntity skeletonEntity) {
            EndermiteEntity endermiteEntity = skeletonEntity.convertTo(EntityType.ENDERMITE, false);
            this.remove(RemovalReason.DISCARDED);
            if (endermiteEntity != null) {
                skeletonEntity.initialize(world, world.getLocalDifficulty(skeletonEntity.getBlockPos()), SpawnReason.CONVERSION, new EntityData() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                }, null);
                if (!this.isSilent()) {
                    world.syncWorldEvent(null, 1026, this.getBlockPos(), 0);
                }

                bl = false;
            }
        }
        return bl;
    }

    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    protected boolean isDisallowedInPeaceful() {
        return false;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(4, new ApparitionWanderAroundGoal());
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, SkeletonEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, StriderEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, MagmaCubeEntity.class, true));
        this.targetSelector.add(3, new UniversalAngerGoal<>(this, false));
    }

    @Override
    public boolean isInAir() {
        return true;
    }

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

    @SuppressWarnings("all")
    private PlayState predicate(AnimationState animationState) {
        if(animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.apparition.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        else {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.apparition.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    private <T extends GeoAnimatable> PlayState attack_predicate(AnimationState<T> tAnimationState) {
        if(this.handSwinging && tAnimationState.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            tAnimationState.getController().forceAnimationReset();
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.apparition.attack", Animation.LoopType.PLAY_ONCE));
            this.handSwinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));
        controllers.add(new AnimationController<>(this, "attack_controller",
                10, this::attack_predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    static {
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    }
}
