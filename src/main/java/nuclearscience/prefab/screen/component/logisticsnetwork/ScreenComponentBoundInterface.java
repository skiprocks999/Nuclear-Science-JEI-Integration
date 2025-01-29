package nuclearscience.prefab.screen.component.logisticsnetwork;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import nuclearscience.api.network.reactorlogistics.Interface;
import nuclearscience.client.screen.util.GenericInterfaceBoundScreen;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.prefab.screen.component.quantumtunnel.QuantumTunnelTextures;

public class ScreenComponentBoundInterface extends ScreenComponentGeneric {

    private Interface bound;

    public ScreenComponentBoundInterface(int x, int y, int width, int height) {
        super(x, y, width, height);
    }


    @Override
    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        if(!isVisible()) {
            return;
        }

        Interface inter = bound;

        GenericInterfaceBoundScreen<?> screen = (GenericInterfaceBoundScreen<?>) gui;

        GenericTileInterfaceBound tile = screen.getMenu().getSafeHost();

        if (tile == null) {
            return;
        }

        ITexture texture = QuantumTunnelTextures.FREQUENCY;

        ScreenComponentEditBox.drawExpandedBox(graphics, texture.getLocation(), xLocation + guiWidth, yLocation + guiHeight, width, height);

        if (inter == null) {
            if(tile.interfaceLocation.get().equals(BlockEntityUtils.OUT_OF_REACH)) {
                return;
            }
            inter = new Interface(tile.interfaceLocation.get(), GenericTileInterface.InterfaceType.values()[tile.interfaceType.get()]);
        }

        graphics.renderItem(GenericTileInterface.getItemFromType(inter.type()), guiWidth + xLocation + 2, guiHeight + yLocation + 2);

        Font font = screen.getFontRenderer();

        Component text = Component.literal(inter.pos().toShortString());

        int xOffset = 20;

        int maxWidth = width - xOffset - 2;

        int width = font.width(text);

        float scale = 1.0F;

        float addY = 0;

        if(width > maxWidth) {
            scale = (float) maxWidth / (float) width;
            addY = (font.lineHeight - font.lineHeight * scale) / 2.0F / scale;
        }

        graphics.pose().pushPose();

        graphics.pose().translate(guiWidth + xLocation, guiHeight + yLocation, 0);

        graphics.pose().scale(scale, scale, 0);

        graphics.drawString(font, text.getVisualOrderText(), xOffset / scale, 7.0F / scale + addY, Color.TEXT_GRAY.color(), false);

        graphics.pose().popPose();

    }

    public void setInterface(Interface bound) {
        this.bound = bound;
    }

    public Interface getInterface() {
        return bound;
    }

}
