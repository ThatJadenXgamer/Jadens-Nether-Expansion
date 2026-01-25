package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.elysium_api.api.tags.ElysiumTags;
import net.jadenxgamer.netherexp.core.block.interfaces.JNEFireParticle;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.Optional;

public class AncientFireBlock extends BaseFireBlock implements JNEFireParticle {
    public static final MapCodec<AncientFireBlock> CODEC = simpleCodec(AncientFireBlock::new);

    private static final Color[] ANCIENT_SMOKE_COLORS = {
            new Color(0x230303),
            new Color(0x290404),
            new Color(0x370505),
            new Color(0x420707)
    };

    public AncientFireBlock(Properties properties) {
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

    @Override
    public Color smokeStartColor(BlockState state, RandomSource random) {
        return new Color(0xFF0000);
    }

    @Override
    public Color smokeEndColor(BlockState state, RandomSource random) {
        return ANCIENT_SMOKE_COLORS[random.nextInt(ANCIENT_SMOKE_COLORS.length)];
    }

    @Override
    public Optional<Color> emberColor(BlockState state, RandomSource random) {
        return Optional.of(new Color(0xFF2E35));
    }
}
