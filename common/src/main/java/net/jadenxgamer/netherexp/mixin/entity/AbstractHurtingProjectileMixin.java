package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHurtingProjectile.class)
public class AbstractHurtingProjectileMixin {

    @ModifyArg(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractHurtingProjectile;setSecondsOnFire(I)V")
    )
    private int netherexp$tick(int seconds) {
        if (JNEConfigs.REDESIGNED_FIREBALLS.get()) {
            if (((AbstractHurtingProjectile) (Object) this) instanceof Fireball) {
                return 0;
            }
        }
        return seconds;
    }

    @Inject(
            method = "tick",
            at = @At(value = "HEAD")
    )
    private void netherexp$tick(CallbackInfo ci) {
        if (JNEConfigs.REDESIGNED_FIREBALLS.get()) {
            AbstractHurtingProjectile projectile = ((AbstractHurtingProjectile) (Object) this);
            if (projectile instanceof Fireball) {
                projectile.level().addParticle((projectile instanceof LargeFireball) ? JNEParticleTypes.FIREBALL_TRAIL.get() : JNEParticleTypes.SMALL_FIREBALL_TRAIL.get(), projectile.getRandomX(0.5), projectile.getRandomY(), projectile.getRandomZ(0.5), 0, 0, 0);
            }
            else if (projectile instanceof DragonFireball) {
                projectile.level().addParticle(JNEParticleTypes.DRAGON_FIREBALL_TRAIL.get(), projectile.getRandomX(0.5), projectile.getRandomY(), projectile.getRandomZ(0.5), 0, 0, 0);
            }
        }
    }
}
