package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.block.ModBlocks;
import net.jadenxgamer.netherexp.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class SorrowsquashBlock
extends GourdBlock implements LandingBlock {

    public static final BooleanProperty NATURAL = BooleanProperty.of("natural");

    public SorrowsquashBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(NATURAL, false));
    }

    @SuppressWarnings("deprecation")
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY()) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
            this.configureFallingBlockEntity(fallingBlockEntity);
        }
    }

    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.scheduleBlockTick(pos, this, this.getFallDelay());
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @SuppressWarnings("unused")
    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
    }

    protected int getFallDelay() {
        return 2;
    }

    public static boolean canFallThrough(BlockState state) {
        Material material = state.getMaterial();
        return state.isAir() || state.isIn(BlockTags.FIRE) || material.isLiquid() || state.isReplaceable();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player2, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player2.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!world.isClient) {
                Direction direction = hit.getSide();
                Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player2.getHorizontalFacing().getOpposite() : direction;
                world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, ModBlocks.CARVED_SORROWSQUASH.getDefaultState().with(CarvedSorrowsquashBlock.FACING, direction2), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5 + (double)direction2.getOffsetX() * 0.65, (double)pos.getY() + 0.1, (double)pos.getZ() + 0.5 + (double)direction2.getOffsetZ() * 0.65, new ItemStack(ModItems.SORROWSQUASH_SEEDS, 4));
                itemEntity.setVelocity(0.05 * (double)direction2.getOffsetX() + world.random.nextDouble() * 0.02, 0.05, 0.05 * (double)direction2.getOffsetZ() + world.random.nextDouble() * 0.02);
                world.spawnEntity(itemEntity);
                itemStack.damage(1, player2, player -> player.sendToolBreakStatus(hand));
                world.emitGameEvent(player2, GameEvent.SHEAR, pos);
                player2.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
            }
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player2, hand, hit);
    }

    @Override
    public StemBlock getStem() {
        return (StemBlock)Blocks.PUMPKIN_STEM;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NATURAL);
    }

    @Override
    public AttachedStemBlock getAttachedStem() {
        return (AttachedStemBlock)Blocks.ATTACHED_PUMPKIN_STEM;
    }
}
