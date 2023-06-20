package net.jadenxgamer.netherexp.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {

    // Particles
    public static final DefaultParticleType ENIGMA_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType SMALL_SOUL_FLAME = FabricParticleTypes.simple();
    public static final DefaultParticleType GOLD_GLIMMER = FabricParticleTypes.simple();
    public static final DefaultParticleType REDSTONE_SPARK = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_NETHER_WART = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_WARPED_WART = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_SHROOMLIGHT = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_SHROOMNIGHT = FabricParticleTypes.simple();
    public static final DefaultParticleType RISING_SHROOMNIGHT = FabricParticleTypes.simple();
    public static final DefaultParticleType CRIMSON_SMOG = FabricParticleTypes.simple();
    public static final DefaultParticleType WARPED_SMOG = FabricParticleTypes.simple();

    // Registering Particles
    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "enigma_spore"),
                ENIGMA_PARTICLE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "small_soul_flame"),
                SMALL_SOUL_FLAME);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "gold_glimmer"),
                GOLD_GLIMMER);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "redstone_spark"),
                REDSTONE_SPARK);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "falling_nether_wart"),
                FALLING_NETHER_WART);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "falling_warped_wart"),
                FALLING_WARPED_WART);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "falling_shroomlight"),
                FALLING_SHROOMLIGHT);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "falling_shroomnight"),
                FALLING_SHROOMNIGHT);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "rising_shroomnight"),
                RISING_SHROOMNIGHT);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "crimson_smog"),
                CRIMSON_SMOG);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "warped_smog"),
                WARPED_SMOG);

        NetherExp.LOGGER.debug("Registering Mod Particles for " + NetherExp.MOD_ID);
    }
}
