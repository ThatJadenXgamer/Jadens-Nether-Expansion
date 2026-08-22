package net.jadenxgamer.netherexp.mixin.compat;

import net.jadenxgamer.netherexp.registry.compat.GardensOfTheDeadCompat;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "gardensofthedead.feature.HugeFlatFungusFeature")
public abstract class HugeFlatFungusFeatureMixin {

    // if any GoTD dev is reading this, PLEASE make changing shroomlights on your trees data-driven :sob:
    @Redirect(
            method = "placeHatBlock",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;SHROOMLIGHT:Lnet/minecraft/world/level/block/Block;")
    )
    private Block netherexp$redirectShroomlight() {
        return GardensOfTheDeadCompat.Blocks.SHROOMBLIGHT.get();
    }
}