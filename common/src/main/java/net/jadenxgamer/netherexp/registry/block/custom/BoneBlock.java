package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BoneBlock extends RotatedPillarBlock {
    private static final BooleanProperty TETHERED = BooleanProperty.create("tethered");

    public BoneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TETHERED, false).setValue(AXIS, Direction.Axis.Y));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        boolean tethered = state.getValue(TETHERED);
        boolean bl = false;
        if (!tethered) {
            if (itemStack.is(Items.LEATHER)) {
                level.playSound(player, pos, SoundEvents.LEASH_KNOT_PLACE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
                level.setBlock(pos, state.cycle(TETHERED), Block.UPDATE_CLIENTS);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                bl = true;
                if (!level.isClientSide) {
                    player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                }
            }
        }
        else {
            if (itemStack.is(Items.SHEARS)) {
                level.playSound(player, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(pos, state.cycle(TETHERED), Block.UPDATE_CLIENTS);
                BoneBlock.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(Items.LEATHER, 1));
                if (!player.isCreative()) {
                    itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(interactionHand));
                }
                bl = true;
                if (!level.isClientSide) {
                    player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                }
            }
        }
        if (bl) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TETHERED, AXIS);
    }
}
