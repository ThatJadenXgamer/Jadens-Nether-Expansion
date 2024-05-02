package net.jadenxgamer.netherexp.fabric.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class UseBlockCallbackEvent implements UseBlockCallback {

    @Override
    public InteractionResult interact(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        BlockState state = level.getBlockState(hitResult.getBlockPos());
        BlockState aboveState = level.getBlockState(hitResult.getBlockPos().above());
        ItemStack stack = player.getItemInHand(hand);
        boolean bl = false;
        if (stack.is(ItemTags.SHOVELS) && aboveState.isAir()) {
            if (state.is(Blocks.CRIMSON_NYLIUM)) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(hitResult.getBlockPos(), JNEBlocks.CRIMSON_NYLIUM_PATH.get().defaultBlockState(), 2);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }
                bl = true;
            }
            else if (state.is(Blocks.WARPED_NYLIUM)) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(hitResult.getBlockPos(), JNEBlocks.WARPED_NYLIUM_PATH.get().defaultBlockState(), 2);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }
                bl = true;
            }
        }
        else if (stack.is(ItemTags.AXES)) {
            if (state.is(JNEBlocks.CLARET_STEM.get())) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(hitResult.getBlockPos(), JNEBlocks.STRIPPED_CLARET_STEM.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }
                bl = true;
            }
            else if (state.is(JNEBlocks.CLARET_HYPHAE.get())) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(hitResult.getBlockPos(), JNEBlocks.STRIPPED_CLARET_HYPHAE.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }
                bl = true;
            }
            else if (state.is(JNEBlocks.SMOKESTALK_BLOCK.get())) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(hitResult.getBlockPos(), JNEBlocks.STRIPPED_SMOKESTALK_BLOCK.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }
                bl = true;
            }
        }
        if (bl) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
