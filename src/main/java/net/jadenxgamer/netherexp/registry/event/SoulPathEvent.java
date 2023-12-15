package net.jadenxgamer.netherexp.registry.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
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

public class SoulPathEvent implements UseBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockState state = world.getBlockState(hitResult.getBlockPos());
        BlockState upState = world.getBlockState(hitResult.getBlockPos().up());
        ItemStack itemStack = player.getStackInHand(hand);
        boolean bl = false;
        if (NetherExp.getConfig().blocks.pathBlockConfigs.enable_soul_path_blocks && itemStack.isIn(ItemTags.SHOVELS) && upState.isAir()) {
            if (state.isOf(Blocks.SOUL_SOIL)) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(hitResult.getBlockPos(), ModBlocks.SOUL_PATH.getDefaultState(), Block.NOTIFY_LISTENERS);
                if (!player.isCreative()) {
                    itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                }
                bl = true;
            }
            if (state.isOf(Blocks.SOUL_SAND)) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(hitResult.getBlockPos(), ModBlocks.SOUL_PATH.getDefaultState(), Block.NOTIFY_LISTENERS);
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
