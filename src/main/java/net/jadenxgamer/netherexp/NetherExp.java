package net.jadenxgamer.netherexp;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.effect.JNEStatusEffects;
import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityTypes;
import net.jadenxgamer.netherexp.registry.entity.custom.*;
import net.jadenxgamer.netherexp.registry.event.LiquidloggedBucketEvent;
import net.jadenxgamer.netherexp.registry.event.NyliumPathEvent;
import net.jadenxgamer.netherexp.registry.event.SoulPathEvent;
import net.jadenxgamer.netherexp.registry.event.WartBeardGrowerEvent;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.item.JNEItemGroup;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotions;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEArmorTrimPatterns;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEResourcePacks;
import net.jadenxgamer.netherexp.registry.particle.JNEParticles;
import net.jadenxgamer.netherexp.registry.worldgen.biome.JNEBiomes;
import net.jadenxgamer.netherexp.registry.worldgen.feature.JNEFeature;
import net.jadenxgamer.netherexp.registry.worldgen.generate.JNEWorldGenerator;
import net.jadenxgamer.netherexp.util.JNELootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class NetherExp implements ModInitializer {
	public static final String MOD_ID = "netherexp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final JNEConfigs CONFIG = AutoConfig.register(JNEConfigs.class, GsonConfigSerializer::new).getConfig();

	@Override
	public void onInitialize() {
		// REGISTRIES
		JNEItemGroup.registerItemGroup();
		JNEBiomes.registerBiomes();

		JNEItems.registerItems();
		JNEBlocks.registerBlocks();
		JNEParticles.registerParticles();
		JNEFluids.registerFluids();
		JNEArmorTrimPatterns.registerArmorTrimPatterns();

		JNEWorldGenerator.generateWorldGen();

		JNEDamageSources.registerDamageSources();
		JNEStatusEffects.registerStatusEffects();
		JNEEnchantments.registerEnchantments();
		JNEPotions.registerPotions();
		JNEFeature.registerFeature();
		JNEResourcePacks.registerResourcePacks();
		JNELootTableModifier.modifyLootTables();

		GeckoLib.initialize();

		// ENTITY ATTRIBUTES
		FabricDefaultAttributeRegistry.register(JNEEntityTypes.WARPHOPPER, WarphopperEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(JNEEntityTypes.STAMPEDE, StampedeEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(JNEEntityTypes.APPARITION, ApparitionEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(JNEEntityTypes.WISP, WispEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(JNEEntityTypes.GRASP, GraspEntity.setAttributes());

		// FUELS
		FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
		fuelRegistry.add(JNEItems.RAW_PYRITE, 1600);
		fuelRegistry.add(JNEItems.FOSSIL_FUEL, 1600);
		fuelRegistry.add(JNEItems.MAGMA_CUBE_BUCKET, 20600);

		// COMPOSTABLES
		CompostingChanceRegistry compostRegistry = CompostingChanceRegistry.INSTANCE;

		// 50%
		compostRegistry.add(JNEBlocks.WEEPING_IVY, 0.5f);
		compostRegistry.add(JNEBlocks.TWISTING_IVY, 0.5f);
		compostRegistry.add(JNEBlocks.TWILIGHT_IVY, 0.5f);
		compostRegistry.add(JNEBlocks.TWILIGHT_VINES, 0.5f);
		compostRegistry.add(JNEBlocks.WHITE_ASH_BLOCK, 0.5f);

		// 30%
		compostRegistry.add(JNEItems.WHITE_ASH_POWDER, 0.3f);
		compostRegistry.add(JNEItems.LIGHTSPORES, 0.3f);
		compostRegistry.add(JNEItems.NIGHTSPORES, 0.3f);
		compostRegistry.add(JNEItems.BLIGHTSPORES, 0.3f);

		// EVENTS
		UseBlockCallback.EVENT.register(new WartBeardGrowerEvent());
		UseBlockCallback.EVENT.register(new NyliumPathEvent());
		UseBlockCallback.EVENT.register(new SoulPathEvent());
		UseBlockCallback.EVENT.register(new LiquidloggedBucketEvent());
	}
	public static JNEConfigs getConfig () {
		return CONFIG;
	}

	// MOD COMPATIBILITY

	public static boolean checkAnyModWithWood() {
		return FabricLoader.getInstance().isModLoaded("cinderscapes") ||
				FabricLoader.getInstance().isModLoaded("infernalexp") ||
				FabricLoader.getInstance().isModLoaded("gardens_of_the_dead");
	}

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
