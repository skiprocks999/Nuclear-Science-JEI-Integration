package nuclearscience.prefab.screen.component.quantumtunnel;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.common.tile.TileQuantumTunnel;

public class ButtonTunnelFrequency extends ScreenComponentButton<ButtonTunnelFrequency> {
    private TunnelFrequency frequency = null;

    private boolean isSelected = false;

    public ButtonTunnelFrequency(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        if (!isVisible()) {
            return;
        }

        GenericScreen<ContainerQuantumTunnel> screen = (GenericScreen<ContainerQuantumTunnel>) gui;

        TileQuantumTunnel tile = screen.getMenu().getSafeHost();

        if (tile == null) {
            return;
        }

        ITexture texture;

        if (frequency != null && (tile.frequency.get().equals(frequency) || isSelected || isHovered())) {

            texture = QuantumTunnelTextures.FREQUENCY_SELECTED;

        } else {

            texture = QuantumTunnelTextures.FREQUENCY;

        }

        ScreenComponentEditBox.drawExpandedBox(graphics, texture.getLocation(), xLocation + guiWidth, yLocation + guiHeight, width, height);

        if (frequency == null) {
            return;
        }

        graphics.drawString(screen.getFontRenderer(), Component.literal(frequency.getName()), guiWidth + xLocation + 5, guiHeight + yLocation + 5, Color.TEXT_GRAY.color(), false);

        Player player = tile.getLevel().getPlayerByUUID(frequency.getCreatorId());

        graphics.drawString(screen.getFontRenderer(), player != null ? player.getName() : Component.literal(frequency.getCreatorFallbackName()), guiWidth + xLocation + 10, guiHeight + yLocation + 15, Color.TEXT_GRAY.color(), false);


    }

    public void setFrequency(TunnelFrequency frequency) {
        this.frequency = frequency;
    }

    public TunnelFrequency getFrequency() {
        return frequency;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

}
