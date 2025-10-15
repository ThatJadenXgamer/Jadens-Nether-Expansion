package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class SoulGlassBlock extends LightableBlock {
    public SoulGlassBlock(Properties properties) {
        super(() -> ParticleTypes.SOUL_FIRE_FLAME, properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity living) {
            if (level.isClientSide()) {
                RandomSource random = level.random;
                if (random.nextInt(2) == 0) level.addParticle(ParticleTypes.SOUL, entity.getX(), pos.getY() + 1, entity.getZ(), Mth.randomBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, Mth.randomBetween(random, -1.0f, 1.0f) * 0.083333336f);
            }
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
}
