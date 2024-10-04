package net.jadenxgamer.netherexp.mixin.entity;

import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Fireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractHurtingProjectile.class)
public class AbstractHurtingProjectileMixin {

    @ModifyArg(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractHurtingProjectile;setSecondsOnFire(I)V")
    )
    private int netherexp$tick(int seconds) {
        if (((AbstractHurtingProjectile) (Object) this) instanceof Fireball) {
            return 0;
        }
        return seconds;
    }
}
