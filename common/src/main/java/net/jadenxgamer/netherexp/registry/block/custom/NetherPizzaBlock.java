package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class NetherPizzaBlock extends Block {
    public static final IntegerProperty SLICES = IntegerProperty.create("slices", 1, 4);

    protected static final VoxelShape ONE_SHAPE = Block.box(0, 0, 0, 8, 2, 8);
    protected static final VoxelShape TWO_SHAPE = Block.box(0, 0, 0, 8, 2, 16);
    protected static final VoxelShape THREE_SHAPE = Shapes.join(Block.box(0, 0, 8, 16, 2, 16), Block.box(0, 0, 0, 8, 2, 8), BooleanOp.OR);
    protected static final VoxelShape FOUR_SHAPE = Block.box(0, 0, 0, 16, 2, 16);

    public NetherPizzaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SLICES, 4));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {
        switch (blockState.getValue(SLICES)) {
            default: {
                return FOUR_SHAPE;
            }
            case 3: {
                return THREE_SHAPE;
            }
            case 2: {
                return TWO_SHAPE;
            }
            case 1:
        }
        return ONE_SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        int s = blockState.getValue(SLICES);
        if (itemStack.isEmpty()) {
            player.setItemInHand(hand, new ItemStack(JNEItems.NETHER_PIZZA_SLICE.get()));
            if (s == 1) {
                level.removeBlock(blockPos, false);
            }
            else {
                level.setBlock(blockPos, this.defaultBlockState().setValue(SLICES, s - 1), UPDATE_CLIENTS);
            }
            return InteractionResult.SUCCESS;
        }
        else return super.use(blockState, level, blockPos, player, hand, blockHitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SLICES);
    }
}
