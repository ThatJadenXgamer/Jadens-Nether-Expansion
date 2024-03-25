package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.block.custom.BasaltBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public class BlocksMixin {

    //TODO: implement Ashy Basalt textures
    @Redirect(
            method = "<clinit>",
            at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/RotatedPillarBlock;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=basalt"
                    )
            )
    )
    private static RotatedPillarBlock netherexp$basalt(BlockBehaviour.Properties settings) {
        return new BasaltBlock(settings);
    }
}
