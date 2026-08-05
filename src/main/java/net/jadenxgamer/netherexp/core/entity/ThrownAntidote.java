package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.core.item.components.AntidoteContents;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownAntidote extends ThrowableItemProjectile implements ItemSupplier {

    private static final EntityDataAccessor<Boolean> LANDED = SynchedEntityData.defineId(ThrownAntidote.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> FUSE_TIME = SynchedEntityData.defineId(ThrownAntidote.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PARTICLE_COLOR = SynchedEntityData.defineId(ThrownAntidote.class, EntityDataSerializers.INT);

    private AntidoteContents contents = AntidoteContents.EMPTY;

    public ThrownAntidote(EntityType<? extends ThrownAntidote> entityType, Level level) { super(entityType, level); }
    public ThrownAntidote(Level level, LivingEntity shooter) { super(JNEEntityType.ANTIDOTE.get(), shooter, level); }
    public ThrownAntidote(Level level, double x, double y, double z) { super(JNEEntityType.ANTIDOTE.get(), x, y, z, level); }

    public void setContents(AntidoteContents contents) {
        this.contents = contents;
        int color = contents.customColor().orElseGet(() -> AntidoteContents.getColorFromEffects(contents.getAllEffects()).orElse(0));
        this.entityData.set(PARTICLE_COLOR, color);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LANDED, false);
        builder.define(FUSE_TIME, 60);
        builder.define(PARTICLE_COLOR, 0);
    }

    @Override protected Item getDefaultItem() { return JNEItems.GRENADE_ANTIDOTE.get(); }
    @Override protected double getDefaultGravity() { return 0.05; }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.setDeltaMovement(Vec3.ZERO);
            this.setPos(result.getLocation());
            this.setLanded(true);
            this.setFuseTime(60);
        }
    }

    @Override
    public void tick() {
        if (!this.isLanded()) super.tick();
        if (this.level().isClientSide) {
            if (this.isLanded() && this.getFuseTime() > 0 && (60 - this.getFuseTime()) % 10 == 0) this.spawnTickParticles();
            return;
        }

        if (this.isLanded() && !this.level().getBlockState(this.blockPosition().below()).isSolid()) {
            this.setLanded(false);
            this.setDeltaMovement(Vec3.ZERO);
        }
        int fuse = this.getFuseTime();
        if (fuse-- > 0) {
            this.setFuseTime(fuse);
            if (this.isLanded() && (60 - fuse) % 10 == 0) {
                float elapsed = 60 - fuse;
                float pitch = 0.5f + (elapsed / 60.0f) * 1.8f;
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.GRENADE_ANTIDOTE_TICK.get(), SoundSource.NEUTRAL, 1.0f, pitch);
            }
        } else {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.GRENADE_ANTIDOTE_EXPLODE.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
            this.spawnExplosionParticles();
            AntidoteEffectCloud cloud = new AntidoteEffectCloud(this.level(), this.getX(), this.getY(), this.getZ(), this.contents);
            this.level().addFreshEntity(cloud);
            this.discard();
        }
    }

    private void spawnTickParticles() {
        int color = this.getParticleColor();
        if (color == 0) {
            for (int i = 0; i < 3; i++) this.level().addParticle(ParticleTypes.BUBBLE_POP,
                        this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, 0, 0);
        } else {
            double r = (color >> 16 & 255) / 255.0, g = (color >> 8 & 255) / 255.0, b = (color & 255) / 255.0;
            var particle = ColorParticleOption.create(JNEParticleTypes.IMMUNITY_EFFECT.get(), FastColor.ARGB32.opaque(color));
            for (int i = 0; i < 3; i++) this.level().addParticle(particle,
                        this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), r, g, b);
        }
    }

    private void spawnExplosionParticles() {
        int color = this.getParticleColor();
        if (color == 0) {
            for (int i = 0; i < 20; i++) this.level().addParticle(ParticleTypes.BUBBLE_POP,
                        this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5),
                        (this.random.nextDouble() - 0.5) * 0.5, this.random.nextDouble() * 0.5,
                        (this.random.nextDouble() - 0.5) * 0.5);
        } else {
            double r = (color >> 16 & 255) / 255.0, g = (color >> 8 & 255) / 255.0, b = (color & 255) / 255.0;
            var particle = ColorParticleOption.create(JNEParticleTypes.IMMUNITY_EFFECT.get(), FastColor.ARGB32.opaque(color));
            for (int i = 0; i < 20; i++) this.level().addParticle(particle,
                        this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5),
                        r + (this.random.nextDouble() - 0.5) * 0.3,
                        g + (this.random.nextDouble() - 0.5) * 0.3,
                        b + (this.random.nextDouble() - 0.5) * 0.3);
        }
    }

    // DATA //

    public boolean isLanded() { return this.entityData.get(LANDED); }
    public void setLanded(boolean landed) { this.entityData.set(LANDED, landed); }
    public int getFuseTime() { return this.entityData.get(FUSE_TIME); }
    public void setFuseTime(int fuseTime) { this.entityData.set(FUSE_TIME, fuseTime); }
    public int getParticleColor() { return this.entityData.get(PARTICLE_COLOR); }
}