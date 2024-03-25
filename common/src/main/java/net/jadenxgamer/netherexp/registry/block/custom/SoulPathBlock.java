package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class SoulPathBlock
extends DirtPathBlock {
    public SoulPathBlock(Properties properties) {
        super(properties);
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return !this.defaultBlockState().canSurvive(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos()) ? Block.pushEntitiesUp(this.defaultBlockState(), Blocks.SOUL_SOIL.defaultBlockState(), blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos()) : super.getStateForPlacement(blockPlaceContext);
    }

    public static void revertToBase(@Nullable Entity entity, BlockState blockState, Level level, BlockPos blockPos) {
        BlockState blockstate = pushEntitiesUp(blockState, Blocks.SOUL_SOIL.defaultBlockState(), level, blockPos);
        level.setBlockAndUpdate(blockPos, blockstate);
        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(entity, blockstate));
    }

    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        revertToBase(null, blockState, serverLevel, blockPos);
    }
}
