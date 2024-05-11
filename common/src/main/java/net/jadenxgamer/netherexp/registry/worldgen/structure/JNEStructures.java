package net.jadenxgamer.netherexp.registry.worldgen.structure;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.worldgen.structure.custom.MegaFossilStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class JNEStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPE = DeferredRegister.create(NetherExp.MOD_ID, Registries.STRUCTURE_TYPE);

    public static final RegistrySupplier<StructureType<MegaFossilStructure>> MEGA_FOSSIL = STRUCTURE_TYPE.register("mega_fossil", () ->
            () -> MegaFossilStructure.CODEC);

    public static void init() {
        STRUCTURE_TYPE.register();
    }
}
