package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.EctoSoulSandBlock;
import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.jadenxgamer.netherexp.registry.particle.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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
implements GeoEntity, Flutterer, Bottleable {
    @SuppressWarnings("all")
    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public int boredDelay = 1000;

    private static final TrackedData<Boolean> FROM_BOTTLE = DataTracker.registerData(WispEntity.class, TrackedDataHandlerRegistry.BOOLEAN);;

    public WispEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    @Override
    public boolean isInAir() {
        return true;
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

    public static DefaultAttributeContainer.Builder setAttributes() {
        return PassiveEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new TemptGoal(this, 1.0, Ingredient.ofItems(Items.SOUL_TORCH), false));
        this.goalSelector.add(3, new FleeGoal<>(this, PiglinEntity.class, 8.0F, 0.4, 0.8));
        this.goalSelector.add(3, new FleeGoal<>(this, PiglinBruteEntity.class, 8.0F, 0.4, 0.8));
        this.goalSelector.add(3, new FleeGoal<>(this, HoglinEntity.class, 8.0F, 0.4, 0.8));
        this.goalSelector.add(4, new BurrowInSoulSandGoal(this, 16));
        this.goalSelector.add(4, new WispWanderAroundGoal());
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            if (this.getBoredDelay() > 0 && !this.isFromBottle()) {
                --this.boredDelay;
            }
        }
        else {
            for(int i = 0; i < 2; ++i) {
                this.getWorld().addParticle(ModParticles.WISP, this.getParticleX(0.5), this.getRandomBodyY() - 0.25, this.getParticleZ(0.5), 0.0, 0.0, 0.0);
            }
        }
    }

    /////////
    // NBT //
    /////////

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BOTTLE, false);
    }

    public void setBoredDelay(int boredDelay) {
        this.boredDelay = boredDelay;
    }

    public int getBoredDelay() {
        return this.boredDelay;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("FromBottle", this.isFromBottle());
        nbt.putInt("BoredDelay", this.boredDelay);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBottle(nbt.getBoolean("FromBottle"));
        this.boredDelay = nbt.getInt("BoredDelay");
    }

    @Override
    public boolean isFromBottle() {
        return this.dataTracker.get(FROM_BOTTLE);
    }

    @Override
    public void setFromBottle(boolean fromBottle) {
        this.dataTracker.set(FROM_BOTTLE, fromBottle);
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        Bottleable.copyDataToStack(this, stack);
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bottleable.copyDataFromNbt(this, nbt);
    }

    @Override
    public ItemStack getBottleItem() {
        return new ItemStack(ModItems.WISP_BOTTLE);
    }

    @Override
    public SoundEvent getBottleFillSound() {
        return SoundEvents.ITEM_BOTTLE_FILL;
    }

    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return Bottleable.tryBottle(player, hand, this).orElse(super.interactMob(player, hand));
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBottle();
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBottle() && !this.hasCustomName();
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

    static class BurrowInSoulSandGoal extends MoveToTargetPosGoal {
        private final WispEntity wisp;

        public BurrowInSoulSandGoal(WispEntity entity, int range) {
            super(entity, 1.0F, range, range);
            wisp = entity;
        }

        @Override
        public double getDesiredDistanceToTarget() {
            return 2.0;
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            BlockState blockState = world.getBlockState(pos);
            return blockState.isOf(Blocks.SOUL_SAND);
        }

        @Override
        protected BlockPos getTargetPos() {
            return this.targetPos;
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos target = getTargetPos();
            World world = wisp.getWorld();
            if (target != null) {
                if (this.hasReached()) {
                    world.setBlockState(target, ModBlocks.ECTO_SOUL_SAND.getDefaultState().with(EctoSoulSandBlock.SPAWNS_WISPS, true));
                    world.addParticle(ParticleTypes.SOUL, wisp.getX(), wisp.getY(), wisp.getZ(), 0.0, 0.0, 0.0);
                    wisp.remove(RemovalReason.DISCARDED);
                }
            }
        }

        @Override
        public boolean canStart() {
            return wisp.getBoredDelay() <= 0 && !wisp.isFromBottle() && super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            BlockPos target = getTargetPos();
            return wisp.getWorld().getBlockState(target).isOf(Blocks.SOUL_SAND) && super.shouldContinue();
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
