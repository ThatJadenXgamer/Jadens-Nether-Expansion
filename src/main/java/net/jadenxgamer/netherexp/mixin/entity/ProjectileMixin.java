package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.jadenxgamer.netherexp.config.JNEConfigs.PROJECTILES_PHASE_THROUGH_GHOSTS;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {

    @Inject(
            method = "onHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/EntityHitResult;getEntity()Lnet/minecraft/world/entity/Entity;"),
            cancellable = true
    )
    private void netherexp$onHit(HitResult result, CallbackInfo ci) {
        if (result instanceof EntityHitResult entityResult) {
            Entity entity = entityResult.getEntity();
            Level level = entity.level();
            Projectile projectile = ((Projectile) (Object) this);
            if (projectile instanceof AbstractArrow abstractArrow && abstractArrow.getPierceLevel() > 0) return;
            if (projectile.getType().is(JNETags.EntityTypes.PHANTASM_HULL_PROTECTS_BLACKLIST)) return;

            if (entity.getType().is(JNETags.EntityTypes.PROJECTILES_PASS_THROUGH) && PROJECTILES_PHASE_THROUGH_GHOSTS.get()) {
                level.addParticle(ParticleTypes.SOUL, entity.getRandomX(0.5), entity.getRandomY() - 0.25, entity.getRandomZ(0.5), Mth.randomBetween(level.random, -1.0f, 1.0f) * 0.083333336f, 0.05f, Mth.randomBetween(level.random, -1.0f, 1.0f) * 0.083333336f);
                ci.cancel();
            }
        }
    }
}
