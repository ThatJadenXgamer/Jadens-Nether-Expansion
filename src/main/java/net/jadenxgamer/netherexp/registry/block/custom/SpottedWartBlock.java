package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
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

    // Base is the vanilla block the Spotted Wart is related to
    protected final Block base;

    /*
     * Spore value dictates what kind of spore to drop when sheared
     * 1 = Lightspores
     * 2 = Nightspores
    */
    protected final int spore;

    public SpottedWartBlock(Settings settings, Block base, int spore) {
        super(settings);
        this.base = base;
        this.spore = spore;
        this.setDefaultState(this.stateManager.getDefaultState().with(SPOTS, 1));
    }

    private static void dropLight(World world, BlockPos pos, BlockState state) {
        int s = state.get(SPOTS);
        SpottedWartBlock.dropStack(world, pos, new ItemStack(JNEItems.LIGHTSPORES, s));
    }
    private static void dropNight(World world, BlockPos pos, BlockState state) {
        int s = state.get(SPOTS);
        SpottedWartBlock.dropStack(world, pos, new ItemStack(JNEItems.NIGHTSPORES, s));
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean bl = false;
        if (itemStack.isOf(Items.SHEARS)) {
            if (this.spore == 1) {
                dropLight(world, pos, state);
            }
            else if (this.spore == 2) {
                dropNight(world, pos, state);
            }
            world.playSound(player, pos, JNESoundEvents.LIGHTSPORES_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.0f);
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

    @SuppressWarnings("all")
    public boolean maxSpots(BlockState state) {
        return state.get(SPOTS) == 3;
    }

    public BlockState setSpots(BlockState state) {
        int s = state.get(SPOTS);
        return state.with(SPOTS, s + 1);
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
