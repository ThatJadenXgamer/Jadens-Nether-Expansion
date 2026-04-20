package net.jadenxgamer.netherexp.mixin.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.WeakHashMap;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {

    @Unique private static final WeakHashMap<Level, Long> LAST_PORTAL_BREAK_SOUND_TIME = new WeakHashMap<>();

    @WrapMethod(method = "animateTick")
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, Operation<Void> original) {
        if (JNEConfigs.IMPROVED_NETHER_PORTAL_PARTICLES.get()) {
            if (random.nextInt(100) == 0) {
                level.playLocalSound(
                        (double) pos.getX() + 0.5,
                        (double) pos.getY() + 0.5,
                        (double) pos.getZ() + 0.5,
                        SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,
                        0.5F, random.nextFloat() * 0.4F + 0.8F, false
                );
            }
            CommonParticles.netherPortalParticle(state, level, pos, random);
        } else original.call(state, level, pos, random);
    }

    @Inject(
            method = "updateShape",
            at = @At(value = "RETURN", ordinal = 0)
    )
    private void updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor levelAccessor, BlockPos currentPos, BlockPos facingPos, CallbackInfoReturnable<BlockState> cir) {
        if (levelAccessor.isClientSide()) return;
        if (!(levelAccessor instanceof Level level)) return;
        long gameTime = level.getGameTime();
        BlockState newState = cir.getReturnValue();
        if (newState.isAir() && state.getBlock() instanceof NetherPortalBlock) {
            Long lastPlayed = LAST_PORTAL_BREAK_SOUND_TIME.get(level);
            if (lastPlayed == null || gameTime - lastPlayed > 20) {
                LAST_PORTAL_BREAK_SOUND_TIME.put(level, gameTime);
                level.playSound(null, currentPos, JNESoundEvents.NETHER_PORTAL_BREAK.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }
}
