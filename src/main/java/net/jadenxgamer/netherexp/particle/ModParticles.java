package net.jadenxgamer.netherexp.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType ENIGMA_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(NetherExp.MOD_ID, "enigma_spore"),
                ENIGMA_PARTICLE);
    }

}
