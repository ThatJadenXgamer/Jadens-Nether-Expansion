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
    public static final Supplier<SimpleParticleType> SMALL_SOUL_FIRE_FLAME = PARTICLE_TYPES.register("small_soul_fire_flame", () -> new SimpleParticleType(false));
    public static final Supplier<ParticleType<ColorParticleOption>> IMMUNITY_EFFECT = RegistryHelper.registerColorParticle("immunity_effect", false);

    public static void init(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
