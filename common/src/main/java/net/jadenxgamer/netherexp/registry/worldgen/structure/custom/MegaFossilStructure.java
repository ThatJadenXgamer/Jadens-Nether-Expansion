package net.jadenxgamer.netherexp.registry.worldgen.structure.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.registry.worldgen.structure.JNEStructureType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MegaFossilStructure extends Structure {

    public static final Codec<MegaFossilStructure> CODEC = RecordCodecBuilder.<MegaFossilStructure>mapCodec(instance ->
            instance.group(MegaFossilStructure.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
            ).apply(instance, MegaFossilStructure::new)).codec();

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    public MegaFossilStructure(Structure.StructureSettings config, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, HeightProvider startHeight, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    public GenerationStep.@NotNull Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public @NotNull Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        WorldgenRandom worldgenRandom = context.random();
        int x = context.chunkPos().getMinBlockX() + worldgenRandom.nextInt(16);
        int z = context.chunkPos().getMinBlockZ() + worldgenRandom.nextInt(16);
        int sea = context.chunkGenerator().getSeaLevel();
        WorldGenerationContext worldGenerationContext = new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor());
        int y = this.startHeight.sample(worldgenRandom, worldGenerationContext);
        NoiseColumn noiseColumn = context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState());
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, y, z);

        while(y > sea) {
            BlockState blockState = noiseColumn.getBlock(y);
            --y;
            BlockState blockState2 = noiseColumn.getBlock(y);
            if (blockState.isAir() && (blockState2.is(Blocks.SOUL_SAND) || blockState2.isFaceSturdy(EmptyBlockGetter.INSTANCE, mutableBlockPos.setY(y), Direction.UP))) {
                break;
            }
        }

        if (y <= sea) {
            return Optional.empty();
        } else {
            BlockPos blockPos = new BlockPos(x, y, z);
            return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.size, blockPos, false, this.projectStartToHeightmap, this.maxDistanceFromCenter);
        }
    }

    @Override
    public @NotNull StructureType<?> type() {
        return JNEStructureType.MEGA_FOSSIL.get();
    }
}
