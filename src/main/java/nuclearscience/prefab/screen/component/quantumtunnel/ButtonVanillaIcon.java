package nuclearscience.prefab.screen.component.quantumtunnel;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import net.minecraft.client.gui.GuiGraphics;

public class ButtonVanillaIcon extends ScreenComponentButton<ButtonVanillaIcon> {

    private final ITexture icon;

    public ButtonVanillaIcon(int x, int y, int width, int height, ITexture icon) {
        super(x, y, width, height);
        this.icon = icon;
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        super.renderBackground(graphics, xAxis, yAxis, guiWidth, guiHeight);
        int deltaX = (width - icon.textureWidth()) / 2;
        int deltaY = (height - icon.textureHeight()) / 2;
        graphics.blit(icon.getLocation(), xLocation + guiWidth + deltaX, yLocation + guiHeight + deltaY, icon.textureU(), icon.textureV(), icon.textureWidth(), icon.textureHeight(), icon.imageWidth(), icon.imageHeight());
    }
}
