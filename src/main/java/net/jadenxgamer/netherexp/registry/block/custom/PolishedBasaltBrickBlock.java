package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.particle.JNEParticles;
import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
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


public class PolishedBasaltBrickBlock
extends RotatingBlock {

    public static final BooleanProperty GILDED = BooleanProperty.of("gilded");

    public PolishedBasaltBrickBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(GILDED, false));
    }


    /*
    Sets Gilded Blockstate to True
    when Interacted with a Gold Ingot
     */
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean gilded = state.get(GILDED);
        boolean bl = false;
        if (!gilded) {
            if (itemStack.isOf(Items.GOLD_NUGGET)) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), JNESoundEvents.GILDING, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, state.cycle(GILDED), Block.NOTIFY_LISTENERS);
                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
                PolishedBasaltBrickBlock.glimmerParticles(world, pos);
                bl = true;
            }
            if (!world.isClient() && bl) {
                player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            }
        }
        if (bl) {
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    // Particles for Gilding
    private static void glimmerParticles(World world, BlockPos pos) {
        Random random = world.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            if (world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double)direction.getOffsetX() : (double)random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double)direction.getOffsetY() : (double)random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double)direction.getOffsetZ() : (double)random.nextFloat();
            world.addParticle(JNEParticles.GOLD_GLIMMER, (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GILDED, FACING);
    }
}
