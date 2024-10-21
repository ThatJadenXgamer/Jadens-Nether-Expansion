package net.jadenxgamer.netherexp.forge.mixin.forge;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {
    @Shadow protected boolean inGround;

    @Shadow public abstract byte getPierceLevel();

    @WrapOperation(
            method = "tick",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;inGround:Z", ordinal = 0)
    )
    private void netherexp$modifyInGround(AbstractArrow instance, boolean value, Operation<Void> original) {
        if (instance.getType().is(JNETags.EntityTypes.IGNORES_BLOCK_COLLISION) && this.getPierceLevel() <= 0) {
            inGround = false;
        } else {
            original.call(instance, value);
        }
    }
}
