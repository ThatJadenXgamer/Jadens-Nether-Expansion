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
    public static final Codec<GiantJigsawStructure> CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(settingsCodec(instance), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((arg) -> {
            return arg.startPool;
        }), ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((arg) -> {
            return arg.startJigsawName;
        }), Codec.intRange(0, 20).fieldOf("size").forGetter((arg) -> {
            return arg.maxDepth;
        }), HeightProvider.CODEC.fieldOf("start_height").forGetter((arg) -> {
            return arg.startHeight;
        }), Codec.BOOL.fieldOf("use_expansion_hack").forGetter((arg) -> {
            return arg.useExpansionHack;
        }), Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((arg) -> {
            return arg.projectStartToHeightmap;
        }), Codec.intRange(1, 256).fieldOf("max_distance_from_center").forGetter((arg) -> {
            return arg.maxDistanceFromCenter;
        })).apply(instance, GiantJigsawStructure::new);
    }), GiantJigsawStructure::verifyRange).codec();
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

    public GiantJigsawStructure(Structure.StructureSettings arg, Holder<StructureTemplatePool> arg2, Optional<ResourceLocation> optional, int i, HeightProvider arg3, boolean bl, Optional<Heightmap.Types> optional2, int j) {
        super(arg);
        this.startPool = arg2;
        this.startJigsawName = optional;
        this.maxDepth = i;
        this.startHeight = arg3;
        this.useExpansionHack = bl;
        this.projectStartToHeightmap = optional2;
        this.maxDistanceFromCenter = j;
    }

    public GiantJigsawStructure(Structure.StructureSettings arg, Holder<StructureTemplatePool> arg2, int i, HeightProvider arg3, boolean bl, Heightmap.Types arg4) {
        this(arg, arg2, Optional.empty(), i, arg3, bl, Optional.of(arg4), 80);
    }

    public GiantJigsawStructure(Structure.StructureSettings arg, Holder<StructureTemplatePool> arg2, int i, HeightProvider arg3, boolean bl) {
        this(arg, arg2, Optional.empty(), i, arg3, bl, Optional.empty(), 80);
    }

    public @NotNull Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext arg) {
        ChunkPos chunkPos = arg.chunkPos();
        int i = this.startHeight.sample(arg.random(), new WorldGenerationContext(arg.chunkGenerator(), arg.heightAccessor()));
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), i, chunkPos.getMinBlockZ());
        return JigsawPlacement.addPieces(arg, this.startPool, this.startJigsawName, this.maxDepth, blockPos, this.useExpansionHack, this.projectStartToHeightmap, this.maxDistanceFromCenter);
    }

    public @NotNull StructureType<?> type() {
        return JNEStructureType.GIANT_JIGSAW.get();
    }
}
