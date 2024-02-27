package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
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
        float r = random.nextInt(NetherExp.getConfig().blocks.fossilOreConfigs.fossil_ore_conversion_odds);
        boolean sideChecks = world.getBlockState(pos.up()).isSolid() &&
                world.getBlockState(pos.down()).isSolid() &&
                world.getBlockState(pos.north()).isSolid() &&
                world.getBlockState(pos.south()).isSolid() &&
                world.getBlockState(pos.east()).isSolid() &&
                world.getBlockState(pos.west()).isSolid();
        if (NetherExp.getConfig().blocks.fossilOreConfigs.enable_fossil_ore_conversion && r == 0 && sideChecks) {
            world.setBlockState(pos, JNEBlocks.FOSSIL_FUEL_ORE.getDefaultState(), NOTIFY_LISTENERS);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }
}
