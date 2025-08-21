package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class JNEHorizontalDirectionalBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<JNEHorizontalDirectionalBlock> CODEC = simpleCodec(JNEHorizontalDirectionalBlock::new);

    public JNEHorizontalDirectionalBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
