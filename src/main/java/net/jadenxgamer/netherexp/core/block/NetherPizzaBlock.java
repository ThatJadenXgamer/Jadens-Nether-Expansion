package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetherPizzaBlock extends Block {

    public static final IntegerProperty SLICES = IntegerProperty.create("slices", 1, 4);

    public static final VoxelShape SHAPE_4 = Block.box(-1, 0, -1, 17, 2, 17);
    public static final VoxelShape SHAPE_3 = Shapes.join(Block.box(-1, 0, 8, 8, 2, 17), Block.box(-1, 0, -1, 17, 2, 8), BooleanOp.OR);
    public static final VoxelShape SHAPE_2 = Block.box(-1, 0, -1, 17, 2, 8);
    public static final VoxelShape SHAPE_1 = Block.box(-1, 0, -1, 8, 2, 8);

    public NetherPizzaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SLICES, 4));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(SLICES)) {
            case 1 -> SHAPE_1;
            case 2 -> SHAPE_2;
            case 3 -> SHAPE_3;
            default -> SHAPE_4;
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!player.isSecondaryUseActive()) {
            int slices = state.getValue(SLICES);
            if (player.addItem(new ItemStack(JNEItems.NETHER_PIZZA_SLICE.get()))) {
                if (slices > 1) {
                    level.setBlock(pos, state.setValue(SLICES, slices - 1), Block.UPDATE_ALL);
                } else {
                    level.removeBlock(pos, false);
                    level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return state.getValue(SLICES) * 3;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SLICES);
    }
}
