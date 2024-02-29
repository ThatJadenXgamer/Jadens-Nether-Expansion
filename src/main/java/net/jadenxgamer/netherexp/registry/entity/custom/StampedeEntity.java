package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class StampedeEntity extends HostileEntity implements GeoEntity, Angerable {
    @SuppressWarnings("all")
    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private int angerTime;
    private UUID angryAt;
    private int eating = 0;
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = (item) -> !item.cannotPickup() && item.isAlive() && item.getStack().isOf(Items.BONE);
    private static final TrackedData<Boolean> ANGRY = DataTracker.registerData(StampedeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public StampedeEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.setCanPickUpLoot(true);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.36)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 4.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 3.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 1.4, 32.0F));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8, 1.0000001E-5f));
        this.goalSelector.add(3, new PickupItemGoal());
        this.goalSelector.add(2, new TemptGoal(this, 1.0, Ingredient.ofItems(JNEItems.SKULL_ON_A_STICK), false));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, SkeletonEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, StrayEntity.class, true));
        this.targetSelector.add(3, new UniversalAngerGoal<>(this, false));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient && this.isAlive() && this.canMoveVoluntarily()) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                ++this.eating;
                this.setAngry(true);
                int f = this.random.nextInt(2);
                if (this.eating > 100) {
                    this.equipStack(EquipmentSlot.MAINHAND, Items.AIR.getDefaultStack());
                    this.setHealth(this.getMaxHealth());
                    this.eating = 0;
                    this.setAngry(false);
                }
                else if (this.eating > 0 && f == 0) {
                    this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, 1.0F);
                }
            }
        }
        damageLivingEntities(this.getWorld().getOtherEntities(this, this.getBoundingBox(), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
    }

    private void damageLivingEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                entity.damage(this.getDamageSources().create(JNEDamageSources.STAMPEDE_CRUSH, this), 10);
            }
        }
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    protected boolean isDisallowedInPeaceful() {
        return false;
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty() && stack.isOf(Items.BONE);
    }

    @Override
    public void onAttacking(Entity target) {
        super.onAttacking(target);
        int i = random.nextInt(10);
        int a = random.nextInt(3) + 1;
        if (i == 0) {
            for (int d = 0; d < a; ++d) {
                this.dropItem(JNEItems.STRIDITE);
            }
        }
    }

    /////////
    // NBT //
    /////////

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGRY, false);
    }

    public boolean isAngry() {
        return this.dataTracker.get(ANGRY);
    }

    public void setAngry(boolean angry) {
        this.dataTracker.set(ANGRY, angry);
    }

    public int getEating() {
        return this.eating;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Eating", this.eating);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.eating = nbt.getInt("Eating");
    }

    ////////////////////
    // GeckoLib Stuff //
    ////////////////////

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.stampede.idle");
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.stampede.walk");
    protected static final RawAnimation RUN_ANIM = RawAnimation.begin().thenLoop("animation.stampede.run");
    protected static final RawAnimation SWIMMING_ANIM = RawAnimation.begin().thenLoop("animation.stampede.swimming");
    protected static final RawAnimation EATING_ANIM = RawAnimation.begin().thenLoop("animation.stampede.eating");

    @SuppressWarnings("all")
    private PlayState movePredicate(AnimationState event) {
        if (this.isInLava() || this.isTouchingWater()) {
            return event.setAndContinue(SWIMMING_ANIM);
        }
        else if (event.isMoving() && isAngry()) {
            return event.setAndContinue(RUN_ANIM);
        }
        else if (event.isMoving()) {
            return event.setAndContinue(WALK_ANIM);
        }
        else if (!event.isMoving()) {
            return event.setAndContinue(IDLE_ANIM);
        }
        return PlayState.STOP;
    }

    @SuppressWarnings("all")
    private PlayState eatPredicate(AnimationState event) {
        if (getEating() > 0) {
            return event.setAndContinue(EATING_ANIM);
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "moveController",
                8, this::movePredicate));
        controllers.add(new AnimationController<>(this, "eatController",
                8, this::eatPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    ////////
    // AI //
    ////////

    class PickupItemGoal extends Goal {
        public PickupItemGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (!StampedeEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (StampedeEntity.this.getTarget() == null) {
                if (StampedeEntity.this.getRandom().nextInt(toGoalTicks(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = StampedeEntity.this.getWorld().getEntitiesByClass(ItemEntity.class, StampedeEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), StampedeEntity.PICKABLE_DROP_FILTER);
                    return !list.isEmpty() && StampedeEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        @Override
        public void tick() {
            List<ItemEntity> list = StampedeEntity.this.getWorld().getEntitiesByClass(ItemEntity.class, StampedeEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), StampedeEntity.PICKABLE_DROP_FILTER);
            ItemStack itemStack = StampedeEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (itemStack.isEmpty() && !list.isEmpty()) {
                StampedeEntity.this.getNavigation().startMovingTo(list.get(0), 1.2000000476837158);
            }
        }

        @Override
        public void start() {
            List<ItemEntity> list = StampedeEntity.this.getWorld().getEntitiesByClass(ItemEntity.class, StampedeEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), StampedeEntity.PICKABLE_DROP_FILTER);
            if (!list.isEmpty()) {
                StampedeEntity.this.getNavigation().startMovingTo(list.get(0), 1.2000000476837158);
            }
        }
    }


    ///////////
    // ANGER //
    ///////////

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
}
