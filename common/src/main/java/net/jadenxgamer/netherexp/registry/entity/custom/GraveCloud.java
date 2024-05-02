package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class GraveCloud extends Entity implements TraceableEntity {
    private final int duration;

    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;

    public GraveCloud(EntityType<?> type, Level level) {
        super(type, level);
        this.duration = 800;
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {

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
            for(int i = 0; i < 2; ++i) {
                this.level().addParticle(JNEParticleTypes.GRASP_MIST.get(), this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
        else {
            if (this.tickCount >= this.duration) {
                this.discard();
            }
            else {
                damageLivingEntities(this.level().getEntities(this, this.getBoundingBox(), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
            }
        }
    }



    private void damageLivingEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !(livingEntity.getType().is(JNETags.EntityTypes.IMMUNE_TO_GRAVE_CLOUDS))) {
                    livingEntity.hurt(this.damageSources().source(JNEDamageSources.DEATH_CLOUD_SUFFOCATION), 1);
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 4, false, true), this);
                }
            }
        }

    /////////
    // NBT //
    /////////

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        if (nbt.contains("Owner")) {
            this.ownerUUID = nbt.getUUID("Owner");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        if (this.ownerUUID != null) {
            nbt.putUUID("Owner", this.ownerUUID);
        }
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
