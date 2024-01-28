package net.jadenxgamer.netherexp;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.config.NetherExpConfigs;
import net.jadenxgamer.netherexp.registry.effect.ModStatusEffects;
import net.jadenxgamer.netherexp.registry.entity.ModEntities;
import net.jadenxgamer.netherexp.registry.entity.custom.ApparitionEntity;
import net.jadenxgamer.netherexp.registry.entity.custom.GraspEntity;
import net.jadenxgamer.netherexp.registry.entity.custom.WarphopperEntity;
import net.jadenxgamer.netherexp.registry.entity.custom.WispEntity;
import net.jadenxgamer.netherexp.registry.event.NyliumPathEvent;
import net.jadenxgamer.netherexp.registry.event.SoulPathEvent;
import net.jadenxgamer.netherexp.registry.event.WartBeardGrowerEvent;
import net.jadenxgamer.netherexp.registry.fluid.ModFluids;
import net.jadenxgamer.netherexp.registry.item.ModItemGroup;
import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.jadenxgamer.netherexp.registry.item.potion.ModPotions;
import net.jadenxgamer.netherexp.registry.misc_registry.*;
import net.jadenxgamer.netherexp.registry.particle.ModParticles;
import net.jadenxgamer.netherexp.registry.worldgen.biome.ModBiomes;
import net.jadenxgamer.netherexp.registry.worldgen.feature.ModFeature;
import net.jadenxgamer.netherexp.registry.worldgen.generate.ModWorldGenerator;
import net.jadenxgamer.netherexp.util.ModLootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class NetherExp implements ModInitializer {
	public static final String MOD_ID = "netherexp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final NetherExpConfigs CONFIG = AutoConfig.register(NetherExpConfigs.class, GsonConfigSerializer::new).getConfig();

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroup();
		ModBiomes.registerBiomes();
		ModBlockSetType.registerBlockSetType();
		ModWoodType.registerWoodType();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModParticles.registerParticles();
		ModFluids.registerModFluids();
		ModArmorTrimPatterns.registerArmorTrimPatterns();

		ModWorldGenerator.generateModWorldGen();

		ModRegistries.registerModStuffs();
		ModDamageSources.registerModDamageSources();
		ModStatusEffects.registerModStatusEffects();
		ModPotions.registerModPotions();
		ModFeature.registerModFeature();
		ModResourcePacks.registerModResourcePacks();
		ModLootTableModifier.modifyLootTables();

		GeckoLib.initialize();

		FabricDefaultAttributeRegistry.register(ModEntities.WARPHOPPER, WarphopperEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.APPARITION, ApparitionEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.WISP, WispEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.GRASP, GraspEntity.setAttributes());

		UseBlockCallback.EVENT.register(new WartBeardGrowerEvent());
		UseBlockCallback.EVENT.register(new NyliumPathEvent());
		UseBlockCallback.EVENT.register(new SoulPathEvent());
	}
	public static NetherExpConfigs getConfig () {
		return CONFIG;
	}

	// MOD COMPATIBILITY

	public static boolean checkModCompatCinderscapes() {
		return FabricLoader.getInstance().isModLoaded("cinderscapes");
	}

	public static boolean checkModCompatInfernalExpansion() {
		return FabricLoader.getInstance().isModLoaded("infernalexp");
	}

	public static boolean checkModCompatGardensOfTheDead() {
		return FabricLoader.getInstance().isModLoaded("gardens_of_the_dead");
	}

	public static boolean checkModCompatPigsteel() {
		return FabricLoader.getInstance().isModLoaded("pigsteel");
	}
}
