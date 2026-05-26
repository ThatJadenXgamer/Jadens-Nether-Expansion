package net.jadenxgamer.netherexp.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jadenxgamer.netherexp.core.block.interfaces.IElysiumBlockExtension;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonStructureResolver.class)
public abstract class PistonStructureResolverMixin {
    @Shadow @Final private Level level;
    @Shadow @Final private Direction pushDirection;

    @WrapOperation(
            method = "addBlockLine",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;canStickTo(Lnet/minecraft/world/level/block/state/BlockState;)Z",
                    ordinal = 0
            )
    )
    private boolean netherexp$wrapBlockLineStick1(BlockState oldState, BlockState blockstate, Operation<Boolean> original) {
        Direction face = this.pushDirection.getOpposite();
        return ((IElysiumBlockExtension) oldState.getBlock()).canStickToFace(oldState, blockstate, face, face.getOpposite());
    }

    @WrapOperation(
            method = "addBlockLine",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;canStickTo(Lnet/minecraft/world/level/block/state/BlockState;)Z",
                    ordinal = 1
            )
    )
    private boolean netherexp$wrapBlockLineStick2(BlockState blockstate, BlockState oldState, Operation<Boolean> original) {
        Direction face = this.pushDirection;
        return ((IElysiumBlockExtension) blockstate.getBlock()).canStickToFace(blockstate, oldState, face, face.getOpposite());
    }

    @WrapOperation(
            method = "addBranchingBlocks",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;canStickTo(Lnet/minecraft/world/level/block/state/BlockState;)Z",
                    ordinal = 0
            )
    )
    private boolean netherexp$wrapBranchingStick1(BlockState blockstate1, BlockState blockstate, Operation<Boolean> original, @Local Direction direction) {
        Direction face = direction.getOpposite();
        return ((IElysiumBlockExtension) blockstate1.getBlock()).canStickToFace(blockstate1, blockstate, face, face.getOpposite());
    }

    @WrapOperation(
            method = "addBranchingBlocks",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;canStickTo(Lnet/minecraft/world/level/block/state/BlockState;)Z",
                    ordinal = 1
            )
    )
    private boolean netherexp$wrapBranchingStick2(BlockState blockstate, BlockState blockstate1, Operation<Boolean> original, @Local Direction face) {
        return ((IElysiumBlockExtension) blockstate.getBlock()).canStickToFace(blockstate, blockstate1, face, face.getOpposite());
    }
}