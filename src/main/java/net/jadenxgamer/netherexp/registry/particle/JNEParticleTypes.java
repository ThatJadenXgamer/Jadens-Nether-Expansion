package net.jadenxgamer.netherexp.registry.particle;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JNEParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, NetherExp.MOD_ID);

    public static final RegistryObject<SimpleParticleType> ENIGMA_KERNEL = PARTICLE_TYPES.register("enigma_kernel", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> FIRE_SPARK = PARTICLE_TYPES.register("fire_spark", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> SMALL_SOUL_FIRE_FLAME = PARTICLE_TYPES.register("small_soul_fire_flame", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> TREACHEROUS_FLAME = PARTICLE_TYPES.register("treacherous_flame", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> GOLD_GLIMMER = PARTICLE_TYPES.register("gold_glimmer", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> REDSTONE_SPARK = PARTICLE_TYPES.register("redstone_spark", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> REDSTONE_EXPLOSION = PARTICLE_TYPES.register("redstone_explosion", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> REDSTONE_EXPLOSION_EMITTER = PARTICLE_TYPES.register("redstone_explosion_emitter", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> FALLING_NETHER_WART = PARTICLE_TYPES.register("falling_nether_wart", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> FALLING_WARPED_WART = PARTICLE_TYPES.register("falling_warped_wart", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> FALLING_SHROOMLIGHT = PARTICLE_TYPES.register("falling_shroomlight", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> FALLING_SHROOMNIGHT = PARTICLE_TYPES.register("falling_shroomnight", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> RISING_SHROOMNIGHT = PARTICLE_TYPES.register("rising_shroomnight", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> CRIMSON_SMOG = PARTICLE_TYPES.register("crimson_smog", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> WARPED_SMOG = PARTICLE_TYPES.register("warped_smog", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> BLACK_SMOKE = PARTICLE_TYPES.register("black_smoke", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> WHITE_SMOKE = PARTICLE_TYPES.register("white_smoke", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> RED_SMOKE = PARTICLE_TYPES.register("red_smoke", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> SOUL_EMBER = PARTICLE_TYPES.register("soul_ember", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> ECTORAYS = PARTICLE_TYPES.register("ectorays", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> ECTOPLASMA = PARTICLE_TYPES.register("ectoplasma", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> BLACK_FLAKE = PARTICLE_TYPES.register("black_flake", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> TRAIL_BLACK_FLAKE = PARTICLE_TYPES.register("trail_black_flake", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> SWIRL_POP = PARTICLE_TYPES.register("swirl_pop", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> GRASP_MIST = PARTICLE_TYPES.register("grasp_mist", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> WISP = PARTICLE_TYPES.register("wisp", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> COLORED_WISP = PARTICLE_TYPES.register("colored_wisp", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> MAGMA_CREAM = PARTICLE_TYPES.register("magma_cream", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> IMMUNITY_EFFECT = PARTICLE_TYPES.register("immunity_effect", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> CANDLE_BURST = PARTICLE_TYPES.register("candle_burst", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> FIREBALL_TRAIL = PARTICLE_TYPES.register("fireball_trail", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> SMALL_FIREBALL_TRAIL = PARTICLE_TYPES.register("small_fireball_trail", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> DRAGON_FIREBALL_TRAIL = PARTICLE_TYPES.register("dragon_fireball_trail", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> SOUL_CLOUD = PARTICLE_TYPES.register("soul_cloud", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> ANCIENT_WAX_CLOUD = PARTICLE_TYPES.register("ancient_wax_cloud", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> HAZE = PARTICLE_TYPES.register("haze", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> RED_HAZE = PARTICLE_TYPES.register("red_haze", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> RED_SPARKLE = PARTICLE_TYPES.register("red_sparkle", () -> new SimpleParticleType(false){});

    /**
     * MOD COMPAT
     */
    public static final RegistryObject<SimpleParticleType> FALLING_SHROOMBRIGHT = PARTICLE_TYPES.register("falling_shroombright", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> SHALE_SWIRL_POP = PARTICLE_TYPES.register("shale_swirl_pop", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> BLIGHT_SWIRL_POP = PARTICLE_TYPES.register("blight_swirl_pop", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> UMBRAL_SMOG = PARTICLE_TYPES.register("umbral_smog", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> SOULBLIGHT_SMOG = PARTICLE_TYPES.register("soulblight_smog", () -> new SimpleParticleType(false){});
    public static final RegistryObject<SimpleParticleType> JACKHAMMER = PARTICLE_TYPES.register("jackhammer", () -> new SimpleParticleType(false){});

    public static void init(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
