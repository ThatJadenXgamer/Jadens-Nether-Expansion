package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class JNEDamageTypes {
    public static final ResourceKey<DamageType> STAMPEDE_CRUSH = register("stampede_crush");
    public static final ResourceKey<DamageType> SORROWSQUISHED = register("sorrowsquished");
    public static final ResourceKey<DamageType> SHOTGUN_PELLET = register("shotgun_pellet");
    public static final ResourceKey<DamageType> WILL_O_WISP = register("will_o_wisp");
    public static final ResourceKey<DamageType> SHOTGUN_EXPLOSION = register("shotgun_explosion");
    public static final ResourceKey<DamageType> JACKHAMMER = register("jackhammer");
    public static final ResourceKey<DamageType> ICICLE = register("icicle");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, NetherExp.id(name));
    }
}
