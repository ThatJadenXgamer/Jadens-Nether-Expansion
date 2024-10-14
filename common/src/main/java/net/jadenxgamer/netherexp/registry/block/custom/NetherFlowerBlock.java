package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NetherFlowerBlock extends FlowerBlock {

    public NetherFlowerBlock(MobEffect mobEffect, int i, Properties properties) {
        super(mobEffect, i, properties);
    }

    @Override
    public boolean canSurvive(BlockState floor, LevelReader level, BlockPos pos) {
        return floor.is(JNETags.Blocks.SOUL_SAND_BLOCKS) || floor.is(BlockTags.NYLIUM) || floor.is(Blocks.SOUL_SOIL) || super.canSurvive(floor, level, pos);
    }
}
