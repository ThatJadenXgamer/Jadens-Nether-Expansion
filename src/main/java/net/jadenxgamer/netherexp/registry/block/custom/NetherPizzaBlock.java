package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class NetherPizzaBlock extends Block {
    public static final IntProperty SLICES = IntProperty.of("slices", 1, 4);

    protected static final VoxelShape ONE_SHAPE = Block.createCuboidShape(0, 0, 0, 8, 2, 8);
    protected static final VoxelShape TWO_SHAPE = Block.createCuboidShape(0, 0, 0, 8, 2, 16);
    protected static final VoxelShape THREE_SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 8, 16, 2, 16), Block.createCuboidShape(0, 0, 0, 8, 2, 8), BooleanBiFunction.OR);
    protected static final VoxelShape FOUR_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 2, 16);

    public NetherPizzaBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SLICES, 4));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(SLICES)) {
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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        int s = state.get(SLICES);
        if (itemStack.isEmpty()) {
            player.setStackInHand(hand, new ItemStack(JNEItems.NETHER_PIZZA_SLICE));
            if (s == 1) {
                world.removeBlock(pos, false);
            }
            else {
                world.setBlockState(pos, this.getDefaultState().with(SLICES, s - 1), NOTIFY_LISTENERS);
            }
            return ActionResult.SUCCESS;
        }
        else return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SLICES);
    }
}
