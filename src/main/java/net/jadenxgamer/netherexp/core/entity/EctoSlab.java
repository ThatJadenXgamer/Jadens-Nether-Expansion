package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.elysium_api.api.util.LookupRegistryHelper;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.block.PetrifiedSwirlsBlock;
import net.jadenxgamer.netherexp.core.block.entity.PetrifiedSwirlsBlockEntity;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.DirectionalParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import java.awt.*;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;
import static net.jadenxgamer.netherexp.util.CommonParticles.SMOKE_VARIANTS;

@SuppressWarnings("deprecation")
public class EctoSlab extends PossessedMob {

    private static final ResourceLocation STACK_SPEED_MODIFIER_ID = NetherExp.netherexpPath("stack_speed");
    private static final ResourceLocation STACK_KNOCKBACK_RESISTANCE_MODIFIER_ID = NetherExp.netherexpPath("stack_knockback_resistance");
    private static final ResourceLocation STACK_DAMAGE_MODIFIER_ID = NetherExp.netherexpPath("stack_damage");
    private static final ResourceLocation STACK_JUMP_STRENGTH_MODIFIER_ID = NetherExp.netherexpPath("stack_jump_strength");

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState idleMirroredAnimation = new AnimationState();
    public final AnimationState idleBurrowedAnimation = new AnimationState();
    public final AnimationState idlePetrifiedAnimation = new AnimationState();
    public final AnimationState petrifiedHitAnimation = new AnimationState();
    public final AnimationState burrowAnimation = new AnimationState();
    public final AnimationState emergeAnimation = new AnimationState();
    private int maxStackSize = ECTO_SLAB_MAX_STACK_SIZE.get();
    private double disturbed = 0.0;
    private boolean wasOnGround;
    private int burrowedCooldown = 40;
    private boolean lookingToStack = false;
    private boolean wasForcedOut = false;

    private static final EntityDimensions BURROWED_DIMENSIONS = EntityDimensions.scalable(1.375f, 0.2f);
    private static final EntityDataAccessor<Boolean> IS_BURROWED = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_PETRIFIED = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> STACK_SIZE = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STACK_COOLDOWN = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.INT);

    private int burrowAnimationAnimationTimer = 20;
    private int emergeAnimationAnimationTimer = 15;
    private boolean belowGroundAnimation = false;
    private boolean aboveGroundAnimation = true;
    public boolean showLight = false;
    public float currentShadowRadius = 0.8f;

    public EctoSlab(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level, NetherExp.minecraftPath("magma_cube"));
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
        this.moveControl = new EctoSlabMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 15.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.JUMP_STRENGTH, 0.5)
                .add(Attributes.FOLLOW_RANGE, 28.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EctoSlabPetrifiedGoal(this));
        this.goalSelector.addGoal(1, new EctoSlabFloatGoal(this));
        this.goalSelector.addGoal(2, new EctoSlabStackGoal(this));
        this.goalSelector.addGoal(3, new EctoSlabAttackGoal(this));
        this.goalSelector.addGoal(4, new EctoSlabRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new EctoSlabKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Piglin.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public boolean canBeCollidedWith() {
        return isPetrified();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) this.setupAnimationStates();
        if (isPetrified()) {
            this.setYHeadRot(0.0f);
            this.setYRot(0.0f);
            this.setYBodyRot(0.0f);
            return;
        }
        if (!isBurrowed() && (this.onGround() && !this.wasOnGround)) {
            float f = this.getDimensions(this.getPose()).width() * 2.0F;
            float f1 = f / 2.0F;
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            for (int i = 0; (float)i < f * 6.0F; i++) {
                float f2 = this.random.nextFloat() * (float) (Math.PI * 2);
                float f3 = this.random.nextFloat() * 0.5F + 0.5F;
                float f4 = Mth.sin(f2) * f1 * f3;
                float f5 = Mth.cos(f2) * f1 * f3;
                this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + (double)f4, this.getY(), this.getZ() + (double)f5, 0.0, 0.0, 0.0);
            }
        }
        this.wasOnGround = this.onGround();
    }

    @Override
    public void aiStep() {
        if (!level().isClientSide()) {
            if (getStackCooldown() > 0) setStackCooldown(getStackCooldown() - 1);
            if (isPetrified()) {
                if (disturbed >= PETRIFIED_ECTO_SLAB_SHATTER_DISTURBANCE.get()) splitIntoIndividuals(false);
                if (disturbed > 0 && level().getGameTime() % 40 == 0) disturbed -= 1;
            }
        }
        super.aiStep();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (ECTO_SLAB_PETRIFY_WITH_GHAST_TEAR.get() && !this.isPetrified() && !isBurrowed() && stack.is(Items.GHAST_TEAR)) {
            this.setPetrified(true);
            stack.shrink(1);
            playSound(SoundEvents.HONEYCOMB_WAX_ON, 1.0f, 1.0f);
            if (level().isClientSide()) Client.petrificationParticle(this, level(), random);
            return InteractionResult.SUCCESS;
        } else return super.mobInteract(player, hand);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}

    @Override
    public void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
        CommonHooks.onLivingJump(this);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof ThrownPotion potion && hurtWithCleanWater(potion)) doExorcism();
        if (isPetrified()) {
            if (source.is(DamageTypes.IN_WALL)) return false;
            disturbed += amount;
            this.level().broadcastEntityEvent(this, (byte) 89);
            amount = 0;
        }
        if (isBurrowed()) {
            if (ECTO_SLAB_FORCE_OUT_WITH_SHOVEL.get() && isShovelHit(source)) {
                playSound(SoundEvents.SHOVEL_FLATTEN, 1.0f, 1.0f);
                this.wasForcedOut = true;
            }
            if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return false;
        }
        if (EXPLOSION_SHATTERS_ECTO_SLAB_STACK.get() && source.is(DamageTypeTags.IS_EXPLOSION) && getStackSize() > 1) {
            splitIntoIndividuals(true);
            return false;
        }
        if (source.getEntity() instanceof Player player) {
            if (player.getMainHandItem().is(JNETags.Items.SILVER_WEAPONS)) {
                amount *= SILVER_PARANORMAL_DAMAGE_MULTIPLIER.get();
                if (SILVER_PARANORMAL_INFLICTS_SLOWNESS.get()) this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
                this.level().broadcastEntityEvent(this, (byte) 91);
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        if (getStackSize() > 1) {
            setStackSize(getStackSize() - 1);
            setHealth(getMaxHealth());
            this.level().playSound(null, this.blockPosition(), this.getDeathSound(), getSoundSource(), 1.0f, 1.0f);
            this.level().broadcastEntityEvent(this, (byte) 86);
            return;
        }
        super.die(source);
    }

    @Override
    public boolean isPushable() {
        return !isBurrowed() && !isPetrified() && super.isPushable();
    }

    @Override
    protected void pushEntities() {
        if (!isBurrowed() && !isPetrified()) super.pushEntities();
    }

    @Override
    public void push(Entity entity) {
        if (isBurrowed() || isPetrified()) return;
        super.push(entity);
        if (entity instanceof Piglin || entity instanceof IronGolem) this.dealDamage((LivingEntity) entity);
    }

    @Override
    public void playerTouch(Player entity) {
        if (!isBurrowed()) this.dealDamage(entity);
    }

    protected void dealDamage(LivingEntity victim) {
        if (isPetrified()) return;
        if (this.isAlive() && this.isWithinMeleeAttackRange(victim) && this.hasLineOfSight(victim)) {
            DamageSource source = this.damageSources().mobAttack(this);
            if (victim.hurt(source, this.getAttackDamage())) {
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                if (this.level() instanceof ServerLevel serverlevel) EnchantmentHelper.doPostAttackEffects(serverlevel, victim, source);
            }
        }
    }

    private void doEmergenceBurst(boolean wasForcedOut) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        AABB area = this.getBoundingBox().inflate(1.2);
        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, area, victim -> victim != this && victim.isAlive());
        float damage = this.getAttackDamage() * EMERGE_BURST_DAMAGE_MULTIPLIER.get().floatValue();
        DamageSource source = this.damageSources().mobAttack(this);

        for (LivingEntity victim : targets) {
            if (victim instanceof EctoSlab) continue;
            if (!wasForcedOut && !FORCED_OUT_ECTO_SLAB_FRIENDLY_FIRE.get() && !victim.getType().is(JNETags.EntityTypes.AFFECTED_BY_ECTO_SLAB_EMERGE_BURST)) continue;
            if (victim.hurt(source, damage)) {
                this.playSound(JNESoundEvents.ECTOPLASM_FREEZE.get(), 1.0F, 1.0F);
                EnchantmentHelper.doPostAttackEffects(serverLevel, victim, source);
            }
        }
        if (ECTO_SLAB_EMERGE_BURST_SCREENSHAKE.get()) ScreenshakeHandler.addScreenshake(
                    new ScreenshakeInstance(10, 1 + ((float) this.getStackSize() / 2), 0, 0,
                            Easing.LINEAR, Easing.LINEAR, 1.0f, Optional.of(new ScreenshakeInstance.ScreenshakePositionData(
                            this.position(),6.0f, Easing.LINEAR))));
        this.wasForcedOut = false;
    }

    private boolean isShovelHit(DamageSource source) {
        return source.getEntity() instanceof LivingEntity attacker &&
                (attacker.getMainHandItem().canPerformAction(ItemAbilities.SHOVEL_DIG) ||
                        attacker.getMainHandItem().is(ItemTags.SHOVELS));
    }

    private void splitIntoIndividuals(boolean explosion) {
        if (!(level() instanceof ServerLevel)) return;
        int currentStack = getStackSize();
        for (int i = 0; i < currentStack; i++) {
            EctoSlab piece = new EctoSlab(JNEEntityType.ECTO_SLAB.get(), this.level());
            piece.setPos(this.getX(), this.getY() + i, this.getZ());
            piece.setStackSize(1);
            piece.setStackCooldown(explosion ? EXPLOSION_SHATTER_COOLDOWN.get() : 20);
            piece.setMaxStackSize(this.getMaxStackSize());
            piece.setHealth(piece.getMaxHealth());
            piece.setNoAi(this.isNoAi());
            piece.setCustomName(this.getCustomName());
            if (this.isPersistenceRequired()) piece.setPersistenceRequired();

            RandomSource random = this.random;
            double vx, vy, vz;
            if (explosion) {
                double angle = random.nextDouble() * 2 * Math.PI;
                double speed = 0.3 + random.nextDouble() * 0.5;
                vx = Math.cos(angle) * speed;
                vz = Math.sin(angle) * speed;
                vy = 0.2 + random.nextDouble() * 0.4;
            } else {
                double angle = random.nextDouble() * 2 * Math.PI;
                double speed = 0.05 + random.nextDouble() * 0.01;
                vx = Math.cos(angle) * speed;
                vz = Math.sin(angle) * speed;
                vy = 0.05 + random.nextDouble() * 0.05;
            }
            piece.setDeltaMovement(vx, vy, vz);
            piece.hasImpulse = true;

            this.level().addFreshEntity(piece);
        }
        this.level().broadcastEntityEvent(this, (byte) 88);
        this.playSound(JNESoundEvents.ECTO_SLAB_COLLAPSE.get(), 1.0f, 1.0f);
        this.discard();
    }

    private void petrifyNearbySwirls(int radius) {
        if (!PETRIFICATION_PETRIFIES_SWIRLS.get()) return;
        if (!(level() instanceof ServerLevel serverLevel)) return;
        BlockPos center = this.blockPosition();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        this.level().broadcastEntityEvent(this, (byte) 120);
        this.playSound(JNESoundEvents.ECTO_SLAB_PETRIFY_SWIRLS.get(), 2.0f, 1.0f);
        if (ECTO_SLAB_PETRIFICATION_SCREENSHAKE.get()) ScreenshakeHandler.addScreenshake(
                    new ScreenshakeInstance(20, 1, 0, 0,
                            Easing.LINEAR, Easing.LINEAR, 1.0f, Optional.of(new ScreenshakeInstance.ScreenshakePositionData(
                            this.position(), (float) getPetrificationRadius() / 2, Easing.LINEAR))));

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    mutablePos.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                    BlockState oldState = serverLevel.getBlockState(mutablePos);
                    if (!oldState.is(JNETags.Blocks.SOUL_SWIRLS)) continue;
                    BlockState newState = JNEBlocks.PETRIFIED_SWIRLS.get().defaultBlockState().setValue(PetrifiedSwirlsBlock.SALTED, false);

                    if (newState.hasProperty(BlockStateProperties.FACING) && oldState.hasProperty(BlockStateProperties.FACING))
                        newState = newState.setValue(BlockStateProperties.FACING, oldState.getValue(BlockStateProperties.FACING));
                    if (newState.hasProperty(BlockStateProperties.WATERLOGGED) && oldState.hasProperty(BlockStateProperties.WATERLOGGED))
                        newState = newState.setValue(BlockStateProperties.WATERLOGGED, oldState.getValue(BlockStateProperties.WATERLOGGED));

                    serverLevel.setBlock(mutablePos, newState, Block.UPDATE_ALL);
                    level().playSound(null, mutablePos, JNESoundEvents.SOUL_SWIRLS_PETRIFY.get(), SoundSource.BLOCKS, 0.2f, 1.0f);

                    if (serverLevel.getBlockEntity(mutablePos) instanceof PetrifiedSwirlsBlockEntity blockEntity) {
                        blockEntity.setPetrifier(this);
                        blockEntity.setUnpetrifiedBlock(BuiltInRegistries.BLOCK.getKey(oldState.getBlock()));
                    }
                    final BlockPos pos = mutablePos.immutable();
                    serverLevel.getServer().execute(() -> {
                        if (serverLevel.getBlockState(pos).is(JNEBlocks.PETRIFIED_SWIRLS.get())) {
                            serverLevel.blockEvent(pos, JNEBlocks.PETRIFIED_SWIRLS.get(), 1, 0);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void doExorcism() {
        if (this.isDeadOrDying()) return;
        BlockPos pos = this.blockPosition();
        this.level().playSound(null, pos, JNESoundEvents.APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
        this.level().broadcastEntityEvent(this, (byte) 92);

        EntityType<?> possessionOf = getPossessionOf() == null ? null : LookupRegistryHelper.getEntityType(ResourceLocation.parse(getPossessionOf()));
        if (possessionOf == null) {
            this.discard();
            return;
        }
        EntityType<? extends Mob> possessionType = (EntityType<? extends Mob>) possessionOf;
        Mob convertTo = this.convertTo(possessionType, true);
        boolean slimeType = convertTo instanceof Slime;
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        if (slimeType) {
            int stack = getStackSize();
            for (int i = 0; i < stack / 3; i++) spawnSlimeOfSize(serverLevel, possessionType, 4);
            for (int i = 0; i < stack % 3; i++) spawnSlimeOfSize(serverLevel, possessionType, 2);
            this.discard();
        } else {
            if (convertTo != null) {
                convertTo.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(pos), MobSpawnType.CONVERSION, null);
                convertTo.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                if (this.hasCustomName()) convertTo.setCustomName(convertTo.getCustomName());
            }
        }
    }

    private void spawnSlimeOfSize(ServerLevel level, EntityType<?> slimeType, int size) {
        if (!(slimeType.create(level) instanceof Slime slime)) return;
        slime.setPos(this.getX(), this.getY(), this.getZ());
        slime.finalizeSpawn(level, level.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null);
        slime.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        if (this.hasCustomName()) slime.setCustomName(slime.getCustomName());
        slime.setSize(size, true);

        level.addFreshEntity(slime);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (spawnType == MobSpawnType.NATURAL) {
            RandomSource random = level.getRandom();
            if (random.nextFloat() < NATURAL_PETRIFIED_ECTO_SLAB_CHANCE.get().floatValue()) {
                this.setPetrified(true);
                int stackSize = NATURAL_PETRIFIED_ECTO_SLAB_MIN_STACK.get() + random.nextInt(NATURAL_PETRIFIED_ECTO_SLAB_MAX_STACK.get() - NATURAL_PETRIFIED_ECTO_SLAB_MIN_STACK.get() + 1);
                this.setStackSize(stackSize);
            } else {
                int packSize = NATURAL_ECTO_SLAB_PACK_MIN.get() + random.nextInt(NATURAL_ECTO_SLAB_PACK_MAX.get() - NATURAL_ECTO_SLAB_PACK_MIN.get() + 1);
                this.setStackSize(1);
                for (int i = 1; i < packSize; i++) {
                    EctoSlab additional = new EctoSlab(JNEEntityType.ECTO_SLAB.get(), this.level());
                    double angle = random.nextDouble() * 2 * Math.PI;
                    double distance = 0.8 + random.nextDouble() * 1.2;
                    BlockPos pos = this.blockPosition();
                    additional.setPos(pos.getX() + 0.5 + Math.cos(angle) * distance, this.getY(), pos.getZ() + 0.5 + Math.sin(angle) * distance);
                    additional.setMaxStackSize(this.getMaxStackSize());
                    additional.finalizeSpawn(level, difficulty, MobSpawnType.REINFORCEMENT, spawnGroupData);
                    this.level().addFreshEntity(additional);
                }
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    public static boolean checkSpawnRules(EntityType<? extends ExorcismMob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (!ExorcismMob.checkSpawnRules(type, level, spawnType, pos, random)) return false;
        if (spawnType == MobSpawnType.NATURAL) {
            AABB area = new AABB(pos).inflate(48.0);
            List<EctoSlab> petrifiedNearby = level.getEntitiesOfClass(EctoSlab.class, area, aPotentialLesbian -> aPotentialLesbian.isPetrified() && aPotentialLesbian.isAlive());
            if (!petrifiedNearby.isEmpty()) return false;
            int nearbyCount = level.getEntitiesOfClass(EctoSlab.class, area, LivingEntity::isAlive).size();
            return nearbyCount < 6;
        }
        return true;
    }
    //////////
    // DATA //
    //////////

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (IS_BURROWED.equals(key)) this.refreshDimensions();
        if (STACK_SIZE.equals(key)) this.refreshDimensions();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_BURROWED, false);
        builder.define(IS_PETRIFIED, false);
        builder.define(STACK_SIZE, 1);
        builder.define(STACK_COOLDOWN, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("IsBurrowed", this.isBurrowed());
        nbt.putBoolean("IsPetrified", this.isPetrified());
        nbt.putInt("StackSize", this.getStackSize());
        nbt.putInt("MaxStackSize", this.getMaxStackSize());
        nbt.putInt("StackCooldown", this.getStackCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setBurrowed(nbt.getBoolean("IsBurrowed"));
        this.setPetrified(nbt.getBoolean("IsPetrified"));
        this.setStackSize(nbt.getInt("StackSize"));
        this.setMaxStackSize(nbt.getInt("MaxStackSize"));
        this.setStackCooldown(nbt.getInt("StackCooldown"));
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        if (isBurrowed()) return BURROWED_DIMENSIONS;
        float height = getStackSize() * 0.75f;
        return EntityDimensions.scalable(1.375f, Math.max(0.2f, height));
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public int apparitionPersonality() {
        return 3;
    }

    @Override
    protected double apparitionUnleashingOdds() {
        return ECTO_SLAB_UNLEASHING_ODDS.get();
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 81 -> {
                belowGroundAnimation = true;
                burrowAnimationAnimationTimer = 20;
            }
            case 82 -> {
                belowGroundAnimation = false;
                burrowAnimationAnimationTimer = 20;
                idleBurrowedAnimation.stop();
                burrowAnimation.stop();
            }
            case 83 -> {
                aboveGroundAnimation = true;
                emergeAnimationAnimationTimer = 15;
            }
            case 84 -> {
                aboveGroundAnimation = false;
                emergeAnimationAnimationTimer = 15;
                emergeAnimation.stop();
                idleAnimation.stop();
            }
            case 85 -> Client.warnParticle(this, this.level(), this.position());
            case 86 -> {
                int stack = getStackSize();
                double yOffset = stack * 0.75;
                for (int i = 0; i < 20; i++) {
                    double dx = random.nextGaussian() * 0.3;
                    double dz = random.nextGaussian() * 0.3;
                    double dy = random.nextDouble() * 0.5;
                    level().addParticle(ParticleTypes.POOF,
                            getX() + dx, getY() + yOffset + dy, getZ() + dz,
                            0, 0, 0);
                }
            }
            case 87 -> Client.forcedEmergeParticle(this, this.level(), this.position());
            case 88 -> Client.shardParticle(this, this.level(), random);
            case 89 -> petrifiedHitAnimation.start(tickCount);
            case 120 -> Client.petrifySwirlsBurstParticle(this, this.level(), this.position());
        }
        super.handleEntityEvent(id);
    }

    ///////////////////////
    // GETTERS & SETTERS //
    ///////////////////////

    public boolean isBurrowed() {
        return this.entityData.get(IS_BURROWED);
    }

    public void setBurrowed(boolean burrowed) {
        if (burrowed == isBurrowed()) return;
        this.entityData.set(IS_BURROWED, burrowed);
        this.refreshDimensions();
        if (burrowed) {
            this.playSound(JNESoundEvents.ECTO_SLAB_BURROW.get(), 1.0f, 1.0f);
            this.level().broadcastEntityEvent(this, (byte) 81);
            this.level().broadcastEntityEvent(this, (byte) 84);
        } else {
            this.level().broadcastEntityEvent(this, (byte) 83);
            this.level().broadcastEntityEvent(this, (byte) 82);
        }
        AttributeInstance stepHeight = this.getAttribute(Attributes.STEP_HEIGHT);
        if (stepHeight != null) stepHeight.setBaseValue(burrowed ? 16.0 : 0.0);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        if (isPetrified()) return false;
        return super.removeWhenFarAway(distanceToClosestPlayer);
    }

    @Override
    public boolean isPersistenceRequired() {
        if (isPetrified()) return true;
        return super.isPersistenceRequired();
    }

    public static int absoluteMaximumStackSize() {
        return ABSOLUTE_MAXIMUM_STACK_SIZE.get();
    }

    private int getPetrificationRadius() {
        int baseRadius = PETRIFICATION_SWIRLS_RANGE.get();
        int incrementPerStack = PETRIFICATION_SWIRLS_RANGE_PER_STACK.get();
        return baseRadius + (getStackSize() - 1) * incrementPerStack;
    }

    public int getStackSize() {
        return this.entityData.get(STACK_SIZE);
    }

    public void setStackSize(int stack) {
        stack = Mth.clamp(stack, 1, isPetrified() ? absoluteMaximumStackSize() : this.maxStackSize);
        this.entityData.set(STACK_SIZE, stack);
        this.updateAttributesForStack(stack);
        this.refreshDimensions();
        if (!level().isClientSide && isPetrified()) petrifyNearbySwirls(getPetrificationRadius());
    }

    public boolean isPetrified() {
        return this.entityData.get(IS_PETRIFIED);
    }

    public void setPetrified(boolean petrified) {
        if (petrified && !isPetrified() && !level().isClientSide) petrifyNearbySwirls(getPetrificationRadius());
        this.entityData.set(IS_PETRIFIED, petrified);
    }

    public int getStackCooldown() {
        return entityData.get(STACK_COOLDOWN);
    }

    public void setStackCooldown(int stackCooldown) {
        entityData.set(STACK_COOLDOWN, stackCooldown);
    }

    protected int getJumpDelay() {
        int stack = getStackSize();
        int baseDelay = switch (stack) {
            case 1 -> 30;
            case 2 -> 20;
            case 3 -> 15;
            default -> 10;
        };
        return baseDelay + ECTO_SLAB_EXTRA_JUMP_DELAY.get();
    }

    private int getBurrowCooldown() {
        int stack = getStackSize();
        int baseCooldown = switch (stack) {
            case 1 -> this.random.nextInt(60, 81);
            case 2 -> 60;
            default -> 50;
        };
        return baseCooldown + ECTO_SLAB_EXTRA_BURROW_COOLDOWN.get();
    }

    private void updateAttributesForStack(int newStackSize) {
        AttributeInstance speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance damage = this.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance knockbackResistance = this.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        AttributeInstance jumpStrength = this.getAttribute(Attributes.JUMP_STRENGTH);

        if (speed != null) {
            speed.removeModifier(STACK_SPEED_MODIFIER_ID);
            double speedBonus = (newStackSize - 1) * ECTO_SLAB_SPEED_PER_STACK.get();
            double speedCap = ECTO_SLAB_SPEED_CAP.get();
            if (speedCap >= 0) speedBonus = Math.min(speedBonus, speedCap);
            if (speedBonus != 0.0) {
                AttributeModifier speedMod = new AttributeModifier(STACK_SPEED_MODIFIER_ID, speedBonus, AttributeModifier.Operation.ADD_VALUE);
                speed.addTransientModifier(speedMod);
            }
        }

        if (damage != null) {
            damage.removeModifier(STACK_DAMAGE_MODIFIER_ID);
            double damageBonus = newStackSize * ECTO_SLAB_DAMAGE_PER_STACK.get();
            double damageCap = ECTO_SLAB_DAMAGE_CAP.get();
            if (damageCap >= 0) damageBonus = Math.min(damageBonus, damageCap);
            AttributeModifier damageMod = new AttributeModifier(STACK_DAMAGE_MODIFIER_ID, damageBonus, AttributeModifier.Operation.ADD_VALUE);
            damage.addTransientModifier(damageMod);
        }

        if (knockbackResistance != null) {
            knockbackResistance.removeModifier(STACK_KNOCKBACK_RESISTANCE_MODIFIER_ID);
            double kbBonus = (newStackSize - 1) * ECTO_SLAB_KNOCKBACK_RESISTANCE_PER_STACK.get();
            double kbCap = ECTO_SLAB_KNOCKBACK_RESISTANCE_CAP.get();
            if (kbCap >= 0) kbBonus = Math.min(kbBonus, kbCap);
            if (kbBonus != 0.0) {
                AttributeModifier kbMod = new AttributeModifier(STACK_KNOCKBACK_RESISTANCE_MODIFIER_ID, kbBonus, AttributeModifier.Operation.ADD_VALUE);
                knockbackResistance.addTransientModifier(kbMod);
            }
        }

        if (jumpStrength != null) {
            jumpStrength.removeModifier(STACK_JUMP_STRENGTH_MODIFIER_ID);
            double jumpBonus = (newStackSize - 1) * ECTO_SLAB_JUMP_STRENGTH_PER_STACK.get();
            double jumpCap = ECTO_SLAB_JUMP_STRENGTH_CAP.get();
            if (jumpCap >= 0) jumpBonus = Math.min(jumpBonus, jumpCap);
            if (jumpBonus != 0.0) {
                AttributeModifier jumpMod = new AttributeModifier(STACK_JUMP_STRENGTH_MODIFIER_ID, jumpBonus, AttributeModifier.Operation.ADD_VALUE);
                jumpStrength.addTransientModifier(jumpMod);
            }
        }
    }

    public int getMaxStackSize() {
        return this.maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        maxStackSize = Mth.clamp(maxStackSize, 1, absoluteMaximumStackSize());
        this.maxStackSize = maxStackSize;
    }

    ////////////////
    // ANIMATIONS //
    ////////////////

    private void setupAnimationStates() {
        if (isPetrified()) {
            if (tickCount % 20 == 0) Client.petrificationParticle(this, level(), random);
            idlePetrifiedAnimation.startIfStopped(tickCount);
            idleAnimation.stop();
            idleBurrowedAnimation.stop();
            burrowAnimation.stop();
            emergeAnimation.stop();
            return;
        } else idlePetrifiedAnimation.stop();

        if (belowGroundAnimation) {
            if (burrowAnimationAnimationTimer == 20) {
                burrowAnimation.startIfStopped(this.tickCount);
                showLight = true;
            }
            if (burrowAnimationAnimationTimer < 18 && burrowAnimationAnimationTimer > 0) {
                Client.burrowDustParticle(this, level(), random);
                for (int i = 0; i < 4; i++)
                    level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, getBlockStateOn()),
                            this.getRandomX(0.7), this.getY(), this.getRandomZ(0.7), 0,0.6, 0);
            }
            else if (burrowAnimationAnimationTimer == 0) {
                burrowAnimation.stop();
                idleBurrowedAnimation.startIfStopped(this.tickCount);
            }
            if (burrowAnimationAnimationTimer > 0) --burrowAnimationAnimationTimer;
        }
        if (aboveGroundAnimation) {
            if (emergeAnimationAnimationTimer == 15) {
                emergeAnimation.startIfStopped(this.tickCount);
                for (int i = 0; i < 32; i++)
                    level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, getBlockStateOn()),
                            this.getRandomX(0.7), this.getY(), this.getRandomZ(0.7), 0,0.6, 0);
            }
            else if (emergeAnimationAnimationTimer == 0) {
                emergeAnimation.stop();
                idleAnimation.startIfStopped(this.tickCount);
                showLight = false;
            }
            if (emergeAnimationAnimationTimer > 0) --emergeAnimationAnimationTimer;
        }

        if (isBurrowed()) Client.burrowedParticle(this, this.level(), this.random);
    }

    ////////////
    // SOUNDS //
    ////////////

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.MAGMA_CUBE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.MAGMA_CUBE_DEATH;
    }

    protected @NotNull SoundEvent getSquishSound() {
        return JNESoundEvents.ECTO_SLAB_SQUISH.get();
    }

    protected @NotNull SoundEvent getJumpSound() {
        return JNESoundEvents.ECTO_SLAB_JUMP.get();
    }

    protected @NotNull SoundEvent getWarnSound() {
        return JNESoundEvents.ECTO_SLAB_WARN.get();
    }

    ////////
    // AI //
    ////////

    static class EctoSlabMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final EctoSlab ectoSlab;
        private boolean isAggressive;
        @Nullable
        private Entity movingTarget;
        private double seekSpeed = 0.0;

        public EctoSlabMoveControl(EctoSlab ectoSlab) {
            super(ectoSlab);
            this.ectoSlab = ectoSlab;
            this.yRot = 180.0F * ectoSlab.getYRot() / (float) Math.PI;
        }

        public void setDirection(float yRot, boolean aggressive) {
            this.yRot = yRot;
            this.isAggressive = aggressive;
        }

        public void setWantedMovement(double speed) {
            this.speedModifier = speed;
            this.operation = Operation.MOVE_TO;
        }

        public void setSeekTarget(@Nullable Entity target, double speed) {
            this.movingTarget = target;
            this.seekSpeed = speed;
        }

        @Override
        public void tick() {
            if (ectoSlab.isBurrowed()) {
                burrowBehavior();
            } else {
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
                this.mob.yHeadRot = this.mob.getYRot();
                this.mob.yBodyRot = this.mob.getYRot();
                slimeBehavior();
            }
            if (!ectoSlab.isBurrowed() && movingTarget != null) {
                movingTarget = null;
            }
        }

        private void slimeBehavior() {
            if (this.operation != Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.ectoSlab.getJumpDelay();
                        if (this.isAggressive) this.jumpDelay /= 2;
                        this.ectoSlab.getJumpControl().jump();
                        this.ectoSlab.playSound(this.ectoSlab.getJumpSound(), this.ectoSlab.getSoundVolume(), 1.0f);
                    } else {
                        this.ectoSlab.xxa = 0.0F;
                        this.ectoSlab.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
        }

        private void burrowBehavior() {
            if (movingTarget != null && movingTarget.isAlive()) {
                Vec3 toTarget = movingTarget.position().subtract(mob.position());
                double distance = toTarget.length();
                if (distance < 0.5) {
                    mob.setPos(movingTarget.getX(), mob.getY(), movingTarget.getZ());
                    mob.setDeltaMovement(Vec3.ZERO);
                    mob.setSpeed(0.0f);
                    return;
                }
                double maxSpeed = seekSpeed * mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.5;
                Vec3 desiredVelocity = toTarget.normalize().scale(maxSpeed);
                mob.setDeltaMovement(desiredVelocity.x, mob.getDeltaMovement().y, desiredVelocity.z);
                float yaw = (float)(Mth.atan2(desiredVelocity.z, desiredVelocity.x) * (180F / Math.PI)) - 90F;
                mob.setYRot(rotlerp(mob.getYRot(), yaw, 90.0F));
                mob.setSpeed((float)seekSpeed);
            } else {
                mob.setDeltaMovement(0, mob.getDeltaMovement().y, 0);
                mob.setSpeed(0.0f);
                mob.setZza(0.0F);
                this.operation = Operation.WAIT;
            }
        }
    }

    static class EctoSlabStackGoal extends Goal {
        private static final double SEARCH_RADIUS = 24.0;
        private static final double STACK_MERGE_DISTANCE = 2.0;

        private int leapCooldown = 10;
        private final EctoSlab ectoSlab;
        @Nullable
        private EctoSlab mergeTarget;

        public EctoSlabStackGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (ectoSlab.getStackCooldown() > 0) return false;
            if (ectoSlab.getTarget() == null) return false;
            if (ectoSlab.getStackSize() > 1) return false;
            mergeTarget = findBestMergeTarget();
            return ectoSlab.getMoveControl() instanceof EctoSlabMoveControl && mergeTarget != null;
        }

        @Override
        public boolean canContinueToUse() {
            if (ectoSlab.getStackCooldown() > 0) return false;
            if (ectoSlab.getTarget() == null) return false;
            if (ectoSlab.getStackSize() > 1) return false;
            if (mergeTarget == null || !mergeTarget.isAlive() || mergeTarget.isDeadOrDying()) return false;
            if (mergeTarget.isPetrified() && mergeTarget.getStackSize() >= absoluteMaximumStackSize()) return false;
            return ectoSlab.distanceToSqr(mergeTarget) > STACK_MERGE_DISTANCE * STACK_MERGE_DISTANCE;
        }

        @Override
        public void start() {
            ectoSlab.lookingToStack = true;
            leapCooldown = 10;
        }

        @Override
        public void stop() {
            mergeTarget = null;
            ectoSlab.lookingToStack = false;
            leapCooldown = 10;
        }

        @Override
        public void tick() {
            if (mergeTarget == null) return;
            if (ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) {
                double dx = mergeTarget.getX() - ectoSlab.getX();
                double dz = mergeTarget.getZ() - ectoSlab.getZ();
                float yaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
                control.setDirection(yaw, true);
                control.setWantedPosition(mergeTarget.getX(), mergeTarget.getY(), mergeTarget.getZ(), ectoSlab.getAttributeValue(Attributes.MOVEMENT_SPEED));

                if (leapCooldown <= 0) {
                    if (!ectoSlab.onGround()) return;
                    ectoSlab.getJumpControl().jump();
                    Vec3 leap = new Vec3(ectoSlab.getLookAngle().x, ectoSlab.getLookAngle().y, ectoSlab.getLookAngle().z).normalize();
                    ectoSlab.playSound(JNESoundEvents.ECTO_SLAB_LEAP.get(), 1.0f, 1.0f);
                    ectoSlab.push(leap.scale(0.55));
                    leapCooldown = 20;
                } else --leapCooldown;
            }

            if (ectoSlab.distanceToSqr(mergeTarget) <= STACK_MERGE_DISTANCE * STACK_MERGE_DISTANCE) {
                mergeTarget.setStackSize(mergeTarget.getStackSize() + 1);
                ectoSlab.playSound(JNESoundEvents.ECTO_SLAB_STACK.get(), 2.0f, 1.0f);
                ectoSlab.discard();
            }
        }

        @Nullable
        private EctoSlab findBestMergeTarget() {
            Level level = ectoSlab.level();
            AABB searchBox = AABB.ofSize(ectoSlab.position(), SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS);
            List<EctoSlab> candidates = level.getEntitiesOfClass(EctoSlab.class, searchBox,
                    candidate -> candidate != ectoSlab &&
                            !candidate.isBurrowed() &&
                            !candidate.isDeadOrDying() &&
                            (candidate.isPetrified()
                                    ? candidate.getStackSize() < absoluteMaximumStackSize()
                                    : candidate.getStackSize() < candidate.getMaxStackSize()));

            if (candidates.isEmpty()) return null;
            candidates.sort(Comparator
                    .<EctoSlab>comparingInt(c -> c.isPetrified() ? 0 : 1)
                    .thenComparingInt(EctoSlab::getStackSize)
                    .thenComparingDouble(e -> e.distanceToSqr(ectoSlab)));

            return candidates.getFirst();
        }
    }

    static class EctoSlabAttackGoal extends Goal {
        private final EctoSlab ectoSlab;
        private int burrowedTime = 0;
        private int burrowAnimationDelay = 0;

        public EctoSlabAttackGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (ectoSlab.lookingToStack) return false;
            LivingEntity target = this.ectoSlab.getTarget();
            if (target == null) return false;
            else return this.ectoSlab.canAttack(target) && this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl;
        }

        @Override
        public void start() {
            super.start();
        }

        @Override
        public void stop() {
            super.stop();
            burrowedTime = 0;
            burrowAnimationDelay = 0;
            ectoSlab.setBurrowed(false);
            ectoSlab.burrowedCooldown = 40;
            if (ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) {
                control.setSeekTarget(null, 0.0);
            }
        }

        @Override
        public boolean canContinueToUse() {
            if (ectoSlab.lookingToStack) return false;
            LivingEntity target = this.ectoSlab.getTarget();
            if (target == null) return false;
            else return this.ectoSlab.canAttack(target);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.ectoSlab.getTarget();
            if (target == null) return;

            if (!ectoSlab.isBurrowed()) {
                this.ectoSlab.lookAt(target, 10.0F, 10.0F);

                if (ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) {
                    double dx = target.getX() - ectoSlab.getX();
                    double dz = target.getZ() - ectoSlab.getZ();
                    float yaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
                    control.setDirection(yaw, true);
                    control.setWantedMovement(ectoSlab.getAttributeValue(Attributes.MOVEMENT_SPEED));
                }

                if (ectoSlab.onGround() && ectoSlab.burrowedCooldown-- <= 0) {
                    ectoSlab.setBurrowed(true);
                    burrowedTime = ECTO_SLAB_MAX_DIG_TIME.get();
                    burrowAnimationDelay = 20;
                }
            } else {
                if (burrowAnimationDelay > 0) {
                    burrowAnimationDelay--;
                    if (ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) {
                        control.setWantedPosition(ectoSlab.getX(), ectoSlab.getY(), ectoSlab.getZ(), 0.0);
                        ectoSlab.setDeltaMovement(Vec3.ZERO);
                        ectoSlab.setSpeed(0.0f);
                    }
                    return;
                }
                if (burrowedTime > 0) burrowedTime--;

                if (ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) {
                    if (ectoSlab.wasForcedOut && burrowedTime > 10) {
                        burrowedTime = 10;
                        ectoSlab.level().broadcastEntityEvent(ectoSlab, (byte) 87);
                    }
                    if (burrowedTime >= 30) control.setSeekTarget(target, ectoSlab.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (burrowedTime == 30) {
                        ectoSlab.playSound(ectoSlab.getWarnSound(), this.ectoSlab.getSoundVolume(), 1.0f);
                        ectoSlab.level().getServer().execute(() -> ectoSlab.level().broadcastEntityEvent(ectoSlab, (byte) 85));
                    }
                    if (burrowedTime > 0 && burrowedTime < 30) {
                        control.setSeekTarget(null, 0.0);
                        control.setWantedPosition(ectoSlab.getX(), ectoSlab.getY(), ectoSlab.getZ(), 0.0);
                        this.ectoSlab.setDeltaMovement(Vec3.ZERO);
                        this.ectoSlab.setSpeed(0.0f);
                    }
                    if (burrowedTime == 0) {
                        ectoSlab.setBurrowed(false);
                        ectoSlab.burrowedCooldown = ectoSlab.getBurrowCooldown();
                        ectoSlab.doEmergenceBurst(ectoSlab.wasForcedOut);
                        control.setSeekTarget(null, 0.0);
                    }
                }
            }
        }
    }

    static class EctoSlabFloatGoal extends Goal {
        private final EctoSlab ectoSlab;

        public EctoSlabFloatGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
            ectoSlab.getNavigation().setCanFloat(true);
        }

        @Override
        public boolean canUse() {
            return (this.ectoSlab.isInWater() || this.ectoSlab.isInLava()) && this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.ectoSlab.getRandom().nextFloat() < 0.8F) this.ectoSlab.getJumpControl().jump();
            if (this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) control.setWantedMovement(1.2);
        }
    }

    static class EctoSlabKeepOnJumpingGoal extends Goal {
        private final EctoSlab ectoSlab;

        public EctoSlabKeepOnJumpingGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.ectoSlab.isPassenger();
        }

        @Override
        public void tick() {
            if (this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) control.setWantedMovement(1.0);
        }
    }

    static class EctoSlabRandomDirectionGoal extends Goal {
        private final EctoSlab ectoSlab;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public EctoSlabRandomDirectionGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.ectoSlab.getTarget() == null
                    && (this.ectoSlab.onGround() || this.ectoSlab.isInWater() || this.ectoSlab.isInLava() || this.ectoSlab.hasEffect(MobEffects.LEVITATION))
                    && this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl;
        }

        @Override
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.ectoSlab.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.ectoSlab.getRandom().nextInt(360);
            }

            if (this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) control.setDirection(this.chosenDegrees, false);
        }
    }

    static class EctoSlabPetrifiedGoal extends Goal {
        private final EctoSlab ectoSlab;

        public EctoSlabPetrifiedGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return ectoSlab.isPetrified();
        }

        @Override
        public boolean canContinueToUse() {
            return ectoSlab.isPetrified();
        }

        @Override
        public void start() {
            alignToBlockCenter();
        }

        @Override
        public void tick() {
            ectoSlab.setDeltaMovement(0.0, ectoSlab.getDeltaMovement().y, 0.0);
            ectoSlab.setSpeed(0);
            ectoSlab.xxa = 0;
            ectoSlab.zza = 0;
            if (ectoSlab.onGround()) alignToBlockCenter();
        }

        private void alignToBlockCenter() {
            if (ectoSlab.level().isClientSide) return;

            BlockPos blockPos = ectoSlab.blockPosition();
            double centerX = blockPos.getX() + 0.5;
            double centerZ = blockPos.getZ() + 0.5;
            ectoSlab.setPos(centerX, ectoSlab.getY(), centerZ);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client {

        public static void warnParticle(EctoSlab ectoSlab, Level level, Vec3 pos) {
            Vec3 direction = new Vec3(0.0, 1.0, 0.0);
            int delay = 0;
            for (int i = 0; i < 3; i++) {
                WorldParticleBuilder.create(JNEParticleTypes.WIND_TRAIL.get())
                        .setFullBrightLighting()
                        .setColorData(ColorParticleData.create(new Color(0x72F4FF)).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1.0f).setEasing(Easing.SINE_OUT).build())
                        .setBehavior(DirectionalParticleBehavior.directional(direction))
                        .setTransparencyData(GenericParticleData.create(0.7f, 0.0f).build())
                        .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                        .setLifetime(20)
                        .disableNoClip()
                        .setLifeDelay(delay)
                        .addTickActor(actor -> {
                            if (ectoSlab.isBurrowed()) actor.setPos(ectoSlab.getX(), ectoSlab.getY() + 0.1, ectoSlab.getZ());
                        })
                        .spawn(level, pos.x, pos.y - 1.5, pos.z);
                delay += 7;
            }
        }

        public static void burrowedParticle(EctoSlab ectoSlab, Level level, RandomSource random) {
            var parX = ectoSlab.getRandomX(0.5f);
            var parZ = ectoSlab.getRandomZ(0.5f);
            WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
                    .setFullBrightLighting()
                    .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                    .setScaleData(GenericParticleData.create(0.0f, 0.28f, 0.0f).build())
                    .setTransparencyData(GenericParticleData.create(0.5f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .setLifetime(random.nextInt(20))
                    .disableNoClip()
                    .setGravity(0f)
                    .modifySpinData(consumer -> {

                    })
                    .setColorData(ColorParticleData.create(new Color(0x72F4FF)).build())
                    .setMotion(0.0, 0.26, 0.0)
                    .spawn(level, parX, ectoSlab.getY(), parZ);
        }

        public static void forcedEmergeParticle(EctoSlab ectoSlab, Level level, Vec3 pos) {
            Vec3 direction = new Vec3(0.0, 1.0, 0.0);
            WorldParticleBuilder.create(JNEParticleTypes.WIND_TRAIL.get())
                    .setFullBrightLighting()
                    .setSpinData(SpinParticleData.createRandomDirection(level.random, 0.0f, 1.0f).setCoefficient(0.25f).setEasing(Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(new Color(0xFFFFFF)).build())
                    .setScaleData(GenericParticleData.create(0.1f, 1.5f).setEasing(Easing.SINE_OUT).build())
                    .setBehavior(DirectionalParticleBehavior.directional(direction))
                    .setTransparencyData(GenericParticleData.create(0.5f, 0.0f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .setLifetime(10)
                    .disableNoClip()
                    .addTickActor(actor -> {
                        if (ectoSlab.isBurrowed()) actor.setPos(ectoSlab.getX(), ectoSlab.getY() + 0.1, ectoSlab.getZ());
                    })
                    .spawn(level, pos.x, pos.y - 1.5, pos.z);
        }

        public static void burrowDustParticle(EctoSlab ectoSlab, Level level, RandomSource random) {
            LodestoneWorldParticleType particle = SMOKE_VARIANTS[random.nextInt(SMOKE_VARIANTS.length)];
            WorldParticleBuilder.create(particle)
                    .setNaturalLighting()
                    .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.33f, 0.4f), 0.95f).build())
                    .setTransparencyData(GenericParticleData.create(0.75f, 0.5f, 0.0f).setEasing(Easing.BOUNCE_OUT).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setColorData(ColorParticleData.create(new Color(0x9E9E9E)).setEasing(Easing.SINE_IN_OUT).build())
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(Mth.randomBetweenInclusive(random, 20, 30))
                    .disableNoClip()
                    .addMotion(0.0 + random.nextDouble() / 24, 0.09, 0.0 + random.nextDouble() / 24)
                    .setGravity(0.3f)
                    .spawn(level, ectoSlab.getRandomX(1.0), ectoSlab.getY() + 0.2, ectoSlab.getRandomZ(1.0));
        }

        public static void shardParticle(EctoSlab ectoSlab, Level level, RandomSource random) {
            LodestoneWorldParticleType particle = JNEParticleTypes.ECTO_SHARD.get();
            for (int i = 0; i < 64; i++) {
                var motionX = random.nextDouble() / 3.6 * (random.nextBoolean() ? 1 : -1);
                var motionY = random.nextDouble() / 2.6 * (random.nextBoolean() ? 1 : -1);
                var motionZ = random.nextDouble() / 3.6 * (random.nextBoolean() ? 1 : -1);
                WorldParticleBuilder.create(particle)
                        .setNaturalLighting()
                        .setSpinData(SpinParticleData.createRandomDirection(level.random, 0.0f, 1.0f).setCoefficient(0.0f).setEasing(Easing.SINE_IN).build())
                        .setScaleData(GenericParticleData.create(0.13f).build())
                        .setTransparencyData(GenericParticleData.create(1.0f).build())
                        .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                        .setLifetime(random.nextInt(80, 120))
                        .disableNoClip()
                        .addMotion(motionX, motionY, motionZ)
                        .setGravity(0.75f)
                        .spawn(level, ectoSlab.getX(), ectoSlab.getY() + 0.2, ectoSlab.getZ());
            }
        }

        public static void petrificationParticle(EctoSlab ectoSlab, Level level, RandomSource random) {
            Color color = new Color(0x0E4E4E);
            for (int i = 0; i < 12; i++) {
                WorldParticleBuilder.create(JNEParticleTypes.SPARKLE.get())
                        .setFullBrightLighting()
                        .setColorData(ColorParticleData.create(color).build())
                        .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 2.0f).setCoefficient(1.0f).setEasing(Easing.SINE_IN).build())
                        .setScaleData(GenericParticleData.create(0.05f, 0.13f, 0.0f).setEasing(Easing.BOUNCE_IN_OUT).build())
                        .setTransparencyData(GenericParticleData.create(0.5f).build())
                        .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .setLifetime(random.nextInt(5, 15) + (ectoSlab.getStackSize() * 10))
                        .enableNoClip()
                        .setLifeDelay(random.nextInt(0, 20))
                        .setGravity(0.0f)
                        .setMotion(0.0, 0.08, 0.0)
                        .spawn(level, ectoSlab.getRandomX(1.0), ectoSlab.getY() + 0.2, ectoSlab.getRandomZ(1.0));
            }
        }

        public static void petrifySwirlsBurstParticle(EctoSlab ectoSlab, Level level, Vec3 pos) {
            Vec3 direction = new Vec3(0.0, 1.0, 0.0);
            WorldParticleBuilder.create(JNEParticleTypes.LARGE_BURST.get())
                    .setFullBrightLighting()
                    .setSpinData(SpinParticleData.createRandomDirection(level.random, 0.0f, 1.0f).setCoefficient(0.25f).setEasing(Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(new Color(0x0E4E4E)).build())
                    .setScaleData(GenericParticleData.create(0.1f, ectoSlab.getPetrificationRadius()).setEasing(Easing.SINE_OUT).build())
                    .setBehavior(DirectionalParticleBehavior.directional(direction))
                    .setTransparencyData(GenericParticleData.create(1.0f, 0.0f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .setLifetime(80)
                    .disableNoClip()
                    .spawn(level, pos.x, pos.y + 0.4, pos.z);
        }
    }
}