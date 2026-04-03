package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.worldgen.carver.BasaltRiverCarver;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNEWorldGenRegistries {

    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(BuiltInRegistries.CARVER, NetherExp.MOD_ID);

    public static final Supplier<WorldCarver<CaveCarverConfiguration>> BASALT_RIVER = CARVERS.register("basalt_river", () ->
            new BasaltRiverCarver(CaveCarverConfiguration.CODEC));

    public static void init(IEventBus eventBus) {
        CARVERS.register(eventBus);
    }
}
