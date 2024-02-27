package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.particle.JNEParticles;
import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class TwilightVinesBlock extends AbstractPlantStemBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    private static final BooleanProperty BUDDING = BooleanProperty.of("budding");

    public TwilightVinesBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false, 0.1);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        boolean budding = state.get(BUDDING);
        float f = random.nextFloat();
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() + 0.8;
        double z = (double)pos.getZ() + random.nextDouble();
        if (!budding && f < 0.3) {
            world.addParticle(JNEParticles.FALLING_SHROOMBLIGHT, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean budding = state.get(BUDDING);
        boolean bl = false;
        if (budding) {
            if (itemStack.isOf(JNEItems.BLIGHTSPORES)) {
                world.playSound(player, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.LIGHTSPORES_APPLY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos,state.cycle(BUDDING), Block.NOTIFY_LISTENERS);
                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
                sporeParticles(world, pos);
                bl = true;
            }
        }
        else {
            if (itemStack.isOf(Items.SHEARS)) {
                dropLight(world, pos);
                world.playSound(player, pos, JNESoundEvents.LIGHTSPORES_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, state.cycle(BUDDING), Block.NOTIFY_LISTENERS);
                world.emitGameEvent(player, GameEvent.SHEAR, pos);
                itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                bl = true;
            }
        }
        if (!world.isClient() && bl) {
            player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
        }
        if (bl) {
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    private static void dropLight(World world, BlockPos pos) {
        dropStack(world, pos, new ItemStack(JNEItems.BLIGHTSPORES, 1));
    }

    private static void sporeParticles(World world, BlockPos pos) {
        Random random = world.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            if (world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getOffsetX() : (double) random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getOffsetY() : (double) random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getOffsetZ() : (double) random.nextFloat();
            world.addParticle(JNEParticles.FALLING_SHROOMBLIGHT, (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        float r = random.nextInt(50);
        boolean b = state.get(BUDDING);
        if (NetherExp.getConfig().blocks.renewableConfigs.glowspores_from_vines && r == 0 && b) {
            world.setBlockState(pos, state.cycle(BUDDING), NOTIFY_LISTENERS);
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BUDDING, AGE);
    }

    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    protected Block getPlant() {
        return JNEBlocks.TWILIGHT_VINES_PLANT;
    }

    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }
}

