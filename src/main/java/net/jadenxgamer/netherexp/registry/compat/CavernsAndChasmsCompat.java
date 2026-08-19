package net.jadenxgamer.netherexp.registry.compat;

import net.jadenxgamer.elysium_api.api.registry.ElysiumReflection;
import net.jadenxgamer.netherexp.core.block.LiquidloggedTransparentBlock;
import net.jadenxgamer.netherexp.core.misc.JNESoundType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

import static net.jadenxgamer.netherexp.util.RegistryHelper.registerBlock;
import static net.jadenxgamer.netherexp.util.RegistryHelper.registerItemPropertiesBlock;

public class CavernsAndChasmsCompat {
    
    public static class Blocks {

        /**
         * Necromium
         */

        public static final Supplier<Block> NECROMIUM_PLATED_BLOCK = registerItemPropertiesBlock("necromium_plated_block", () ->
                new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.5f, 0.5f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), new Item.Properties().fireResistant());

        public static final Supplier<Block> CUT_NECROMIUM_BLOCK = registerItemPropertiesBlock("cut_necromium_block", () ->
                new Block(BlockBehaviour.Properties.ofLegacyCopy(NECROMIUM_PLATED_BLOCK.get())), new Item.Properties().fireResistant());

        public static final Supplier<Block> CUT_NECROMIUM_SLAB = registerItemPropertiesBlock("cut_necromium_slab", () ->
                new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(CUT_NECROMIUM_BLOCK.get())), new Item.Properties().fireResistant());

        public static final Supplier<Block> CUT_NECROMIUM_STAIRS = registerItemPropertiesBlock("cut_necromium_stairs", () ->
                new StairBlock(CUT_NECROMIUM_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(CUT_NECROMIUM_BLOCK.get())), new Item.Properties().fireResistant());

        public static final Supplier<Block> CUT_NECROMIUM_PILLAR = registerItemPropertiesBlock("cut_necromium_pillar", () ->
                new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(CUT_NECROMIUM_BLOCK.get())), new Item.Properties().fireResistant());

        public static final Supplier<Block> NECROMIUM_GRATE = registerItemPropertiesBlock("necromium_grate", () ->
                new LiquidloggedTransparentBlock(BlockBehaviour.Properties.ofLegacyCopy(CUT_NECROMIUM_BLOCK.get()).noOcclusion().sound(JNESoundType.NETHERITE_GRATE)
                        .lightLevel(LiquidloggedTransparentBlock::stateToProperty)), new Item.Properties().fireResistant());

        public static final Supplier<Block> TREACHEROUS_BRAZIER = registerBlock("treacherous_brazier", () ->
                ElysiumReflection.createBlock().className("com.teamabnormals.caverns_and_chasms.common.block.BrazierBlock")
                        .constructorValues(0.0f, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3.5F).lightLevel(state -> 9).noOcclusion())
        );

        public static void init() {}
    }

    public static class Items {

        public static final Supplier<Item> NECROMIUM_PLATING = JNEItems.ITEMS.register("necromium_plating", () ->
                new Item(new Item.Properties().fireResistant()));

        public static void init() {}
    }
}