package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.util.HolderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;

@SuppressWarnings("deprecation")
public class SoulGlassBlock extends LightableBlock {
    public SoulGlassBlock(Properties properties) {
        super(() -> ParticleTypes.SOUL_FIRE_FLAME, properties
                .isValidSpawn(Blocks::never)
                .isViewBlocking((s, g, p) -> false)
                .isRedstoneConductor((s, g, p) -> false)
                .isSuffocating((s, g, p) -> false)
        );
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        RandomSource random = level.random;
        if (entity instanceof LivingEntity living) {
            if (level.isClientSide() && random.nextInt(7) == 0)
                Client.particle(level, random, living.getRandomX(1.5), living.getRandomY() - 0.25, living.getRandomZ(1.5));
            if (EnchantmentHelper.getItemEnchantmentLevel(HolderHelper.getEnchantmentHolder(Enchantments.SOUL_SPEED), living.getItemBySlot(EquipmentSlot.FEET)) > 0) return;
            double slowdown = JNEConfigs.SOUL_GLASS_MOVEMENT_SLOWDOWN.get();
            entity.makeStuckInBlock(state, new Vec3(slowdown, slowdown, slowdown));
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext collisionContext) {
            Entity entity = collisionContext.getEntity();
            if (entity != null && entity.getType().is(JNETags.EntityTypes.CAN_PHASE_THROUGH_SOUL_GLASS)) {
                if (context.isAbove(Shapes.block(), pos, true) && !context.isDescending()) return Shapes.block();
                return Shapes.empty();
            }
        }
        return state.getShape(level, pos);
    }

    // Glass Stuff

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
        return adjacentState.is(this) || super.skipRendering(state, adjacentState, direction);
    }

    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    private static class Client {

        public static void particle(Level level, RandomSource random, double x, double y, double z) {
            Color color = new Color(0x0E4E4E);
            WorldParticleBuilder.create(JNEParticleTypes.SPARKLE.get())
                    .setFullBrightLighting()
                    .setColorData(ColorParticleData.create(color).build())
                    .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.13f, 0.0f).setEasing(Easing.BOUNCE_IN_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.5f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(random.nextInt(30, 40))
                    .enableNoClip()
                    .setGravity(0.0f)
                    .setMotion(0.0, 0.04, 0.0)
                    .spawn(level, x, y, z);
        }
    }
}
