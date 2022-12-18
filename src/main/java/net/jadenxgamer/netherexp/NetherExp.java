package net.jadenxgamer.netherexp;

import net.fabricmc.api.ModInitializer;
import net.jadenxgamer.netherexp.block.ModBlocks;
import net.jadenxgamer.netherexp.item.ModItems;
import net.jadenxgamer.netherexp.misc_registry.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetherExp implements ModInitializer {
	public static final String MOD_ID = "netherexp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModRegistries.registerModStuffs();
	}
}
