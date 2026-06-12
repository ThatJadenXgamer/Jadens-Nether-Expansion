package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherrackBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.util.Arrays;

public class BlottedNetherrackBlock extends NetherrackBlock {
    public BlottedNetherrackBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (level.isClientSide() && random.nextInt(24) == 0) Client.particle(level, random, pos);
    }

    @OnlyIn(Dist.CLIENT)
    private static class Client {

        public static void particle(Level level, RandomSource random, BlockPos pos) {
            Direction[] directions = Arrays.stream(Direction.values()).filter(d -> d != Direction.UP).toArray(Direction[]::new);
            if (directions.length == 0) return;

            Direction direction = directions[random.nextInt(directions.length)];
            BlockPos relativePos = pos.relative(direction);

            if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
                Direction.Axis axis = direction.getAxis();
                double x = (axis == Direction.Axis.X ? 0.5 + 0.65 * direction.getStepX() : random.nextFloat()) + pos.getX();
                double y = (axis == Direction.Axis.Y ? 0.5 + 0.65 * direction.getStepY() : random.nextFloat()) + pos.getY();
                double z = (axis == Direction.Axis.Z ? 0.5 + 0.65 * direction.getStepZ() : random.nextFloat()) + pos.getZ();

                WorldParticleBuilder.create(JNEParticleTypes.BLOT_DROP.get())
                        .setNaturalLighting()
                        .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                        .setTransparencyData(GenericParticleData.create(0.5f).build())
                        .disableNoClip()
                        .setGravity(Mth.randomBetween(random, 0.2f, 0.6f))
                        .spawn(level, x, y, z);
            }
        }
    }
}
