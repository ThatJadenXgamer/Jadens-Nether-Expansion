package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SporeshroomBlock
extends Block
implements Waterloggable, Fertilizable {

    // BlockStates
    public static final BooleanProperty HANGING = Properties.HANGING;
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    // VoxelShapes
    private static final VoxelShape STANDING_SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 7, 0, 16, 16, 16), Block.createCuboidShape(5, 0, 5, 11, 8, 11), BooleanBiFunction.OR);
    private static final VoxelShape HANGING_SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 0, 16, 9, 16), Block.createCuboidShape(5, 9, 5, 11, 17, 11), BooleanBiFunction.OR);

    // Particles
    /*
    Spore is the particle emitted by this block
    Smog the type of smog it emits
    */
    protected final ParticleEffect smog;
    protected final ParticleEffect spore;
    protected final TagKey<Biome> biome;

    public SporeshroomBlock(Settings settings, ParticleEffect smog, ParticleEffect spore, TagKey<Biome> biome) {
        super(settings);
        this.smog = smog;
        this.spore = spore;
        this.biome = biome;
        this.setDefaultState(this.getStateManager().getDefaultState().with(HANGING, false).with(WATERLOGGED, false).with(ACTIVE, false));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        boolean bl = state.get(HANGING);
        Vec3d vec3d = entity.getVelocity();
        if (!entity.isSneaking() && !bl) {
            entity.addVelocity(vec3d.x,1.0, vec3d.z);
            world.playSound(pos.getX(),pos.getY(),pos.getZ(), ModSoundEvents.SPORESHROOM_TRAMPOLINED, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean player = Objects.requireNonNull(ctx.getPlayer()).isSneaking();
        for (Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState;
            if (direction.getAxis() != Direction.Axis.Y || !(blockState = this.getDefaultState().with(HANGING, direction == Direction.UP)).canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) continue;
            return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(ACTIVE, !player);
        }
        return null;
    }


    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (SporeshroomBlock.attachedDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    protected static Direction attachedDirection(BlockState state) {
        return state.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = SporeshroomBlock.attachedDirection(state).getOpposite();
        return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        boolean h = state.get(HANGING);
        boolean a = state.get(ACTIVE);
        boolean b = world.getBiome(pos).isIn(this.biome);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = 0; l < 14; ++l) {
            mutable.set(i + MathHelper.nextInt(random, -20, 20), j + random.nextInt(20), k + MathHelper.nextInt(random, -20, 20));
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.isFullCube(world, mutable)) continue;
            if (a && !b) {
                world.addParticle(this.spore, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }
        if (!h && a) {
            world.addParticle(this.smog, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.1, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.008, 0.0);
        }
        else if (h && a) {
            world.addParticle(this.smog, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() - 0.05, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, -0.008, 0.0);
        }
    }


    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.0f, world.getDamageSources().fall());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HANGING, WATERLOGGED, ACTIVE);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return NetherExp.getConfig().blocks.renewableConfigs.bone_meal_sporeshroom_duplication;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Direction direction = Direction.Type.HORIZONTAL.random(random);
        BlockPos blockPos2 = pos.offset(direction);
        BlockState blockState2 = world.getBlockState(blockPos2.down());
        boolean h = state.get(HANGING);
        if (world.getBlockState(blockPos2).isAir() && (blockState2.isIn(BlockTags.NYLIUM)) && !h) {
            world.setBlockState(blockPos2, this.getDefaultState().with(HANGING, false).with(ACTIVE, true));
        }
    }
}
