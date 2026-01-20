package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class JNEDamageTypes {
    public static final ResourceKey<DamageType> STAMPEDE_CRUSH = registerKey("stampede_crush");
    public static final ResourceKey<DamageType> SORROWSQUISHED = registerKey("sorrowsquished");
    public static final ResourceKey<DamageType> SHOTGUN_PELLET = registerKey("shotgun_pellet");
    public static final ResourceKey<DamageType> WILL_O_WISP = registerKey("will_o_wisp");
    public static final ResourceKey<DamageType> SHOTGUN_EXPLOSION = registerKey("shotgun_explosion");
    public static final ResourceKey<DamageType> JACKHAMMER = registerKey("jackhammer");
    public static final ResourceKey<DamageType> ICICLE = registerKey("icicle");

    private static ResourceKey<DamageType> registerKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, NetherExp.id(name));
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(STAMPEDE_CRUSH, new DamageType("stampedeCrush",0.1f));
        context.register(SORROWSQUISHED, new DamageType("sorrowsquished", DamageScaling.NEVER,0f));
        context.register(SHOTGUN_PELLET, new DamageType("soulBullet",0.1f));
        context.register(WILL_O_WISP, new DamageType("will_o_wisp",0.1f));
        context.register(SHOTGUN_EXPLOSION, new DamageType("shotgunExplosion", DamageScaling.NEVER,0f));
    }
}
