package net.jadenxgamer.netherexp.block.custom;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SkullCandleBlock
extends Block {

    public static final IntProperty ROTATION = Properties.ROTATION;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
    protected final ParticleEffect particle;
    protected final ParticleEffect smoke;

    public SkullCandleBlock(Settings settings, ParticleEffect particle, ParticleEffect smoke){
        super(settings);
        this.particle = particle;
        this.smoke = smoke;
    }

    @SuppressWarnings("all")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(ROTATION, MathHelper.floor((double)(ctx.getPlayerYaw() * 16.0f / 360.0f) + 0.5) & 0xF);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double x = (double)pos.getX() + 0.5;
        double y = (double)pos.getY() + 1;
        double z = (double)pos.getZ() + 0.5;
        float f = random.nextFloat();
        if (f < 0.3f) {
            world.addParticle(this.smoke, x, y, z, 0.0, 0.0, 0.0);
            if (f < 0.17f) {
                world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_CANDLE_AMBIENT,SoundCategory.BLOCKS,1.0f + random.nextFloat(),random.nextFloat() * 0.7f + 0.3f,false);
            }
        }
        world.addParticle(this.particle, x, y, z, 0.0, 0.0, 0.0);
    }


    @SuppressWarnings("all")
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(ROTATION, rotation.rotate(state.get(ROTATION), 16));
    }

    @SuppressWarnings("all")
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(ROTATION, mirror.mirror(state.get(ROTATION), 16));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }
}
