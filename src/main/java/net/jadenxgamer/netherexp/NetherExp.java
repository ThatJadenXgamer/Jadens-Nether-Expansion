package net.jadenxgamer.netherexp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.jadenxgamer.netherexp.block.ModBlocks;
import net.jadenxgamer.netherexp.effect.ModStatusEffects;
import net.jadenxgamer.netherexp.entity.ModEntities;
import net.jadenxgamer.netherexp.entity.custom.WarphopperEntity;
import net.jadenxgamer.netherexp.item.ModItemGroup;
import net.jadenxgamer.netherexp.item.ModItems;
import net.jadenxgamer.netherexp.misc_registry.ModRegistries;
import net.jadenxgamer.netherexp.misc_registry.ModResourcePacks;
import net.jadenxgamer.netherexp.particle.ModParticles;
import net.jadenxgamer.netherexp.potion.ModPotions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class NetherExp implements ModInitializer {
	public static final String MOD_ID = "netherexp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroup();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModParticles.registerParticles();

		ModRegistries.registerModStuffs();
		ModStatusEffects.registerModStatusEffects();
		ModPotions.registerModPotions();
		ModResourcePacks.init();

		GeckoLib.initialize();

		FabricDefaultAttributeRegistry.register(ModEntities.WARPHOPPER, WarphopperEntity.setAttributes());
	}
}
