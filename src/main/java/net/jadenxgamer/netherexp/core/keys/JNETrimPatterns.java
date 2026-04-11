package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.armortrim.TrimPattern;

public class JNETrimPatterns {
    public static final ResourceKey<TrimPattern> RIFT = key("rift");
    public static final ResourceKey<TrimPattern> SPIRIT = key("spirit");
    public static final ResourceKey<TrimPattern> VALOR = key("valor");

    private static ResourceKey<TrimPattern> key(String name) {
        return ResourceKey.create(Registries.TRIM_PATTERN, NetherExp.netherexpPath(name));
    }
}
