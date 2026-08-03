package net.jadenxgamer.netherexp.client.gui.hud;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.Stampede;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class StampedeHungerHud {

    private static final ResourceLocation TEXTURE = NetherExp.netherexpPath("textures/gui/sprites/hud/stampede_hunger.png");
    private static final int ICON_SIZE = 20;
    private static final int MAX_HUNGER = 20;
    private static final int FILL_TOP = 4;
    private static final int FILL_HEIGHT = 13;
    private static final int FLASH_DURATION_TICKS = 50;

    private static int lastHunger = -1;
    private static int flashTimer = 0;
    private static float tickAccumulator = 0.0F;
    private static boolean wasRiding = false;

    public static final LayeredDraw.Layer OVERLAY = StampedeHungerHud::render;

    private static void render(GuiGraphics gui, DeltaTracker deltaTracker) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;
        if (player == null) return;
        if (!(player.getVehicle() instanceof Stampede stampede)) {
            lastHunger = -1;
            flashTimer = 0;
            tickAccumulator = 0.0F;
            wasRiding = false;
            return;
        }

        int hunger = stampede.getHunger();
        tickAccumulator += deltaTracker.getGameTimeDeltaTicks();
        while (tickAccumulator >= 1.0F) {
            tickAccumulator -= 1.0F;
            if (flashTimer > 0) flashTimer--;
        }

        if (wasRiding) {
            if (hunger != lastHunger) {
                if (hunger > lastHunger) flashTimer = FLASH_DURATION_TICKS;
                lastHunger = hunger;
            }
        } else {
            lastHunger = hunger;
            wasRiding = true;
        }

        int screenHeight = client.getWindow().getGuiScaledHeight();
        int baseY = screenHeight - (stampede.getControllingPassenger() == player ? 50 : 60);
        int shakeOffset = 0;
        if (hunger <= 4) shakeOffset = (player.tickCount / 4) % 2 == 0 ? -1 : 0;
        int x = (client.getWindow().getGuiScaledWidth() - ICON_SIZE) / 2;
        int y = baseY + shakeOffset;

        gui.blit(TEXTURE, x, y, ICON_SIZE, ICON_SIZE, 20, 0, ICON_SIZE, ICON_SIZE, 60, 20);

        if (hunger > 0) {
            int fillPixels = (int) ((float) hunger / MAX_HUNGER * FILL_HEIGHT);
            int srcV = FILL_TOP + (FILL_HEIGHT - fillPixels);
            int destY = y + srcV;
            gui.blit(TEXTURE, x, destY, ICON_SIZE, fillPixels, 0, srcV, ICON_SIZE, fillPixels, 60, 20);
        }

        if (flashTimer > 0 && (flashTimer / 10) % 2 == 0) gui.blit(TEXTURE, x, y, ICON_SIZE, ICON_SIZE, 40, 0, ICON_SIZE, ICON_SIZE, 60, 20);
    }
}