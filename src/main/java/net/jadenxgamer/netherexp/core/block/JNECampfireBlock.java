package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.core.block.entity.JNECampfireBlockEntity;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class JNECampfireBlock extends CampfireBlock {
    private final int fireDamage;
    public JNECampfireBlock(boolean spawnParticles, int fireDamage, Properties properties) {
        super(spawnParticles, fireDamage, properties);
        this.fireDamage = fireDamage;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (this.fireDamage < 1) return;
        super.entityInside(state, level, pos, entity);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new JNECampfireBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return state.getValue(LIT) ? createTickerHelper(blockEntityType, JNEBlockEntityType.JNE_CAMPFIRE.get(), JNECampfireBlockEntity::particleTick) : null;
        } else {
            return state.getValue(LIT) ? createTickerHelper(blockEntityType, JNEBlockEntityType.JNE_CAMPFIRE.get(), JNECampfireBlockEntity::cookTick) : createTickerHelper(blockEntityType, JNEBlockEntityType.JNE_CAMPFIRE.get(), JNECampfireBlockEntity::cooldownTick);
        }
    }
}
