package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {
    @Unique
    private static final List<String> DEV_CAPE_PLAYERS = Arrays.asList("JadenXgamer", "Dev");

    @Shadow @Nullable
    protected abstract PlayerInfo getPlayerInfo();

    @Inject(
            method = "getCloakTextureLocation",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getCustomCapeTexture(CallbackInfoReturnable<ResourceLocation> cir) {
        PlayerInfo playerInfo = this.getPlayerInfo();
        if (playerInfo != null && DEV_CAPE_PLAYERS.contains(playerInfo.getProfile().getName())) {
            cir.setReturnValue(new ResourceLocation(NetherExp.MOD_ID, "textures/entity/cape/jadenxgamer.png"));
        }
    }
}
