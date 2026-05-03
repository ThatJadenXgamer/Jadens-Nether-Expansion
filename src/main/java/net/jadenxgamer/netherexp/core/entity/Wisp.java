package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.entity.interfaces.Bottleable;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.util.EnumSet;

public class Wisp extends ExorcismMob implements FlyingAnimal, Bottleable {

    public final AnimationState idleAnimation = new AnimationState();
    private int idleAnimationTimeout = 0;
    public int boredCounter = 0;
    public boolean salted = false;

    private static final EntityDataAccessor<Boolean> FROM_BOTTLE = SynchedEntityData.defineId(Wisp.class, EntityDataSerializers.BOOLEAN);

    public Wisp(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 1;
        this.setPathfindingMalus(PathType.LAVA, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 2.0)
                .add(Attributes.FLYING_SPEED, 1.2)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0, Ingredient.of(ItemTags.PIGLIN_REPELLENTS), false));
        this.goalSelector.addGoal(3, new WispAvoidEntityGoal<>(this, Piglin.class, 8.0F, 0.4, 0.8));
        this.goalSelector.addGoal(3, new WispAvoidEntityGoal<>(this, PiglinBrute.class, 8.0F, 0.4, 0.8));
        this.goalSelector.addGoal(3, new WispAvoidEntityGoal<>(this, Hoglin.class, 8.0F, 0.4, 0.8));
        this.goalSelector.addGoal(4, new BurrowInSoulSandGoal(this, 16));
        this.goalSelector.addGoal(4, new WispWanderAroundGoal());
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) this.setupAnimationStates();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide()) {
            Client.trailParticle(JNEParticleTypes.WISP.get(), level(), random, this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5));
        } else {
            if (!canGetBored()) return;
            if (this.tickCount % 20 == 0 && random.nextDouble() < JNEConfigs.WISP_BOREDOM_CHANCE.get() && this.getBored() < 6) ++boredCounter;
        }
    }

    @Override
    public boolean canBeLeashed() {
        return true;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!this.isSalted() && stack.is(Items.HONEYCOMB)) {
            if (this.level() instanceof ServerLevel serverLevel) {
                this.setSalted(true);
                stack.shrink(1);
                for(int i = 0; i < 4; ++i) {
                    serverLevel.sendParticles(ParticleTypes.WAX_ON, this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return Bottleable.bottleMobPickup(player, hand, this, () -> Items.GLASS_BOTTLE).orElse(super.mobInteract(player, hand));
        }
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.isSalted();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return canGetBored();
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    private boolean canGetBored() {
        return !this.hasCustomName() && !isSalted() && !isLeashed();
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {

            @Override
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    //////////
    // DATA //
    //////////

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FROM_BOTTLE, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("FromBottle", this.fromBottle());
        nbt.putInt("BoredCounter", this.getBored());
        nbt.putBoolean("Salted", this.isSalted());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setFromBottle(nbt.getBoolean("FromBottle"));
        this.setBored(nbt.getInt("BoredCounter"));
        this.setSalted(nbt.getBoolean("Salted"));
    }

    /////////////
    // BOTTLED //
    /////////////

    @Override
    public boolean fromBottle() {
        return this.entityData.get(FROM_BOTTLE);
    }

    @Override
    public void setFromBottle(boolean fromBottle) {
        this.entityData.set(FROM_BOTTLE, fromBottle);
    }

    @Override
    public void saveToBottleTag(ItemStack stack) {
        Bottleable.saveDefaultDataToBottleTag(this, stack);
    }

    @Override
    public void loadFromBottleTag(CompoundTag tag) {
        Bottleable.loadDefaultDataFromBottleTag(this, tag);
    }

    @Override
    public ItemStack getBottleItemStack() {
        return new ItemStack(JNEItems.WISP_BOTTLE.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return JNESoundEvents.WISP_BOTTLE_FILL.get();
    }

    ////////////////
    // ANIMATIONS //
    ////////////////

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80;
            this.idleAnimation.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(partialTick * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    ///////////////////////
    // GETTERS & SETTERS //
    ///////////////////////

    public int getBored() {
        return this.boredCounter;
    }

    public void setBored(int boredDelay) {
        this.boredCounter = boredDelay;
    }

    public boolean isSalted() {
        return salted;
    }

    public void setSalted(boolean salted) {
        this.salted = salted;
    }

    ////////////
    // SOUNDS //
    ////////////

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return JNESoundEvents.WISP_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JNESoundEvents.WISP_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return JNESoundEvents.WISP_DEATH.get();
    }

    ////////
    // AI //
    ////////

    static class WispAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {

        public WispAvoidEntityGoal(Wisp wisp, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
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
            Vec3 vec3;
            vec3 = Wisp.this.getViewVector(0.0F);

            Vec3 vec32 = HoverRandomPos.getPos(Wisp.this, 8, 7, vec3.x, vec3.z, 1.5707964F, 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(Wisp.this, 8, 4, -2, vec3.x, vec3.z, 1.5707963705062866);
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
                level.setBlock(target, JNEBlocks.ECTO_SOUL_SAND.get().defaultBlockState(), Block.UPDATE_ALL);
                ((ServerLevel) level).sendParticles(ParticleTypes.SOUL, wisp.getRandomX(0.5), wisp.getRandomY() - 0.25, wisp.getRandomZ(0.5), 5, 0.0, 0.0, 0.0, 0.0);
                wisp.remove(RemovalReason.DISCARDED);
            }
        }

        @Override
        public boolean canUse() {
            return wisp.getBored() > 5 && !wisp.isSalted() && super.canUse();
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

    @OnlyIn(Dist.CLIENT)
    public static class Client {
        public static void trailParticle(LodestoneWorldParticleType particle, Level level, RandomSource random, double x, double y, double z) {
            for (int i = 0; i < 2; ++i) {
                WorldParticleBuilder.create(particle)
                        .setFullBrightLighting()
                        .setScaleData(GenericParticleData.create(0.13f).build())
                        .setTransparencyData(GenericParticleData.create(1).build())
                        .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .setLifetime(random.nextInt(40, 60))
                        .enableNoClip()
                        .spawn(level, x, y, z);
            }
        }
    }
}
