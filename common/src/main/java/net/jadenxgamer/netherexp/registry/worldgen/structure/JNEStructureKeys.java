package net.jadenxgamer.netherexp.registry.worldgen.structure;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

public class JNEStructureKeys {
    public static final ResourceKey<Structure> SANCTUM = createKey("sanctum");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(NetherExp.MOD_ID, name));
    }
}
