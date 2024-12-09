package nuclearscience.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.prefab.screen.component.ScreenComponentVerticalSlider;
import nuclearscience.prefab.screen.component.quantumtunnel.*;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ScreenQuantumTunnel extends GenericScreen<ContainerQuantumTunnel> {

    public WrapperQuantumTunnelFrequencies frequencyWrapper;
    public WrapperIOEditor ioWrapper;
    public WrapperNewFrequency newFrequencyWrapper;

    public ScreenComponentVerticalSlider slider;

    public ScreenQuantumTunnel(ContainerQuantumTunnel container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);

        imageHeight += 35;

        frequencyWrapper = new WrapperQuantumTunnelFrequencies(this, 0, 0);

        addComponent(slider = new ScreenComponentVerticalSlider(5, 64, 125).setClickConsumer(frequencyWrapper.getSliderClickedConsumer()).setDragConsumer(frequencyWrapper.getSliderDraggedConsumer()));

        ioWrapper = new WrapperIOEditor(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 80, 28, 8, 23);

        newFrequencyWrapper = new WrapperNewFrequency(this, -AbstractScreenComponentInfo.SIZE + 1, 2, 0, 15);



    }

    @Override
    protected void containerTick() {
        super.containerTick();
        frequencyWrapper.tick();
    }

    @Override
    protected void initializeComponents() {
        super.initializeComponents();
        playerInvLabel.setVisible(false);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (frequencyWrapper != null) {
            if (scrollY > 0) {
                // scroll up
                frequencyWrapper.handleMouseScroll(-1);
            } else if (scrollY < 0) {
                // scroll down
                frequencyWrapper.handleMouseScroll(1);
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);
        if (slider != null && slider.isVisible()) {
            slider.mouseMoved(mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (slider != null && slider.isVisible()) {
            slider.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (slider != null && slider.isVisible()) {
            slider.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(pKeyCode, pScanCode);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey) && newFrequencyWrapper.nameEditBox.isActive()) {
            return false;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

}