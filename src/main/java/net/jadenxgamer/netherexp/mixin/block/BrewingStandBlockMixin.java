package net.jadenxgamer.netherexp.mixin.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import static net.jadenxgamer.netherexp.util.CommonParticles.brewingStandParticle;

@Mixin(BrewingStandBlock.class)
public class BrewingStandBlockMixin {

    @WrapMethod(method = "animateTick")
    private void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, Operation<Void> original) {
        if (JNEConfigs.IMPROVED_BREWING_STAND_PARTICLES.get()) {
            double x = (double)pos.getX() + 0.4 + (double)random.nextFloat() * 0.2;
            double y = (double)pos.getY() + 0.7 + (double)random.nextFloat() * 0.3;
            double z = (double)pos.getZ() + 0.4 + (double)random.nextFloat() * 0.2;
            brewingStandParticle(level, pos, random, x, y, z);
        } else original.call(state, level, pos, random);
    }
}
