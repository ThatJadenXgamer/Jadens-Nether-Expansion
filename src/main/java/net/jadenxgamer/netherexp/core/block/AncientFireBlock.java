package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.elysium_api.api.tags.ElysiumTags;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AncientFireBlock extends BaseFireBlock {
    public static final MapCodec<AncientFireBlock> CODEC = simpleCodec(AncientFireBlock::new);

    public AncientFireBlock(Properties properties) {
        super(properties, 0.0f);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (!state.is(ElysiumTags.Blocks.NON_SOLID_FIRE_SUPPORT) && !belowState.isFaceSturdy(level, belowPos, Direction.UP)) return false;
        return canSurviveOnBlock(belowState);
    }

    public boolean canSurviveOnBlock(BlockState state) {
        return state.is(JNETags.Blocks.ANCIENT_FIRE_BASE_BLOCKS);
    }

    @Override
    protected MapCodec<? extends BaseFireBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean canBurn(BlockState state) {
        return true;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
    }
}
