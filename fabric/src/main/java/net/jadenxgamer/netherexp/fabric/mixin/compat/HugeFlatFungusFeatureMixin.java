package net.jadenxgamer.netherexp.fabric.mixin.compat;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "gardensofthedead.feature.HugeFlatFungusFeature")
public abstract class HugeFlatFungusFeatureMixin {

    @Redirect(
            method = "placeHatBlock",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;SHROOMLIGHT:Lnet/minecraft/world/level/block/Block;")
    )
    private Block redirectShroomlight() {
        return JNEBlocks.SHROOMBLIGHT.get();
    }
}
