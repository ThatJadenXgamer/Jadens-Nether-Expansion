package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class SmokestalkBlock
extends AbstractPlantStemBlock {

    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(5, 0, 5, 11, 8, 11), Block.createCuboidShape(4, 8, 4, 12, 12, 12), BooleanBiFunction.OR);

    public SmokestalkBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false, 0.1);
        this.setDefaultState(this.getDefaultState().with(ACTIVE, false));
    }

    @Override
    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.SMOKESTALK_PLANT;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean active = state.get(ACTIVE);
        boolean bl = false;
        if (!active) {
            if (itemStack.isIn(ModTags.Items.SMOKESTALK_FUEL)) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, state.cycle(ACTIVE), Block.NOTIFY_LISTENERS);
                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
                fuelParticles(world, pos);
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

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        boolean active = state.get(ACTIVE);
        if (active) {
            world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.8, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
        }
    }

    // Particles for Fueling
    private static void fuelParticles(World world, BlockPos pos) {
        Random random = world.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            if (world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double)direction.getOffsetX() : (double)random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double)direction.getOffsetY() : (double)random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double)direction.getOffsetZ() : (double)random.nextFloat();
            world.addParticle(ParticleTypes.FLAME, (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE, AGE);
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }
}
