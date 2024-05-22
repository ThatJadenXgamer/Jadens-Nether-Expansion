package net.jadenxgamer.netherexp.fabric.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.item.custom.ShotgunFistItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ShotgunTemperatureOverlayFabric implements HudRenderCallback {
    private final Minecraft minecraft = Minecraft.getInstance();

    @Override
    public void onHudRender(GuiGraphics guiGraphics, float tickDelta) {
        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledHeight();
        Entity entity = minecraft.cameraEntity;
        if (entity instanceof Player player) {
            if (!player.getAbilities().instabuild && !player.isSpectator()) {
                ItemStack stack = player.getMainHandItem();
                if (stack.is(JNEItems.SHOTGUN_FIST.get()) && EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.CARTRIDGE.get(), stack) > 0) {
                    int ammo = ShotgunFistItem.getAmmo(stack);
                    guiGraphics.blit(new ResourceLocation(NetherExp.MOD_ID, "textures/gui/shotgun_temperature.png"), (width / 2 -6), height - 50, ammo * 12, 12, 12, 12);
                }
                else if (stack.is(JNEItems.SHOTGUN_FIST.get()) || stack.is(JNEItems.PUMP_CHARGE_SHOTGUN.get())) {
                    int temperature = ShotgunFistItem.getTemperature(stack);
                    guiGraphics.blit(new ResourceLocation(NetherExp.MOD_ID, "textures/gui/shotgun_temperature.png"), (width / 2 -6), height - 50, temperature * 12, 0, 12, 12);
                }
            }
        }
    }
}
