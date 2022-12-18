package net.jadenxgamer.netherexp.misc_registry;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.block.ModBlocks;

public class ModRegistries {
    public static void registerModStuffs() {
        registerFuels();
        registerStrippables();
    }

    private static void registerFuels() {
        NetherExp.LOGGER.info("registering Fuels for " + NetherExp.MOD_ID);
        FuelRegistry registry = FuelRegistry.INSTANCE;
    }

    private static void registerStrippables() {
        StrippableBlockRegistry.register(ModBlocks.CLARET_STEM, ModBlocks.STRIPPED_CLARET_STEM);
        StrippableBlockRegistry.register(ModBlocks.CLARET_HYPHAE, ModBlocks.STRIPPED_CLARET_HYPHAE);
    }

}
