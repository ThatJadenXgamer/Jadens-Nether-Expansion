package net.jadenxgamer.netherexp.mixin.misc;

import net.jadenxgamer.netherexp.core.entity.PortalGlow;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.portal.PortalForcer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PortalForcer.class)
public class PortalForcerMixin {

    @Shadow
    @Final
    protected ServerLevel level;

    @Inject(method = "createPortal", at = @At("RETURN"))
    private void netherexp$spawnPortalGlow(BlockPos pos, Direction.Axis axis, CallbackInfoReturnable<Optional<BlockUtil.FoundRectangle>> cir) {
        cir.getReturnValue().ifPresent(rectangle -> PortalGlow.spawnForPortal(level, rectangle.minCorner));
    }
}