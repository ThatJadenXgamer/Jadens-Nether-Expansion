package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.elysium_api.api.tags.ElysiumTags;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TreacherousFireBlock extends BaseFireBlock {
    public static final MapCodec<TreacherousFireBlock> CODEC = simpleCodec(TreacherousFireBlock::new);

    public TreacherousFireBlock(Properties properties) {
        super(properties, 0.0f);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos belowPos = context.getClickedPos().below();
        BlockState belowState = level.getBlockState(belowPos);
        return SoulFireBlock.canSurviveOnBlock(belowState) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return this.canSurvive(state, level, pos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (state.is(ElysiumTags.Blocks.NON_SOLID_FIRE_SUPPORT)) {
            return canSurviveOnBlock(belowState);
        } else return canSurviveOnBlock(belowState) && belowState.isFaceSturdy(level, belowPos, Direction.UP);
    }

    public static boolean canSurviveOnBlock(BlockState state) {
        return state.is(JNETags.Blocks.TREACHEROUS_FIRE_BASE_BLOCKS);
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
        if (!(entity instanceof LivingEntity living)) return;
        if (living.getHealth() <= living.getMaxHealth() / 3) entity.hurt(level.damageSources().inFire(), 8);
        living.setRemainingFireTicks(18);
    }
}
