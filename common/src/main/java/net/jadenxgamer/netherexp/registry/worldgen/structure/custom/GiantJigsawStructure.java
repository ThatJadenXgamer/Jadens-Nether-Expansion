package net.jadenxgamer.netherexp.registry.worldgen.structure.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.registry.worldgen.structure.JNEStructureType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GiantJigsawStructure extends Structure {
    public static final Codec<GiantJigsawStructure> CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec((instance) -> instance.group(settingsCodec(instance),
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((arg) -> arg.startPool),
            ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((arg) -> arg.startJigsawName),
            Codec.intRange(0, 20).fieldOf("size").forGetter((arg) -> arg.maxDepth),
            HeightProvider.CODEC.fieldOf("start_height").forGetter((arg) -> arg.startHeight),
            Codec.BOOL.fieldOf("use_expansion_hack").forGetter((arg) -> arg.useExpansionHack),
            Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((arg) -> arg.projectStartToHeightmap),
            Codec.intRange(1, 256).fieldOf("max_distance_from_center").forGetter((arg) -> arg.maxDistanceFromCenter)).apply(instance, GiantJigsawStructure::new)),
            GiantJigsawStructure::verifyRange).codec();
    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    private static DataResult<GiantJigsawStructure> verifyRange(GiantJigsawStructure arg) {
        int i = switch (arg.terrainAdaptation()) {
            case NONE -> 0;
            case BURY, BEARD_THIN, BEARD_BOX -> 12;
        };
        return arg.maxDistanceFromCenter + i > 256 ? DataResult.error(() -> "Structure size including terrain adaptation must not exceed 256") : DataResult.success(arg);
    }

    public GiantJigsawStructure(Structure.StructureSettings structure, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
        super(structure);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    public GiantJigsawStructure(Structure.StructureSettings structure, Holder<StructureTemplatePool> startPool, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Heightmap.Types projectStartToHeightmap) {
        this(structure, startPool, Optional.empty(), maxDepth, startHeight, useExpansionHack, Optional.of(projectStartToHeightmap), 240);
    }

    public GiantJigsawStructure(Structure.StructureSettings structure, Holder<StructureTemplatePool> startPool, int maxDepth, HeightProvider startHeight, boolean useExpansionHack) {
        this(structure, startPool, Optional.empty(), maxDepth, startHeight, useExpansionHack, Optional.empty(), 240);
    }

    @Override
    public @NotNull Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        int i = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), i, chunkPos.getMinBlockZ());
        return GiantJigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.maxDepth, blockPos, this.useExpansionHack, this.projectStartToHeightmap, this.maxDistanceFromCenter);
    }

    public @NotNull StructureType<?> type() {
        return JNEStructureType.GIANT_JIGSAW.get();
    }
}
