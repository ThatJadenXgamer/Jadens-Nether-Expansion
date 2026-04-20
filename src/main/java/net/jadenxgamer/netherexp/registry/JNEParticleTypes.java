package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.particle.*;
import net.jadenxgamer.netherexp.util.RegistryHelper;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
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
    public static final Supplier<LodestoneWorldParticleType> WIND_TRAIL = PARTICLE_TYPES.register("wind_trail", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> GLOWING_DOT = PARTICLE_TYPES.register("glowing_dot", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> GLOWING_DOT_COIL = PARTICLE_TYPES.register("glowing_dot_coil", LodestoneWorldParticleType::new);
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
    public static final Supplier<LodestoneWorldParticleType> REDUX_POOF = PARTICLE_TYPES.register("redux_poof", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> REDUX_POOF_BLOB = PARTICLE_TYPES.register("redux_poof_blob", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> REDUX_POOF_STAR = PARTICLE_TYPES.register("redux_poof_star", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> GENERIC_GLOW = PARTICLE_TYPES.register("generic_glow", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> SPARKLE = PARTICLE_TYPES.register("sparkle", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> SHOTGUN_FLASH = PARTICLE_TYPES.register("shotgun_flash", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> PUMP_SHOTGUN_FLASH = PARTICLE_TYPES.register("pump_shotgun_flash", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> PELLET_HIT = PARTICLE_TYPES.register("pellet_hit", LodestoneWorldParticleType::new);
    public static final Supplier<SimpleParticleType> RED_EXPLOSION = PARTICLE_TYPES.register("red_explosion", () -> new SimpleParticleType(false){});
    public static final Supplier<SimpleParticleType> RED_EXPLOSION_EMITTER = PARTICLE_TYPES.register("red_explosion_emitter", () -> new SimpleParticleType(false){});
    public static final Supplier<LodestoneWorldParticleType> JNE_PORTAL = PARTICLE_TYPES.register("jne_portal", LodestoneWorldParticleType::new);
    public static final Supplier<LodestoneWorldParticleType> PORTA_MIST = PARTICLE_TYPES.register("portal_mist", LodestoneWorldParticleType::new);

    public static void init(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SOUL_SWIRL_POP.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(WISP.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(SMALL_SOUL_FIRE_FLAME.get(), FlameParticle.SmallFlameProvider::new);
        event.registerSpriteSet(IMMUNITY_EFFECT.get(), SpellParticle.MobEffectProvider::new);
        event.registerSpriteSet(SOUL_MAGMA.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(CRIMSON_SMOG.get(), SmogParticle.Provider::new);
        event.registerSpriteSet(WARPED_SMOG.get(), SmogParticle.Provider::new);
        event.registerSpriteSet(BLACK_SMOKE.get(), SmogParticle.Provider::new);
        event.registerSpriteSet(WHITE_SMOKE.get(), SmogParticle.Provider::new);
        event.registerSpriteSet(BLACK_FLAKE.get(), BlackFlakeParticle.Provider::new);
        event.registerSpriteSet(DRIPPING_ECTOPLASM.get(), JNEDripHangParticle.EctoplasmProvider::new);
        event.registerSpriteSet(FALLING_ECTOPLASM.get(), JNEFallAndLandParticle.EctoplasmProvider::new);
        event.registerSpriteSet(ECTOSPLASH.get(), SplashParticle.Provider::new);
        event.registerSpriteSet(ECTOPLASM_RAYS.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(WIND_TRAIL.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(GLOWING_DOT.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(GLOWING_DOT_COIL.get(), CoilParticle.Factory::new);
        event.registerSpriteSet(SOUL_CLOUD.get(), JNEPoofParticle.SoulProvider::new);
        event.registerSpriteSet(SILVER_GLIMMER.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(TREACHEROUS_FLAME.get(), FlameParticle.Provider::new);
        event.registerSpriteSet(POSSESSION.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(SHOTGUN_SPARK.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(NETHER_FOG.get(), ProximityFadeParticle.Factory::new);
        event.registerSpriteSet(LIGHTSPORE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(NIGHTSPORE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(WINDY_ASH.get(), WindBlownParticle.Provider::new);
        event.registerSpriteSet(DRIFTING_SOUL.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(WILL_O_WISP_IMPACT.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(REDUX_DUST_BLOB.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(REDUX_DUST_STAR.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(REDUX_POOF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(REDUX_POOF_BLOB.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(REDUX_POOF_STAR.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(GENERIC_GLOW.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(SPARKLE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(SHOTGUN_FLASH.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(PUMP_SHOTGUN_FLASH.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(PELLET_HIT.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(RED_EXPLOSION.get(), HugeExplosionParticle.Provider::new);
        event.registerSpecial(RED_EXPLOSION_EMITTER.get(), (new RedExplosionEmitterParticle.Factory()));
        event.registerSpriteSet(JNE_PORTAL.get(), JNEPortalParticle.Factory::new);
        event.registerSpriteSet(PORTA_MIST.get(), LodestoneWorldParticleType.Factory::new);
    }
}
