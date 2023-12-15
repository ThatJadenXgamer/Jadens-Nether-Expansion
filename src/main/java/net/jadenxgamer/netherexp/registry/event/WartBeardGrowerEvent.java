package net.jadenxgamer.netherexp.registry.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class WartBeardGrowerEvent implements UseBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockState state = world.getBlockState(hitResult.getBlockPos());
        BlockState bottomState = world.getBlockState(hitResult.getBlockPos().down());
        ItemStack itemStack = player.getStackInHand(hand);
        boolean bl = false;
        if (NetherExp.getConfig().blocks.renewableConfigs.wart_beards_from_wart_blocks && itemStack.isOf(Items.BONE_MEAL)) {
            if (state.isIn(ModTags.Blocks.NETHER_WART_BLOCKS) && bottomState.isAir()) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(hitResult.getBlockPos().down(), ModBlocks.NETHER_WART_BEARD.getDefaultState(), Block.NOTIFY_LISTENERS);
                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
                boneMealParticles(world, hitResult.getBlockPos().down());
                bl = true;
            }
            else if (state.isIn(ModTags.Blocks.WARPED_WART_BLOCKS) && bottomState.isAir()) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(hitResult.getBlockPos().down(), ModBlocks.WARPED_WART_BEARD.getDefaultState(), Block.NOTIFY_LISTENERS);
                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
                boneMealParticles(world, hitResult.getBlockPos().down());
                bl = true;
            }
            else if (NetherExp.checkModCompatCinderscapes() && state.isIn(ModTags.Blocks.UMBRAL_WART_BLOCKS) && bottomState.isAir()) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(hitResult.getBlockPos().down(), ModBlocks.UMBRAL_WART_BEARD.getDefaultState(), Block.NOTIFY_LISTENERS);
                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
                boneMealParticles(world, hitResult.getBlockPos().down());
                bl = true;
            }
        }
        if (bl) {
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
    private static void boneMealParticles(World world, BlockPos pos) {
        Random random = world.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            if (world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double)direction.getOffsetX() : (double)random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double)direction.getOffsetY() : (double)random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double)direction.getOffsetZ() : (double)random.nextFloat();
            world.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.0, 0.0);
        }
    }
}
