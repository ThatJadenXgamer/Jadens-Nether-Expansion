package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AntidoteItem extends Item {
    boolean inactive;
    StatusEffectInstance statusEffect;

    public AntidoteItem(Settings settings, StatusEffectInstance statusEffect, boolean inactive) {
        super(settings);
        this.statusEffect = statusEffect;
        this.inactive = inactive;
    }

    public int getColor() {
        return 3402751;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.inactive) {
            tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
            tooltip.add((Text.empty()));
            tooltip.add(Text.translatable("item.netherexp.antidote.inactive_tooltip").formatted(Formatting.DARK_PURPLE));
            tooltip.add(Text.translatable(this.statusEffect.getTranslationKey()).formatted(Formatting.BLUE));
        }
        else if (!stack.isOf(ModItems.AWKWARD_ANTIDOTE)) {
            List<StatusEffectInstance> list = new ArrayList<>();
            list.add(this.statusEffect);
            PotionUtil.buildTooltip(list, tooltip, 1.0F);
        }
        else {
            tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
        }
    }
}
