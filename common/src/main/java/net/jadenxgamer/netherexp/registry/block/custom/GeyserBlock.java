package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class GeyserBlock extends Block {

    public static final BooleanProperty COOLDOWN = BooleanProperty.create("cooldown");
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    /**
     * type is used to define what kind of particle it should produce
     * 1 - Ash
     * 2 - White Ash
     * 3 - Fire Spark
     */
    protected final int type;

    /**
     * spark is a boolean which handles if the block produces fire sparks
     */
    protected final boolean spark;

    /**
     * the block will not produce particles if inside this biome tag
     */
    protected final TagKey<Biome> biome;

    public GeyserBlock(Properties properties, int type, boolean spark, TagKey<Biome> biome) {
        super(properties);
        this.type = type;
        this.spark = spark;
        this.biome = biome;
        registerDefaultState(this.defaultBlockState().setValue(COOLDOWN, false).setValue(ACTIVE, false));
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        boolean c = state.getValue(COOLDOWN);
        if (!c) {
            Vec3 vec3 = entity.getDeltaMovement();
            entity.push(vec3.x,JNEConfigs.GEYSER_PUSH_VELOCITY.get(), vec3.z);
            entity.hurt(level.damageSources().hotFloor(), 1.0f);
            level.playSound(null, pos.getX(),pos.getY(),pos.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, state.cycle(COOLDOWN), UPDATE_CLIENTS);
            level.scheduleTick(pos, this, JNEConfigs.GEYSER_COOLDOWN.get() * 20);
        }
    }


    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(COOLDOWN)) {
            level.setBlock(pos, state.cycle(COOLDOWN), UPDATE_CLIENTS);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean player = Objects.requireNonNull(context.getPlayer()).isShiftKeyDown();
        return this.defaultBlockState().setValue(ACTIVE, !player);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        boolean active = state.getValue(ACTIVE);
        boolean cooldown = state.getValue(COOLDOWN);
        boolean homeBiome = level.getBiome(pos).is(this.biome);
        float f = random.nextFloat();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int l = 0; l < 14; ++l) {
            mutable.set(x + Mth.nextInt(random, -20, 20), y + random.nextInt(20), z + Mth.nextInt(random, -20, 20));
            BlockState blockState = level.getBlockState(mutable);
            if (blockState.isSolidRender(level, mutable)) continue;
            if (active && !homeBiome) {
                // we use switch case because architectury is a bitch and registers particles late causing a null exception
                biomeParticles(level, mutable, random);
            }
        }
        if (active) {
            smokeParticles(level, pos, random);
        }
        if (!cooldown && this.spark && f < 0.3f) {
            level.addParticle(ParticleTypes.LAVA, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
        }
    }

    private void biomeParticles(Level level, BlockPos.MutableBlockPos mutable, RandomSource random)  {
        switch (type) {
            default: {
                level.addParticle(ParticleTypes.ASH, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                break;
            }
            case 2: {
                level.addParticle(ParticleTypes.WHITE_ASH, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                break;
            }
            case 3: {
                level.addParticle(JNEParticleTypes.FIRE_SPARK.get(), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                break;
            }
        }
    }

    private void smokeParticles(Level level, BlockPos pos, RandomSource random) {
        switch (type) {
            default: {
                level.addParticle(JNEParticleTypes.BLACK_SMOKE.get(), (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
                break;
            }
            case 2: {
                level.addParticle(JNEParticleTypes.WHITE_SMOKE.get(), (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
                break;
            }
            case 3: {
                level.addParticle(JNEParticleTypes.RED_SMOKE.get(), (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
                break;
            }
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.4f, level.damageSources().fall());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COOLDOWN, ACTIVE);
    }
}
