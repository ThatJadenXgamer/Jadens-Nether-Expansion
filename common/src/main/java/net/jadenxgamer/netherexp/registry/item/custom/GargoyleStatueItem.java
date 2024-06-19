package net.jadenxgamer.netherexp.registry.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GargoyleStatueItem extends BlockItem {
    public GargoyleStatueItem(Block block, Properties properties) {
        super(block, properties);
    }

    public @NotNull String getDescriptionId() {
        return "block.netherexp.gargoyle_statue";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable(this.getBlock().getDescriptionId()).withStyle(ChatFormatting.DARK_AQUA));
    }
}
