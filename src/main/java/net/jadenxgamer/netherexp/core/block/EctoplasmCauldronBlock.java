package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.misc.JNECauldronInteractions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EctoplasmCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<EctoplasmCauldronBlock> CODEC = simpleCodec(EctoplasmCauldronBlock::new);

    public EctoplasmCauldronBlock(Properties properties) {
        super(properties, JNECauldronInteractions.ECTOPLASM);
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return 0.9375;
    }

    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!JNEConfigs.ECTOPLASM_FREEZES.get()) return;
        if (this.isEntityInsideContent(state, pos, entity) && entity instanceof LivingEntity living) {
            if (level.isClientSide || living.isInPowderSnow || living.isDeadOrDying() || !entity.canFreeze()) return;

            living.setTicksFrozen(Math.min(entity.getTicksFrozen() + 3, 200));
        }
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }
}
