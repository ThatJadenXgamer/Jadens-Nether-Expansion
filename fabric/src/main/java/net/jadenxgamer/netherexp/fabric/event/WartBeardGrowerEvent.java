package net.jadenxgamer.netherexp.fabric.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class WartBeardGrowerEvent implements UseBlockCallback {

    @Override
    public InteractionResult interact(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        BlockState state = level.getBlockState(hitResult.getBlockPos());
        BlockState bottomState = level.getBlockState(hitResult.getBlockPos().below());
        ItemStack stack = player.getItemInHand(hand);
        boolean success = false;

        if (stack.is(Items.BONE_MEAL)) {
            if (state.is(JNETags.Blocks.NETHER_WART_BLOCKS) && bottomState.isAir()) {
                level.setBlock(hitResult.getBlockPos().below(), JNEBlocks.NETHER_WART_BEARD.get().defaultBlockState(), Block.UPDATE_ALL);
                success = true;
            }
            else if (state.is(JNETags.Blocks.WARPED_WART_BLOCKS) && bottomState.isAir()) {
                level.setBlock(hitResult.getBlockPos().below(), JNEBlocks.WARPED_WART_BEARD.get().defaultBlockState(), Block.UPDATE_ALL);
                success = true;
            }
        }

        if (success) {
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
            boneMealParticles(level, hitResult.getBlockPos().below());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static void boneMealParticles(Level level, BlockPos pos) {
        RandomSource random = level.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.relative(direction);
            if (!level.getBlockState(blockPos).isCollisionShapeFullBlock(level, blockPos) && random.nextInt(120) == 0) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat();
                level.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }
    }
}