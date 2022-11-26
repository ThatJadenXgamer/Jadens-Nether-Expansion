package net.jadenxgamer.netherexp.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup NETHEREXP_BLOCKS = FabricItemGroupBuilder.build(
            new Identifier(NetherExp.MOD_ID, "netherexp_blocks"), () -> new ItemStack(ModBlocks.BLUE_NETHER_BRICKS));
    public static final ItemGroup NETHEREXP_ITEMS = FabricItemGroupBuilder.build(
            new Identifier(NetherExp.MOD_ID, "netherexp_items"), () -> new ItemStack(ModItems.BLAZIUM_INGOT));
}
