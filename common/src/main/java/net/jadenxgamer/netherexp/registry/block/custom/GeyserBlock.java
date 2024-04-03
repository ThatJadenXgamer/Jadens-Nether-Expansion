package net.jadenxgamer.netherexp.registry.block.custom;

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

    // Particles
    /*
    Ash is the particle emitted by this block
    Smoke the type of smoke it emits
    */
    protected final int type;
    protected final boolean spark;
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
            entity.push(vec3.x,1.2, vec3.z);
            entity.hurt(level.damageSources().hotFloor(), 1.0f);
            level.playSound(null, pos.getX(),pos.getY(),pos.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            // TODO Add Configs
//            if (NetherExp.getConfig().blocks.geyserConfigs.geyser_cooldown) {
//                level.setBlock(pos, state.cycle(COOLDOWN), UPDATE_CLIENTS);
//                level.scheduleTick(pos, this, NetherExp.getConfig().blocks.geyserConfigs.geyser_cooldown_ticks);
//            }
            level.setBlock(pos, state.cycle(COOLDOWN), UPDATE_CLIENTS);
            level.scheduleTick(pos, this, 100);
        }
    }


    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(COOLDOWN)) {
            world.setBlock(pos, state.cycle(COOLDOWN), UPDATE_CLIENTS);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean player = Objects.requireNonNull(ctx.getPlayer()).isShiftKeyDown();
        return this.defaultBlockState().setValue(ACTIVE, !player);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        boolean a = state.getValue(ACTIVE);
        boolean cd = state.getValue(COOLDOWN);
        boolean b = world.getBiome(pos).is(this.biome);
        float f = random.nextFloat();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int l = 0; l < 14; ++l) {
            mutable.set(i + Mth.nextInt(random, -20, 20), j + random.nextInt(20), k + Mth.nextInt(random, -20, 20));
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.isSolidRender(world, mutable)) continue;
            if (a && !b) {
                switch (type) {
                    default: {
                        world.addParticle(ParticleTypes.ASH, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                        break;
                    }
                    case 2: {
                        world.addParticle(ParticleTypes.WHITE_ASH, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                        break;
                    }
                    case 3: {
                        world.addParticle(JNEParticleTypes.FIRE_SPARK.get(), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                        break;
                    }
                }
            }
        }
        if (a) {
            switch (type) {
                default: {
                    world.addParticle(JNEParticleTypes.BLACK_SMOKE.get(), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                    break;
                }
                case 2: {
                    world.addParticle(JNEParticleTypes.WHITE_SMOKE.get(), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                    break;
                }
                case 3: {
                    world.addParticle(JNEParticleTypes.RED_SMOKE.get(), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                    break;
                }
            }
        }
        if (!cd && this.spark && f < 0.3f) {
            world.addParticle(ParticleTypes.LAVA, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
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
