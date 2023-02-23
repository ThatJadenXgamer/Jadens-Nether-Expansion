package net.jadenxgamer.netherexp.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightAbleBlock extends Block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public LightAbleBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
    }

    //todo: Use MIXINS to light the block instead
    @SuppressWarnings("all")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
    PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if(!world.isClient() && itemStack.isOf(Items.FLINT_AND_STEEL)) {
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            world.setBlockState(pos, state.with(LIT, true));
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @SuppressWarnings("all")
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
