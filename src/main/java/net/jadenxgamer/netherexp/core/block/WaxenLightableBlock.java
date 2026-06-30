package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class WaxenLightableBlock extends Block {

    public static final EnumProperty<WaxenLit> LIT = EnumProperty.create("lit", WaxenLit.class);

    private final Supplier<SimpleParticleType> particle;

    public WaxenLightableBlock(Supplier<SimpleParticleType> particle, Properties properties) {
        super(properties);
        this.particle = particle;
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, WaxenLit.FALSE));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(LIT) == WaxenLit.FALSE) {
            boolean changeState = false;
            boolean waxen = false;
            if (stack.is(Items.FLINT_AND_STEEL)) {
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                changeState = true;
            } else if (stack.is(Items.FIRE_CHARGE)) {
                level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                changeState = true;
            } else if (stack.is(JNEItems.TREACHEROUS_WAX.get())) {
                level.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                changeState = true;
                waxen = true;
            }

            if (changeState) {
                level.setBlock(pos, state.setValue(LIT, waxen ? WaxenLit.WAXEN : WaxenLit.TRUE), Block.UPDATE_ALL);
                if (particle.get() != null) ParticleHelper.surroundBlockParticle(level, pos, waxen ? JNEParticleTypes.TREACHEROUS_FLAME.get() : particle.get());
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public static int stateForProperty(BlockState state) {
        return switch (state.getValue(LIT)) {
            case FALSE -> 0;
            case TRUE -> 4;
            case WAXEN -> 3;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    public enum WaxenLit implements StringRepresentable {
        FALSE("false"),
        TRUE("true"),
        WAXEN("waxen");

        private final String name;

        WaxenLit(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
