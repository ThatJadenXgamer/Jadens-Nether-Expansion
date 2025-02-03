package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
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
import java.util.function.Supplier;

public class GeyserBlock extends Block {
    public static final BooleanProperty COOLDOWN = BooleanProperty.create("cooldown");
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    protected final boolean spark;
    private final Supplier<SimpleParticleType> ashParticle;
    private final Supplier<SimpleParticleType> smokeParticle;
    protected final TagKey<Biome> homeBiome;

    /**
     * @param ashSupplier - defines the particle that'll generate around the geyser
     * @param smokeSupplier - defines the particle that rises from the center of the block
     * @param spark - if true the block will produce lava sparks when not in cooldown
     * @param biome - ash particles will NOT be produced inside the provided tag key
     */
    public GeyserBlock(Properties properties, Supplier<SimpleParticleType> ashSupplier, Supplier<SimpleParticleType> smokeSupplier, boolean spark, TagKey<Biome> biome) {
        super(properties);
        this.ashParticle = ashSupplier;
        this.smokeParticle = smokeSupplier;
        this.spark = spark;
        this.homeBiome = biome;
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
        boolean homeBiome = level.getBiome(pos).is(this.homeBiome);
        float f = random.nextFloat();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int l = 0; l < 14; ++l) {
            mutable.set(x + Mth.nextInt(random, -20, 20), y + random.nextInt(20), z + Mth.nextInt(random, -20, 20));
            BlockState blockState = level.getBlockState(mutable);
            if (blockState.isSolidRender(level, mutable)) continue;
            if (active && !homeBiome) {
                level.addParticle(this.ashParticle.get(), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }
        if (active && random.nextInt(2) == 0) {
            level.addParticle(this.smokeParticle.get(), (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
        }
        if (!cooldown && this.spark && f < 0.3f) {
            level.addParticle(ParticleTypes.LAVA, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
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
