package net.jadenxgamer.netherexp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.effect.ModStatusEffects;
import net.jadenxgamer.netherexp.registry.entity.ModEntities;
import net.jadenxgamer.netherexp.registry.entity.custom.ApparitionEntity;
import net.jadenxgamer.netherexp.registry.entity.custom.WarphopperEntity;
import net.jadenxgamer.netherexp.registry.event.WartBeardGrowerEvent;
import net.jadenxgamer.netherexp.registry.item.ModItemGroup;
import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.jadenxgamer.netherexp.registry.item.potion.ModPotions;
import net.jadenxgamer.netherexp.registry.misc_registry.*;
import net.jadenxgamer.netherexp.registry.particle.ModParticles;
import net.jadenxgamer.netherexp.registry.worldgen.feature.ModFeature;
import net.jadenxgamer.netherexp.registry.worldgen.generate.ModWorldGenerator;
import net.jadenxgamer.netherexp.util.ModLootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class NetherExp implements ModInitializer {
	public static final String MOD_ID = "netherexp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroup();
		ModBlockSetType.registerBlockSetType();
		ModWoodType.registerWoodType();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModParticles.registerParticles();
		ModArmorTrimPatterns.registerArmorTrimPatterns();

		ModWorldGenerator.generateModWorldGen();

		ModRegistries.registerModStuffs();
		ModStatusEffects.registerModStatusEffects();
		ModPotions.registerModPotions();
		ModFeature.registerModFeature();
		ModResourcePacks.registerModResourcePacks();
		ModLootTableModifier.modifyLootTables();

		GeckoLib.initialize();

		FabricDefaultAttributeRegistry.register(ModEntities.WARPHOPPER, WarphopperEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.APPARITION, ApparitionEntity.setAttributes());

		UseBlockCallback.EVENT.register(new WartBeardGrowerEvent());
	}

	// MOD COMPATIBILITY

	public static boolean checkModCompatCinderscapes() {
		return FabricLoader.getInstance().isModLoaded("cinderscapes");
	}
}
