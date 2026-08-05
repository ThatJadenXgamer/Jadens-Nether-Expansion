package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.core.item.components.AntidoteContents;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.OptionalInt;

public class AntidoteEffectCloud extends AreaEffectCloud {

    private final AntidoteContents contents;

    public AntidoteEffectCloud(EntityType<AntidoteEffectCloud> entityType, Level level) {
        super(entityType, level);
        this.contents = AntidoteContents.EMPTY;
    }

    public AntidoteEffectCloud(Level level, double x, double y, double z, AntidoteContents contents) {
        super(JNEEntityType.ANTIDOTE_EFFECT_CLOUD.get(), level);
        this.contents = contents;
        this.setPos(x, y, z);
        this.setRadius(12.0F);
        this.setDuration(200);
        this.setWaitTime(0);
        this.setRadiusPerTick(-this.getRadius() / this.getDuration());
        this.setRadiusOnUse(0.0F);
        this.setDurationOnUse(0);
        int color = contents.customColor().orElseGet(() ->
                AntidoteContents.getColorFromEffects(contents.getAllEffects()).orElse(0x808080));
        this.setParticle(ColorParticleOption.create(JNEParticleTypes.IMMUNITY_EFFECT.get(), FastColor.ARGB32.opaque(color)));
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0F, this.getRadius());
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            float radius = this.getRadius() + this.getRadiusPerTick();
            if (radius < 1.0F) this.discard();
            else this.setRadius(radius);
            if (this.tickCount % 5 == 0) this.applyEffectsToEntities();
            if (this.tickCount >= this.getDuration()) this.discard();
        } else {
            this.spawnAntidoteParticles();
        }
        this.baseTick();
    }

    private void spawnAntidoteParticles() {
        float radius = this.getRadius();
        if (radius <= 0.0F) return;
        int count = Math.min(Mth.ceil((float) Math.PI * radius * radius * (radius / 0.5F) * 0.1F), 100);
        ParticleOptions particle = this.getParticle();
        for (int i = 0; i < count; i++) {
            float angle = this.random.nextFloat() * (float) (Math.PI * 2);
            float dist = Mth.sqrt(this.random.nextFloat()) * radius;
            this.level().addAlwaysVisibleParticle(particle,
                    this.getX() + Mth.cos(angle) * dist,
                    this.getY() - radius / 2.0 + this.random.nextDouble() * radius,
                    this.getZ() + Mth.sin(angle) * dist,
                    0, 0, 0);
        }
    }

    private void applyEffectsToEntities() {
        List<MobEffectInstance> effects = this.contents.getAllEffects();
        if (effects.isEmpty()) return;
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
        entities.stream()
                .filter(e -> e.isAffectedByPotions() && effects.stream().anyMatch(e::canBeAffected))
                .forEach(e -> effects.forEach(inst -> {
                    if (inst.getEffect().value().isInstantenous())
                        inst.getEffect().value().applyInstantenousEffect(this, this.getOwner(), e, inst.getAmplifier(), 0.5);
                    else e.addEffect(new MobEffectInstance(inst), this);
                }));
    }
}