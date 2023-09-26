package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.particle.ModParticles;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TwistingVinesBlock.class)
public abstract class TwistingVineMixin
extends AbstractPlantStemBlock {

    private static final BooleanProperty DRIPPING = BooleanProperty.of("dripping");

    public TwistingVineMixin(Settings settings, Direction growthDirection, VoxelShape outlineShape, boolean tickWater, double growthChance) {
        super(settings, growthDirection, outlineShape, tickWater, growthChance);
        setDefaultState(this.stateManager.getDefaultState().with(DRIPPING, false).with(AGE, 0));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        boolean d = state.get(DRIPPING);
        float f = random.nextFloat();
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() + 0.8;
        double z = (double)pos.getZ() + random.nextDouble();
        if (d && f < 0.3) {
            world.addParticle(ModParticles.RISING_SHROOMNIGHT, x, y, z, MathHelper.nextDouble(random, -0.02, 0.02), 0.08, MathHelper.nextDouble(random, -0.02, 0.02));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DRIPPING, AGE);
    }
}
