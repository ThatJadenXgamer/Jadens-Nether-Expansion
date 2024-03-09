package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.particle.JNEParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MagmaCreamBlock extends Block {
    public MagmaCreamBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            entity.slowMovement(state, new Vec3d(0.8999999761581421, 0.5, 0.8999999761581421));

            if (!world.isClient) {
                if (entity.isOnFire() && entity.canModifyAt(world, pos)) {
                    world.playSound(entity, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    entity.setOnFire(false);
                    entity.setFireTicks(0);
                }
            }
            if (world.isClient) {
                boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                Random random = world.getRandom();
                if (bl && random.nextBoolean()) {
                    world.addParticle(JNEParticles.MAGMA_CREAM, entity.getX(), pos.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }
}
