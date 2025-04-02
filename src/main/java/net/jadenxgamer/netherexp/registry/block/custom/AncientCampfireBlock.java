package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.block.entity.AncientCampfireBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AncientCampfireBlock extends CampfireBlock {
    public AncientCampfireBlock(boolean pSpawnParticles, int pFireDamage, Properties pProperties) {
        super(pSpawnParticles, pFireDamage, pProperties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AncientCampfireBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return pState.getValue(LIT) ? createTickerHelper(pBlockEntityType, BlockEntityType.CAMPFIRE, AncientCampfireBlockEntity::particleTick) : null;
        } else {
            return pState.getValue(LIT) ? createTickerHelper(pBlockEntityType, BlockEntityType.CAMPFIRE, AncientCampfireBlockEntity::cookTick) : createTickerHelper(pBlockEntityType, JNEBlockEntityType.ANCIENT_CAMPFIRE.get(), AncientCampfireBlockEntity::cooldownTick);
        }
    }
}
