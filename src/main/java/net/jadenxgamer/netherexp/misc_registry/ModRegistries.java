package net.jadenxgamer.netherexp.misc_registry;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.block.ModBlocks;
import net.jadenxgamer.netherexp.item.ModItems;
import net.minecraft.block.Blocks;

public class ModRegistries {
    public static void registerModStuffs() {
        registerFuels();
        registerStrippables();
        registerFlattenable();
        registerCompostingChances();
    }

    private static void registerFuels() {
        NetherExp.LOGGER.info("registering Fuels for " + NetherExp.MOD_ID);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(ModItems.RAW_PYRITE, 1600);
    }

    private static void registerCompostingChances() {
        NetherExp.LOGGER.info("registering Composting Chances for " + NetherExp.MOD_ID);
        CompostingChanceRegistry registry = CompostingChanceRegistry.INSTANCE;

        registry.add(ModBlocks.WHITE_ASH_BLOCK, 0.5f);
    }

    private static void registerStrippables() {
        StrippableBlockRegistry.register(ModBlocks.CLARET_STEM, ModBlocks.STRIPPED_CLARET_STEM);
        StrippableBlockRegistry.register(ModBlocks.CLARET_HYPHAE, ModBlocks.STRIPPED_CLARET_HYPHAE);
    }

    private static void registerFlattenable() {
        FlattenableBlockRegistry.register(Blocks.SOUL_SOIL, ModBlocks.SOUL_PATH.getDefaultState());
        FlattenableBlockRegistry.register(Blocks.CRIMSON_NYLIUM, ModBlocks.CRIMSON_NYLIUM_PATH.getDefaultState());
        FlattenableBlockRegistry.register(Blocks.WARPED_NYLIUM, ModBlocks.WARPED_NYLIUM_PATH.getDefaultState());
    }
}
