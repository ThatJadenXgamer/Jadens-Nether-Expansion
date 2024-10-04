package net.jadenxgamer.netherexp.registry.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TreacherousFlameItem extends Item {
    public TreacherousFlameItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add((Component.empty()));
        tooltip.add(Component.translatable("item.netherexp.treacherous_flame.description").withStyle(ChatFormatting.RED));
    }
}
