package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.datadriven.WispArchaeology;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class JNERegistries {
    public static final ResourceKey<Registry<WispArchaeology>> WISP_ARCHAEOLOGY = key("wisp_archaeology");

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(NetherExp.id(name));
    }
}
