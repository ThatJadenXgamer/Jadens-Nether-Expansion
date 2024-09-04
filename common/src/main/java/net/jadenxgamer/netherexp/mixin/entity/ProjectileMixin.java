package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {

    @Inject(
            method = "onHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V"),
            cancellable = true
    )
    private void netherexp$entityCollisionCheck(HitResult hitResult, CallbackInfo ci) {
        // makes it so entities with phantasm hull or a specific tag are immune to all projectiles because it'll phase through it
        Entity entity = ((EntityHitResult) hitResult).getEntity();
        Level level = entity.level();
        if (entity.getType().is(JNETags.EntityTypes.PROJECTILES_PASS_THROUGH) && ((Projectile) (Object) this).getType().is(JNETags.EntityTypes.PHANTASM_HULL_PROTECTS_AGAINST)) {
            level.addParticle(ParticleTypes.SOUL, entity.getRandomX(0.5), entity.getRandomY() - 0.25, entity.getRandomZ(0.5), Mth.randomBetween(level.random, -1.0f, 1.0f) * 0.083333336f, 0.05f, Mth.randomBetween(level.random, -1.0f, 1.0f) * 0.083333336f);
            ci.cancel();
        }
        else if (entity instanceof LivingEntity livingEntity && EnchantmentHelper.getEnchantmentLevel(JNEEnchantments.PHANTASM_HULL.get(), livingEntity) > 0 && livingEntity.isShiftKeyDown() && ((Projectile) (Object) this).getType().is(JNETags.EntityTypes.PHANTASM_HULL_PROTECTS_AGAINST)) {
            level.addParticle(ParticleTypes.SOUL, livingEntity.getRandomX(0.5), livingEntity.getRandomY() - 0.25, livingEntity.getRandomZ(0.5), Mth.randomBetween(level.random, -1.0f, 1.0f) * 0.083333336f, 0.05f, Mth.randomBetween(level.random, -1.0f, 1.0f) * 0.083333336f);
            if (level.getRandom().nextInt(2) == 0) {
                ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
                itemStack.hurtAndBreak(1, livingEntity, (user) -> user.broadcastBreakEvent(EquipmentSlot.CHEST));
            }
            ci.cancel();
        }
    }
}
