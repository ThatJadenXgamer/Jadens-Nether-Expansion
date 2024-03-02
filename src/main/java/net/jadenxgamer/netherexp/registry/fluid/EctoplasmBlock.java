package net.jadenxgamer.netherexp.registry.fluid;

import com.google.common.collect.Maps;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Property;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class EctoplasmBlock extends FluidBlock {

    private static Map<Block, Block> FREEZES;

    public EctoplasmBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        this.checkFreeze(world, pos);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        this.checkFreeze(world, pos);
    }

    public void checkFreeze(World world, BlockPos pos) {
        initFreeze();
        Direction[] var4 = Direction.values();
        for (Direction direction : var4) {
            BlockPos offset = pos.offset(direction);
            BlockState oldState = world.getBlockState(offset);
            if (FREEZES.containsKey(oldState.getBlock())) {
                BlockState newState = FREEZES.get(oldState.getBlock()).getDefaultState();
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_SLATE_SOLIDIFYING, SoundCategory.BLOCKS, 1.0f, 1.0f);
                for (Property property : oldState.getProperties()) {
                    newState = newState.contains(property) ? newState.with(property, oldState.get(property)) : newState;
                }
                world.setBlockState(offset, newState);
            }
        }
    }

    private static void initFreeze() {
        if (FREEZES != null) {
            return;
        }
        FREEZES = Util.make(Maps.newHashMap(), (map) -> {
            map.put(JNEBlocks.NETHERITE_PLATED_BLOCK, JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK);
            map.put(JNEBlocks.NETHERITE_GRATE, JNEBlocks.RUSTY_NETHERITE_GRATE);
            map.put(JNEBlocks.CUT_NETHERITE_BLOCK, JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK);
            map.put(JNEBlocks.CUT_NETHERITE_SLAB, JNEBlocks.RUSTY_CUT_NETHERITE_SLAB);
            map.put(JNEBlocks.CUT_NETHERITE_STAIRS, JNEBlocks.RUSTY_CUT_NETHERITE_STAIRS);
            map.put(JNEBlocks.CUT_NETHERITE_PILLAR, JNEBlocks.RUSTY_CUT_NETHERITE_PILLAR);
        });
    }
}
