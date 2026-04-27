package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SiltMarramBlock extends RootsBlock {

    private final TagKey<Biome> homeBiomes;

    public SiltMarramBlock(TagKey<Biome> homeBiomes, Properties properties) {
        super(properties);
        this.homeBiomes = homeBiomes;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        var biome = level.getBiome(pos);
        if (biome.is(homeBiomes)) return;

        BlockState targetState = JNEBlocks.MOIST_SILTMARRAM.get().defaultBlockState();
        if (biome.is(JNETags.Biomes.SPAWNS_DAMP_VARIANT_STRIDERS_AND_SILTMARRAM)) targetState = JNEBlocks.DAMP_SILTMARRAM.get().defaultBlockState();
        if (biome.is(JNETags.Biomes.SPAWNS_DRY_VARIANT_STRIDERS_AND_SILTMARRAM)) targetState = JNEBlocks.DRY_SILTMARRAM.get().defaultBlockState();

        if (!state.equals(targetState)) level.setBlock(pos, targetState, UPDATE_ALL);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(JNETags.Blocks.SILTMARRAM_PLANTABLE_ON);
    }
}