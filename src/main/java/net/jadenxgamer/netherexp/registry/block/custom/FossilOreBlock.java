package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class FossilOreBlock extends Block {

    public FossilOreBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState topState = world.getBlockState(pos.up());
        float r = random.nextInt(NetherExp.getConfig().blocks.fossilOreConfigs.fossil_ore_conversion_odds);
        if (NetherExp.getConfig().blocks.fossilOreConfigs.enable_fossil_ore_conversion && r == 0 && topState.isSolid()) {
            world.setBlockState(pos, ModBlocks.FOSSIL_FUEL_ORE.getDefaultState(), NOTIFY_LISTENERS);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }
}
