package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.util.RegistryHelper;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.util.function.Supplier;

public class JNEParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, NetherExp.MOD_ID);

    public static final Supplier<LodestoneWorldParticleType> SOUL_SWIRL_POP = PARTICLE_TYPES.register("soul_swirl_pop", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> WISP = PARTICLE_TYPES.register("wisp", LodestoneWorldParticleType::new);
    public static final Supplier<SimpleParticleType> SMALL_SOUL_FIRE_FLAME = PARTICLE_TYPES.register("small_soul_fire_flame", () -> new SimpleParticleType(false));
    public static final Supplier<ParticleType<ColorParticleOption>> IMMUNITY_EFFECT = RegistryHelper.registerColorParticle("immunity_effect", false);
    public static final Supplier<LodestoneWorldParticleType> SOUL_MAGMA = PARTICLE_TYPES.register("soul_magma", LodestoneWorldParticleType::new);
    public static final Supplier<SimpleParticleType> CRIMSON_SMOG = PARTICLE_TYPES.register("crimson_smog", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> WARPED_SMOG = PARTICLE_TYPES.register("warped_smog", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> BLACK_SMOKE = PARTICLE_TYPES.register("black_smoke", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> WHITE_SMOKE = PARTICLE_TYPES.register("white_smoke", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> BLACK_FLAKE = PARTICLE_TYPES.register("black_flake", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> DRIPPING_ECTOPLASM = PARTICLE_TYPES.register("dripping_ectoplasm", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> FALLING_ECTOPLASM = PARTICLE_TYPES.register("falling_ectoplasm", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> ECTOSPLASH = PARTICLE_TYPES.register("ectosplash", () -> new SimpleParticleType(false));
    public static final Supplier<LodestoneWorldParticleType> ECTOPLASM_RAYS = PARTICLE_TYPES.register("ectoplasm_rays", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> ECTOPLASM = PARTICLE_TYPES.register("ectoplasm", LodestoneWorldParticleType::new);
    public static final Supplier<SimpleParticleType> SOUL_CLOUD = PARTICLE_TYPES.register("soul_cloud", () -> new SimpleParticleType(false));
    public static final Supplier<LodestoneWorldParticleType> SILVER_GLIMMER = PARTICLE_TYPES.register("silver_glimmer", LodestoneWorldParticleType::new);
    public static final Supplier<SimpleParticleType> TREACHEROUS_FLAME = PARTICLE_TYPES.register("treacherous_flame", () -> new SimpleParticleType(false));
    public static final Supplier<LodestoneWorldParticleType> POSSESSION = PARTICLE_TYPES.register("possession", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> SHOTGUN_SPARK = PARTICLE_TYPES.register("shotgun_spark", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> NETHER_FOG = PARTICLE_TYPES.register("nether_fog", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> LIGHTSPORE = PARTICLE_TYPES.register("lightspore", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> NIGHTSPORE = PARTICLE_TYPES.register("nightspore", LodestoneWorldParticleType::new);
    public static final Supplier<SimpleParticleType> WINDY_ASH = PARTICLE_TYPES.register("windy_ash", () -> new SimpleParticleType(false));
    public static final Supplier<LodestoneWorldParticleType> DRIFTING_SOUL = PARTICLE_TYPES.register("drifting_soul", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> WILL_O_WISP_IMPACT = PARTICLE_TYPES.register("will_o_wisp_impact", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> REDUX_DUST_BLOB = PARTICLE_TYPES.register("redux_dust_blob", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> REDUX_DUST_STAR = PARTICLE_TYPES.register("redux_dust_star", LodestoneWorldParticleType::new);

    public static void init(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
