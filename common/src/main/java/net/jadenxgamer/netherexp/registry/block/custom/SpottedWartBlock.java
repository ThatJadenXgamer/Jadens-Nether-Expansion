package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class SpottedWartBlock extends Block {
    public static final IntegerProperty SPOTS = IntegerProperty.create("spots", 1, 3);

    // Base is the vanilla block the Spotted Wart is related to
    protected final Block base;

    /*
     * Spore value dictates what kind of spore to drop when sheared
     * 1 = Lightspores
     * 2 = Nightspores
    */
    protected final int spore;

    public SpottedWartBlock(Properties properties, Block base, int spore) {
        super(properties);
        this.base = base;
        this.spore = spore;
        this.registerDefaultState(this.defaultBlockState().setValue(SPOTS, 1));
    }

    private static void dropLight(Level level, BlockPos pos, BlockState state) {
        int s = state.getValue(SPOTS);
        SpottedWartBlock.popResource(level, pos, new ItemStack(JNEItems.LIGHTSPORES.get(), s));
    }

    private static void dropNight(Level level, BlockPos pos, BlockState state) {
        int s = state.getValue(SPOTS);
        SpottedWartBlock.popResource(level, pos, new ItemStack(JNEItems.NIGHTSPORES.get(), s));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        boolean bl = false;
        int s = state.getValue(SPOTS);
        if (itemStack.is(Items.SHEARS)) {
            if (this.spore == 1) {
                dropLight(level, pos, state);
            } else if (this.spore == 2) {
                dropNight(level, pos, state);
            }
            level.playSound(player, pos, JNESoundEvents.LIGHTSPORES_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlockAndUpdate(pos, state.setValue(SPOTS, 0));
            level.gameEvent(player, GameEvent.SHEAR, pos);
            itemStack.hurtAndBreak(1, player, (playerEntity) -> {
                playerEntity.broadcastBreakEvent(hand);
            });
            bl = true;
        }
        if (!level.isClientSide && bl) {
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        }
        if (bl) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @SuppressWarnings("all")
    public boolean maxSpots(BlockState state) {
        return state.getValue(SPOTS) == 3;
    }

    public BlockState setSpots(BlockState state) {
        int s = state.getValue(SPOTS);
        return state.setValue(SPOTS, s + 1);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SPOTS);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(this.base);
    }
}
