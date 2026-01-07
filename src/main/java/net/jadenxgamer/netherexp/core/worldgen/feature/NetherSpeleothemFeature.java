package net.jadenxgamer.netherexp.core.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class NetherSpeleothemFeature extends Feature<NoneFeatureConfiguration> {

    private static final int MAX_VERTICAL_DEVIATION = 12;

    public NetherSpeleothemFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int maxScan = 64; //todo: make this a json config later
        BlockPos centerCeiling = findSurface(level, origin, Direction.UP, maxScan);
        BlockPos centerFloor = findSurface(level, origin, Direction.DOWN, maxScan);

        if (centerCeiling == null || centerFloor == null) return false;

        int minY = centerFloor.getY();
        int maxY = centerCeiling.getY();
        int totalHeight = maxY - minY;

        if (totalHeight < 4) return false; //todo: json config

        float baseRadius = Mth.clamp((totalHeight / 10.0f) + 1.0f, 1.5f, 7.0f);
        int scanRadius = Mth.ceil(baseRadius * 2.5f);

        List<ColumnData> validColumns = new ArrayList<>();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        boolean anyColumnOutOfRange = false;

        for (int x = -scanRadius; x <= scanRadius; x++) {
            for (int z = -scanRadius; z <= scanRadius; z++) {
                double distSq = x * x + z * z;
                double dist = Math.sqrt(distSq);
                if (dist > baseRadius * 2.5) continue;

                cursor.set(origin.getX() + x, origin.getY(), origin.getZ() + z);

                BlockPos localCeiling = findSurface(level, cursor, Direction.UP, maxScan);
                BlockPos localFloor = findSurface(level, cursor, Direction.DOWN, maxScan);
                if (localCeiling == null || localFloor == null) continue;

                int localMinY = localFloor.getY();
                int localMaxY = localCeiling.getY();

                // This was added to stop a weird cascading effect, but I have no fucking clue if that solves it properly
                // and yes there is profanity in JNE's codebase <3
                boolean floorTooLow = localMinY < minY - MAX_VERTICAL_DEVIATION;
                boolean ceilingTooHigh = localMaxY > maxY + MAX_VERTICAL_DEVIATION;

                if (floorTooLow || ceilingTooHigh) anyColumnOutOfRange = true;
                validColumns.add(new ColumnData(x, z, localMinY, localMaxY, dist, floorTooLow || ceilingTooHigh));
            }
        }

        if (validColumns.isEmpty()) return false; // this might be too lenient? idk might change it later
        if (anyColumnOutOfRange) return false;

        for (ColumnData column : validColumns) {
            if (column.isOutOfRange) continue;

            int x = column.x;
            int z = column.z;
            int localMinY = column.localMinY;
            int localMaxY = column.localMaxY;
            double distance = column.distance;

            for (int y = localMinY; y <= localMaxY; y++) {
                // DIE DENSITY FUNCTION
                float relativeY = (float)(y - minY) / totalHeight; // 0.0 to 1.0
                float taperFactor = 2.0f * (relativeY - 0.5f); // -1.0 to 1.0
                float hourglassMultiplier = 1.0f + (taperFactor * taperFactor * 0.8f);

                int distToSurface = Math.min(y - localMinY, localMaxY - y);
                float meltStrength = 4.0f / (distToSurface + 1.0f);

                double noise = getImperfectionNoise(origin.getX() + x, y, origin.getZ() + z);

                double targetRadius = (baseRadius * hourglassMultiplier) - (hourglassMultiplier * 0.5);
                double blendBonus = meltStrength * 1.5;
                double noiseBonus = noise * 1.2;

                double maxRadiusHere = targetRadius + blendBonus + noiseBonus;

                if (distance < maxRadiusHere) {
                    cursor.set(origin.getX() + x, y, origin.getZ() + z);
                    if (canReplace(level, cursor)) {
                        level.setBlock(cursor, Blocks.NETHERRACK.defaultBlockState(), 2);
                    }
                }
            }
        }

        return true;
    }

    private record ColumnData(int x, int z, int localMinY, int localMaxY, double distance, boolean isOutOfRange) {}

    private BlockPos findSurface(WorldGenLevel level, BlockPos start, Direction direction, int maxDistance) {
        BlockPos.MutableBlockPos mPos = start.mutable();
        for (int i = 0; i < maxDistance; i++) {
            if (!level.isEmptyBlock(mPos) && level.getBlockState(mPos).isSolid()) {
                return mPos.immutable();
            }
            mPos.move(direction);
        }
        return null;
    }

//    private BlockPos findSurfaceWithConstraint(WorldGenLevel level, BlockPos start, Direction direction, int maxDistance, int referenceY, int maxDeviation) {
//        BlockPos.MutableBlockPos mPos = start.mutable();
//        for (int i = 0; i < maxDistance; i++) {
//            if (Math.abs(mPos.getY() - referenceY) > maxDeviation) return null;
//            if (!level.isEmptyBlock(mPos) && level.getBlockState(mPos).isSolid()) return mPos.immutable();
//
//            mPos.move(direction);
//        }
//        return null;
//    }

    private boolean canReplace(WorldGenLevel level, BlockPos pos) {
        return level.isEmptyBlock(pos) || !level.getBlockState(pos).isSolid();
    }

    private double getImperfectionNoise(int x, int y, int z) {
        double n1 = Mth.sin(x * 0.3f) * Mth.cos(y * 0.3f) * Mth.sin(z * 0.3f);
        double n2 = Mth.cos(x * 0.1f + y * 0.05f) * Mth.sin(z * 0.1f);
        return n1 + n2;
    }
}