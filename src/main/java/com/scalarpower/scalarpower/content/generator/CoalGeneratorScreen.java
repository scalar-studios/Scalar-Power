package com.scalarpower.scalarpower.content.generator;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class CoalGeneratorScreen extends AbstractContainerScreen<CoalGeneratorMenu> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("scalarpower", "textures/gui/coal_generator.png");

    private static final int TEXTURE_WIDTH  = 256;
    private static final int TEXTURE_HEIGHT = 256;

    // Fuel slot is at menu pos (80,35) → slot box: x+80 to x+96, y+35 to y+51
    // Flame indicator sits directly below the slot
    private static final int FLAME_X      = 81;   // aligned with slot left edge
    private static final int FLAME_Y      = 54;   // 3 px below slot bottom (51+3)
    private static final int FLAME_WIDTH  = 14;
    private static final int FLAME_HEIGHT = 13;

    // Energy bar on the right side of the panel
    private static final int ENERGY_X      = 155;
    private static final int ENERGY_Y      = 17;
    private static final int ENERGY_WIDTH  = 6;
    private static final int ENERGY_HEIGHT = 50;

    // Steam animation: 3 columns rising above the slot
    private static final int[] STEAM_DX  = {-2, 3, 8};   // offsets from FLAME_X
    private static final int   STEAM_RISE = 22;           // total pixels of rise

    public CoalGeneratorScreen(CoalGeneratorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth  = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos, y = topPos;

        // Background texture
        guiGraphics.blit(
                TEXTURE,
                x, y, x + imageWidth, y + imageHeight,
                0.0F, (float) imageWidth  / TEXTURE_WIDTH,
                0.0F, (float) imageHeight / TEXTURE_HEIGHT);

        // ── Flame burn indicator (grows upward as fuel is consumed) ──────────
        if (menu.getBurnTimeTotal() > 0 && menu.getBurnTime() > 0) {
            int pixels = (int) ((float) FLAME_HEIGHT * menu.getBurnTime() / menu.getBurnTimeTotal());
            int top    = y + FLAME_Y + (FLAME_HEIGHT - pixels);
            guiGraphics.fill(x + FLAME_X, top,
                             x + FLAME_X + FLAME_WIDTH, y + FLAME_Y + FLAME_HEIGHT,
                             0xFFFF6600);
            // bright tip
            guiGraphics.fill(x + FLAME_X + 2, top,
                             x + FLAME_X + FLAME_WIDTH - 2, top + 2,
                             0xFFFFCC44);
        }

        // ── Energy bar ───────────────────────────────────────────────────────
        // dark background
        guiGraphics.fill(x + ENERGY_X, y + ENERGY_Y,
                         x + ENERGY_X + ENERGY_WIDTH, y + ENERGY_Y + ENERGY_HEIGHT,
                         0x88000000);
        int filled = menu.getEnergyCapacity() > 0
                ? (int) ((float) ENERGY_HEIGHT * menu.getEnergy() / menu.getEnergyCapacity()) : 0;
        if (filled > 0) {
            guiGraphics.fill(x + ENERGY_X,
                             y + ENERGY_Y + (ENERGY_HEIGHT - filled),
                             x + ENERGY_X + ENERGY_WIDTH,
                             y + ENERGY_Y + ENERGY_HEIGHT,
                             0xFF44CC44);
        }

        // ── Steam animation (only while burning) ─────────────────────────────
        if (menu.getBurnTime() > 0) {
            long t = System.currentTimeMillis();
            for (int i = 0; i < STEAM_DX.length; i++) {
                // each column has a phase offset so they don't all move together
                int phase  = (int) (((t / 120) + i * 8) % STEAM_RISE);
                int sx     = x + FLAME_X + STEAM_DX[i];
                int sy     = y + FLAME_Y - phase;          // rises upward
                int alpha  = (int) (200 * (1.0 - (float) phase / STEAM_RISE));
                int colour = (alpha << 24) | 0xCCCCCC;
                guiGraphics.fill(sx, sy, sx + 2, sy + 3, colour);   // small puff
            }
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 8, 6, 0x404040, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 94, 0x404040, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
