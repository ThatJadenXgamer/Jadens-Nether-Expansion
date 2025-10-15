package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.DirectionalBehaviorComponent;

import static net.jadenxgamer.netherexp.config.JNEConfigs.SOUL_SAND_VALLEY_WIND_SPEED;

public class DriftingSoulsBlock extends Block {

    public DriftingSoulsBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(JNEItems.WISP_BOTTLE.get())) {
            Direction growthDirection = Direction.Plane.HORIZONTAL.getRandomDirection(level.random);
            BlockPos growthPos = pos.relative(growthDirection);
            if (!canSurvive(state, level, growthPos)) super.useItemOn(stack, state, level, pos, player, hand, hitResult);

            level.playSound(player, pos, JNESoundEvents.WISP_BOTTLE_EMPTY.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!player.getAbilities().instabuild) stack.shrink(1);
            level.setBlock(growthPos, state, Block.UPDATE_ALL);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return canSurvive(state, level, pos) ? state : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(JNETags.Blocks.SOUL_SANDS);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        for (int i = 0; i < JNEConfigs.DRIFTING_SOULS_SPAWN_QUANTITY.getAsInt(); i++) {
            int x = pos.getX() + Mth.nextInt(random, -30, 30);
            int y = pos.getY() + Mth.nextInt(random, -30, 30);
            int z = pos.getZ() + Mth.nextInt(random, -30, 30);
            BlockPos particlePos = new BlockPos(x, y, z);
            BlockState particleState = level.getBlockState(particlePos);
            if (particleState.isSolidRender(level, particlePos)) continue;

            driftingSoulParticle(level, random, x + random.nextDouble(), y + random.nextDouble(), z + random.nextDouble());
        }
    }

    private void driftingSoulParticle(Level level, RandomSource random, double x, double y, double z) {
        Vec3 direction = new Vec3(-1, 0, 1);
        WorldParticleBuilder.create(JNEParticleTypes.DRIFTING_SOUL.get())
                .setFullBrightLighting()
                .setSpinData(SpinParticleData.create(0.05f, -0.05f, 0.05f).build())
                .setScaleData(GenericParticleData.create(0.695f).build())
                .setTransparencyData(GenericParticleData.create(0.1f, 0.25f, 0.0f).build())
                .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                .setBehavior(new DirectionalBehaviorComponent(direction))
                .setLifetime(random.nextInt(40, 50))
                .enableForcedSpawn()
                .enableCull()
                .enableNoClip()
                .setMotion(SOUL_SAND_VALLEY_WIND_SPEED.get() + (random.nextDouble() * 0.2), (Mth.randomBetween(level.random, 0.1f, 0.5f)) * 0.1, SOUL_SAND_VALLEY_WIND_SPEED.get() + (random.nextDouble() * 0.2))
                .spawn(level, x, y, z);
    }
}
