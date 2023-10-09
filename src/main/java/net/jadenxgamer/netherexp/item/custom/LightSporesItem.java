package net.jadenxgamer.netherexp.item.custom;

import net.jadenxgamer.netherexp.block.ModBlocks;
import net.jadenxgamer.netherexp.block.custom.DecayableWartBlock;
import net.jadenxgamer.netherexp.block.custom.SpottedWartBlock;
import net.jadenxgamer.netherexp.particle.ModParticles;
import net.jadenxgamer.netherexp.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class LightSporesItem extends Item {
    public LightSporesItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // Player
        PlayerEntity player = context.getPlayer();
        ItemStack itemStack = context.getStack();
        // Block
        BlockPos pos;
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos = context.getBlockPos());
        Block block = state.getBlock();
        SpottedWartBlock spottedWartBlock;
        DecayableWartBlock decayableWartBlock;
        /*
          Changes Wart Block to Spotted Wart Block
        */
        if (state.isOf(Blocks.NETHER_WART_BLOCK)) {
            world.setBlockState(pos, ModBlocks.SPOTTED_NETHER_WART_BLOCK.getDefaultState());
            //TODO: Make a custom sound effect for this
            world.playSound(player, pos.getX(), pos.getY(), pos.getZ(), ModSoundEvents.LIGHTSPORES_APPLY, SoundCategory.BLOCKS, 1.0f, 1.0f);
            sporeParticles(world, pos);

            if (player != null) {
                itemStack.decrement(1);
            }
            return ActionResult.success(world.isClient);
        }
        /*
          Increases Spotted Wart Block's Spots
        */
        else if (state.isOf(ModBlocks.SPOTTED_NETHER_WART_BLOCK) && !(spottedWartBlock = (SpottedWartBlock) block).maxSpots(state)) {
            world.setBlockState(pos, spottedWartBlock.setSpots(state));
            world.playSound(player, pos.getX(), pos.getY(), pos.getZ(), ModSoundEvents.LIGHTSPORES_APPLY, SoundCategory.BLOCKS, 1.0f, 1.0f);
            sporeParticles(world, pos);

            if (player != null) {
                itemStack.decrement(1);
            }
            return ActionResult.success(world.isClient);
        }
        /*
          Increases Decayable Wart Block's Spots
        */
        else if (state.isOf(ModBlocks.DECAYABLE_NETHER_WART_BLOCK) && !(decayableWartBlock = (DecayableWartBlock) block).maxSpots(state)) {
            world.setBlockState(pos, decayableWartBlock.setSpots(state));
            world.playSound(player, pos.getX(), pos.getY(), pos.getZ(), ModSoundEvents.LIGHTSPORES_APPLY, SoundCategory.BLOCKS, 1.0f, 1.0f);
            sporeParticles(world, pos);

            if (player != null) {
                itemStack.decrement(1);
            }
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }

    public static void sporeParticles(World world, BlockPos pos) {
        Random random = world.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            if (world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double)direction.getOffsetX() : (double)random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double)direction.getOffsetY() : (double)random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double)direction.getOffsetZ() : (double)random.nextFloat();
            world.addParticle(ModParticles.FALLING_SHROOMLIGHT, (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.0, 0.0);
        }
    }
}
