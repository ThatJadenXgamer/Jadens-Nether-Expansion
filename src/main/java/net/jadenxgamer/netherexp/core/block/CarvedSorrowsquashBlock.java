package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.core.worldgen.feature.JNEConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class CarvedSorrowsquashBlock extends HorizontalDirectionalBlock implements BonemealableBlock {

    public static final MapCodec<CarvedSorrowsquashBlock> CODEC = simpleCodec(CarvedSorrowsquashBlock::new);

    public CarvedSorrowsquashBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return JNEConfigs.SORROWSQUASH_TAINTING.get() && level.getBlockState(pos.below()).is(JNETags.Blocks.SORROWEED_REPLACEABLE);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((registry) -> registry.getHolder(JNEConfiguredFeatures.SORROWEED_PATCH_BONEMEAL)).ifPresent((reference) ->
                reference.value().place(level, level.getChunkSource().getGenerator(), random, pos));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
