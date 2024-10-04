package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Fireball.class)
public abstract class FireballMixin extends AbstractHurtingProjectile {

    @Unique
    private boolean isConfigEnabled = JNEConfigs.REDESIGNED_FIREBALLS.get();

    public FireballMixin(EntityType<? extends Fireball> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (isConfigEnabled) {
            this.level().addParticle((((Fireball) (Object) this) instanceof LargeFireball) ? JNEParticleTypes.FIREBALL_TRAIL.get() : JNEParticleTypes.SMALL_FIREBALL_TRAIL.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, 0, 0);
        }
    }
}
