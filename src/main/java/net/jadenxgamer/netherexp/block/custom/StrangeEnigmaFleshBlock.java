package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.particle.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class StrangeEnigmaFleshBlock extends ModFacingBlock{
    public StrangeEnigmaFleshBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        VoxelShape voxelShape = this.getOutlineShape(state, world, pos, ShapeContext.absent());
        Vec3d vec3d = voxelShape.getBoundingBox().getCenter();
        double d = (double)pos.getX() + vec3d.x;
        double f = (double)pos.getY() + 1.1;
        double e = (double)pos.getZ() + vec3d.z;
        for (int i = 0; i < 3; ++i) {
            if (!random.nextBoolean()) continue;
            world.addParticle(ModParticles.ENIGMA_PARTICLE, d + random.nextDouble() / 5.0, f, e + random.nextDouble() / 5.0, 0.0, 0.0, 0.0);
        }
    }
}
