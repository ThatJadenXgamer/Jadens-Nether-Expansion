package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class GeyserBlock extends Block {

    public static final BooleanProperty COOLDOWN = BooleanProperty.of("cooldown");
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    // Particles
    /*
    Ash is the particle emitted by this block
    Smoke the type of smoke it emits
    */
    protected final ParticleEffect smoke;
    protected final ParticleEffect spark;
    protected final ParticleEffect ash;
    protected final TagKey<Biome> biome;

    public GeyserBlock(Settings settings, ParticleEffect smoke, ParticleEffect spark, ParticleEffect ash, TagKey<Biome> biome) {
        super(settings);
        this.smoke = smoke;
        this.spark = spark;
        this.ash = ash;
        this.biome = biome;
        setDefaultState(this.getStateManager().getDefaultState().with(COOLDOWN, false).with(ACTIVE, false));
    }

    //TODO: Some VFXs would be great here
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        boolean cd = state.get(COOLDOWN);
        if (!cd) {
            Vec3d vec3d = entity.getVelocity();
            entity.addVelocity(vec3d.x,1.2, vec3d.z);
            entity.damage(world.getDamageSources().hotFloor(), 1.0f);
            state.cycle(COOLDOWN);
            world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            world.scheduleBlockTick(pos, this, 100);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(COOLDOWN)) {
            state.cycle(COOLDOWN);
        }
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        boolean player = Objects.requireNonNull(ctx.getPlayer()).isSneaking();
        return this.getDefaultState().with(ACTIVE, !player);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        boolean a = state.get(ACTIVE);
        boolean cd = state.get(COOLDOWN);
        boolean b = world.getBiome(pos).isIn(this.biome);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = 0; l < 14; ++l) {
            mutable.set(i + MathHelper.nextInt(random, -20, 20), j + random.nextInt(20), k + MathHelper.nextInt(random, -20, 20));
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.isFullCube(world, mutable)) continue;
            if (a && !b) {
                world.addParticle(this.ash, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }
        if (!cd && a) {
            world.addParticle(this.smoke, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
        }
        if (cd) {
            world.addParticle(this.spark, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.8f, world.getDamageSources().fall());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COOLDOWN, ACTIVE);
    }
}
