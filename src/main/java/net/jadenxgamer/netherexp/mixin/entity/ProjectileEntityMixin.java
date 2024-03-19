package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {

    @Inject(
            method = "onCollision",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V"),
            cancellable = true
    )
    private void netherexp$onCollision(HitResult hitResult, CallbackInfo ci) {
        EntityHitResult entityHitResult = (EntityHitResult) hitResult;
        Entity entity = entityHitResult.getEntity();
        World world = entity.getWorld();
        if (entity.getType().isIn(JNETags.EntityTypes.PROJECTILES_PASS_THROUGH) || EnchantmentHelper.getEquipmentLevel(JNEEnchantments.PHANTASM_HULL, (LivingEntity) entity) > 0) {
            world.addParticle(ParticleTypes.SOUL, entity.getParticleX(0.5), entity.getRandomBodyY() - 0.25, entity.getParticleZ(0.5), MathHelper.nextBetween(world.random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(world.random, -1.0f, 1.0f) * 0.083333336f);
            ci.cancel();
        }
    }
}
