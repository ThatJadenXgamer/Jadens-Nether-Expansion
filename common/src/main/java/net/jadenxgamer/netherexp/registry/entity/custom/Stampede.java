package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.advancements.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Stampede extends Monster implements NeutralMob, ItemSteerable, Saddleable, PlayerRideable, RiderShieldingMount {
    private int changeType = 1;
    private static final Ingredient FOOD_ITEMS = Ingredient.of(JNETags.Items.STAMPEDE_EDIBLE);
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = (item) -> !item.hasPickUpDelay() && item.isAlive() && item.getItem().is(JNETags.Items.STAMPEDE_EDIBLE);

    private static final EntityDataAccessor<Integer> BOOST_TIME = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLE_ID = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TAMED = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_PATRICK = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> EATING_TIME = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AGITATED = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.INT);
    private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);

    public final AnimationState chewAnimationState = new AnimationState();
    public final AnimationState grinAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private int stepSoundCooldown = 0;
    private int idleAnimationTimeout = 0;
    private int angerTime;
    private UUID angryAt;
    private final ItemBasedSteering steering;

    public Stampede(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(3.5f);
        this.blocksBuilding = true;
        this.steering = new ItemBasedSteering(this.entityData, BOOST_TIME, SADDLE_ID);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        this.setCanPickUpLoot(true);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        if (this.getAngry()) {
            grinAnimationState.startIfStopped(this.tickCount);
        } else {
            grinAnimationState.stop();
        }
        if (this.getEating()) {
            chewAnimationState.startIfStopped(this.tickCount);
        } else {
            chewAnimationState.stop();
        }
    }

    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.02F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
               .add(Attributes.MAX_HEALTH, 80.0)
               .add(Attributes.MOVEMENT_SPEED, 0.30)
               .add(Attributes.ATTACK_DAMAGE, 8.0)
               .add(Attributes.ATTACK_KNOCKBACK, 4.0)
               .add(Attributes.KNOCKBACK_RESISTANCE, 3.0)
               .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    public int getMaxFallDistance() {
        return 6;
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.isSaddled()) {
            this.spawnAtLocation(Items.SADDLE);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.2, 32.0f));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new Stampede.PickupItemGoal());
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WitherSkeleton.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Stray.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Vessel.class, true));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    public void aiStep() {
        if (this.stepSoundCooldown > 0) {
            this.stepSoundCooldown--;
        }
        if (this.isInWaterOrRain()) {
            if (getChangeType() == 0) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.ENTITY_APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                this.discard();
            } else this.doExorcism();
        }
        if (this.isAlive()) {
            ItemStack item = this.getItemBySlot(EquipmentSlot.MAINHAND);
            int eating = this.getEatingTime();
            int agitated = this.getAgitated();
            if (this.isVehicle()) {
                if (agitated < 6000) {
                    this.setAgitated(++agitated);
                }
                if (agitated >= 6000 && this.getFirstPassenger() instanceof LivingEntity passenger) {
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.ENTITY_STAMPEDE_AGITATED.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                    this.setTarget(passenger);
                    passenger.stopRiding();
                }
            }
            if (!item.isEmpty()) {
                this.setEatingTime(++eating);
                this.playEatingAnimation();
                this.setEating(true);
                if (eating > 100) {
                    if (item.is(JNETags.Items.STAMPEDE_FAVORITES) && !this.getIsTamed()) {
                        if (!level().isClientSide && random.nextInt(5) == 0) {
                            this.setIsTamed(true);
                            List<ServerPlayer> nearbyPlayers = this.level().getEntitiesOfClass(ServerPlayer.class, new AABB(this.blockPosition()).inflate(6.5, 6.5, 6.5));
                            for (ServerPlayer serverPlayer : nearbyPlayers) {
                                JNECriteriaTriggers.TAME_STAMPEDE.trigger(serverPlayer);
                            }
                        }
                        for(int i = 0; i < 12; ++i) {
                            this.level().addParticle(ParticleTypes.HEART, this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), 0.0, 0.0, 0.0);
                        }
                    }
                    this.setItemSlot(EquipmentSlot.MAINHAND, Items.AIR.getDefaultInstance());
                    if (this.getHealth() < this.getMaxHealth()) {
                        this.heal(item.is(JNETags.Items.STAMPEDE_FAVORITES) ? 10 : 5);
                    }
                    this.setAgitated(agitated - (item.is(JNETags.Items.STAMPEDE_FAVORITES) ? 900 : 600));
                    if (this.getAgitated() < 0) {
                        this.setAgitated(0);
                    }
                    this.setTarget(null);
                    this.setEatingTime(0);
                    this.setEating(false);
                }
            }
            this.setAngry(this.getTarget() != null && !this.getEating());
        }
        if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
            if (this.isVehicle()) {
                damageLivingEntities(this.level().getEntities(this, this.getBoundingBox(), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
            } else if (this.isMoving()) {
                damageLivingEntities(this.level().getEntities(this, this.getBoundingBox(), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
            }
        }

        super.aiStep();
    }

    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.isSaddled() && stack.isEmpty() && player.isShiftKeyDown()) {
            this.steering.setSaddle(false);
            player.level().playSound(null, player.getOnPos(), SoundEvents.STRIDER_SADDLE, SoundSource.PLAYERS, 1.0f, 1.0f);
            player.setItemInHand(hand, Items.SADDLE.getDefaultInstance());
            return InteractionResult.SUCCESS;
        } else if (this.isSaddled() && !this.isVehicle() && !player.isSecondaryUseActive()) {
            if (!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (this.isSaddleable() && !this.isSaddled() && stack.is(Items.SADDLE)) {
            this.equipSaddle(SoundSource.NEUTRAL);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private void playEatingAnimation() {
        if (this.getEatingTime() % 5 == 0) {
            this.playSound(JNESoundEvents.ENTITY_STAMPEDE_EAT.get(), 0.5F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            for (int i = 0; i < 6; ++i) {
                Vec3 vec3 = new Vec3(((double) this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double) this.random.nextFloat() - 0.5) * 0.1);
                vec3 = vec3.xRot(-this.getXRot() * 0.017453292F);
                vec3 = vec3.yRot(-this.getYRot() * 0.017453292F);
                double d = (double) (-this.random.nextFloat()) * 0.6 - 0.3;
                Vec3 vec32 = new Vec3(((double) this.random.nextFloat() - 0.5) * 0.8, d, 1.0 + ((double) this.random.nextFloat() - 0.5) * 0.4);
                vec32 = vec32.yRot(-this.yBodyRot * 0.017453292F);
                vec32 = vec32.add(this.getX(), this.getEyeY() + 1.0, this.getZ());
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, Items.BONE_BLOCK.getDefaultInstance()), vec32.x, vec32.y, vec32.z, vec3.x, vec3.y + 0.05, vec3.z);
            }
        }
    }

    private void damageLivingEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !livingEntity.isPassenger() && !livingEntity.getType().is(JNETags.EntityTypes.STAMPEDE_CANNOT_RUN_OVER)) {
                entity.hurt(this.damageSources().source(JNEDamageSources.STAMPEDE_CRUSH, this), 10);
            }
        }
    }

    public double getPassengersRidingOffset() {
        float f = Math.min(0.25F, this.walkAnimation.speed());
        float g = this.walkAnimation.position();
        return (double)this.getBbHeight() - 0.19 + (double)(0.12F * Mth.cos(g * 1.5F) * 2.0F * f);
    }

    private void doExorcism() {
        Strider strider = this.convertTo(EntityType.STRIDER, false);
        if (strider != null && this.level() instanceof ServerLevel serverLevel) {
            strider.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(strider.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, false), null);
            strider.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            strider.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
            strider.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
            if (this.hasCustomName()) {
                strider.setCustomName(strider.getCustomName());
            }
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.ENTITY_APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
        for(int i = 0; i < 10; i++) {
            this.level().addParticle(JNEParticleTypes.WISP.get(), this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getDirectEntity() instanceof ThrownPotion thrownPotion && hurtWithCleanWater(thrownPotion) && getChangeType() > 0) {
            doExorcism();
            if (thrownPotion.getOwner() instanceof Player player) {
                JNECriteriaTriggers.EXORCISM.trigger((ServerPlayer) player);
            }
        }
        return super.hurt(damageSource, f);
    }

    private boolean hurtWithCleanWater(ThrownPotion thrownPotion) {
        ItemStack itemStack = thrownPotion.getItem();
        Potion potion = PotionUtils.getPotion(itemStack);
        List<MobEffectInstance> list = PotionUtils.getMobEffects(itemStack);
        return potion == Potions.WATER && list.isEmpty();
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        int escapingOdds = this.random.nextInt(this.level().getDifficulty() == Difficulty.HARD ? 1 : 3);
        if (this.level().getDifficulty() != Difficulty.EASY && escapingOdds == 0) {
            Apparition apparition = JNEEntityType.APPARITION.get().create(this.level());
            if (apparition != null) {
                apparition.setPos(this.getX(), this.getY(), this.getZ());
                apparition.setCooldown(1200);
                apparition.setPreference(2);
                if (this.getTarget() != null) {
                    apparition.setTarget(this.getTarget());
                }
            }
            this.level().addFreshEntity(apparition);
        }
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        ItemStack holdingStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return !this.isVehicle() && holdingStack.isEmpty() && stack.is(JNETags.Items.STAMPEDE_EDIBLE);
    }

    @Override
    public LivingEntity getControllingPassenger() {
        Entity passenger = this.getFirstPassenger();
        if (passenger instanceof Player player) {
            if (player.getMainHandItem().is(JNEItems.SKULL_ON_A_STICK.get()) || player.getOffhandItem().is(JNEItems.SKULL_ON_A_STICK.get())) {
                return player;
            }
        }

        return null;
    }

    @Override
    protected void tickRidden(Player player, Vec3 vec3) {
        this.setRot(player.getYRot(), player.getXRot() * 0.5F);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        this.steering.tickBoost();
        super.tickRidden(player, vec3);
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.getIsTamed();
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(Player player, Vec3 vec3) {
        return new Vec3(0.0, 0.0, 1.0);
    }

    @Override
    protected float getRiddenSpeed(Player player) {
        return (float)(0.23 * (double)this.steering.boostFactor());
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        int i = random.nextInt(10);
        if (i == 0) {
            this.dropStridite();
        }
        return super.doHurtTarget(target);
    }

    private void dropStridite() {
        int a = random.nextInt(3) + 1;
        for (int d = 0; d < a; ++d) {
            this.spawnAtLocation(JNEItems.STRIDITE.get());
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ANGRY, false);
        this.getEntityData().define(EATING, false);
        this.getEntityData().define(EATING_TIME, 0);
        this.getEntityData().define(IS_PATRICK, false);
        this.getEntityData().define(BOOST_TIME, 0);
        this.getEntityData().define(SADDLE_ID, false);
        this.getEntityData().define(TAMED, false);
        this.getEntityData().define(AGITATED, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Eating", this.getEating());
        nbt.putBoolean("Tamed", this.getIsTamed());
        nbt.putInt("Agitated", this.getAgitated());
        nbt.putBoolean("Saddled", this.entityData.get(SADDLE_ID));
        nbt.putBoolean("Patrick", this.getIsPatrick());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setEating(nbt.getBoolean("Eating"));
        this.setIsTamed(nbt.getBoolean("Tamed"));
        this.setAgitated(nbt.getInt("Agitated"));
        this.entityData.set(SADDLE_ID, nbt.getBoolean("Saddled"));
        this.setIsPatrick(nbt.getBoolean("Patrick"));
    }

    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        if (!this.getIsPatrick() && name != null && name.getString().equals("Patrick")) {
            this.setIsPatrick(true);
        }
    }

    public int getEatingTime() {
        return this.entityData.get(EATING_TIME);
    }

    private void setEatingTime(int time) {
        this.entityData.set(EATING_TIME, time);
    }

    public boolean getIsTamed() {
        return this.entityData.get(TAMED);
    }

    private void setIsTamed(boolean flag) {
        this.entityData.set(TAMED, flag);
    }

    public int getAgitated() {
        return this.entityData.get(AGITATED);
    }

    private void setAgitated(int flag) {
        this.entityData.set(AGITATED, flag);
    }

    public boolean getAngry() {
        return getEntityData().get(ANGRY);
    }

    private void setAngry(boolean angry) {
        getEntityData().set(ANGRY, angry);
    }

    public boolean getEating() {
        return getEntityData().get(EATING);
    }

    private void setEating(boolean eating) {
        getEntityData().set(EATING, eating);
    }

    public int getChangeType() {
        return this.changeType;
    }

    public void setChangeType(int changeType) {
        this.changeType = changeType;
    }

    public boolean getIsPatrick() {
        return getEntityData().get(IS_PATRICK);
    }

    private void setIsPatrick(boolean patrick) {
        getEntityData().set(IS_PATRICK, patrick);
    }

    @Override
    public double getRiderShieldingHeight() {
        return 1.5;
    }

    ////////
    // AI //
    ////////

    class PickupItemGoal extends Goal {
        public PickupItemGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (Stampede.this.isVehicle()) {
                return false;
            }
            if (!Stampede.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (Stampede.this.getTarget() == null) {
                if (Stampede.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = getItems();
                    return !list.isEmpty() && Stampede.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        @Override
        public void tick() {
            List<ItemEntity> list = getItems();
            ItemStack itemStack = Stampede.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemStack.isEmpty() && !list.isEmpty()) {
                Stampede.this.getNavigation().moveTo(list.get(0), 1.2000000476837158);
            }
        }

        @Override
        public void start() {
            List<ItemEntity> list = getItems();
            if (!list.isEmpty()) {
                Stampede.this.getNavigation().moveTo(list.get(0), 1.2000000476837158);
            }
        }

        private List<ItemEntity> getItems() {
            return Stampede.this.level().getEntitiesOfClass(ItemEntity.class, Stampede.this.getBoundingBox().inflate(8.0, 8.0, 8.0), Stampede.PICKABLE_DROP_FILTER);
        }
    }

    ///////////
    // ANGER //
    ///////////

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.angryAt;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(this.random));
    }

    ////////////
    // SADDLE //
    ////////////

    @Override
    public boolean boost() {
        return this.steering.boost(this.getRandom());
    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && this.getIsTamed();
    }

    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {
        this.steering.setSaddle(true);
        if (soundSource != null) {
            this.level().playSound(null, this, SoundEvents.STRIDER_SADDLE, soundSource, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean isSaddled() {
        return this.steering.hasSaddle();
    }

    ////////////
    // SOUNDS //
    ////////////

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if (this.isVehicle()) {
            if (stepSoundCooldown <= 0) {
                this.playSound(JNESoundEvents.ENTITY_STAMPEDE_STEP.get(), 0.7F, 1.0F);
                stepSoundCooldown = 10;
            }
        } else {
            this.playSound(JNESoundEvents.ENTITY_STAMPEDE_STEP.get(), 0.7F, 1.0F);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JNESoundEvents.ENTITY_STAMPEDE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource arg) {
        return JNESoundEvents.ENTITY_STAMPEDE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return JNESoundEvents.ENTITY_STAMPEDE_DEATH.get();
    }
}
