package net.jadenxgamer.netherexp.registry.misc_registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.armortrim.TrimPattern;

public class JNETrimPatterns {
    public static final ResourceKey<TrimPattern> RIFT = registerTrim("rift");
    public static final ResourceKey<TrimPattern> SPIRIT = registerTrim("spirit");
    public static final ResourceKey<TrimPattern> VALOR = registerTrim("valor");

    private static ResourceKey<TrimPattern> registerTrim(String name) {
        return ResourceKey.create(Registries.TRIM_PATTERN, new ResourceLocation(NetherExp.MOD_ID, name));
    }
}
