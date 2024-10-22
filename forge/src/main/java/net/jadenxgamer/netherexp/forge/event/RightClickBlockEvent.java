package net.jadenxgamer.netherexp.forge.event;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class RightClickBlockEvent {

    public static void WartBeardGrowerEvent(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        BlockState bottomState = level.getBlockState(pos.below());
        ItemStack stack = event.getItemStack();
        boolean success = false;

        if (stack.is(Items.BONE_MEAL)) {
            if (state.is(JNETags.Blocks.NETHER_WART_BLOCKS) && bottomState.isAir()) {
                level.setBlock(pos.below(), JNEBlocks.NETHER_WART_BEARD.get().defaultBlockState(), Block.UPDATE_ALL);
                success = true;
            }
            else if (state.is(JNETags.Blocks.WARPED_WART_BLOCKS) && bottomState.isAir()) {
                level.setBlock(pos.below(), JNEBlocks.WARPED_WART_BEARD.get().defaultBlockState(), Block.UPDATE_ALL);
                success = true;
            }
        }

        if (success) {
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
            boneMealParticles(level, pos.below());
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
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
