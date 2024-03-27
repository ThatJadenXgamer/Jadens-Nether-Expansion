package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class VineStemPlantBlock
extends GrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

    private final StemGrownBlock stemGrownBlock;
    private final Supplier<Item> pickBlockItem;

    public VineStemPlantBlock(StemGrownBlock stemGrownBlock, Supplier<Item> pickBlockItem, Properties properties) {
        super(properties, Direction.UP, SHAPE, false);
        this.stemGrownBlock = stemGrownBlock;
        this.pickBlockItem = pickBlockItem;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockPos = pos.offset(this.growthDirection.getOpposite().getNormal());
        BlockState blockState = level.getBlockState(blockPos);
        if (!this.canAttachTo(blockState)) {
            return false;
        } else {
            return blockState.is(this.getHeadBlock()) || blockState.is(this.getBodyBlock()) || blockState.is(JNETags.Blocks.SOUL_SAND_BLOCKS);
        }
    }

    @Override
    protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) JNEBlocks.SORROWSQUASH_STEM.get();
    }
}

