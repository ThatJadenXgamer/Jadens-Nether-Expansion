package net.jadenxgamer.netherexp.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;

public class GeyserBlock extends Block {

    public static final BooleanProperty CREATES_ASH = BooleanProperty.of("creates_ash");
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    // Particles
    /*
    Ash is the actual ash particle
    which is generated from the block
    Smoke the type of smoke it emits
    */
    protected final ParticleEffect smoke;
    protected final ParticleEffect ash;

    public GeyserBlock(Settings settings, ParticleEffect smoke, ParticleEffect ash) {
        super(settings);
        this.smoke = smoke;
        this.ash = ash;
        setDefaultState(this.getStateManager().getDefaultState().with(CREATES_ASH, false).with(ACTIVE, false));
    }

    //TODO: Some VFXs would be great here
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        entity.addVelocity(vec3d.x,1.2, vec3d.z);
        entity.damage(DamageSource.HOT_FLOOR, 1.0f);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            return state.with(CREATES_ASH, this.isMagmaBlock(neighborState));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        boolean player = Objects.requireNonNull(ctx.getPlayer()).isSneaking();
        BlockPos blockPos;
        blockPos = ctx.getBlockPos();
        World worldAccess = ctx.getWorld();
        return this.getDefaultState().with(CREATES_ASH, this.isMagmaBlock(worldAccess.getBlockState(blockPos.down()))).with(ACTIVE, !player);
    }

    private boolean isMagmaBlock(BlockState state) {
        return state.isOf(Blocks.MAGMA_BLOCK);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        boolean a = state.get(ACTIVE);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = 0; l < 14; ++l) {
            mutable.set(i + MathHelper.nextInt(random, -20, 20), j + random.nextInt(20), k + MathHelper.nextInt(random, -20, 20));
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.isFullCube(world, mutable)) continue;
            if (a) {
                world.addParticle(this.smoke, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.3, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.008, 0.0);
                world.addParticle(this.ash, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.8f, DamageSource.FALL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CREATES_ASH, ACTIVE);
    }
}
