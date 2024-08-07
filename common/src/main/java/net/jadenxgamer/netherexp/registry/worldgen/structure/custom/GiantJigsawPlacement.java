package net.jadenxgamer.netherexp.registry.worldgen.structure.custom;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

import java.util.*;

public class GiantJigsawPlacement {
    static final Logger LOGGER = LogUtils.getLogger();

    public GiantJigsawPlacement() {
    }

    public static Optional<Structure.GenerationStub> addPieces(Structure.GenerationContext context, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, BlockPos pos, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
        RegistryAccess registryAccess = context.registryAccess();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        StructureTemplateManager structureTemplateManager = context.structureTemplateManager();
        LevelHeightAccessor levelHeightAccessor = context.heightAccessor();
        WorldgenRandom worldgenRandom = context.random();
        Registry<StructureTemplatePool> registry = registryAccess.registryOrThrow(Registries.TEMPLATE_POOL);
        Rotation rotation = Rotation.getRandom(worldgenRandom);
        StructureTemplatePool structureTemplatePool = startPool.value();
        StructurePoolElement structurePoolElement = structureTemplatePool.getRandomTemplate(worldgenRandom);
        if (structurePoolElement == EmptyPoolElement.INSTANCE) {
            return Optional.empty();
        } else {
            BlockPos blockPos2;
            if (startJigsawName.isPresent()) {
                ResourceLocation resourceLocation = startJigsawName.get();
                Optional<BlockPos> optional3 = getRandomNamedJigsaw(structurePoolElement, resourceLocation, pos, rotation, structureTemplateManager, worldgenRandom);
                if (optional3.isEmpty()) {
                    LOGGER.error("No starting jigsaw {} found in start pool {}", resourceLocation, startPool.unwrapKey().map((resourceKey) -> resourceKey.location().toString()).orElse("<unregistered>"));
                    return Optional.empty();
                }

                blockPos2 = optional3.get();
            } else {
                blockPos2 = pos;
            }

            Vec3i vec3i = blockPos2.subtract(pos);
            BlockPos blockPos3 = pos.subtract(vec3i);
            PoolElementStructurePiece poolElementStructurePiece = new PoolElementStructurePiece(structureTemplateManager, structurePoolElement, blockPos3, structurePoolElement.getGroundLevelDelta(), rotation, structurePoolElement.getBoundingBox(structureTemplateManager, blockPos3, rotation));
            BoundingBox boundingBox = poolElementStructurePiece.getBoundingBox();
            int k = (boundingBox.maxX() + boundingBox.minX()) / 2;
            int l = (boundingBox.maxZ() + boundingBox.minZ()) / 2;
            int m;
            if (projectStartToHeightmap.isPresent()) {
                m = pos.getY() + chunkGenerator.getFirstFreeHeight(k, l, projectStartToHeightmap.get(), levelHeightAccessor, context.randomState());
            } else {
                m = blockPos3.getY();
            }

            int n = boundingBox.minY() + poolElementStructurePiece.getGroundLevelDelta();
            poolElementStructurePiece.move(0, m - n, 0);
            int o = m + vec3i.getY();
            return Optional.of(new Structure.GenerationStub(new BlockPos(k, o, l), (structurePiecesBuilder) -> {
                List<PoolElementStructurePiece> list = Lists.newArrayList();
                list.add(poolElementStructurePiece);
                if (maxDepth > 0) {
                    AABB aABB = new AABB(k - maxDistanceFromCenter, o - 64, l - maxDistanceFromCenter, (k + maxDistanceFromCenter + 1), (o + 64 + 1), (l + maxDistanceFromCenter + 1));
                    VoxelShape voxelShape = Shapes.join(Shapes.create(aABB), Shapes.create(AABB.of(boundingBox)), BooleanOp.ONLY_FIRST);
                    addPiecesChild(context.randomState(), maxDepth, useExpansionHack, chunkGenerator, structureTemplateManager, levelHeightAccessor, worldgenRandom, registry, poolElementStructurePiece, list, voxelShape);
                    Objects.requireNonNull(structurePiecesBuilder);
                    list.forEach(structurePiecesBuilder::addPiece);
                }
            }));
        }
    }

    private static Optional<BlockPos> getRandomNamedJigsaw(StructurePoolElement structurePoolElement, ResourceLocation resourceLocation, BlockPos blockPos, Rotation rotation, StructureTemplateManager structureTemplateManager, WorldgenRandom worldgenRandom) {
        List<StructureTemplate.StructureBlockInfo> list = structurePoolElement.getShuffledJigsawBlocks(structureTemplateManager, blockPos, rotation, worldgenRandom);
        Optional<BlockPos> optional = Optional.empty();
        Iterator var8 = list.iterator();

        while(var8.hasNext()) {
            StructureTemplate.StructureBlockInfo structureBlockInfo = (StructureTemplate.StructureBlockInfo)var8.next();
            ResourceLocation resourceLocation2 = ResourceLocation.tryParse(structureBlockInfo.nbt().getString("name"));
            if (resourceLocation.equals(resourceLocation2)) {
                optional = Optional.of(structureBlockInfo.pos());
                break;
            }
        }

        return optional;
    }

    private static void addPiecesChild(RandomState randomState, int i, boolean bl, ChunkGenerator chunkGenerator, StructureTemplateManager structureTemplateManager, LevelHeightAccessor levelHeightAccessor, RandomSource randomSource, Registry<StructureTemplatePool> registry, PoolElementStructurePiece poolElementStructurePiece, List<PoolElementStructurePiece> list, VoxelShape voxelShape) {
        GiantJigsawPlacement.Placer placer = new GiantJigsawPlacement.Placer(registry, i, chunkGenerator, structureTemplateManager, list, randomSource);
        placer.placing.addLast(new GiantJigsawPlacement.PieceState(poolElementStructurePiece, new MutableObject(voxelShape), 0));

        while(!placer.placing.isEmpty()) {
            GiantJigsawPlacement.PieceState pieceState = (GiantJigsawPlacement.PieceState)placer.placing.removeFirst();
            placer.tryPlacingChildren(pieceState.piece, pieceState.free, pieceState.depth, bl, levelHeightAccessor, randomState);
        }

    }

    public static boolean generateJigsaw(ServerLevel serverLevel, Holder<StructureTemplatePool> holder, ResourceLocation resourceLocation, int i, BlockPos blockPos, boolean bl) {
        ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
        StructureTemplateManager structureTemplateManager = serverLevel.getStructureManager();
        StructureManager structureManager = serverLevel.structureManager();
        RandomSource randomSource = serverLevel.getRandom();
        Structure.GenerationContext generationContext = new Structure.GenerationContext(serverLevel.registryAccess(), chunkGenerator, chunkGenerator.getBiomeSource(), serverLevel.getChunkSource().randomState(), structureTemplateManager, serverLevel.getSeed(), new ChunkPos(blockPos), serverLevel, (holderx) -> {
            return true;
        });
        Optional<Structure.GenerationStub> optional = addPieces(generationContext, holder, Optional.of(resourceLocation), i, blockPos, false, Optional.empty(), 512);
        if (optional.isPresent()) {
            StructurePiecesBuilder structurePiecesBuilder = optional.get().getPiecesBuilder();
            Iterator var13 = structurePiecesBuilder.build().pieces().iterator();

            while(var13.hasNext()) {
                StructurePiece structurePiece = (StructurePiece)var13.next();
                if (structurePiece instanceof PoolElementStructurePiece) {
                    PoolElementStructurePiece poolElementStructurePiece = (PoolElementStructurePiece)structurePiece;
                    poolElementStructurePiece.place(serverLevel, structureManager, chunkGenerator, randomSource, BoundingBox.infinite(), blockPos, bl);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    static final class Placer {
        private final Registry<StructureTemplatePool> pools;
        private final int maxDepth;
        private final ChunkGenerator chunkGenerator;
        private final StructureTemplateManager structureTemplateManager;
        private final List<? super PoolElementStructurePiece> pieces;
        private final RandomSource random;
        final Deque<GiantJigsawPlacement.PieceState> placing = Queues.newArrayDeque();

        Placer(Registry<StructureTemplatePool> registry, int i, ChunkGenerator chunkGenerator, StructureTemplateManager structureTemplateManager, List<? super PoolElementStructurePiece> list, RandomSource randomSource) {
            this.pools = registry;
            this.maxDepth = i;
            this.chunkGenerator = chunkGenerator;
            this.structureTemplateManager = structureTemplateManager;
            this.pieces = list;
            this.random = randomSource;
        }

        void tryPlacingChildren(PoolElementStructurePiece poolElementStructurePiece, MutableObject<VoxelShape> sizeLimit, int i, boolean bl, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
            StructurePoolElement structurePoolElement = poolElementStructurePiece.getElement();
            BlockPos blockPos = poolElementStructurePiece.getPosition();
            Rotation rotation = poolElementStructurePiece.getRotation();
            StructureTemplatePool.Projection projection = structurePoolElement.getProjection();
            boolean bl2 = projection == StructureTemplatePool.Projection.RIGID;
            MutableObject<VoxelShape> mutableObject2 = new MutableObject();
            BoundingBox boundingBox = poolElementStructurePiece.getBoundingBox();
            int j = boundingBox.minY();
            Iterator var15 = structurePoolElement.getShuffledJigsawBlocks(this.structureTemplateManager, blockPos, rotation, this.random).iterator();

            while(true) {
                label129:
                while(var15.hasNext()) {
                    StructureTemplate.StructureBlockInfo structureBlockInfo = (StructureTemplate.StructureBlockInfo)var15.next();
                    Direction direction = JigsawBlock.getFrontFacing(structureBlockInfo.state());
                    BlockPos blockPos2 = structureBlockInfo.pos();
                    BlockPos blockPos3 = blockPos2.relative(direction);
                    int k = blockPos2.getY() - j;
                    int l = -1;
                    ResourceKey<StructureTemplatePool> resourceKey = readPoolName(structureBlockInfo);
                    Optional<? extends Holder<StructureTemplatePool>> optional = this.pools.getHolder(resourceKey);
                    if (optional.isEmpty()) {
                        GiantJigsawPlacement.LOGGER.warn("Empty or non-existent pool: {}", resourceKey.location());
                    } else {
                        Holder<StructureTemplatePool> holder = (Holder)optional.get();
                        if (((StructureTemplatePool)holder.value()).size() == 0 && !holder.is(Pools.EMPTY)) {
                            GiantJigsawPlacement.LOGGER.warn("Empty or non-existent pool: {}", resourceKey.location());
                        } else {
                            Holder<StructureTemplatePool> holder2 = ((StructureTemplatePool)holder.value()).getFallback();
                            if (((StructureTemplatePool)holder2.value()).size() == 0 && !holder2.is(Pools.EMPTY)) {
                                GiantJigsawPlacement.LOGGER.warn("Empty or non-existent fallback pool: {}", holder2.unwrapKey().map((resourceKeyx) -> {
                                    return resourceKeyx.location().toString();
                                }).orElse("<unregistered>"));
                            } else {
                                boolean bl3 = boundingBox.isInside(blockPos3);
                                MutableObject mutableObject3;
                                if (bl3) {
                                    mutableObject3 = mutableObject2;
                                    if (mutableObject2.getValue() == null) {
                                        mutableObject2.setValue(Shapes.create(AABB.of(boundingBox)));
                                    }
                                } else {
                                    mutableObject3 = sizeLimit;
                                }

                                List<StructurePoolElement> list = Lists.newArrayList();
                                if (i != this.maxDepth) {
                                    list.addAll(((StructureTemplatePool)holder.value()).getShuffledTemplates(this.random));
                                }

                                list.addAll(((StructureTemplatePool)holder2.value()).getShuffledTemplates(this.random));
                                Iterator var29 = list.iterator();

                                while(var29.hasNext()) {
                                    StructurePoolElement structurePoolElement2 = (StructurePoolElement)var29.next();
                                    if (structurePoolElement2 == EmptyPoolElement.INSTANCE) {
                                        break;
                                    }

                                    Iterator var31 = Rotation.getShuffled(this.random).iterator();

                                    label125:
                                    while(var31.hasNext()) {
                                        Rotation rotation2 = (Rotation)var31.next();
                                        List<StructureTemplate.StructureBlockInfo> list2 = structurePoolElement2.getShuffledJigsawBlocks(this.structureTemplateManager, BlockPos.ZERO, rotation2, this.random);
                                        BoundingBox boundingBox2 = structurePoolElement2.getBoundingBox(this.structureTemplateManager, BlockPos.ZERO, rotation2);
                                        int m;
                                        if (bl && boundingBox2.getYSpan() <= 16) {
                                            m = list2.stream().mapToInt((structureBlockInfox) -> {
                                                if (!boundingBox2.isInside(structureBlockInfox.pos().relative(JigsawBlock.getFrontFacing(structureBlockInfox.state())))) {
                                                    return 0;
                                                } else {
                                                    ResourceKey<StructureTemplatePool> templatePoolResourceKey = readPoolName(structureBlockInfox);
                                                    Optional<? extends Holder<StructureTemplatePool>> optionalJ1 = this.pools.getHolder(templatePoolResourceKey);
                                                    Optional<Holder<StructureTemplatePool>> optional2 = optionalJ1.map((holderJ3) -> {
                                                        return ((StructureTemplatePool)holderJ3.value()).getFallback();
                                                    });
                                                    int p = (Integer)optionalJ1.map((holderJ1) -> {
                                                        return ((StructureTemplatePool)holderJ1.value()).getMaxSize(this.structureTemplateManager);
                                                    }).orElse(0);
                                                    int h = (Integer)optional2.map((holderJ2) -> {
                                                        return ((StructureTemplatePool)holderJ2.value()).getMaxSize(this.structureTemplateManager);
                                                    }).orElse(0);
                                                    return Math.max(p, h);
                                                }
                                            }).max().orElse(0);
                                        } else {
                                            m = 0;
                                        }

                                        Iterator var36 = list2.iterator();

                                        StructureTemplatePool.Projection projection2;
                                        boolean bl4;
                                        int o;
                                        int p;
                                        int q;
                                        BoundingBox boundingBox4;
                                        BlockPos blockPos6;
                                        int s;
                                        do {
                                            StructureTemplate.StructureBlockInfo structureBlockInfo2;
                                            do {
                                                if (!var36.hasNext()) {
                                                    continue label125;
                                                }

                                                structureBlockInfo2 = (StructureTemplate.StructureBlockInfo)var36.next();
                                            } while(!JigsawBlock.canAttach(structureBlockInfo, structureBlockInfo2));

                                            BlockPos blockPos4 = structureBlockInfo2.pos();
                                            BlockPos blockPos5 = blockPos3.subtract(blockPos4);
                                            BoundingBox boundingBox3 = structurePoolElement2.getBoundingBox(this.structureTemplateManager, blockPos5, rotation2);
                                            int n = boundingBox3.minY();
                                            projection2 = structurePoolElement2.getProjection();
                                            bl4 = projection2 == StructureTemplatePool.Projection.RIGID;
                                            o = blockPos4.getY();
                                            p = k - o + JigsawBlock.getFrontFacing(structureBlockInfo.state()).getStepY();
                                            if (bl2 && bl4) {
                                                q = j + p;
                                            } else {
                                                if (l == -1) {
                                                    l = this.chunkGenerator.getFirstFreeHeight(blockPos2.getX(), blockPos2.getZ(), Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor, randomState);
                                                }

                                                q = l - o;
                                            }

                                            int r = q - n;
                                            boundingBox4 = boundingBox3.moved(0, r, 0);
                                            blockPos6 = blockPos5.offset(0, r, 0);
                                            if (m > 0) {
                                                s = Math.max(m + 1, boundingBox4.maxY() - boundingBox4.minY());
                                                boundingBox4.encapsulate(new BlockPos(boundingBox4.minX(), boundingBox4.minY() + s, boundingBox4.minZ()));
                                            }
                                        } while(Shapes.joinIsNotEmpty((VoxelShape)mutableObject3.getValue(), Shapes.create(AABB.of(boundingBox4)), BooleanOp.ONLY_SECOND));

                                        mutableObject3.setValue(Shapes.joinUnoptimized((VoxelShape)mutableObject3.getValue(), Shapes.create(AABB.of(boundingBox4)), BooleanOp.ONLY_FIRST));
                                        s = poolElementStructurePiece.getGroundLevelDelta();
                                        int t;
                                        if (bl4) {
                                            t = s - p;
                                        } else {
                                            t = structurePoolElement2.getGroundLevelDelta();
                                        }

                                        PoolElementStructurePiece poolElementStructurePiece2 = new PoolElementStructurePiece(this.structureTemplateManager, structurePoolElement2, blockPos6, t, rotation2, boundingBox4);
                                        int u;
                                        if (bl2) {
                                            u = j + k;
                                        } else if (bl4) {
                                            u = q + o;
                                        } else {
                                            if (l == -1) {
                                                l = this.chunkGenerator.getFirstFreeHeight(blockPos2.getX(), blockPos2.getZ(), Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor, randomState);
                                            }

                                            u = l + p / 2;
                                        }

                                        poolElementStructurePiece.addJunction(new JigsawJunction(blockPos3.getX(), u - k + s, blockPos3.getZ(), p, projection2));
                                        poolElementStructurePiece2.addJunction(new JigsawJunction(blockPos2.getX(), u - o + t, blockPos2.getZ(), -p, projection));
                                        this.pieces.add(poolElementStructurePiece2);
                                        if (i + 1 <= this.maxDepth) {
                                            this.placing.addLast(new GiantJigsawPlacement.PieceState(poolElementStructurePiece2, mutableObject3, i + 1));
                                        }
                                        continue label129;
                                    }
                                }
                            }
                        }
                    }
                }

                return;
            }
        }

        private static ResourceKey<StructureTemplatePool> readPoolName(StructureTemplate.StructureBlockInfo structureBlockInfo) {
            return ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation(structureBlockInfo.nbt().getString("pool")));
        }
    }

    static final class PieceState {
        final PoolElementStructurePiece piece;
        final MutableObject<VoxelShape> free;
        final int depth;

        PieceState(PoolElementStructurePiece poolElementStructurePiece, MutableObject<VoxelShape> mutableObject, int i) {
            this.piece = poolElementStructurePiece;
            this.free = mutableObject;
            this.depth = i;
        }
    }
}
