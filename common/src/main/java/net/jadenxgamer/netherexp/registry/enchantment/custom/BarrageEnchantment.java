package net.jadenxgamer.netherexp.registry.enchantment.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.QuickChargeEnchantment;

public class BarrageEnchantment extends Enchantment {

    public BarrageEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.is(JNETags.Items.SHOTGUNS);
    }

    public int getMinCost(int i) {
        return 12 + (i - 1) * 20;
    }

    public int getMaxCost(int i) {
        return this.getMinCost(i) + 25;
    }

    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof QuickChargeEnchantment) && super.checkCompatibility(other);
    }
}
