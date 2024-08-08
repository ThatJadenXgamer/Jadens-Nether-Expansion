package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WillOWisp extends ThrowableItemProjectile {
    private LivingEntity target;
    private float speed;

    public WillOWisp(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public WillOWisp(LivingEntity livingEntity, Level level, LivingEntity target, float speed) {
        super(JNEEntityType.WILL_O_WISP.get(), livingEntity, level);
        this.noPhysics = true;
        this.target = target;
        this.speed = speed;
    }

    public WillOWisp(LivingEntity livingEntity, Level level, LivingEntity target, double x, double y, double z, float speed) {
        super(JNEEntityType.WILL_O_WISP.get(), livingEntity, level);
        this.noPhysics = true;
        this.target = target;
        this.setPos(x, y, z);
        this.speed = speed;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return JNEItems.WILL_O_WISP.get();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        entity.hurt(this.damageSources().source(JNEDamageSources.SOUL_BULLET), 5);
        this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        if (!this.level().isClientSide) {
            this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.MUD_PLACE, SoundSource.NEUTRAL, 1F, 1F);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockPos pos = blockHitResult.getBlockPos();
        this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        if (!this.level().isClientSide) {
            this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.MUD_PLACE, SoundSource.NEUTRAL, 1F, 1F);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.target != null && this.target.isAlive()) {
            Vec3 vec3 = new Vec3(this.target.getX() - this.getX(), this.target.getY() + this.target.getEyeHeight() - this.getY(), this.target.getZ() - this.getZ());
            Vec3 direction = vec3.normalize().scale(speed);
            this.setDeltaMovement(direction);
        }
        if (!this.level().isClientSide && tickCount > 600) {
            this.discard();
        }
        this.spawnParticles();
    }

    private void spawnParticles() {
        for (int j = 0; j < 1; ++j) {
            this.level().addParticle(JNEParticleTypes.WISP.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, 0, 0);
        }
    }

    @Override
    protected float getGravity() {
        return 0.0f;
    }
}
