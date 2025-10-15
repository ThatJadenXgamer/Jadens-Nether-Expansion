package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.data.worldgen.features.JNENetherWastesFeatures;
import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.AdvancementGranter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

public class CerebrageSkullBlock extends AbstractHeadBlock implements BonemealableBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;

    public CerebrageSkullBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(Items.BONE_MEAL)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        int age = state.getValue(AGE);
        if (age > 2) {
            if (MAX_CEREBRAGE_DROPPED.get() > 0 && age == 3) popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(JNEItems.CEREBRAGE.get(), level.random.nextInt(MIN_CEREBRAGE_DROPPED.get(), MAX_CEREBRAGE_DROPPED.get())));
            if (level.random.nextDouble() < CEREBRAGE_SEEDS_DROP_CHANCE.get()) popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(JNEItems.CEREBRAGE_SEEDS.get(), 1));
            level.setBlock(pos, state.setValue(AGE, 1), CerebrageSkullBlock.UPDATE_CLIENTS);
            level.playSound(null, pos, JNESoundEvents.CEREBRAGE_PLANT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() < CEREBRAGE_GROWTH_CHANCE.get()) {
            int age = state.getValue(AGE);
            level.setBlock(pos, state.setValue(AGE, age + 1), CerebrageSkullBlock.UPDATE_CLIENTS);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(AGE) != 3 || level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextInt(state.getValue(AGE) < 3 ? 8 : 2) == 0;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        if (age < 3) {
            level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
        } else if (CEREBRAGE_GROWS_BRAIN_TREES.get()) {
            level.setBlock(pos, state.setValue(AGE, 4), Block.UPDATE_CLIENTS);
            level.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((registry) -> registry.getHolder(JNENetherWastesFeatures.BRAIN_TREE)).ifPresent((reference) ->
                    reference.value().place(level, level.getChunkSource().getGenerator(), random, pos));
            AdvancementGranter.grantPlayersInRadius(level, pos, JNECriteriaTriggers.GROW_CEREBRAGE_CLARET);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(JNEItems.CEREBRAGE_SEEDS.get());
    }
}
