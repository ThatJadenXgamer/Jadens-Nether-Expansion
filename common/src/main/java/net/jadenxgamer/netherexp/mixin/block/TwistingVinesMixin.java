package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.TwistingVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TwistingVinesBlock.class)
public abstract class TwistingVinesMixin extends GrowingPlantHeadBlock {

    @Unique
    private static final BooleanProperty BUDDING = BooleanProperty.create("budding");

    public TwistingVinesMixin(Properties properties, Direction growthDirection, VoxelShape outlineShape, boolean tickWater, double growthChance) {
        super(properties, growthDirection, outlineShape, tickWater, growthChance);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        float r = random.nextInt(50);
        boolean b = state.getValue(BUDDING);
        //TODO Add Configs
//        if (NetherExp.getConfig().blocks.renewableConfigs.glowspores_from_vines && r == 0 && b) {
//            level.setBlockState(pos, state.cycle(BUDDING), NOTIFY_LISTENERS);
//        }
        if (r == 0 && b) {
            level.setBlock(pos, state.cycle(BUDDING), UPDATE_CLIENTS);
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean budding = state.getValue(BUDDING);
        float f = random.nextFloat();
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() + 0.8;
        double z = (double)pos.getZ() + random.nextDouble();
        if (!budding && f < 0.3) {
            level.addParticle(JNEParticleTypes.RISING_SHROOMNIGHT.get(), x, y, z, Mth.nextDouble(random, -0.02, 0.02), 0.08, Mth.nextDouble(random, -0.02, 0.02));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        boolean budding = state.getValue(BUDDING);
        boolean bl = false;
        if (budding) {
            if (itemStack.is(JNEItems.NIGHTSPORES.get())) {
                level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.LIGHTSPORES_APPLY.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(pos,state.cycle(BUDDING), UPDATE_CLIENTS);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                netherexp$sporeParticles(level, pos);
                bl = true;
            }
        }
        else {
           if (itemStack.is(Items.SHEARS)) {
               netherexp$dropNight(level, pos);
               level.playSound(player, pos, JNESoundEvents.LIGHTSPORES_SHEAR.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
               level.setBlock(pos, state.cycle(BUDDING), UPDATE_CLIENTS);
               level.gameEvent(player, GameEvent.SHEAR, pos);
               itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
               bl = true;
            }
        }
        if (!level.isClientSide && bl) {
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        }
        if (bl) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Unique
    private static void netherexp$dropNight(Level level, BlockPos pos) {
        popResource(level, pos, new ItemStack(JNEItems.NIGHTSPORES.get(), 1));
    }

    @Unique
    private static void netherexp$sporeParticles(Level level, BlockPos pos) {
        RandomSource random = level.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction.getNormal());
            if (level.getBlockState(blockPos).isSolidRender(level, blockPos)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat();
            level.addParticle(JNEParticleTypes.FALLING_SHROOMNIGHT.get(), (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BUDDING, AGE);
    }
}
