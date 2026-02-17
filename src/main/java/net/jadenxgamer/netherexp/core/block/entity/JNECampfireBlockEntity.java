package net.jadenxgamer.netherexp.core.block.entity;

import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class JNECampfireBlockEntity extends CampfireBlockEntity {
    public JNECampfireBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return JNEBlockEntityType.JNE_CAMPFIRE.get();
    }
}
