package nuclearscience.prefab.screen.component.quantumtunnel;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import net.minecraft.client.gui.GuiGraphics;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.common.tile.TileQuantumTunnel;

public class ButtonTunnelFrequency extends ScreenComponentButton<ButtonTunnelFrequency> {

    public boolean isSelected = false;
    private TunnelFrequency frequency = null;

    public ButtonTunnelFrequency(ITexture texture, int x, int y) {
        super(texture, x, y);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        if(!isVisible()) {
            return;
        }

        GenericScreen<ContainerQuantumTunnel> screen = (GenericScreen<ContainerQuantumTunnel>) gui;

        TileQuantumTunnel tile = screen.getMenu().getSafeHost();

        if(tile == null) {
            return;
        }

        if(frequency == null || !tile.frequency.get().equals(frequency)) {

        } else if(frequency != null && isSelected) {

        }


    }
}
