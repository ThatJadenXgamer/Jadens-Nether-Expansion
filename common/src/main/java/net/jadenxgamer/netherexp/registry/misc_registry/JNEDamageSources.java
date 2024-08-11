package net.jadenxgamer.netherexp.registry.misc_registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class JNEDamageSources {
    public static final ResourceKey<DamageType> DEATH_CLOUD_SUFFOCATION = register("grave_cloud_suffocation");
    public static final ResourceKey<DamageType> STAMPEDE_CRUSH = register("stampede_crush");
    public static final ResourceKey<DamageType> SOUL_BULLET = register("soul_bullet");
    public static final ResourceKey<DamageType> WILL_O_WISP = register("will_o_wisp");
    public static final ResourceKey<DamageType> SHOTGUN_EXPLOSION = register("shotgun_explosion");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(NetherExp.MOD_ID, name));
    }
}
