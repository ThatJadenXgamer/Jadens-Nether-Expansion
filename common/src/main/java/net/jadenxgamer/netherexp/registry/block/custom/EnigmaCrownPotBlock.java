package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnigmaCrownPotBlock extends FlowerPotBlock {
    public EnigmaCrownPotBlock(Block content, Properties properties) {
        super(content, properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        VoxelShape voxelShape = this.getShape(state, level, pos, CollisionContext.empty());
        Vec3 vec3 = voxelShape.bounds().getCenter();
        double d = (double)pos.getX() + vec3.x;
        double e = (double)pos.getZ() + vec3.z;
        for (int i = 0; i < 3; ++i) {
            if (!random.nextBoolean()) continue;
            //TODO: Add Particles
            //level.addParticle(JNEParticles.ENIGMA_PARTICLE, d + random.nextDouble() / 5.0, (double)pos.getY() + (0.5 - random.nextDouble()), e + random.nextDouble() / 5.0, 0.0, 0.0, 0.0);
        }
    }
}
