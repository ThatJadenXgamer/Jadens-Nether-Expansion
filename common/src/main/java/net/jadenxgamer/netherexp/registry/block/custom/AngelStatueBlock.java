package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

public class AngelStatueBlock extends GargoyleStatueBlock {
    public static final BooleanProperty SALTED = BooleanProperty.create("salted");

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = EnumProperty.create("half", DoubleBlockHalf.class);

    public AngelStatueBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SALTED, false).setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        if (!blockState.getValue(SALTED) && blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            Direction direction = Direction.getRandom(randomSource);
            BlockPos directionPos = pos.offset(direction.getNormal());
            BlockState directionState = level.getBlockState(directionPos);
            BlockState directionAboveState = level.getBlockState(directionPos.above());
            if (directionState.isAir() && directionAboveState.isAir()) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
                level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 0);
                level.setBlock(directionPos, this.defaultBlockState(), 2);
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }
}
