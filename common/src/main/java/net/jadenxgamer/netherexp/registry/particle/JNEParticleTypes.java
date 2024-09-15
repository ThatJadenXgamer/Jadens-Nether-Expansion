package net.jadenxgamer.netherexp.registry.particle;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class JNEParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(NetherExp.MOD_ID, Registries.PARTICLE_TYPE);

    public static final RegistrySupplier<SimpleParticleType> ENIGMA_KERNEL = PARTICLE_TYPES.register("enigma_kernel", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> FIRE_SPARK = PARTICLE_TYPES.register("fire_spark", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> SMALL_SOUL_FIRE_FLAME = PARTICLE_TYPES.register("small_soul_fire_flame", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> TREACHEROUS_FLAME = PARTICLE_TYPES.register("treacherous_flame", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> GOLD_GLIMMER = PARTICLE_TYPES.register("gold_glimmer", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> REDSTONE_SPARK = PARTICLE_TYPES.register("redstone_spark", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> REDSTONE_EXPLOSION = PARTICLE_TYPES.register("redstone_explosion", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> REDSTONE_EXPLOSION_EMITTER = PARTICLE_TYPES.register("redstone_explosion_emitter", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> FALLING_NETHER_WART = PARTICLE_TYPES.register("falling_nether_wart", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> FALLING_WARPED_WART = PARTICLE_TYPES.register("falling_warped_wart", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> FALLING_SHROOMLIGHT = PARTICLE_TYPES.register("falling_shroomlight", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> FALLING_SHROOMNIGHT = PARTICLE_TYPES.register("falling_shroomnight", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> RISING_SHROOMNIGHT = PARTICLE_TYPES.register("rising_shroomnight", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> CRIMSON_SMOG = PARTICLE_TYPES.register("crimson_smog", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> WARPED_SMOG = PARTICLE_TYPES.register("warped_smog", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> BLACK_SMOKE = PARTICLE_TYPES.register("black_smoke", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> WHITE_SMOKE = PARTICLE_TYPES.register("white_smoke", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> RED_SMOKE = PARTICLE_TYPES.register("red_smoke", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> SOUL_EMBER = PARTICLE_TYPES.register("soul_ember", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> ECTORAYS = PARTICLE_TYPES.register("ectorays", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> ECTOPLASMA = PARTICLE_TYPES.register("ectoplasma", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> BLACK_AEROSOL = PARTICLE_TYPES.register("black_aerosol", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> SWIRL_POP = PARTICLE_TYPES.register("swirl_pop", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> GRASP_MIST = PARTICLE_TYPES.register("grasp_mist", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> WISP = PARTICLE_TYPES.register("wisp", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> COLORED_WISP = PARTICLE_TYPES.register("colored_wisp", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> MAGMA_CREAM = PARTICLE_TYPES.register("magma_cream", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> IMMUNITY_EFFECT = PARTICLE_TYPES.register("immunity_effect", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> FALLING_BLOOD = PARTICLE_TYPES.register("falling_blood", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> LANDING_BLOOD = PARTICLE_TYPES.register("landing_blood", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> CANDLE_BURST = PARTICLE_TYPES.register("candle_burst", () -> new SimpleParticleType(false){});

    // MOD COMPAT
    public static final RegistrySupplier<SimpleParticleType> FALLING_SHROOMBLIGHT = PARTICLE_TYPES.register("falling_shroomblight", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> UMBRAL_SMOG = PARTICLE_TYPES.register("umbral_smog", () -> new SimpleParticleType(false){});
    public static final RegistrySupplier<SimpleParticleType> SHALE_SWIRL_POP = PARTICLE_TYPES.register("shale_swirl_pop", () -> new SimpleParticleType(false){});

    public static void init() {
        PARTICLE_TYPES.register();
    }
}
