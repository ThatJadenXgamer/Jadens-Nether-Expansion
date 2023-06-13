package net.jadenxgamer.netherexp.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class GeyserBlock extends Block {

    public static final IntProperty PRESSURE = IntProperty.of("pressure", 0, 6);
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public static final int MAX_PRESSURE = 6;

    public GeyserBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(PRESSURE, 0).with(ACTIVE, false));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        int p = state.get(PRESSURE);
        boolean a = state.get(ACTIVE);
        Vec3d vec3d = entity.getVelocity();
        if (p == 6) {
            if (a) {
                entity.addVelocity(vec3d.x,1.8, vec3d.z);
            }
            else {
                entity.addVelocity(vec3d.x,1.0, vec3d.z);
            }
            entity.damage(DamageSource.HOT_FLOOR, 2.0f);
            world.setBlockState(pos, (state.with(PRESSURE, 0)), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int p = state.get(PRESSURE);
        float f = random.nextFloat();
        if (p < 6) {
            world.setBlockState(pos, (state.with(PRESSURE, p  + 1)), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            return state.with(ACTIVE, this.isActiveBaseBlock(neighborState));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos;
        blockPos = ctx.getBlockPos();
        World worldAccess = ctx.getWorld();
        return this.getDefaultState().with(ACTIVE, this.isActiveBaseBlock(worldAccess.getBlockState(blockPos.down())));
    }

    private boolean isActiveBaseBlock(BlockState state) {
        return state.isOf(Blocks.MAGMA_BLOCK);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        boolean a = state.get(ACTIVE);
        int p = state.get(PRESSURE);
        float f = random.nextFloat();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        if (f < 0.3f && p == 6) {
            world.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.5, (double)pos.getY() + 1, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        }
        for (int l = 0; l < 14; ++l) {
            mutable.set(i + MathHelper.nextInt(random, -10, 10), j + random.nextInt(10), k + MathHelper.nextInt(random, -10, 10));
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.isFullCube(world, mutable)) continue;
            if (a) {
                world.addParticle(ParticleTypes.WHITE_ASH, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PRESSURE, ACTIVE);
    }
}
