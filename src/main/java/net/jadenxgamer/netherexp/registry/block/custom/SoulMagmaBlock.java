package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoulMagmaBlock
extends Block
{
    public SoulMagmaBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.bypassesSteppingEffects() && entity instanceof LivingEntity && !EnchantmentHelper.hasSoulSpeed((LivingEntity)entity)) {
            entity.damage(DamageSource.HOT_FLOOR, 2.0f);
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
