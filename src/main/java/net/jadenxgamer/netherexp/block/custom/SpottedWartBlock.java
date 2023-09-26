package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SpottedWartBlock extends Block {
    public static final IntProperty SPOTS = IntProperty.of("spots", 1, 3);

    protected final Block base;
    protected final int spore;

    public SpottedWartBlock(Settings settings, Block base, int spore) {
        super(settings);
        this.base = base;
        this.spore = spore;
        this.setDefaultState(this.stateManager.getDefaultState().with(SPOTS, 1));
    }

    public static void dropNight(World world, BlockPos pos, BlockState state) {
        int s = state.get(SPOTS);
        SpottedWartBlock.dropStack(world, pos, new ItemStack(ModItems.NIGHTSPORES, s));
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        int s = state.get(SPOTS);
        boolean bl = false;
        if (itemStack.isOf(Items.SHEARS)) {
            if (this.spore == 2) {
                SpottedWartBlock.dropNight(world, pos, state);
            }
            world.playSound(player, pos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.setBlockState(pos, this.base.getDefaultState(), Block.NOTIFY_LISTENERS);
            world.emitGameEvent(player, GameEvent.SHEAR, pos);
            itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            bl = true;
            }
            if (!world.isClient() && bl) {
                player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            }
        if (bl) {
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    public boolean maxSpots(BlockState state) {
        return state.get(SPOTS) == 3;
    }

    public BlockState setSpots(BlockState state) {
        int s = state.get(SPOTS);
        if (s == 1) {
            return state.with(SPOTS, 2);
        }
        return state.with(SPOTS, 3);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SPOTS);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.base);
    }
}
