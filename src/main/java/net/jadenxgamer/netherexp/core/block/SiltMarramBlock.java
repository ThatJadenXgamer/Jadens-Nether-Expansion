package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.ItemAbilities;

public class SiltMarramBlock extends RootsBlock {

    public static final BooleanProperty SHEARED = BooleanProperty.create("sheared");
    private final TagKey<Biome> homeBiomes;

    public SiltMarramBlock(TagKey<Biome> homeBiomes, Properties properties) {
        super(properties);
        this.homeBiomes = homeBiomes;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        var biome = level.getBiome(pos);
        if (state.getValue(SHEARED)) return;
        if (biome.is(homeBiomes)) return;

        BlockState targetState = JNEBlocks.MOIST_SILTMARRAM.get().defaultBlockState();
        if (biome.is(JNETags.Biomes.SPAWNS_DAMP_VARIANT_STRIDERS_AND_SILTMARRAM)) targetState = JNEBlocks.DAMP_SILTMARRAM.get().defaultBlockState();
        if (biome.is(JNETags.Biomes.SPAWNS_DRY_VARIANT_STRIDERS_AND_SILTMARRAM)) targetState = JNEBlocks.DRY_SILTMARRAM.get().defaultBlockState();

        if (!state.equals(targetState)) level.setBlock(pos, targetState, UPDATE_ALL);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.canPerformAction(ItemAbilities.SHEARS_TRIM) && !state.getValue(SHEARED)) {
            level.setBlock(pos, state.setValue(SHEARED, true), UPDATE_ALL);
            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(JNETags.Blocks.SILTMARRAM_PLANTABLE_ON);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHEARED);
    }
}