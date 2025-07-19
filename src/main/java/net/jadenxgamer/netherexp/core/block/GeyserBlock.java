package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.elysium_api.api.util.LookupRegistryHelper;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class GeyserBlock extends Block {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty COOLDOWN = BooleanProperty.create("cooldown");

    private final Supplier<SimpleParticleType> ashParticle;
    private final Supplier<SimpleParticleType> smokeParticle;
    protected final TagKey<Biome> homeBiome;

    /**
     * Sporeshroom constructor for normal usage
     * @param homeBiome biomes in which the block won't produce ash particles
     * @param ashParticle the kind of ash particles it produces
     * @param smokeParticle the kind of smoke it emits from the chimney
     * @param properties block properties
     */
    public GeyserBlock(TagKey<Biome> homeBiome, Supplier<SimpleParticleType> ashParticle, Supplier<SimpleParticleType> smokeParticle, Properties properties) {
        super(properties);
        this.homeBiome = homeBiome;
        this.ashParticle = ashParticle;
        this.smokeParticle = smokeParticle;
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false).setValue(COOLDOWN, false));
    }

    /**
     * Geyser constructor mostly reserved for mod compat, it retrieves the ash particles dynamically from just the registered id
     * @param homeBiome biomes in which the block won't produce ash particles
     * @param ashParticleId define a {@link ResourceLocation} for the ash particles it produces
     * @param smokeParticle the kind of smoke it emits from the chimney
     * @param properties block properties
     */
    public GeyserBlock(TagKey<Biome> homeBiome, ResourceLocation ashParticleId, Supplier<SimpleParticleType> smokeParticle, Properties properties) {
        super(properties);
        this.homeBiome = homeBiome;
        this.ashParticle = () -> getAshFromResourceLocation(ashParticleId);
        this.smokeParticle = smokeParticle;
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false).setValue(COOLDOWN, false));
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity.isShiftKeyDown() || state.getValue(COOLDOWN)) return;

        Vec3 velocity = entity.getDeltaMovement();
        entity.push(velocity.x, JNEConfigs.GEYSER_HEIGHT_VELOCITY.get(), velocity.z);
        level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
        level.setBlock(pos, state.setValue(COOLDOWN, true), Block.UPDATE_ALL);
        level.scheduleTick(pos, this, JNEConfigs.GEYSER_COOLDOWN.get() * 20);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.5F, entity.damageSources().fall());
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(COOLDOWN)) {
            level.setBlock(pos, state.cycle(COOLDOWN), Block.UPDATE_ALL);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
        return this.defaultBlockState().setValue(ACTIVE, !sneaking);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        if (state.getValue(COOLDOWN)) {
            for (int i = 0; i < 3; ++i) {
                level.addParticle(smokeParticle.get(), x + 0.5, y + 1.4, z + 0.5, 0.0, JNEConfigs.GEYSER_HEIGHT_VELOCITY.get(), 0.0);
            }
        }

        if (!state.getValue(ACTIVE)) return;

        boolean inHomeBiome = level.getBiome(pos).is(this.homeBiome);
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        if (!inHomeBiome) {
            for (int i = 0; i < 14; ++i) {
                mutablePos.set(x + Mth.nextInt(random, -20, 20), y + random.nextInt(20), z + Mth.nextInt(random, -20, 20));
                BlockState mutableState = level.getBlockState(mutablePos);
                if (mutableState.isSolidRender(level, mutablePos)) continue;
                level.addParticle(ashParticle.get(), mutablePos.getX() + random.nextDouble(), mutablePos.getY() + random.nextDouble(), mutablePos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }

        if (!state.getValue(COOLDOWN) && random.nextInt(2) == 0) {
            level.addParticle(smokeParticle.get(), x + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1), y + 1.4, z + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1), 0.0, 0.008, 0.0);
        }
    }

    private SimpleParticleType getAshFromResourceLocation(ResourceLocation id) {
        ParticleType<?> particle = LookupRegistryHelper.getParticleType(id);
        if (particle instanceof SimpleParticleType simpleParticleType) {
            return simpleParticleType;
        }
        return ParticleTypes.ASH; // Fallbacks to ash if the provided particle is not a SimpleParticleType
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE, COOLDOWN);
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}
