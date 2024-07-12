package net.jadenxgamer.netherexp.registry.enchantment.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;

public class BloodshedEnchantment extends Enchantment {
    public BloodshedEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public boolean canEnchant(ItemStack stack) {
        return super.canEnchant(stack) || stack.is(JNETags.Items.SHOTGUNS);
    }

    @Override
    public int getMinCost(int level) {
        return level * 25;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 50;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof DamageEnchantment) && super.checkCompatibility(other);
    }
}