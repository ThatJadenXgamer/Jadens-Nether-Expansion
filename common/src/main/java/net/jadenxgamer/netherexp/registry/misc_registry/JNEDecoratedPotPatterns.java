package net.jadenxgamer.netherexp.registry.misc_registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class JNEDecoratedPotPatterns {

    public static final ResourceKey<String> SEALED = registerKey("sealed_pottery_pattern");
    public static final ResourceKey<String> SPECTRE = registerKey("spectre_pottery_pattern");
    public static final ResourceKey<String> MARIONETTE = registerKey("marionette_pottery_pattern");
    public static final ResourceKey<String> ELDRITCH = registerKey("eldritch_pottery_pattern");
    public static final ResourceKey<String> DECEPTION = registerKey("deception_pottery_pattern");
    public static final ResourceKey<String> FIREARM = registerKey("firearm_pottery_pattern");
    public static final ResourceKey<String> BOTANICAL = registerKey("botanical_pottery_pattern");

    public static ResourceKey<String> registerKey(String name) {
        return ResourceKey.create(Registries.DECORATED_POT_PATTERNS, new ResourceLocation(NetherExp.MOD_ID, name));
    }
}
