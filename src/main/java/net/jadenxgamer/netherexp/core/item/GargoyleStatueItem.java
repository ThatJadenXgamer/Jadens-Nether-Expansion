package net.jadenxgamer.netherexp.core.item;

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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable(this.getBlock().getDescriptionId()).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
    }

    // Instead of crafting above max stack size, we make the block itself its remainder.
    // The other option would be to change the max stack size.
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return itemStack;
    }
}
