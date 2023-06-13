package net.jadenxgamer.netherexp.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType ENIGMA_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType SMALL_SOUL_FLAME = FabricParticleTypes.simple();
    public static final DefaultParticleType GOLD_GLIMMER = FabricParticleTypes.simple();
    public static final DefaultParticleType REDSTONE_SPARK = FabricParticleTypes.simple();
    public static final DefaultParticleType CRIMSON_SMOG = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "enigma_spore"),
                ENIGMA_PARTICLE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "small_soul_flame"),
                SMALL_SOUL_FLAME);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "gold_glimmer"),
                GOLD_GLIMMER);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "redstone_spark"),
                REDSTONE_SPARK);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "crimson_smog"),
                CRIMSON_SMOG);
    }
}
