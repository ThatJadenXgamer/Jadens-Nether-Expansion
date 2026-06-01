package net.jadenxgamer.netherexp.client.gui;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.awt.*;
import java.util.List;

public class BetaPopupWarning extends Screen {
    private static final ResourceLocation POPUP_TEXTURE = NetherExp.netherexpPath("textures/gui/popup.png");
    private static final int POPUP_WIDTH = 227;
    private static final int POPUP_HEIGHT = 185;

    private static final int TEXT_AREA_X_OFFSET = 10;
    private static final int TEXT_AREA_Y_OFFSET = 44;
    private static final int TEXT_AREA_WIDTH = 208;
    private static final int TEXT_AREA_HEIGHT = 120;

    private static final int LINE_HEIGHT = 9;

    public BetaPopupWarning() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        super.init();
        int popupX = (this.width - POPUP_WIDTH) / 2;
        int popupY = (this.height - POPUP_HEIGHT) / 2;
        Button closeButton = Button.builder(Component.literal("I Understand"), button -> this.onClose())
                .bounds(popupX + 10, popupY + 156, 100, 20)
                .build();
        Button dontShowAgainButton = Button.builder(Component.literal("Don't show again"), button -> {
                    this.onClose();
                    JNEConfigs.SHOW_BETA_WARNING_POPUP.set(false);
                    JNEConfigImpl.COMMON.save();
                })
                .bounds(popupX + 116, popupY + 156, 100, 20)
                .build();
        this.addRenderableWidget(closeButton);
        this.addRenderableWidget(dontShowAgainButton);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {}

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        renderTransparentBackground(gui);
        int popupX = (this.width - POPUP_WIDTH) / 2;
        int popupY = (this.height - POPUP_HEIGHT) / 2;

        gui.blit(POPUP_TEXTURE, popupX, popupY, 0, 0, POPUP_WIDTH, POPUP_HEIGHT, POPUP_WIDTH, POPUP_HEIGHT);

        int textStartX = popupX + TEXT_AREA_X_OFFSET;
        int textStartY = popupY + TEXT_AREA_Y_OFFSET;

        renderWrappedText(gui, textStartX, textStartY, TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
        super.render(gui, mouseX, mouseY, partialTick);
    }

    private void renderWrappedText(GuiGraphics gui, int x, int y, int maxWidth, int maxHeight) {
        Minecraft minecraft = Minecraft.getInstance();
        var font = minecraft.font;
        List<Component> textLines = List.of(
                Component.literal("WARNING!").withStyle(ChatFormatting.BOLD),
                Component.literal(" "),
                Component.literal("This is just a BETA build of JNE 1.21.1; it is not in a playable state yet,"),
                Component.literal("as such expect many bugs, missing features and general unstableness."),
                Component.literal(" "),
                Component.literal("It is recommended that you do not use this current build in worlds that you care about as there is a high chance things will inevitably ").append(
                        Component.literal("BREAK!").withStyle(ChatFormatting.BOLD)
                )
        );

        int currentY = y;
        for (Component line : textLines) {
            List<FormattedCharSequence> wrappedLines = font.split(line, maxWidth);

            for (FormattedCharSequence wrappedLine : wrappedLines) {
                if (currentY + LINE_HEIGHT > y + maxHeight) return;
                int lineWidth = font.width(wrappedLine);
                int centeredX = x + (maxWidth - lineWidth) / 2;
                gui.drawString(font, wrappedLine, centeredX, currentY, 0xFFFFFF, false);
                currentY += LINE_HEIGHT;
            }
        }
    }

    @Override
    public void renderTransparentBackground(GuiGraphics gui) {
        Color color = new Color(0xA32B19);
        gui.fillGradient(0, 0, this.width, this.height, 0xC0000000, color.getRGB());
    }
}