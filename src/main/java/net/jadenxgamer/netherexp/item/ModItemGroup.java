package net.jadenxgamer.netherexp.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup NETHEREXP = FabricItemGroupBuilder.build(
            new Identifier(NetherExp.MOD_ID, "netherexp_tab"), () -> new ItemStack(ModBlocks.PYRITE_NETHER_BRICKS));
}
