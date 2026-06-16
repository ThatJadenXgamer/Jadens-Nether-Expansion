package net.jadenxgamer.netherexp.core.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class BlottedPatchFeature extends Feature<BlottedPatchFeature.Config> {

    public BlottedPatchFeature(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Config config = context.config();
        RandomSource random = context.random();

        int radius = config.radius();
        int verticalRadius = config.thickness();
        int holeCount = config.holeCount();
        int holeMin = config.holeRadiusMin();
        int holeMax = config.holeRadiusMax();
        BlockPredicate replaceable = config.replaceable();
        BlockState state = config.state();

        int originX = origin.getX();
        int originY = origin.getY();
        int originZ = origin.getZ();

        int[] holeX = new int[holeCount];
        int[] holeZ = new int[holeCount];
        int[] holeR = new int[holeCount];

        for (int i = 0; i < holeCount; i++) {
            int x, z;
            do {
                x = random.nextInt(radius * 2 + 1) - radius;
                z = random.nextInt(radius * 2 + 1) - radius;
            } while (x * x + z * z > radius * radius);
            holeX[i] = originX + x;
            holeZ[i] = originZ + z;
            holeR[i] = random.nextInt(holeMax - holeMin + 1) + holeMin;
        }

        boolean placed = false;

        for (int dy = -verticalRadius; dy <= verticalRadius; dy++) {
            int y = originY + dy;
            for (int dx = -radius; dx <= radius; dx++) {
                int dx2 = dx * dx;
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx2 + dz * dz > radius * radius) continue;
                    BlockPos pos = new BlockPos(originX + dx, y, originZ + dz);

                    boolean insideHole = false;
                    for (int i = 0; i < holeCount; i++) {
                        int hx = holeX[i];
                        int hz = holeZ[i];
                        int hr = holeR[i];
                        int diffX = pos.getX() - hx;
                        int diffZ = pos.getZ() - hz;
                        if (diffX * diffX + diffZ * diffZ <= hr * hr) {
                            insideHole = true;
                            break;
                        }
                    }
                    if (insideHole) continue;
                    if (!replaceable.test(level, pos)) continue;

                    level.setBlock(pos, state, 2);
                    placed = true;
                }
            }
        }

        return placed;
    }

    public record Config(BlockPredicate replaceable, BlockState state,
                         int radius, int thickness, int holeCount, int holeRadiusMin, int holeRadiusMax
    ) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        BlockPredicate.CODEC.fieldOf("replaceable").forGetter(Config::replaceable),
                        BlockState.CODEC.fieldOf("state").forGetter(Config::state),
                        Codec.INT.fieldOf("radius").forGetter(Config::radius),
                        Codec.INT.fieldOf("thickness").forGetter(Config::thickness),
                        Codec.INT.fieldOf("hole_count").forGetter(Config::holeCount),
                        Codec.INT.fieldOf("hole_radius_min").forGetter(Config::holeRadiusMin),
                        Codec.INT.fieldOf("hole_radius_max").forGetter(Config::holeRadiusMax)
                        ).apply(instance, Config::new)
        );
    }
}