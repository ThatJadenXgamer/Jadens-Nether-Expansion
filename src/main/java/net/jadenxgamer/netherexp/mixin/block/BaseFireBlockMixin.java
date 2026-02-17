package net.jadenxgamer.netherexp.mixin.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jadenxgamer.netherexp.client.assetdriven.FireParticles;
import net.jadenxgamer.netherexp.core.block.AncientFireBlock;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
import static net.jadenxgamer.netherexp.util.ParticleHelper.emberParticle;
import static net.jadenxgamer.netherexp.util.ParticleHelper.smokeParticle;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin extends Block {

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
        if (AncientFireBlock.canSurviveOnBlock(state))
            cir.setReturnValue(JNEBlocks.ANCIENT_FIRE.get().defaultBlockState());
    }

    @WrapMethod(
            method = "animateTick"
    )
    private void netherexp$betterFireParticles(BlockState state, Level level, BlockPos pos, RandomSource random, Operation<Void> original) {
        if (IMPROVED_FIRE_PARTICLES.get()) {
            if (random.nextInt(24) == 0) level.playLocalSound(
                    (double) pos.getX() + (double) 0.5F, (double) pos.getY() + (double) 0.5F, (double) pos.getZ() + (double) 0.5F,
                    SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            ResourceLocation blockId = level.registryAccess().registryOrThrow(Registries.BLOCK).getKey(state.getBlock());

            if (FIRE_SMOKE_PARTICLES.get()) {
                FireParticles particles = FireParticles.FIRE_PARTICLES.getOrDefault(blockId, FireParticles.DEFAULT);
                Color smokeStart = particles.smokeStartColors()[random.nextInt(particles.smokeStartColors().length)];
                Color smokeEnd = particles.smokeEndColors()[random.nextInt(particles.smokeEndColors().length)];

                smokeParticle(level, random, pos.getX(), pos.getY(), pos.getZ(), smokeStart, smokeEnd);
            }
            if (FIRE_EMBER_PARTICLES.get()) {
                FireParticles particles = FireParticles.FIRE_PARTICLES.getOrDefault(blockId, FireParticles.DEFAULT);
                if (particles != null && particles.emberColors().length > 0) {
                    Color ember = particles.emberColors()[random.nextInt(particles.emberColors().length)];
                    emberParticle(level, random, pos.getX(), pos.getY(), pos.getZ(), ember);
                }
            }
        } else original.call(state, level, pos, random);
    }
}
