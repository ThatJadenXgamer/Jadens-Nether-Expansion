package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.util.HolderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

public class SoulMagmaBlock extends Block {
    public SoulMagmaBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity living && canHurtEntity(living)) {
            living.hurt(level.damageSources().hotFloor(), 2.0f);
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        particle(level, level.random, entity.getRandomX(0.5), entity.blockPosition().getY(), entity.getRandomZ(0.5));
        return true;
    }

    private boolean canHurtEntity(LivingEntity entity) {
        return entity.isSprinting() && EnchantmentHelper.getItemEnchantmentLevel(HolderHelper.getEnchantmentHolder(Enchantments.SOUL_SPEED), entity.getItemBySlot(EquipmentSlot.FEET)) <= 0;
    }

    private void particle(Level level, RandomSource random, double x, double y, double z) {
        WorldParticleBuilder.create(JNEParticleTypes.SOUL_MAGMA.get())
                .setFullBrightLighting()
                .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.17f).build())
                .setTransparencyData(GenericParticleData.create(1).build())
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setLifetime(random.nextInt(20, 30))
                .disableNoClip()
                .setGravityStrength(0.05f)
                .setMotion(random.nextDouble() * 0.1, 0.04, random.nextDouble() * 0.1)
                .spawn(level, x, y, z);
    }
}
