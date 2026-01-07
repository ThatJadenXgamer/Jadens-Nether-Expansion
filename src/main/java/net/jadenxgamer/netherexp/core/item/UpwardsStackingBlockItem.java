package net.jadenxgamer.netherexp.core.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class UpwardsStackingBlockItem extends BlockItem {

    public UpwardsStackingBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @Nullable BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState state = level.getBlockState(pos);

        if (!state.is(this.getBlock())) return context;

        BlockPos currentPos = pos;
        while (currentPos.getY() < level.getMaxBuildHeight() && level.getBlockState(currentPos.above()).is(this.getBlock())) {
            currentPos = currentPos.above();
        }

        return level.getBlockState(currentPos.above()).canBeReplaced() ? BlockPlaceContext.at(context, currentPos.above(), Direction.UP) : null;
    }
}