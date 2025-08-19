package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NetherFlowerBlock extends FlowerBlock {
    public NetherFlowerBlock(Holder<MobEffect> effect, float seconds, Properties properties) {
        super(effect, seconds, properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
        return floor.is(JNETags.Blocks.SOUL_CROP_MUTATION_BLOCKS) || floor.is(BlockTags.NYLIUM) || floor.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(floor, level, pos);
    }
}
