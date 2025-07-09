package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class LightableBlock extends Block {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private final Supplier<SimpleParticleType> particle;

    public LightableBlock(Supplier<SimpleParticleType> particle, Properties properties) {
        super(properties);
        this.particle = particle;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!state.getValue(LIT)) {
            boolean changeState = false;
            if (stack.is(Items.FLINT_AND_STEEL)) {
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                changeState = true;
            } else if (stack.is(Items.FIRE_CHARGE)) {
                level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                changeState = true;
            }

            if (changeState) {
                level.setBlock(pos, state.cycle(LIT), Block.UPDATE_ALL);
                if (particle.get() != null) ParticleHelper.surroundBlockParticle(level, pos, particle.get());
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
