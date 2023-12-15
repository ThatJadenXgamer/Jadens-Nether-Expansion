package net.jadenxgamer.netherexp.registry.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class NyliumPathEvent implements UseBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockState state = world.getBlockState(hitResult.getBlockPos());
        BlockState upState = world.getBlockState(hitResult.getBlockPos().up());
        ItemStack itemStack = player.getStackInHand(hand);
        boolean bl = false;
        if (NetherExp.getConfig().blocks.pathBlockConfigs.enable_nylium_path_blocks && itemStack.isIn(ItemTags.SHOVELS) && upState.isAir()) {
            if (state.isOf(Blocks.CRIMSON_NYLIUM)) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(hitResult.getBlockPos(), ModBlocks.CRIMSON_NYLIUM_PATH.getDefaultState(), Block.NOTIFY_LISTENERS);
                if (!player.isCreative()) {
                    itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                }
                bl = true;
            }
            if (state.isOf(Blocks.WARPED_NYLIUM)) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(hitResult.getBlockPos(), ModBlocks.WARPED_NYLIUM_PATH.getDefaultState(), Block.NOTIFY_LISTENERS);
                if (!player.isCreative()) {
                    itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                }
                bl = true;
            }
            if (state.isIn(ModTags.Blocks.UMBRAL_NYLIUM) && NetherExp.checkModCompatCinderscapes()) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(hitResult.getBlockPos(), ModBlocks.UMBRAL_NYLIUM_PATH.getDefaultState(), Block.NOTIFY_LISTENERS);
                if (!player.isCreative()) {
                    itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                }
                bl = true;
            }
        }
        if (bl) {
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
