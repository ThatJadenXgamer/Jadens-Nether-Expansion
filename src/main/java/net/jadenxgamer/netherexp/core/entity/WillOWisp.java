package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
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
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

public class WillOWisp extends ThrowableItemProjectile {
    private LivingEntity target;
    private float speed;
    private int damage;

    public WillOWisp(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public WillOWisp(LivingEntity shooter, Level level, LivingEntity target, float speed) {
        super(JNEEntityType.WILL_O_WISP.get(), shooter, level);
        this.noPhysics = true;
        this.target = target;
        this.speed = speed;
        this.damage = 3;
    }

    public WillOWisp(LivingEntity shooter, Level level, LivingEntity target, double x, double y, double z, float speed, int damage) {
        super(JNEEntityType.WILL_O_WISP.get(), shooter, level);
        this.noPhysics = true;
        this.target = target;
        this.setPos(x, y, z);
        this.speed = speed;
        this.damage = damage;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.isBlocking()) {
                this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.NEUTRAL, 1f, 1f);
            } else entity.hurt(this.damageSources().source(JNEDamageSources.WILL_O_WISP, this.getOwner()), damage);
        }

        this.impact();
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        this.impact();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.target != null && this.target.isAlive()) {
            Vec3 vec3 = new Vec3(this.target.getX() - this.getX(), this.target.getY() + this.target.getEyeHeight() - this.getY(), this.target.getZ() - this.getZ());
            Vec3 direction = vec3.normalize().scale(speed);
            this.setDeltaMovement(direction);
        }
        if (!this.level().isClientSide && tickCount > 600) this.impact();
        Wisp.trailParticle(JNEParticleTypes.WISP.get(), level(), random, this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5));
    }

    private void impact() {
        if (this.level().isClientSide()) {
            this.impactParticle(this.level(), this.getX(), this.getY(), this.getZ());
        } else {
            this.level().playSound(null, this.getX(), this.getX(), this.getX(), SoundEvents.MUD_PLACE, SoundSource.NEUTRAL, 1F, 1F);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return JNEItems.WILL_O_WISP.get();
    }

    private void impactParticle(Level level, double x, double y, double z) {
        for (int i = 0; i < 12; i++) {
            WorldParticleBuilder.create(JNEParticleTypes.WILL_O_WISP_IMPACT.get())
                    .setFullBrightLighting()
                    .setSpinData(SpinParticleData.create(0).build())
                    .setScaleData(GenericParticleData.create(0.395f).build())
                    .setTransparencyData(GenericParticleData.create(1).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(10)
                    .enableNoClip()
                    .spawn(level, x, y, z);
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }
}
