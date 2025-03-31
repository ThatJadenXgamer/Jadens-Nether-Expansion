package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.AncientFireBlock;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {

    @Inject(
            method = "animateTick",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if (((BaseFireBlock) (Object) this) instanceof SoulFireBlock && JNEConfigs.IMPROVED_SOUL_FIRE_PARTICLES.get()) {
            if (pRandom.nextInt(24) == 0) {
                pLevel.playLocalSound((double)pPos.getX() + 0.5, (double)pPos.getY() + 0.5, (double)pPos.getZ() + 0.5, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.3F, false);
            }
            pLevel.addParticle(JNEParticleTypes.SOUL_EMBER.get(), (double)pPos.getX() + 0.5 + pRandom.nextDouble() / 4.0 * (double)(pRandom.nextBoolean() ? 1 : -1), (double)pPos.getY() + 0.6, (double)pPos.getZ() + 0.5 + pRandom.nextDouble() / 4.0 * (double)(pRandom.nextBoolean() ? 1 : -1), 0.0, 0.015, 0.0);
            ci.cancel();
        }
    }

    @Inject(
            method = "getState",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$getState(BlockGetter pReader, BlockPos pPos, CallbackInfoReturnable<BlockState> cir) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pReader.getBlockState(blockpos);
        if (AncientFireBlock.canSurviveOnBlock(blockstate)) {
            cir.setReturnValue(JNEBlocks.ANCIENT_FIRE.get().defaultBlockState());
        }
    }
}
