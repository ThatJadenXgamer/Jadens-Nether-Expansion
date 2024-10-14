package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {
    @Shadow protected boolean inGround;

    @Shadow public abstract byte getPierceLevel();

    @Redirect(
            method = "tick",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;inGround:Z", ordinal = 0)
    )
    private void netherexp$tick(AbstractArrow instance, boolean value) {
        if (instance.getType().is(JNETags.EntityTypes.IGNORES_BLOCK_COLLISION) && this.getPierceLevel() <= 0) {
            inGround = false;
        }
    }
}
