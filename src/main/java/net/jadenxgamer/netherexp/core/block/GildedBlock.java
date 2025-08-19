package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class GildedBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<GildedBlock> CODEC = simpleCodec(GildedBlock::new);
    public static final BooleanProperty GILDED = BooleanProperty.create("gilded");

    public GildedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(GILDED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!state.getValue(GILDED)) {
            if (stack.is(Items.GOLD_NUGGET)) {
                level.playSound(player, pos, JNESoundEvents.GOLD_GILDING.get(), SoundSource.BLOCKS, 1.0F, 1.0f);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                level.setBlock(pos, state.cycle(GILDED), Block.UPDATE_ALL);
                ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.WAX_ON);
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GILDED, FACING);
    }
}
