package net.jadenxgamer.netherexp.mixin.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jadenxgamer.netherexp.core.block.AncientFireBlock;
import net.jadenxgamer.netherexp.core.block.interfaces.JNEFireParticle;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;
import static net.jadenxgamer.netherexp.util.ParticleHelper.*;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin extends Block implements JNEFireParticle {

    private static final Color[] SMOKE_COLORS = {
            new Color(0x515151),
            new Color(0x575757),
            new Color(0x676767),
            new Color(0x717171)
    };

    public BaseFireBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "getState",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$getState(BlockGetter reader, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = reader.getBlockState(pos.below());
        if (AncientFireBlock.canSurviveOnBlock(state)) cir.setReturnValue(JNEBlocks.ANCIENT_FIRE.get().defaultBlockState());
    }

    @WrapMethod(
            method = "animateTick"
    )
    private void netherexp$betterFireParticles(BlockState state, Level level, BlockPos pos, RandomSource random, Operation<Void> original) {
        if (IMPROVED_FIRE_SMOKE.get()) {
            if (random.nextInt(24) == 0) level.playLocalSound(
                    (double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F,
                    SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            smokeParticle(level, random, pos.getX(), pos.getY(), pos.getZ(), smokeStartColor(state, random), smokeEndColor(state, random));
            if (emberColor(state, random).isPresent()) emberParticle(level, random, pos.getX(), pos.getY(), pos.getZ(), emberColor(state, random).get());
        } else original.call(state, level, pos, random);
    }

    @Override
    public Color smokeStartColor(BlockState state, RandomSource random) {
        return smokeEndColor(state, random);
    }

    @Override
    public Color smokeEndColor(BlockState state, RandomSource random) {
        return SMOKE_COLORS[random.nextInt(SMOKE_COLORS.length)];
    }
}
