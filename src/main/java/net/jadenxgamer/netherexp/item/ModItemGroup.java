package net.jadenxgamer.netherexp.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static ItemGroup NETHEREXP;

    public static void registerItemGroup() {
        NETHEREXP = FabricItemGroup.builder(new Identifier(NetherExp.MOD_ID, "netherexp"))
                .displayName(Text.literal("Nether Expansion Tab"))
                .icon(() -> new ItemStack(ModBlocks.PYRITE_NETHER_BRICKS)).build();
    }
}
