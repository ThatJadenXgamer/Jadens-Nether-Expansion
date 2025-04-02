package net.jadenxgamer.netherexp.registry.block.custom;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class AncientCandleBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty LIT = AbstractSoulCandleBlock.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final Vec3 PARTICLE_OFFSET = new Vec3(0.5, 1.1, 0.5);

    private static final VoxelShape SHAPE = Block.box(5.5, 0, 5.5, 10.5, 16, 10.5);

    public AncientCandleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false).setValue(WATERLOGGED, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        boolean lit = state.getValue(LIT);
        boolean bl = false;
        if (player.getAbilities().mayBuild && itemStack.isEmpty() && lit) {
            AncientCandleBlock.extinguish(player, state, level, pos);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else if (itemStack.is(Items.FLINT_AND_STEEL) && !lit && canBeLit(state)) {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
            level.setBlock(pos, state.cycle(LIT), Block.UPDATE_CLIENTS);
            if (!player.isCreative()) {
                itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }
            bl = true;
        } else if (itemStack.is(Items.FIRE_CHARGE) && !lit && this.canBeLit(state)){
            level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
            level.setBlock(pos, state.cycle(LIT), Block.UPDATE_CLIENTS);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
            bl = true;
        }
        if (bl) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean bl = fluidState.getType() == Fluids.WATER;
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(WATERLOGGED, bl);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
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
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.getValue(WATERLOGGED) || fluidState.getType() != Fluids.WATER) {
            return false;
        }
        BlockState newState = state.setValue(WATERLOGGED, true);
        if (newState.getValue(LIT)) {
            AncientCandleBlock.extinguish(null, state, level, pos);
        } else {
            level.setBlock(pos, state, Block.UPDATE_ALL);
        }
        level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
        return true;
    }

    @SuppressWarnings("deprecation")
    protected Vec3 getParticleOffset() {
        return PARTICLE_OFFSET;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportCenter(level, pos.below(), Direction.UP);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
        if (!level.isClientSide && projectile.isOnFire() && !state.getValue(LIT)) {
            setLit(level, state, hitResult.getBlockPos(), true);
        }
    }

    protected boolean canBeLit(BlockState state) {
        return !(Boolean)state.getValue(LIT);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT) && !level.getBlockState(pos.above()).is(JNEBlocks.ANCIENT_CANDLE.get())) {
            addParticlesAndSound(level, getParticleOffset().add(pos.getX(), pos.getY(), pos.getZ()), random);
        }
    }

    private static void addParticlesAndSound(Level level, Vec3 vec3, RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            level.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                level.playLocalSound(vec3.x + 0.5, vec3.y + 0.5, vec3.z + 0.5, JNESoundEvents.BLOCK_SOUL_CANDLE_AMBIENT.get(), SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        level.addParticle(JNEParticleTypes.TREACHEROUS_FLAME.get(), vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
    }

    public static void extinguish(Player player, BlockState state, LevelAccessor level, BlockPos pos) {
        setLit(level, state, pos, false);
        if (state.getBlock() instanceof AbstractSoulCandleBlock) {
            ((AbstractSoulCandleBlock)state.getBlock()).getParticleOffsets(state).forEach((vec3) -> level.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + vec3.x(), (double)pos.getY() + vec3.y(), (double)pos.getZ() + vec3.z(), 0.0, 0.10000000149011612, 0.0));
        }

        level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }


    private static void setLit(LevelAccessor level, BlockState state, BlockPos pos, boolean lit) {
        level.setBlock(pos, state.setValue(LIT, lit), Block.UPDATE_ALL);
    }
}
