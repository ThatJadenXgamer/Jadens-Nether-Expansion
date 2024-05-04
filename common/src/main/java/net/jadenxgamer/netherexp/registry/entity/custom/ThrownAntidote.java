package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ThrownAntidote extends ThrowableItemProjectile implements ItemSupplier {
    private static final EntityDataAccessor<Boolean> IS_STUCK = SynchedEntityData.defineId(ThrownAntidote.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TICK_COUNTER = SynchedEntityData.defineId(ThrownAntidote.class, EntityDataSerializers.INT);

    public ThrownAntidote(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownAntidote(Level level, LivingEntity livingEntity) {
        super(JNEEntityType.ANTIDOTE.get(), livingEntity, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (getIsStuck()) {
            int tick = this.getTickCounter();
            this.setTickCounter(tick + 1);
            ItemStack stack = this.getItem();
            MobEffect mobEffect = getAntidoteEffect(stack);
            if (tick % 15 == 0) {
                if (this.level().isClientSide) {
                    if (mobEffect != null) {
                        this.playPotionParticles(mobEffect);
                    }
                    else {
                        this.level().addParticle(ParticleTypes.BUBBLE_POP, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0f, 0.2f, 0.0f);
                    }
                }
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.ANTIDOTE_NEGATE.get(), SoundSource.NEUTRAL, 1.0f, 0.8f);
            }
            else {
                if (tick > 40) {
                    this.applyGrenade(stack);
                    this.discard();
                    if (mobEffect != null) {
                        this.playPotionParticles(mobEffect);
                    }
                    else {
                        this.level().addParticle(ParticleTypes.BUBBLE_POP, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0f, 0.2f, 0.0f);
                    }
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BREWING_STAND_BREW, SoundSource.NEUTRAL, 1.0f, 0.8f);
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide) {
            this.setDeltaMovement(0, 0, 0);
            this.setNoGravity(true);
            this.getEntityData().set(IS_STUCK, true);
        }
    }

    private void applyGrenade(ItemStack stack) {
        GrenadeEffectCloud grenadeEffectCloud = new GrenadeEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        Entity entity = this.getOwner();
        CompoundTag nbt = stack.getTag();
        if (entity instanceof LivingEntity) {
            grenadeEffectCloud.setOwnerUUID((LivingEntity)entity);
        }

        grenadeEffectCloud.setMobEffect(getAntidoteEffect(stack));
        grenadeEffectCloud.setEffectDuration(getDuration(stack));
        if (nbt != null) {
            if (nbt.contains("Inactive")) {
                grenadeEffectCloud.setInactive(nbt.getBoolean("Inactive"));
            }
            else {
                grenadeEffectCloud.setInactive(false);
            }
            if (nbt.contains("CustomAntidoteColor", 99)) {
                grenadeEffectCloud.setColor(nbt.getInt("CustomPotionColor"));
            }
            else {
                grenadeEffectCloud.setColor(Objects.requireNonNull(getAntidoteEffect(stack)).getColor());
            }
        }

        this.level().addFreshEntity(grenadeEffectCloud);
    }

    private void playPotionParticles(MobEffect mobEffect) {
        int c = mobEffect.getColor();
        if (c > 0 && this.random.nextInt(5) == 0) {
            double d0 = (double) (c >> 16 & 255) / 255.0;
            double d1 = (double) (c >> 8 & 255) / 255.0;
            double d2 = (double) (c & 255) / 255.0;
            for(int i = 0; i < 5; ++i) {
                this.level().addParticle(JNEParticleTypes.IMMUNITY_EFFECT.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), d0, d1, d2);
            }
        }
    }

    /////////
    // NBT //
    /////////

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(IS_STUCK, false);
        this.getEntityData().define(TICK_COUNTER, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("isStuck")) {
            this.setIsStuck(nbt.getBoolean("isStuck"));
        }
        if (nbt.contains("TickCounter")) {
            this.setTickCounter(nbt.getInt("TickCounter"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("isStuck", this.getIsStuck());
        nbt.putInt("TickCounter", this.getTickCounter());
    }

    private int getTickCounter() {
        return this.getEntityData().get(TICK_COUNTER);
    }

    private void setTickCounter(int i) {
        this.getEntityData().set(TICK_COUNTER, i);
    }

    private boolean getIsStuck() {
        return this.getEntityData().get(IS_STUCK);
    }

    private void setIsStuck(boolean bl) {
        this.getEntityData().set(IS_STUCK, bl);
    }

    private static MobEffect getAntidoteEffect(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("AntidoteEffect") || Objects.equals(nbt.getString("Antidote"), "Awkward")) {
            return null;
        }

        ResourceLocation antidoteLocation = new ResourceLocation(stack.getOrCreateTag().getString("AntidoteEffect"));
        return Objects.requireNonNull(BuiltInRegistries.MOB_EFFECT.get(antidoteLocation));
    }

    public static int getDuration(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("Duration")) {
            return nbt.getInt("Duration") * 20;
        }
        else return 1800 * 20;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return JNEItems.GRENADE_ANTIDOTE.get();
    }

    protected float getGravity() {
        return 0.05F;
    }
}
