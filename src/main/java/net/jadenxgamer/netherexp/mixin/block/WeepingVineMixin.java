package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.particle.ModParticles;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WeepingVinesBlock.class)
public class WeepingVineMixin extends AbstractPlantStemBlock {
    public WeepingVineMixin(Settings settings, Direction growthDirection, VoxelShape outlineShape, boolean tickWater, double growthChance) {
        super(settings, growthDirection, outlineShape, tickWater, growthChance);
    }

    @Override
    public int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    @Override
    public boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }

    @Override
    public Block getPlant() {
        return Blocks.WEEPING_VINES_PLANT;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int age = state.get(AGE);
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() - 0.05;
        double z = (double)pos.getZ() + random.nextDouble();
        if (age >= 25) {
            world.addParticle(ModParticles.FALLING_SHROOMLIGHT, x, y, z, 0.0, 0.0, 0.0);
        }
    }
}
