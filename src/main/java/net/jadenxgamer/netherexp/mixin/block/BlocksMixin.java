package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.block.custom.BasaltBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public class BlocksMixin {

    //TODO: implement Ashy Basalt textures
    @Redirect(
            method = "<clinit>",
            at = @At(value = "NEW", target = "net/minecraft/block/PillarBlock",
            ordinal = 0
    ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=basalt"
                    )
            )
    )
    private static PillarBlock netherexp$basalt(AbstractBlock.Settings settings) {
        return new BasaltBlock(settings);
    }
}
