package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagmaCreamBlock extends Block {
    public MagmaCreamBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient) {
            if (entity.isOnFire() && entity.canModifyAt(world, pos)) {
                entity.setOnFire(false);
            }
        }
    }
}
