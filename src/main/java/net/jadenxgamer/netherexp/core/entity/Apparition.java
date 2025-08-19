package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.elysium_api.api.util.LookupRegistryHelper;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.datadriven.ApparitionAggression;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class Apparition extends ExorcismMob implements FlyingAnimal {
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState attackAnimation = new AnimationState();
    private int idleAnimationTimeout = 0;
    public int possessionCooldown = 0;
    public boolean dropWisps = true;
    public boolean salted = false;
    private static final EntityDataAccessor<Integer> PERSONALITY = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.INT);

    public Apparition(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.LAVA, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.moveControl = new FlyingMoveControl(this, 20, true);
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

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) return;

        if (!this.isSalted() && this.getPossessionCooldown() > 0) --this.possessionCooldown;
        if (this.random.nextInt(30) == 0) playStepSound(this.getOnPos(), this.getBlockStateOn());
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (this.getPersonality() == 0) this.setPersonality(Mth.randomBetweenInclusive(random, 1, 4));
        if (level instanceof ServerLevel) this.initPreferredTargetGoals();
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    private void initPreferredTargetGoals() {
        List<ApparitionAggression> apparitionAggression = this.level().registryAccess().registryOrThrow(JNERegistries.APPARITION_AGGRESSION).stream()
                .filter(json -> json.preferredByPersonalities().contains(this.getPersonality())).toList();
        if (JNEConfigs.DEVELOPER_MODE.get()) NetherExp.LOGGER.info("Found {} apparition aggressions for personality {}", apparitionAggression.size(), this.getPersonality());

        for (ApparitionAggression entry : apparitionAggression) {
            EntityType<?> type = LookupRegistryHelper.getEntityType(entry.targetMob());
            if (type != null) {
                this.targetSelector.addGoal(entry.targetPriority(), new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, entity -> entity.getType() == type));
                if (JNEConfigs.DEVELOPER_MODE.get()) NetherExp.LOGGER.info("Added {} as target for apparition", type);
            }
        }

        if (this.getPersonality() != 2) this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        this.playSound(JNESoundEvents.APPARITION_ATTACK.get(), 1.0f, 1.0f);
        this.level().broadcastEntityEvent(this, (byte) 40);
        return super.doHurtTarget(target);
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 40) attackAnimation.start(this.tickCount);
        super.handleEntityEvent(id);
    }

    @Override
    public boolean canBeLeashed() {
        return JNEConfigs.BRUSH_WISPS_OUT.get();
    }

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

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        if (!canDropWisps() || JNEConfigs.WISPS_DROPPED_BY_APPARITION.get() < 1) return;

        for (int i = 0; i < JNEConfigs.WISPS_DROPPED_BY_APPARITION.get(); i++) {
            Wisp wisp = JNEEntityType.WISP.get().create(this.level());
            if (wisp != null) {
                wisp.setBored(6);
                wisp.setPos(this.getX() + random.nextDouble(), this.getY() + random.nextDouble(), this.getZ() + random.nextDouble());
                this.level().addFreshEntity(wisp);
            }
        }
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity entity) {
        if (this.isSalted()) return super.killedEntity(level, entity);
        Optional<ApparitionAggression> apparitionAggression = level.registryAccess().registryOrThrow(JNERegistries.APPARITION_AGGRESSION).stream()
                .filter(json -> {
                    EntityType<?> type = LookupRegistryHelper.getEntityType(json.targetMob());
                    return json.hasPossession() && entity.getType() == type;
                }).findFirst();

        if (apparitionAggression.isPresent() && entity instanceof Mob mob) {
            EntityType<?> possessionType = LookupRegistryHelper.getEntityType(apparitionAggression.get().possessedMob());
            EntityType<? extends Mob> mobType = (EntityType<? extends Mob>) possessionType;
            var possession = mob.convertTo(mobType, true);
            if (possession != null) {
                possession.finalizeSpawn(level, this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null);
                if (entity.hasCustomName()) possession.setCustomName(entity.getCustomName());
                this.discard();

                return false;
            }
        }

        return super.killedEntity(level, entity);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!this.isSalted() && stack.is(Items.HONEYCOMB)) {
            if (this.level() instanceof ServerLevel serverLevel) {
                this.setSalted(true);
                stack.shrink(1);
                for(int i = 0; i < 8; ++i) {
                    serverLevel.sendParticles(ParticleTypes.WAX_ON, this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    //////////
    // DATA //
    //////////

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PERSONALITY, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Personality", this.getPersonality());
        nbt.putInt("PossessionCooldown", this.getPossessionCooldown());
        nbt.putBoolean("DropsWisps", this.canDropWisps());
        nbt.putBoolean("Salted", this.isSalted());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setPersonality(nbt.getInt("Personality"));
        this.setPossessionCooldown(nbt.getInt("PossessionCooldown"));
        this.setDropWisps(nbt.getBoolean("DropsWisps"));
        this.setSalted(nbt.getBoolean("Salted"));
    }

    ////////////////
    // ANIMATIONS //
    ////////////////

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimation.startIfStopped(this.tickCount);
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

    ////////////
    // SOUNDS //
    ////////////

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return JNESoundEvents.APPARITION_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JNESoundEvents.APPARITION_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return JNESoundEvents.APPARITION_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(JNESoundEvents.APPARITION_FLY.get(), 0.15F, 1.0F);
    }

    ///////////////////////
    // GETTERS & SETTERS //
    ///////////////////////

    public int getPossessionCooldown() {
        return possessionCooldown;
    }

    public void setPossessionCooldown(int possessionCooldown) {
        this.possessionCooldown = possessionCooldown;
    }

    public boolean canDropWisps() {
        return dropWisps;
    }

    public void setDropWisps(boolean dropWisps) {
        this.dropWisps = dropWisps;
    }

    public boolean isSalted() {
        return salted;
    }

    public void setSalted(boolean salted) {
        this.salted = salted;
    }

    public int getPersonality() {
        return this.entityData.get(PERSONALITY);
    }

    public void setPersonality(int personality) {
        this.entityData.set(PERSONALITY, personality);
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
            Vec3 vec3;
            vec3 = Apparition.this.getViewVector(0.0F);

            Vec3 vec32 = HoverRandomPos.getPos(Apparition.this, 8, 7, vec3.x, vec3.z, 1.5707964F, 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(Apparition.this, 8, 4, -2, vec3.x, vec3.z, 1.5707963705062866);
        }
    }
}
