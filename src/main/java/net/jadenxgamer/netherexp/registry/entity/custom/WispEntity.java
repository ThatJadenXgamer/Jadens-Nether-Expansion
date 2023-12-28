package net.jadenxgamer.netherexp.registry.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.NoWaterTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;

public class WispEntity
extends PassiveEntity
implements GeoEntity, Flutterer {
    @SuppressWarnings("all")
    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    @Nullable
    BlockPos soulFirePos;

    public WispEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    @Override
    public boolean isInAir() {
        return true;
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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.8)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new TemptGoal(this, 1.0, Ingredient.ofItems(Items.SOUL_TORCH), false));
        this.goalSelector.add(3, new FleeGoal<>(this, PlayerEntity.class, 8.0F, 0.4, 0.8));
        this.goalSelector.add(4, new WispWanderAroundGoal());
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    @Nullable
    public BlockPos getSoulFirePos() {
        return this.soulFirePos;
    }

    public boolean hasSoulFire() {
        return this.soulFirePos != null;
    }

    boolean isSoulFire(BlockPos pos) {
        return this.getWorld().canSetBlock(pos) && this.getWorld().getBlockState(pos).isOf(Blocks.SOUL_TORCH);
    }

    @SuppressWarnings("all")
    boolean isWithinDistance(BlockPos pos, int distance) {
        return pos.isWithinDistance(this.getBlockPos(), distance);
    }

    boolean isTooFar(BlockPos pos) {
        return !this.isWithinDistance(pos, 32);
    }

    void startMovingTo(BlockPos pos) {
        Vec3d vec3d = Vec3d.ofBottomCenter(pos);
        int i = 0;
        BlockPos blockPos = this.getBlockPos();
        int j = (int)vec3d.y - blockPos.getY();
        if (j > 2) {
            i = 4;
        } else if (j < -2) {
            i = -4;
        }

        int k = 6;
        int l = 8;
        int m = blockPos.getManhattanDistance(pos);
        if (m < 15) {
            k = m / 2;
            l = m / 2;
        }

        Vec3d vec3d2 = NoWaterTargeting.find(this, k, l, i, vec3d, 0.3141592741012573);
        if (vec3d2 != null) {
            this.navigation.setRangeMultiplier(0.5F);
            this.navigation.startMovingTo(vec3d2.x, vec3d2.y, vec3d2.z, 1.0);
        }
    }

    /////////
    // NBT //
    /////////

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.hasSoulFire()) {
            assert this.getSoulFirePos() != null;
            nbt.put("SoulFirePos", NbtHelper.fromBlockPos(this.getSoulFirePos()));
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.soulFirePos = null;
        if (nbt.contains("SoulFirePos")) {
            this.soulFirePos = NbtHelper.toBlockPos(nbt.getCompound("SoulFirePos"));
        }
    }

    ////////////////////
    // GeckoLib Stuff //
    ////////////////////

    @SuppressWarnings("all")
    private PlayState predicate(AnimationState animationState) {
        if(animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.wisp.move", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        else {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.wisp.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    ////////
    // AI //
    ////////

    static class FleeGoal<T extends LivingEntity>
            extends FleeEntityGoal<T> {

        public FleeGoal(WispEntity wisp, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
            super(wisp, fleeFromType, distance, slowSpeed, fastSpeed);
        }
    }

    class WispWanderAroundGoal extends Goal {
        WispWanderAroundGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            return WispEntity.this.navigation.isIdle() && WispEntity.this.random.nextInt(10) == 0;
        }

        public boolean shouldContinue() {
            return WispEntity.this.navigation.isFollowingPath();
        }

        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                WispEntity.this.navigation.startMovingAlong(WispEntity.this.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }

        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2;
            vec3d2 = WispEntity.this.getRotationVec(0.0F);

            Vec3d vec3d3 = AboveGroundTargeting.find(WispEntity.this, 8, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
            return vec3d3 != null ? vec3d3 : NoPenaltySolidTargeting.find(WispEntity.this, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }
}
