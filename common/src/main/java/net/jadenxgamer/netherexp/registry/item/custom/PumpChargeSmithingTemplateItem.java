package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class PumpChargeSmithingTemplateItem extends SmithingTemplateItem {
    private static final ResourceLocation EMPTY_SLOT_TREACHEROUS_FLAME = new ResourceLocation(NetherExp.MOD_ID, "item/empty_slot_treacherous_flame");
    private static final ResourceLocation EMPTY_SLOT_SHOTGUN = new ResourceLocation(NetherExp.MOD_ID, "item/empty_slot_shotgun");

    public static Component UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(NetherExp.MOD_ID, "smithing_template.pump_charge_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE);
    public static Component UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(NetherExp.MOD_ID, "smithing_template.pump_charge_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE);
    public static Component PUMP_CHARGE_UPGRADE = Component.translatable(Util.makeDescriptionId("upgrade", new ResourceLocation(NetherExp.MOD_ID, "pump_charge_upgrade"))).withStyle(ChatFormatting.GRAY);
    public static Component PUMP_CHARGE_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(NetherExp.MOD_ID, "smithing_template.pump_charge_upgrade.base_slot_description")));
    public static Component PUMP_CHARGE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(NetherExp.MOD_ID, "smithing_template.pump_charge_upgrade.additions_slot_description")));

    public PumpChargeSmithingTemplateItem() {
        super(UPGRADE_APPLIES_TO, UPGRADE_INGREDIENTS, PUMP_CHARGE_UPGRADE, PUMP_CHARGE_UPGRADE_BASE_SLOT_DESCRIPTION, PUMP_CHARGE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION, List.of(EMPTY_SLOT_SHOTGUN), List.of(EMPTY_SLOT_TREACHEROUS_FLAME));
    }
}
