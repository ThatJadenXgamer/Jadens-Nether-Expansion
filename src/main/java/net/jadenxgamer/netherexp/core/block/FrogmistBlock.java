package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.enums.Liquidlogged;
import net.jadenxgamer.netherexp.core.block.interfaces.LiquidloggedBlock;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FrogmistBlock extends Block implements LiquidloggedBlock {
    public static final EnumProperty<Liquidlogged.AllFluids> LIQUIDLOGGED = EnumProperty.create("liquidlogged", Liquidlogged.AllFluids.class);

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 2, 16);

    public FrogmistBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIQUIDLOGGED, Liquidlogged.AllFluids.AIR));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (JNEConfigImpl.COMMON.isLoaded() && JNEConfigs.CAN_ANYTHING_BREAK_FROGMIST.get()) return SHAPE;
        else if (((EntityCollisionContext) context).getEntity() instanceof Player player && player.getMainHandItem().is(JNETags.Items.FROGMIST_VISIBLE_ITEMS)) {
            return SHAPE;
        }
        return Shapes.empty();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        Liquidlogged.AllFluids liquidlogged = Liquidlogged.AllFluids.AIR;
        if (fluidState.getType() == Fluids.WATER) {
            liquidlogged = Liquidlogged.AllFluids.WATER;
        } else if (fluidState.getType() == Fluids.LAVA) {
            liquidlogged = Liquidlogged.AllFluids.LAVA;
        } else if (fluidState.getType() == JNEFluids.ECTOPLASM_SOURCE.get()) {
            liquidlogged = Liquidlogged.AllFluids.ECTOPLASM;
        }
        return this.defaultBlockState().setValue(LIQUIDLOGGED, liquidlogged);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if ((state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.WATER)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        } else if ((state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.LAVA)) {
            level.scheduleTick(pos, Fluids.LAVA, Fluids.LAVA.getTickDelay(level));
        } else if ((state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.ECTOPLASM)) {
            level.scheduleTick(pos, JNEFluids.ECTOPLASM_SOURCE.get(), JNEFluids.ECTOPLASM_SOURCE.get().getTickDelay(level));
        }

        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.WATER) {
            return Fluids.WATER.getSource(true);
        } else if (state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.LAVA) {
            return Fluids.LAVA.getSource(true);
        } else if (state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.ECTOPLASM) {
            return JNEFluids.ECTOPLASM_SOURCE.get().getSource(true);
        } else {
            return Fluids.EMPTY.defaultFluidState();
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP) || !(level.getFluidState(pos.below()).getType() == Fluids.EMPTY);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIQUIDLOGGED);
    }

    @Override
    public EnumProperty<Liquidlogged.AllFluids> getLiquidloggedBlockState() {
        return LIQUIDLOGGED;
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
        level.playSound(null, pos, this.soundType.getBreakSound(), SoundSource.BLOCKS, this.soundType.volume, this.soundType.pitch);
    }
}
