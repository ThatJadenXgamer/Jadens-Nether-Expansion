package net.jadenxgamer.netherexp;

import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.SpellParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

@Mod(value = NetherExp.MOD_ID, dist = Dist.CLIENT)
public final class NetherExpClient {

    public NetherExpClient(IEventBus modEventBus, ModContainer modContainer) {

    }

    public static void registerEntityRenderers() {

    }

    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(JNEParticleTypes.SOUL_SWIRL_POP.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.SMALL_SOUL_FIRE_FLAME.get(), FlameParticle.SmallFlameProvider::new);
        event.registerSpriteSet(JNEParticleTypes.IMMUNITY_EFFECT.get(), SpellParticle.MobEffectProvider::new);
    }
}
