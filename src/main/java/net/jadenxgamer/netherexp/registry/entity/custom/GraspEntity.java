package net.jadenxgamer.netherexp.registry.entity.custom;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class GraspEntity
extends HostileEntity
implements GeoEntity {

    private static final TrackedData<BlockPos> HOME_POS;

    @SuppressWarnings("all")
    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    protected GraspEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setHomePos(BlockPos pos) {
        this.dataTracker.set(HOME_POS, pos);
    }

    BlockPos getHomePos() {
        return this.dataTracker.get(HOME_POS);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HOME_POS, BlockPos.ORIGIN);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("HomePosX", this.getHomePos().getX());
        nbt.putInt("HomePosY", this.getHomePos().getY());
        nbt.putInt("HomePosZ", this.getHomePos().getZ());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        int i = nbt.getInt("HomePosX");
        int j = nbt.getInt("HomePosY");
        int k = nbt.getInt("HomePosZ");
        this.setHomePos(new BlockPos(i, j, k));
        super.readCustomDataFromNbt(nbt);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setHomePos(this.getBlockPos());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    static {
        HOME_POS = DataTracker.registerData(GraspEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8, 1.0000001E-5f));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(8, new GoHomeGoal(this, 0.8));
    }

    static class GoHomeGoal extends Goal {
        private final GraspEntity grasp;
        private final double speed;
        private boolean noPath;
        private int homeReachingTryTicks;

        GoHomeGoal(GraspEntity grasp, double speed) {
            this.grasp = grasp;
            this.speed = speed;
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.grasp.getTarget();
            if (livingEntity != null && livingEntity.isAlive() && this.grasp.canTarget(livingEntity)) {
                return true;
            } else if (this.grasp.getRandom().nextInt(toGoalTicks(700)) != 0) {
                return false;
            } else {
                return !this.grasp.getHomePos().isWithinDistance(this.grasp.getPos(), 64.0);
            }
        }

        public void start() {
            this.noPath = false;
            this.homeReachingTryTicks = 0;
        }

        public void stop() {
            this.grasp.getHomePos();
        }

        public boolean shouldContinue() {
            return !this.grasp.getHomePos().isWithinDistance(this.grasp.getPos(), 7.0) && !this.noPath && this.homeReachingTryTicks <= this.getTickCount(600);
        }

        public void tick() {
            BlockPos blockPos = this.grasp.getHomePos();
            boolean bl = blockPos.isWithinDistance(this.grasp.getPos(), 16.0);
            if (bl) {
                ++this.homeReachingTryTicks;
            }

            if (this.grasp.getNavigation().isIdle()) {
                Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
                Vec3d vec3d2 = NoPenaltyTargeting.findTo(this.grasp, 16, 3, vec3d, 0.3141592741012573);
                if (vec3d2 == null) {
                    vec3d2 = NoPenaltyTargeting.findTo(this.grasp, 8, 7, vec3d, 1.5707963705062866);
                }

                if (vec3d2 != null && !bl && !this.grasp.getWorld().getBlockState(BlockPos.ofFloored(vec3d2)).isOf(Blocks.WATER)) {
                    vec3d2 = NoPenaltyTargeting.findTo(this.grasp, 16, 5, vec3d, 1.5707963705062866);
                }

                if (vec3d2 == null) {
                    this.noPath = true;
                    return;
                }

                this.grasp.getNavigation().startMovingTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);
            }

        }
    }

    @SuppressWarnings("all")
    private PlayState idlePredicate(AnimationState animationState) {
        if(!animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.grasp.idle", Animation.LoopType.LOOP).then("animation.grasp.souls", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @SuppressWarnings("all")
    private PlayState movePredicate(AnimationState animationState) {
        if(animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.grasp.walk", Animation.LoopType.LOOP).then("animation.grasp.souls", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::idlePredicate));
        controllers.add(new AnimationController<>(this, "move_controller",
                10, this::movePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}