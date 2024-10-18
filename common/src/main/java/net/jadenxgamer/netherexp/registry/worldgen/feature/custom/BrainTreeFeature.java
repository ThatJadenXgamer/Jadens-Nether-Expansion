package net.jadenxgamer.netherexp.registry.worldgen.feature.custom;

import com.mojang.serialization.Codec;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BrainTreeFeature extends Feature<NoneFeatureConfiguration> {

    public BrainTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos origin = context.origin().above(3);
        LevelAccessor level = context.level();
        RandomSource random = level.getRandom();

        int radius = 2;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = 0; y <= radius; y++) {
                    double distance = Math.sqrt(x * x + z * z + y * y);
                    if (distance <= radius) {
                        mutablePos.set(origin.getX() + x, origin.getY() - y, origin.getZ() + z);
                        level.setBlock(mutablePos, JNEBlocks.CEREBRAGE_CLARET_STEM.get().defaultBlockState(), 3);
                    }
                }
            }
        }

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double distance = Math.sqrt(x * x + z * z);

                if (distance <= radius) {
                    int pillarHeight = 1 + random.nextInt(4);
                    for (int h = 0; h < pillarHeight; h++) {
                        mutablePos.set(origin.getX() + x, origin.getY() + h, origin.getZ() + z);
                        level.setBlock(mutablePos, JNEBlocks.CEREBRAGE_CLARET_STEM.get().defaultBlockState(), 3);
                    }
                }
            }
        }

        return true;
    }
}
