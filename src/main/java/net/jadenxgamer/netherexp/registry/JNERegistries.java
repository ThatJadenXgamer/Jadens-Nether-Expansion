package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.registry.data_driven.wisp_archeology.WispArcheology;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DataPackRegistryEvent;

public class JNERegistries {
    public static final ResourceKey<Registry<WispArcheology>> WISP_ARCHEOLOGY = key("wisp_archeology");

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(new ResourceLocation("netherexp", name));
    }
}
