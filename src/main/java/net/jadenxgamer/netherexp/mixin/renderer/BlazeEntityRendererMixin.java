package net.jadenxgamer.netherexp.mixin.renderer;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.client.render.entity.BlazeEntityRenderer;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlazeEntityRenderer.class)
public abstract class BlazeEntityRendererMixin {

    @Unique
    private static final Identifier LIT = new Identifier(NetherExp.MOD_ID, "textures/entity/blaze/lit.png");
    @Unique
    private static final Identifier DIM = new Identifier(NetherExp.MOD_ID, "textures/entity/blaze/dim.png");
    @Unique
    private static final Identifier DULL = new Identifier(NetherExp.MOD_ID, "textures/entity/blaze/dull.png");
    @Unique
    private static final Identifier FADING = new Identifier(NetherExp.MOD_ID, "textures/entity/blaze/fading.png");

    @Inject(
            method = "getTexture(Lnet/minecraft/entity/mob/BlazeEntity;)Lnet/minecraft/util/Identifier;",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getDiminishingTexture(BlazeEntity blazeEntity, CallbackInfoReturnable<Identifier> cir) {
        float light = blazeEntity.getHealth();
        if (NetherExp.getConfig().entities.blazeConfigs.diminishing_blazes) {
            if (light <= 2.0F) {
                cir.setReturnValue(FADING);
            }
            else if (light <= 6.0F) {
                cir.setReturnValue(DULL);
            }
            else if (light <= 15.0F) {
                cir.setReturnValue(DIM);
            }
            else if (light <= 20.0F) {
                cir.setReturnValue(LIT);
            }
        }
    }

    @Inject(
            method = "getBlockLight(Lnet/minecraft/entity/mob/BlazeEntity;Lnet/minecraft/util/math/BlockPos;)I",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getDiminishingBlockLight(BlazeEntity blazeEntity, BlockPos blockPos, CallbackInfoReturnable<Integer> cir) {
        float light = blazeEntity.getHealth();
        if (NetherExp.getConfig().entities.blazeConfigs.diminishing_blazes) {
            if (light <= 2.0F) {
                cir.setReturnValue(2);
            }
            else if (light <= 6.0F) {
                cir.setReturnValue(5);
            }
            else if (light <= 15.0F) {
                cir.setReturnValue(10);
            }
            else if (light <= 20.0F) {
                cir.setReturnValue(15);
            }
        }
    }
}
