package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class GrenadeEffectCloud extends Entity implements TraceableEntity {
    private final int duration;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;
    private MobEffect effect;
    private int effectDuration;
    private boolean inactive;
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(GrenadeEffectCloud.class, EntityDataSerializers.INT);
    public static final Predicate<LivingEntity> WATER_SENSITIVE_OR_ON_FIRE = (arg) -> arg.isSensitiveToWater() || arg.isOnFire();

    public GrenadeEffectCloud(EntityType<?> type, Level level) {
        super(type, level);
        this.duration = 100;
        this.noPhysics = true;
    }

    public GrenadeEffectCloud(Level level, double d, double e, double f) {
        super(JNEEntityType.GRENADE_EFFECT_CLOUD.get(), level);
        this.duration = 100;
        this.noPhysics = true;
        this.setPos(d, e, f);
    }

    public void refreshDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.refreshDimensions();
        this.setPos(d, e, f);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            int c = this.entityData.get(COLOR);
            if (c > 0) {
                if (this.random.nextInt(5) == 0) {
                    double d0 = (double) (c >> 16 & 255) / 255.0;
                    double d1 = (double) (c >> 8 & 255) / 255.0;
                    double d2 = (double) (c & 255) / 255.0;
                    for(int i = 0; i < 20; ++i) {
                        this.level().addParticle(JNEParticleTypes.IMMUNITY_EFFECT.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), d0, d1, d2);
                    }
                }
            }
            else {
                this.level().addParticle(ParticleTypes.BUBBLE_POP, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0f, 0.2f, 0.0f);
            }
        }
        else {
            if (this.tickCount >= this.duration) {
                this.discard();
            }
            else if (this.effect != null && !this.inactive) {
                effectLivingEntities(this.level().getEntities(this, this.getBoundingBox(), EntitySelector.LIVING_ENTITY_STILL_ALIVE));
            }
            else {
                applyWater();
            }
        }
    }

    private void effectLivingEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                if (this.effect != null) {
                    livingEntity.addEffect(new MobEffectInstance(this.effect, this.effectDuration, 0, false, true), this);
                }
            }
        }
    }

    private void applyWater() {
        AABB aABB = this.getBoundingBox();
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, aABB, WATER_SENSITIVE_OR_ON_FIRE);

        for (LivingEntity livingEntity : list) {
            double d = this.distanceToSqr(livingEntity);
            if (d < 16.0) {
                if (livingEntity.isSensitiveToWater()) {
                    livingEntity.hurt(this.damageSources().indirectMagic(this, this.getOwner()), 15.0F);
                }

                if (livingEntity.isOnFire() && livingEntity.isAlive()) {
                    livingEntity.extinguishFire();
                }
            }
        }

        List<Axolotl> list2 = this.level().getEntitiesOfClass(Axolotl.class, aABB);

        for (Axolotl axolotl : list2) {
            axolotl.rehydrate();
        }
    }

    /////////
    // NBT //
    /////////

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(COLOR, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        if (nbt.contains("Owner")) {
            this.ownerUUID = nbt.getUUID("Owner");
        }
        if (nbt.contains("Color")) {
            this.setColor(nbt.getInt("Color"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        if (this.ownerUUID != null) {
            nbt.putUUID("Owner", this.ownerUUID);
        }
        nbt.putInt("Color", this.getColor());
    }

    public void setMobEffect(MobEffect effect) {
        this.effect = effect;
    }

    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }

    public void setColor(int i) {
        this.getEntityData().set(COLOR, i);
    }

    public void setInactive(boolean bl) {
        this.inactive = bl;
    }

    public int getColor() {
        return this.getEntityData().get(COLOR);
    }

    public void setOwnerUUID(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUUID = owner == null ? null : owner.getUUID();
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }
}
