package net.jadenxgamer.netherexp.registry.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    // Particles
    public static final DefaultParticleType ENIGMA_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType FIRE_SPARK = FabricParticleTypes.simple();
    public static final DefaultParticleType SMALL_SOUL_FLAME = FabricParticleTypes.simple();
    public static final DefaultParticleType GOLD_GLIMMER = FabricParticleTypes.simple();
    public static final DefaultParticleType REDSTONE_SPARK = FabricParticleTypes.simple();
    public static final DefaultParticleType REDSTONE_EXPLOSION = FabricParticleTypes.simple();
    public static final DefaultParticleType REDSTONE_EXPLOSION_EMITTER = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_NETHER_WART = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_WARPED_WART = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_SHROOMLIGHT = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_SHROOMNIGHT = FabricParticleTypes.simple();
    public static final DefaultParticleType RISING_SHROOMNIGHT = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_SHROOMBLIGHT = FabricParticleTypes.simple();
    public static final DefaultParticleType CRIMSON_SMOG = FabricParticleTypes.simple();
    public static final DefaultParticleType WARPED_SMOG = FabricParticleTypes.simple();
    public static final DefaultParticleType BLACK_SMOKE = FabricParticleTypes.simple();
    public static final DefaultParticleType WHITE_SMOKE = FabricParticleTypes.simple();
    public static final DefaultParticleType RED_SMOKE = FabricParticleTypes.simple();
    public static final DefaultParticleType SOUL_EMBER = FabricParticleTypes.simple();
    public static final DefaultParticleType ECTORAYS = FabricParticleTypes.simple();
    public static final DefaultParticleType ECTOPLASMA = FabricParticleTypes.simple();
    public static final DefaultParticleType BLACK_AEROSOL = FabricParticleTypes.simple();
    public static final DefaultParticleType SWIRL_POP = FabricParticleTypes.simple();
    public static final DefaultParticleType SHALE_SWIRL_POP = FabricParticleTypes.simple();
    public static final DefaultParticleType GRASP_MIST = FabricParticleTypes.simple();

    // MOD COMPAT
    public static final DefaultParticleType UMBRAL_SMOG = FabricParticleTypes.simple();
    public static final DefaultParticleType SOULBLIGHT_SMOG = FabricParticleTypes.simple();
    public static final DefaultParticleType SOULBLIGHT_SPORE = FabricParticleTypes.simple();

    // Registering Particles
    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "enigma_spore"),
                ENIGMA_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "fire_spark"),
                FIRE_SPARK);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "small_soul_flame"),
                SMALL_SOUL_FLAME);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "gold_glimmer"),
                GOLD_GLIMMER);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "redstone_spark"),
                REDSTONE_SPARK);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "redstone_explosion"),
                REDSTONE_EXPLOSION);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "redstone_explosion_emitter"),
                REDSTONE_EXPLOSION_EMITTER);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "falling_nether_wart"),
                FALLING_NETHER_WART);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "falling_warped_wart"),
                FALLING_WARPED_WART);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "falling_shroomlight"),
                FALLING_SHROOMLIGHT);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "falling_shroomnight"),
                FALLING_SHROOMNIGHT);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "rising_shroomnight"),
                RISING_SHROOMNIGHT);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "falling_shroomblight"),
                FALLING_SHROOMBLIGHT);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "crimson_smog"),
                CRIMSON_SMOG);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "warped_smog"),
                WARPED_SMOG);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "black_smoke"),
                BLACK_SMOKE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "white_smoke"),
                WHITE_SMOKE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "red_smoke"),
                RED_SMOKE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "soul_ember"),
                SOUL_EMBER);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "ectorays"),
                ECTORAYS);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "ectoplasma"),
                ECTOPLASMA);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "black_aerosol"),
                BLACK_AEROSOL);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "swirl_pop"),
                SWIRL_POP);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "shale_swirl_pop"),
                SHALE_SWIRL_POP);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "grasp_mist"),
                GRASP_MIST);

        // MOD COMPAT
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "umbral_smog"),
                UMBRAL_SMOG);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "soulblight_smog"),
                SOULBLIGHT_SMOG);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "soulblight_spore"),
                SOULBLIGHT_SPORE);

        NetherExp.LOGGER.debug("Registering Particles for " + NetherExp.MOD_ID);
    }
}
