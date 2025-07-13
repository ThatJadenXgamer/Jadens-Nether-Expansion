package net.jadenxgamer.netherexp.core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class NonConsumableItem extends Item {

    public NonConsumableItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("item.netherexp.non_consumable.tooltip").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
    }
}
