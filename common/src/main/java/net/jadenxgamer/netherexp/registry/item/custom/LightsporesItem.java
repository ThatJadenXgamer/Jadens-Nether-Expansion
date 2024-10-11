package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.DecayableWartBlock;
import net.jadenxgamer.netherexp.registry.block.custom.WartBlock;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class LightsporesItem extends Item {
    public LightsporesItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        // Player
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        // Block
        BlockPos pos;
        Level level = context.getLevel();
        BlockState state = level.getBlockState(pos = context.getClickedPos());
        Block block = state.getBlock();
        WartBlock spottedWartBlock;
        DecayableWartBlock decayableWartBlock;
        /*
          Increases Spotted Wart Block's Spots
        */
        if (state.is(Blocks.NETHER_WART_BLOCK) && !(spottedWartBlock = (WartBlock) block).maxSpots(state)) {
            level.setBlock(pos, spottedWartBlock.setSpots(state), 2);
            level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.LIGHTSPORES_APPLY.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            sporeParticles(level, pos);
            if (player != null) {
                itemStack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        /*
          Increases Decayable Wart Block's Spots
        */
        else if (state.is(JNEBlocks.DECAYABLE_NETHER_WART_BLOCK.get()) && !(decayableWartBlock = (DecayableWartBlock) block).maxSpots(state)) {
            level.setBlock(pos, decayableWartBlock.setSpots(state), 2);
            level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.LIGHTSPORES_APPLY.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            sporeParticles(level, pos);

            if (player != null) {
                itemStack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(context);
    }

    public static void sporeParticles(Level level, BlockPos pos) {
        RandomSource random = level.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction.getNormal());
            if (level.getBlockState(blockPos).isSolidRender(level, blockPos)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double)direction.getStepX() : (double)random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double)direction.getStepY() : (double)random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double)direction.getStepZ() : (double)random.nextFloat();
            level.addParticle(JNEParticleTypes.FALLING_SHROOMLIGHT.get(), (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.0, 0.0);
        }
    }
}
