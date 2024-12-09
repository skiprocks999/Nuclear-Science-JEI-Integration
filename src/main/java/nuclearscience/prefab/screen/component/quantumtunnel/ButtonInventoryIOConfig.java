package nuclearscience.prefab.screen.component.quantumtunnel;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentGuiTab;
import net.minecraft.client.gui.GuiGraphics;
import nuclearscience.prefab.screen.component.NuclearIconTypes;

public class ButtonInventoryIOConfig extends ScreenComponentButton<ButtonInventoryIOConfig> {

    public boolean isPressed = false;

    public ButtonInventoryIOConfig(int x, int y) {
        super(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR, x, y);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        super.renderBackground(graphics, xAxis, yAxis, guiWidth, guiHeight);

        ITexture icon = NuclearIconTypes.IOCONFIG;

        int slotXOffset = (texture.imageWidth() - icon.imageWidth()) / 2;
        int slotYOffset = (texture.imageHeight() - icon.imageHeight()) / 2;
        graphics.blit(icon.getLocation(), guiWidth + xLocation + slotXOffset, guiHeight + yLocation + slotYOffset, icon.textureU(), icon.textureV(), icon.textureWidth(), icon.textureHeight(), icon.imageWidth(), icon.imageHeight());
    }

}
