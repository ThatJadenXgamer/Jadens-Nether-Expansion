package net.jadenxgamer.netherexp.registry.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.LiquidloggedGrateBlock;
import net.jadenxgamer.netherexp.registry.block.custom.Liquids;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class LiquidloggedBucketEvent implements UseBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockState state = world.getBlockState(hitResult.getBlockPos());
        ItemStack itemStack = player.getStackInHand(hand);
        boolean bl = false;
        if (itemStack.isOf(Items.LAVA_BUCKET) && state.isIn(JNETags.Blocks.NETHERITE_GRATES) && state.get(LiquidloggedGrateBlock.LIQUIDLOGGED) == Liquids.AIR) {
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.setBlockState(hitResult.getBlockPos(), state.with(LiquidloggedGrateBlock.LIQUIDLOGGED, Liquids.LAVA), Block.NOTIFY_LISTENERS);
            if (!player.isCreative()) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(itemStack, player, new ItemStack(Items.BUCKET)));
            }
            bl = true;
        }
        else if (itemStack.isOf(JNEFluids.ECTOPLASM_BUCKET) && state.isIn(JNETags.Blocks.NETHERITE_GRATES) && state.get(LiquidloggedGrateBlock.LIQUIDLOGGED) == Liquids.AIR) {
            world.playSound(player, player.getX(), player.getY(), player.getZ(), JNESoundEvents.ITEM_BUCKET_EMPTY_ECTOPLASM, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SOUL_SLATE_SOLIDIFYING, SoundCategory.BLOCKS, 0.5f, 1.0f);
            world.setBlockState(hitResult.getBlockPos(), JNEBlocks.RUSTY_NETHERITE_GRATE.getDefaultState().with(LiquidloggedGrateBlock.LIQUIDLOGGED, Liquids.ECTOPLASM), Block.NOTIFY_LISTENERS);
            if (!player.isCreative()) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(itemStack, player, new ItemStack(Items.BUCKET)));
            }
            bl = true;
        }
        if (bl) {
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
