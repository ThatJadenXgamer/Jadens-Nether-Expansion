package net.jadenxgamer.netherexp.core.worldgen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.function.Function;

public class BasaltRiverCarver extends WorldCarver<CaveCarverConfiguration> {

    public BasaltRiverCarver(Codec<CaveCarverConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean isStartChunk(CaveCarverConfiguration config, RandomSource random) {
        return random.nextFloat() <= config.probability;
    }

    @Override
    public boolean carve(CarvingContext context, CaveCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, RandomSource random, Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask) {
        int branchCount = random.nextInt(random.nextInt(random.nextInt(15) + 1) + 1);

        for (int i = 0; i < branchCount; ++i) {
            double x = chunkPos.getBlockX(random.nextInt(16));
            double y = 32.0;
            double z = chunkPos.getBlockZ(random.nextInt(16));

            float radius = 4.0f + random.nextFloat() * 2.0f;
            float yaw = random.nextFloat() * ((float) Math.PI * 2F);
            float pitch = 0.0f;

            this.carveRiverNode(config, chunk, biomeAccessor, random.nextLong(), aquifer, x, y, z, radius, yaw, pitch, 0, 100, 1.0, chunkPos, carvingMask);
        }
        return true;
    }

    private void carveRiverNode(CaveCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, long seed, Aquifer aquifer, double x, double y, double z, float radius, float yaw, float pitch, int step, int maxSteps, double scale, ChunkPos chunkPos, CarvingMask mask) {
        RandomSource random = RandomSource.create(seed);

        for (int i = step; i < maxSteps; ++i) {
            double d0 = 1.5 + (double) (Mth.sin((float) i * (float) Math.PI / (float) maxSteps) * radius);
            double d1 = d0 * 0.8;

            float cos = Mth.cos(pitch);
            float sin = Mth.sin(pitch);
            x += Mth.cos(yaw) * cos;
            y += sin;
            z += Mth.sin(yaw) * cos;

            pitch *= 0.5f;
            yaw += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 1.5f;

            if (y < 31.0) y += 0.3;
            if (y > 33.0) y -= 0.3;

            // Branching logic
            if (i == step && radius > 2.0f && random.nextInt(4) == 0) {
                this.carveRiverNode(config, chunk, biomeAccessor, random.nextLong(), aquifer, x, y, z, random.nextFloat() * 0.5f + 1.5f, yaw - ((float) Math.PI / 3f), pitch, i, maxSteps, 1.0, chunkPos, mask);
                this.carveRiverNode(config, chunk, biomeAccessor, random.nextLong(), aquifer, x, y, z, random.nextFloat() * 0.5f + 1.5f, yaw + ((float) Math.PI / 3f), pitch, i, maxSteps, 1.0, chunkPos, mask);
                return;
            }

            if (random.nextInt(4) != 0) {
                if (!canReach(chunkPos, x, z, i, maxSteps, radius)) return;
                this.carveRiverEllipsoid(config, chunk, biomeAccessor, aquifer, x, y, z, d0, d1, mask);
            }
        }
    }

    private boolean carveRiverEllipsoid(CaveCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, Aquifer aquifer, double x, double y, double z, double radiusX, double radiusY, CarvingMask mask) {
        ChunkPos chunkPos = chunk.getPos();

        int minX = Mth.floor(x - radiusX) - chunkPos.getMinBlockX() - 1;
        int maxX = Mth.floor(x + radiusX) - chunkPos.getMinBlockX() + 1;
        int minY = Mth.floor(y - radiusY) - 1;
        int maxY = Mth.floor(y + radiusY) + 1;
        int minZ = Mth.floor(z - radiusX) - chunkPos.getMinBlockZ() - 1;
        int maxZ = Mth.floor(z + radiusX) - chunkPos.getMinBlockZ() + 1;

        minX = Math.max(0, minX);
        maxX = Math.min(15, maxX);
        minZ = Math.max(0, minZ);
        maxZ = Math.min(15, maxZ);

        boolean carvedAny = false;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int lx = minX; lx <= maxX; ++lx) {
            double dx = ((double) lx + chunkPos.getMinBlockX() + 0.5 - x) / radiusX;
            for (int lz = minZ; lz <= maxZ; ++lz) {
                double dz = ((double) lz + chunkPos.getMinBlockZ() + 0.5 - z) / radiusX;
                if (dx * dx + dz * dz >= 1.0) continue;

                for (int ly = maxY; ly >= minY; --ly) {
                    double dy = ((double) ly + 0.5 - y) / radiusY;
                    double distSq = dx * dx + dy * dy + dz * dz;
                    if (distSq >= 1.0) continue;

                    pos.set(lx + chunkPos.getMinBlockX(), ly, lz + chunkPos.getMinBlockZ());
                    BlockState currentState = chunk.getBlockState(pos);

                    if (!this.canReplaceBlock(config, currentState) && !currentState.is(Blocks.BASALT)) continue;
                    boolean isEdge = distSq > 0.55;

                    if (isEdge && dy <= 0.0) {
                        chunk.setBlockState(pos, Blocks.BASALT.defaultBlockState(), false);
                    } else {
                        if (ly <= 31) {
                            chunk.setBlockState(pos, Blocks.LAVA.defaultBlockState(), false);
                        } else chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), false);
                    }
                    carvedAny = true;
                }
            }
        }
        return carvedAny;
    }

    @Override
    protected boolean carveBlock(CarvingContext context, CaveCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, CarvingMask mask, BlockPos.MutableBlockPos pos, BlockPos.MutableBlockPos checkPos, Aquifer aquifer, MutableBoolean reachedSurface) {
        return false;
    }
}