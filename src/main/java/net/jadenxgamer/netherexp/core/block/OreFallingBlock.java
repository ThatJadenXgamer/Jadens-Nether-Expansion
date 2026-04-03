package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class OreFallingBlock extends FallingBlock {

    private final IntProvider xpRange;
    private final ColorRGBA dustColor;
    public static final MapCodec<OreFallingBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    IntProvider.codec(0, 10).fieldOf("experience").forGetter(block -> block.xpRange),
                    ColorRGBA.CODEC.fieldOf("falling_dust_color").forGetter(block -> block.dustColor),
            propertiesCodec()
            ).apply(instance, OreFallingBlock::new)
    );

    public OreFallingBlock(IntProvider xpRange, ColorRGBA dustColor, BlockBehaviour.Properties properties) {
        super(properties);
        this.xpRange = xpRange;
        this.dustColor = dustColor;
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelAccessor level, BlockPos pos, @Nullable net.minecraft.world.level.block.entity.BlockEntity blockEntity, @Nullable net.minecraft.world.entity.Entity breaker, ItemStack tool) {
        return this.xpRange.sample(level.getRandom());
    }

    @Override
    public int getDustColor(BlockState state, BlockGetter level, BlockPos pos) {
        return this.dustColor.rgba();
    }
}
