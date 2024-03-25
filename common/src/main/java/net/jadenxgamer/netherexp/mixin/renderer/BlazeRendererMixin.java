package net.jadenxgamer.netherexp.mixin.renderer;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlazeRenderer.class)
public abstract class BlazeRendererMixin {

    @Unique
    private static final ResourceLocation netherexp$LIT = new ResourceLocation(NetherExp.MOD_ID, "textures/entity/blaze/lit.png");
    @Unique
    private static final ResourceLocation netherexp$DIM = new ResourceLocation(NetherExp.MOD_ID, "textures/entity/blaze/dim.png");
    @Unique
    private static final ResourceLocation netherexp$DULL = new ResourceLocation(NetherExp.MOD_ID, "textures/entity/blaze/dull.png");
    @Unique
    private static final ResourceLocation netherexp$FADING = new ResourceLocation(NetherExp.MOD_ID, "textures/entity/blaze/fading.png");

    @Inject(
            method = "getTextureLocation(Lnet/minecraft/world/entity/monster/Blaze;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getDiminishingTexture(Blaze blaze, CallbackInfoReturnable<ResourceLocation> cir) {
        float light = blaze.getHealth();
        if (light <= 2.0F) {
            cir.setReturnValue(netherexp$FADING);
        }
        else if (light <= 6.0F) {
            cir.setReturnValue(netherexp$DULL);
        }
        else if (light <= 15.0F) {
            cir.setReturnValue(netherexp$DIM);
        }
        else if (light <= 20.0F) {
            cir.setReturnValue(netherexp$LIT);
        }
    }

    @Inject(
            method = "getBlockLightLevel(Lnet/minecraft/world/entity/monster/Blaze;Lnet/minecraft/core/BlockPos;)I",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getDiminishingBlockLight(Blaze blaze, BlockPos blockPos, CallbackInfoReturnable<Integer> cir) {
        float light = blaze.getHealth();
        if (light <= 2.0F) {
            cir.setReturnValue(1);
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
