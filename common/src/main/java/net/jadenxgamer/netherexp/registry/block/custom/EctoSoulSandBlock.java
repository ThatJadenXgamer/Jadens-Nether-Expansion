package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.entity.JNEBrushableBlockEntity;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.Wisp;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class EctoSoulSandBlock extends Block {
    protected static final VoxelShape COLLISION_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public static final BooleanProperty SALTED = BooleanProperty.create("salted");

    public static final ResourceLocation ARCHAEOLOGY_NETHER_WASTES = new ResourceLocation(NetherExp.MOD_ID, "archaeology/nether_wastes");
    public static final ResourceLocation ARCHAEOLOGY_SOUL_SAND_VALLEY = new ResourceLocation(NetherExp.MOD_ID, "archaeology/soul_sand_valley");
    public static final ResourceLocation ARCHAEOLOGY_CRIMSON_FOREST = new ResourceLocation(NetherExp.MOD_ID, "archaeology/crimson_forest");
    public static final ResourceLocation ARCHAEOLOGY_WARPED_FOREST = new ResourceLocation(NetherExp.MOD_ID, "archaeology/warped_forest");
    public static final ResourceLocation ARCHAEOLOGY_BASALT_DELTAS = new ResourceLocation(NetherExp.MOD_ID, "archaeology/basalt_deltas");
    public static final ResourceLocation ARCHAEOLOGY_FORTRESS = new ResourceLocation(NetherExp.MOD_ID, "archaeology/fortress");
    public static final ResourceLocation ARCHAEOLOGY_BASTION_REMNANT = new ResourceLocation(NetherExp.MOD_ID, "archaeology/bastion_remnant");

    public EctoSoulSandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SALTED, false));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(SALTED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        boolean salted = blockState.getValue(SALTED);
        boolean bl = false;
        if (!salted) {
            if (itemStack.is(Items.HONEYCOMB)) {
                level.playSound(player, blockPos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
                level.setBlock(blockPos, blockState.cycle(SALTED), Block.UPDATE_CLIENTS);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                blockParticle(level, blockPos);
                bl = true;
            }
            if (!level.isClientSide && bl) {
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            }
        }
        if (bl) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private static void blockParticle(Level level, BlockPos blockPos) {
        RandomSource randomSource = level.random;
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            BlockPos blockPos2 = blockPos.relative(direction);
            if (!level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) randomSource.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) randomSource.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) randomSource.nextFloat();
                level.addParticle(ParticleTypes.WAX_ON, (double) blockPos.getX() + e, (double) blockPos.getY() + f, (double) blockPos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        //TODO: Configs
//        int i = random.nextInt(NetherExp.getConfig().blocks.ectoSoulSandConfigs.wisp_emerging_chances);
        int i = random.nextInt(50);
        if (i == 0 && !(level.getBlockState(pos.below()).isAir())) {
            this.setSusSoulSand(level, pos, random);
            BlockPos targetPos = findAirNeighbor(level, pos);
            if (targetPos != null) {
                spawnWisp(level, targetPos, random);
            }
        }
    }

    private BlockPos findAirNeighbor(ServerLevel level, BlockPos pos) {
        Direction[] var5 = Direction.values();
        for (Direction direction : var5) {
            BlockPos neighborPos = pos.offset(direction.getNormal());
            if (level.getBlockState(neighborPos).isAir()) {
                return neighborPos;
            }
        }
        return null;
    }

    private void setSusSoulSand(Level level, BlockPos pos, RandomSource random) {
        ResourceLocation lootTable = ARCHAEOLOGY_NETHER_WASTES;
        if (level.getBiome(pos).is(Biomes.SOUL_SAND_VALLEY)) {
            lootTable = ARCHAEOLOGY_SOUL_SAND_VALLEY;
        }
        else if (level.getBiome(pos).is(Biomes.CRIMSON_FOREST)) {
            lootTable = ARCHAEOLOGY_CRIMSON_FOREST;
        }
        else if (level.getBiome(pos).is(Biomes.WARPED_FOREST)) {
            lootTable = ARCHAEOLOGY_WARPED_FOREST;
        }
        else if (level.getBiome(pos).is(Biomes.BASALT_DELTAS)) {
            lootTable = ARCHAEOLOGY_BASALT_DELTAS;
        }
        level.setBlock(pos, JNEBlocks.SUSPICIOUS_SOUL_SAND.get().defaultBlockState().setValue(JNEBrushableBlock.PERSISTENT, false), UPDATE_CLIENTS);
        JNEBrushableBlockEntity.setLootTable(level, random, pos, lootTable);
    }

    private void spawnWisp(ServerLevel level, BlockPos pos, RandomSource random) {
        Wisp wisp = JNEEntityType.WISP.get().create(level);
        if (wisp != null) {
            wisp.setBoredDelay(random.nextInt(1000) + 600);
            wisp.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            level.addFreshEntity(wisp);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SALTED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.block();
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 0.2f;
    }
}
