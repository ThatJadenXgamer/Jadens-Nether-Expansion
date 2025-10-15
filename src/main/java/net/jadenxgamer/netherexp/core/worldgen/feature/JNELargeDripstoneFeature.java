package net.jadenxgamer.netherexp.core.worldgen.feature;

import com.mojang.serialization.Codec;
import net.jadenxgamer.netherexp.core.worldgen.feature.config.JNELargeDripstoneConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class JNELargeDripstoneFeature extends Feature<JNELargeDripstoneConfiguration> {
    
    // Literally just a copy and paste of LargeDripstoneFeature.java but with a config to change the block it places
    // I would clean this up, but also I cannot be bothered to
    
    public JNELargeDripstoneFeature(Codec<JNELargeDripstoneConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<JNELargeDripstoneConfiguration> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos blockPos = context.origin();
        JNELargeDripstoneConfiguration configuration = context.config();
        RandomSource randomSource = context.random();
        if (!isEmptyOrWater(worldGenLevel, blockPos)) {
            return false;
        } else {
            Optional<Column> optional = Column.scan(worldGenLevel, blockPos, configuration.floorToCeilingSearchRange(), DripstoneUtils::isEmptyOrWater, state -> isBaseOrLava(state, configuration));
            if (!optional.isEmpty() && optional.get() instanceof Column.Range) {
                Column.Range range = (Column.Range)optional.get();
                if (range.height() < 4) {
                    return false;
                } else {
                    int i = (int)((float)range.height() * configuration.maxColumnRadiusToCaveHeightRatio());
                    int j = Mth.clamp(i, configuration.columnRadius().getMinValue(), configuration.columnRadius().getMaxValue());
                    int k = Mth.randomBetweenInclusive(randomSource, configuration.columnRadius().getMinValue(), j);
                    LargeDripstone largeDripstone = makeDripstone(blockPos.atY(range.ceiling() - 1), false, randomSource, k, configuration.stalactiteBluntness(), configuration.heightScale());
                    LargeDripstone largeDripstone2 = makeDripstone(blockPos.atY(range.floor() + 1), true, randomSource, k, configuration.stalagmiteBluntness(), configuration.heightScale());
                    WindOffsetter windOffsetter;
                    if (largeDripstone.isSuitableForWind(configuration) && largeDripstone2.isSuitableForWind(configuration)) {
                        windOffsetter = new WindOffsetter(blockPos.getY(), randomSource, configuration.windSpeed());
                    } else {
                        windOffsetter = WindOffsetter.noWind();
                    }

                    boolean bl = largeDripstone.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldGenLevel, windOffsetter);
                    boolean bl2 = largeDripstone2.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldGenLevel, windOffsetter);
                    if (bl) {
                        largeDripstone.placeBlocks(worldGenLevel, randomSource, windOffsetter, configuration);
                    }

                    if (bl2) {
                        largeDripstone2.placeBlocks(worldGenLevel, randomSource, windOffsetter, configuration);
                    }

                    return true;
                }
            } else {
                return false;
            }
        }
    }

    private static LargeDripstone makeDripstone(BlockPos root, boolean pointingUp, RandomSource random, int radius, FloatProvider bluntnessBase, FloatProvider scaleBase) {
        return new LargeDripstone(root, pointingUp, radius, (double)bluntnessBase.sample(random), (double)scaleBase.sample(random));
    }

    private void placeDebugMarkers(WorldGenLevel level, BlockPos pos, Column.Range range, WindOffsetter windOffsetter) {
        level.setBlock(windOffsetter.offset(pos.atY(range.ceiling() - 1)), Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
        level.setBlock(windOffsetter.offset(pos.atY(range.floor() + 1)), Blocks.GOLD_BLOCK.defaultBlockState(), 2);

        for(BlockPos.MutableBlockPos mutableBlockPos = pos.atY(range.floor() + 2).mutable(); mutableBlockPos.getY() < range.ceiling() - 1; mutableBlockPos.move(Direction.UP)) {
            BlockPos blockPos = windOffsetter.offset(mutableBlockPos);
            if (isEmptyOrWater(level, blockPos) || level.getBlockState(blockPos).is(Blocks.DRIPSTONE_BLOCK)) {
                level.setBlock(blockPos, Blocks.CREEPER_HEAD.defaultBlockState(), 2);
            }
        }

    }

    static final class LargeDripstone {
        private BlockPos root;
        private final boolean pointingUp;
        private int radius;
        private final double bluntness;
        private final double scale;

        LargeDripstone(BlockPos root, boolean pointingUp, int radius, double bluntness, double scale) {
            this.root = root;
            this.pointingUp = pointingUp;
            this.radius = radius;
            this.bluntness = bluntness;
            this.scale = scale;
        }

        private int getHeight() {
            return this.getHeightAtRadius(0.0F);
        }

        private int getMinY() {
            return this.pointingUp ? this.root.getY() : this.root.getY() - this.getHeight();
        }

        private int getMaxY() {
            return !this.pointingUp ? this.root.getY() : this.root.getY() + this.getHeight();
        }

        boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel level, WindOffsetter windOffsetter) {
            while(this.radius > 1) {
                BlockPos.MutableBlockPos mutableBlockPos = this.root.mutable();
                int i = Math.min(10, this.getHeight());

                for(int j = 0; j < i; ++j) {
                    if (level.getBlockState(mutableBlockPos).is(Blocks.LAVA)) {
                        return false;
                    }

                    if (isCircleMostlyEmbeddedInStone(level, windOffsetter.offset(mutableBlockPos), this.radius)) {
                        this.root = mutableBlockPos;
                        return true;
                    }

                    mutableBlockPos.move(this.pointingUp ? Direction.DOWN : Direction.UP);
                }

                this.radius /= 2;
            }

            return false;
        }

        private int getHeightAtRadius(float radius) {
            return (int)getDripstoneHeight((double)radius, (double)this.radius, this.scale, this.bluntness);
        }

        void placeBlocks(WorldGenLevel level, RandomSource random, WindOffsetter windOffsetter, JNELargeDripstoneConfiguration largeDripstoneConfiguration) {
            for(int i = -this.radius; i <= this.radius; ++i) {
                for(int j = -this.radius; j <= this.radius; ++j) {
                    float f = Mth.sqrt((float)(i * i + j * j));
                    if (!(f > (float)this.radius)) {
                        int k = this.getHeightAtRadius(f);
                        if (k > 0) {
                            if ((double)random.nextFloat() < 0.2) {
                                k = (int)((float)k * Mth.randomBetween(random, 0.8F, 1.0F));
                            }

                            BlockPos.MutableBlockPos mutableBlockPos = this.root.offset(i, 0, j).mutable();
                            boolean bl = false;
                            int l = this.pointingUp ? level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, mutableBlockPos.getX(), mutableBlockPos.getZ()) : Integer.MAX_VALUE;

                            for(int m = 0; m < k && mutableBlockPos.getY() < l; ++m) {
                                BlockPos blockPos = windOffsetter.offset(mutableBlockPos);
                                if (isEmptyOrWaterOrLava(level, blockPos)) {
                                    bl = true;
                                    level.setBlock(blockPos, largeDripstoneConfiguration.block(), 2);
                                } else if (bl && level.getBlockState(blockPos).is(BlockTags.BASE_STONE_NETHER)) {
                                    break;
                                }

                                mutableBlockPos.move(this.pointingUp ? Direction.UP : Direction.DOWN);
                            }
                        }
                    }
                }
            }

        }

        boolean isSuitableForWind(JNELargeDripstoneConfiguration config) {
            return this.radius >= config.minRadiusForWind() && this.bluntness >= (double)config.minBluntnessForWind();
        }
    }

    private static final class WindOffsetter {
        private final int originY;
        @Nullable
        private final Vec3 windSpeed;

        WindOffsetter(int originY, RandomSource random, FloatProvider magnitude) {
            this.originY = originY;
            float f = magnitude.sample(random);
            float g = Mth.randomBetween(random, 0.0F, 3.1415927F);
            this.windSpeed = new Vec3((double)(Mth.cos(g) * f), 0.0, (double)(Mth.sin(g) * f));
        }

        private WindOffsetter() {
            this.originY = 0;
            this.windSpeed = null;
        }

        static WindOffsetter noWind() {
            return new WindOffsetter();
        }

        BlockPos offset(BlockPos pos) {
            if (this.windSpeed == null) {
                return pos;
            } else {
                int i = this.originY - pos.getY();
                Vec3 vec3 = this.windSpeed.scale((double)i);
                return pos.offset(Mth.floor(vec3.x), 0, Mth.floor(vec3.z));
            }
        }
    }

    protected static boolean isEmptyOrWaterOrLava(LevelAccessor level, BlockPos pos) {
        return level.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWaterOrLava);
    }

    protected static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel level, BlockPos pos, int radius) {
        if (isEmptyOrWaterOrLava(level, pos)) {
            return false;
        } else {
            float f = 6.0F;
            float g = 6.0F / (float)radius;

            for(float h = 0.0F; h < 6.2831855F; h += g) {
                int i = (int)(Mth.cos(h) * (float)radius);
                int j = (int)(Mth.sin(h) * (float)radius);
                if (isEmptyOrWaterOrLava(level, pos.offset(i, 0, j))) {
                    return false;
                }
            }

            return true;
        }
    }

    protected static double getDripstoneHeight(double radius, double maxRadius, double scale, double minRadius) {
        if (radius < minRadius) {
            radius = minRadius;
        }

        double d = 0.384;
        double e = radius / maxRadius * 0.384;
        double f = 0.75 * Math.pow(e, 1.3333333333333333);
        double g = Math.pow(e, 0.6666666666666666);
        double h = 0.3333333333333333 * Math.log(e);
        double i = scale * (f - g - h);
        i = Math.max(i, 0.0);
        return i / 0.384 * maxRadius;
    }

    protected static boolean isEmptyOrWater(LevelAccessor level, BlockPos pos) {
        return level.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWater);
    }

    public static boolean isBaseOrLava(BlockState state, JNELargeDripstoneConfiguration config) {
        return isBase(state, config) || state.is(Blocks.LAVA);
    }

    public static boolean isBase(BlockState state, JNELargeDripstoneConfiguration config) {
        return config.baseBlocks().contains(state.getBlockHolder());
    }
}
