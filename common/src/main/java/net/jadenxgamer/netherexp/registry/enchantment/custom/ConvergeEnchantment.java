package net.jadenxgamer.netherexp.registry.enchantment.custom;

import net.jadenxgamer.netherexp.registry.item.custom.ShotgunFistItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ConvergeEnchantment extends Enchantment {

    public ConvergeEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ShotgunFistItem;
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
    protected boolean checkCompatibility(Enchantment other) {
        return !(other instanceof PumpChargeEnchantment);
    }
}