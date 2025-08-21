package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class LesionBlock extends Block {

    public static final IntegerProperty SLICES = IntegerProperty.create("slices", 1, 4);
    protected static final VoxelShape SHAPE_4 = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SHAPE_3 = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    protected static final VoxelShape SHAPE_2 = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    protected static final VoxelShape SHAPE_1 = Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
    private final Supplier<Item> lesionOf;

    public LesionBlock(Supplier<Item> lesionOf, Properties properties) {
        super(properties);
        this.lesionOf = lesionOf;
        this.registerDefaultState(this.defaultBlockState().setValue(SLICES, 4));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(SLICES)) {
            case 1 -> SHAPE_1;
            case 2 -> SHAPE_2;
            case 3 -> SHAPE_3;
            default -> SHAPE_4;
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.isEmpty()) {
            int slices = state.getValue(SLICES);
            if (slices == 1) {
                level.removeBlock(pos, false);
            } else {
                level.setBlock(pos, state.setValue(SLICES, slices - 1), Block.UPDATE_ALL);
            }
            popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(lesionOf.get(), JNEConfigs.LESION_DROPS_PER_HARVEST.get()));
            level.playSound(null, pos, JNESoundEvents.LESION_BLOCK_HARVEST.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.SOUL);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(SLICES) < 4;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() > JNEConfigs.LESION_GROWTH_CHANCE.get()) return;
        int slices = state.getValue(SLICES);

        level.setBlock(pos, state.setValue(SLICES, slices + 1), Block.UPDATE_ALL);
        level.playSound(null, pos, JNESoundEvents.LESION_BLOCK_GROWS.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        level.levelEvent(2005, pos, 8);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return state.getValue(SLICES) * 3;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SLICES);
    }
}
