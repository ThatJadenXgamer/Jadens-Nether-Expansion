package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SoulMagmaBlock extends Block {
    public SoulMagmaBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity.isSprinting() && entity instanceof LivingEntity && !EnchantmentHelper.hasSoulSpeed((LivingEntity)entity)) {
            entity.hurt(level.damageSources().hotFloor(), 2.0f);
        }
        super.stepOn(level, pos, state, entity);
    }
}
