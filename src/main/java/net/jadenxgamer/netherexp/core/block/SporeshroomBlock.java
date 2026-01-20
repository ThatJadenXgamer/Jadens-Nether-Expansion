package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.elysium_api.api.util.LookupRegistryHelper;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SporeshroomBlock extends Block implements SimpleWaterloggedBlock, BonemealableBlock {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape STANDING_SHAPE = Shapes.join(Block.box(0, 7, 0, 16, 16, 16), Block.box(5, 0, 5, 11, 8, 11), BooleanOp.OR);
    private static final VoxelShape HANGING_SHAPE = Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(2, 8, 2, 14, 16, 14), BooleanOp.OR);

    private final Supplier<SimpleParticleType> sporeParticle;
    private final Supplier<SimpleParticleType> smogParticle;
    protected final TagKey<Biome> homeBiome;

    /**
     * Sporeshroom constructor for normal usage
     * @param homeBiome biomes in which the block won't produce spore particles
     * @param sporeParticle the kind of spore particles it produces
     * @param smogParticle the kind of smog it emits from its cap
     * @param properties block properties
     */
    public SporeshroomBlock(TagKey<Biome> homeBiome, Supplier<SimpleParticleType> sporeParticle, Supplier<SimpleParticleType> smogParticle, Properties properties) {
        super(properties);
        this.homeBiome = homeBiome;
        this.sporeParticle = sporeParticle;
        this.smogParticle = smogParticle;
        this.registerDefaultState(this.defaultBlockState().setValue(HANGING, false).setValue(WATERLOGGED, false).setValue(ACTIVE, false));
    }

    /**
     * Sporeshroom constructor mostly reserved for mod compat, it retrieves the spore particles dynamically from just the registered id
     * @param homeBiome biomes in which the block won't produce spore particles
     * @param sporeParticleId define a {@link ResourceLocation} for the spore particles it produces
     * @param smogParticle the kind of smog it emits from its cap
     * @param properties block properties
     */
    public SporeshroomBlock(TagKey<Biome> homeBiome, ResourceLocation sporeParticleId, Supplier<SimpleParticleType> smogParticle, Properties properties) {
        super(properties);
        this.homeBiome = homeBiome;
        this.sporeParticle = () -> getSporeFromResourceLocation(sporeParticleId);
        this.smogParticle = smogParticle;
        this.registerDefaultState(this.defaultBlockState().setValue(HANGING, false).setValue(WATERLOGGED, false).setValue(ACTIVE, false));
    }

    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        Vec3 velocity = entity.getDeltaMovement();
        if (!state.getValue(HANGING) && !entity.isShiftKeyDown()) {
            entity.push(velocity.x, JNEConfigs.SPORESHROOM_HEIGHT_VELOCITY.get(), velocity.z);
            level.playSound(null, pos, JNESoundEvents.SPORESHROOM_TRAMPOLINED.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        if (SporeshroomBlock.attachedDirection(state).getOpposite() == direction && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
        boolean hanging = context.getClickedFace() == Direction.DOWN;

        return this.defaultBlockState().setValue(HANGING, hanging).setValue(WATERLOGGED, fluid.is(Fluids.WATER)).setValue(ACTIVE, !sneaking);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = SporeshroomBlock.attachedDirection(state).getOpposite();
        return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) return Fluids.WATER.getSource(false);
        return super.getFluidState(state);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(ACTIVE)) return;

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        boolean inHomeBiome = level.getBiome(pos).is(this.homeBiome);
        BlockPos.MutableBlockPos mPos = new BlockPos.MutableBlockPos();
        if (!inHomeBiome) {
            for (int i = 0; i < 14; ++i) {
                mPos.set(x + Mth.nextInt(random, -20, 20), y + random.nextInt(20), z + Mth.nextInt(random, -20, 20));
                BlockState mutableState = level.getBlockState(mPos);
                if (mutableState.isSolidRender(level, mPos)) continue;
                level.addParticle(sporeParticle.get(), mPos.getX() + random.nextDouble(), mPos.getY() + random.nextDouble(), mPos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }

        if (random.nextInt(2) == 0) {
            level.addParticle(smogParticle.get(), x + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1), y + 1.1, z + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1), 0.0, (state.getValue(HANGING) ? -0.008 : 0.008), 0.0);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos relativePos = pos.relative(direction);

        if (level.getBlockState(relativePos).isAir() && state.canSurvive(level, relativePos)) {
            level.setBlock(relativePos, state, Block.UPDATE_ALL);
        }
    }

    private SimpleParticleType getSporeFromResourceLocation(ResourceLocation id) {
        ParticleType<?> particle = LookupRegistryHelper.getParticleType(id);
        if (particle instanceof SimpleParticleType simpleParticleType) {
            return simpleParticleType;
        }
        return ParticleTypes.CRIMSON_SPORE; // Fallbacks to crimson spore if the provided particle is not a SimpleParticleType
    }

    private static Direction attachedDirection(BlockState state) {
        return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE, HANGING, WATERLOGGED);
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}
