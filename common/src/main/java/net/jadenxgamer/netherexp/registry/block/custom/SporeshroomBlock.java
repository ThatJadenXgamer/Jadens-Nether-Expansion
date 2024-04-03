package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SporeshroomBlock
extends Block
implements SimpleWaterloggedBlock, BonemealableBlock {

    // BlockStates
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // VoxelShapes
    private static final VoxelShape STANDING_SHAPE = Shapes.join(Block.box(0, 7, 0, 16, 16, 16), Block.box(5, 0, 5, 11, 8, 11), BooleanOp.OR);
    private static final VoxelShape HANGING_SHAPE = Shapes.join(Block.box(0, 0, 0, 16, 9, 16), Block.box(5, 9, 5, 11, 17, 11), BooleanOp.OR);

    protected final int type;

    protected final TagKey<Biome> biome;

    public SporeshroomBlock(Properties properties, int type, TagKey<Biome> biome) {
        super(properties);
        this.type = type;
        this.biome = biome;
        this.registerDefaultState(this.defaultBlockState().setValue(HANGING, false).setValue(WATERLOGGED, false).setValue(ACTIVE, false));
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        boolean bl = state.getValue(HANGING);
        Vec3 vec3 = entity.getDeltaMovement();
        if (!entity.isShiftKeyDown() && !bl) {
            entity.push(vec3.x, 1.0, vec3.z);
            level.playSound(null, pos.getX(),pos.getY(),pos.getZ(), JNESoundEvents.SPORESHROOM_TRAMPOLINED.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        boolean player = Objects.requireNonNull(ctx.getPlayer()).isShiftKeyDown();
        for (Direction direction : ctx.getNearestLookingDirections()) {
            BlockState blockState;
            if (direction.getAxis() != Direction.Axis.Y || !(blockState = this.defaultBlockState().setValue(HANGING, direction == Direction.UP)).canSurvive(ctx.getLevel(), ctx.getClickedPos())) continue;
            return blockState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(ACTIVE, !player);
        }
        return null;
    }


    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (SporeshroomBlock.attachedDirection(state).getOpposite() == direction && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    protected static Direction attachedDirection(BlockState state) {
        return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = SporeshroomBlock.attachedDirection(state).getOpposite();
        return Block.canSupportCenter(level, pos.offset(direction.getNormal()), direction.getOpposite());
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        boolean h = state.getValue(HANGING);
        boolean a = state.getValue(ACTIVE);
        boolean b = level.getBiome(pos).is(this.biome);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int l = 0; l < 14; ++l) {
            mutable.set(i + Mth.nextInt(random, -20, 20), j + random.nextInt(20), k + Mth.nextInt(random, -20, 20));
            BlockState blockState = level.getBlockState(mutable);
            if (blockState.isSolidRender(level, mutable)) continue;
            if (a && !b) {
                switch (type) {
                    default: {
                        level.addParticle(ParticleTypes.CRIMSON_SPORE, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                        break;
                    }
                    case 2, 3: {
                        level.addParticle(ParticleTypes.WARPED_SPORE, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                        break;
                    }
                }
            }
        }
        if (a) {
            switch (type) {
                default: {
                    level.addParticle(JNEParticleTypes.CRIMSON_SMOG.get(), (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.1, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, (h ? -0.008 : 0.008), 0.0);
                    break;
                }
                case 2: {
                    level.addParticle(JNEParticleTypes.WARPED_SMOG.get(), (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.1, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, (h ? -0.008 : 0.008), 0.0);
                    break;
                }
                case 3: {
                    level.addParticle(JNEParticleTypes.UMBRAL_SMOG.get(), (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.1, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, (h ? -0.008 : 0.008), 0.0);
                    break;
                }
            }
        }
    }

    @Override
    public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.0f, level.damageSources().fall());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HANGING, WATERLOGGED, ACTIVE);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        // TODO: Add Configs
//        return NetherExp.getConfig().blocks.renewableConfigs.bone_meal_sporeshroom_duplication;
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos blockPos2 = pos.offset(direction.getNormal());
        BlockState blockState2 = level.getBlockState(blockPos2.below());
        boolean h = state.getValue(HANGING);
        if (level.getBlockState(blockPos2).isAir() && (blockState2.is(BlockTags.NYLIUM)) && !h) {
            level.setBlock(blockPos2, this.defaultBlockState().setValue(HANGING, false).setValue(ACTIVE, true), UPDATE_CLIENTS);
        }
    }
}
