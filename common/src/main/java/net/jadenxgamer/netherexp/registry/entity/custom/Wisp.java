package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.EctoSoulSandBlock;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Wisp
extends AgeableMob
implements FlyingAnimal, Bottleable {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public int boredDelay = 1000;

    private static final EntityDataAccessor<Boolean> FROM_BOTTLE = SynchedEntityData.defineId(Wisp.class, EntityDataSerializers.BOOLEAN);

    public Wisp(EntityType<? extends AgeableMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            if (this.isMoving()) {
                idleAnimationState.stop();
                walkAnimationState.startIfStopped(this.tickCount);
            }
            else {
                walkAnimationState.stop();
                idleAnimationState.startIfStopped(this.tickCount);
            }
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.01F;
    }

    @Override
    public boolean isFlying() {
        return true;
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
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AgeableMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2.0)
                .add(Attributes.FLYING_SPEED, 1.2)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0, Ingredient.of(Items.SOUL_TORCH), false));
        this.goalSelector.addGoal(3, new wispAvoidEntityGoal<>(this, Piglin.class, 8.0F, 0.4, 0.8));
        this.goalSelector.addGoal(3, new wispAvoidEntityGoal<>(this, PiglinBrute.class, 8.0F, 0.4, 0.8));
        this.goalSelector.addGoal(3, new wispAvoidEntityGoal<>(this, Hoglin.class, 8.0F, 0.4, 0.8));
        this.goalSelector.addGoal(4, new BurrowInSoulSandGoal(this, 16));
        this.goalSelector.addGoal(4, new WispWanderAroundGoal());
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            if (this.getBoredDelay() > 0 && !this.fromBottle()) {
                --this.boredDelay;
            }
        }
        else {
            for(int i = 0; i < 2; ++i) {
                this.level().addParticle(JNEParticleTypes.WISP.get(), this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), 0.0, 0.0, 0.0);
            }
        }
    }

    /////////
    // NBT //
    /////////

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BOTTLE, false);
    }

    public void setBoredDelay(int boredDelay) {
        this.boredDelay = boredDelay;
    }

    public int getBoredDelay() {
        return this.boredDelay;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("FromBottle", this.fromBottle());
        nbt.putInt("BoredDelay", this.boredDelay);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setFromBottle(nbt.getBoolean("FromBottle"));
        this.boredDelay = nbt.getInt("BoredDelay");
    }

    @Override
    public boolean fromBottle() {
        return this.entityData.get(FROM_BOTTLE);
    }

    @Override
    public void setFromBottle(boolean bl) {
        this.entityData.set(FROM_BOTTLE, bl);
    }

    @Override
    public void saveToBottleTag(ItemStack stack) {
        Bottleable.saveDefaultDataToBottleTag(this, stack);
    }

    @Override
    public void loadFromBottleTag(CompoundTag nbt) {
        Bottleable.loadDefaultDataFromBottleTag(this, nbt);
    }

    @Override
    public ItemStack getBottleItemStack() {
        return new ItemStack(JNEItems.WISP_BOTTLE.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BOTTLE_FILL;
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        return Bottleable.bottleMobPickup(player, interactionHand, this).orElse(super.mobInteract(player, interactionHand));
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBottle();
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return !this.fromBottle() && !this.hasCustomName();
    }

    ////////
    // AI //
    ////////

    static class wispAvoidEntityGoal<T extends LivingEntity>
    extends AvoidEntityGoal<T> {

        public wispAvoidEntityGoal(Wisp wisp, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
            super(wisp, fleeFromType, distance, slowSpeed, fastSpeed);
        }
    }

    class WispWanderAroundGoal extends Goal {
        WispWanderAroundGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return Wisp.this.navigation.isDone() && Wisp.this.random.nextInt(10) == 0;
        }

        public boolean canContinueToUse() {
            return Wisp.this.navigation.isInProgress();
        }

        public void start() {
            Vec3 vec3 = this.getRandomLocation();
            if (vec3 != null) {
                Wisp.this.navigation.moveTo(Wisp.this.navigation.createPath(BlockPos.containing(vec3), 1), 1.0);
            }
        }

        @Nullable
        private Vec3 getRandomLocation() {
            Vec3 vec3d2;
            vec3d2 = Wisp.this.getViewVector(0.0F);

            Vec3 vec3d3 = HoverRandomPos.getPos(Wisp.this, 8, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
            return vec3d3 != null ? vec3d3 : AirAndWaterRandomPos.getPos(Wisp.this, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }

    static class BurrowInSoulSandGoal extends MoveToBlockGoal {
        private final Wisp wisp;

        public BurrowInSoulSandGoal(Wisp entity, int range) {
            super(entity, 1.0F, range, range);
            wisp = entity;
        }

        @Override
        public double acceptedDistance() {
            return 2.0;
        }

        @Override
        protected boolean isValidTarget(LevelReader level, BlockPos pos) {
            BlockState blockState = level.getBlockState(pos);
            return blockState.is(Blocks.SOUL_SAND);
        }

        @Override
        protected @NotNull BlockPos getMoveToTarget() {
            return this.blockPos;
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos target = getMoveToTarget();
            Level level = wisp.level();
            if (this.isReachedTarget()) {
                level.setBlock(target, JNEBlocks.ECTO_SOUL_SAND.get().defaultBlockState().setValue(EctoSoulSandBlock.SALTED, false), 2);
                level.addParticle(ParticleTypes.SOUL, wisp.getX(), wisp.getY(), wisp.getZ(), 0.0, 0.0, 0.0);
                wisp.remove(RemovalReason.DISCARDED);
            }
        }

        @Override
        public boolean canUse() {
            return wisp.getBoredDelay() == 0 && !wisp.fromBottle() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            BlockPos target = getMoveToTarget();
            return wisp.level().getBlockState(target).is(Blocks.SOUL_SAND) && super.canContinueToUse();
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

    ////////////
    // SOUNDS //
    ////////////

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return JNESoundEvents.ENTITY_WISP_AMBIENT.get();
    }
}
