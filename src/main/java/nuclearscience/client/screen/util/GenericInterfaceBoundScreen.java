package nuclearscience.client.screen.util;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentVerticalSlider;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.util.GenericInterfaceBoundContainer;
import nuclearscience.prefab.screen.component.logisticsnetwork.WrapperBindInterface;

public abstract class GenericInterfaceBoundScreen<T extends GenericInterfaceBoundContainer<?>> extends GenericScreen<T> {

    public final WrapperBindInterface binderWrapper;
    public final ScreenComponentVerticalSlider binderSlider;

    public GenericInterfaceBoundScreen(T container, Inventory inv, Component title, boolean needsButton) {
        super(container, inv, title);

        binderWrapper = new WrapperBindInterface(this, 0, 0, -AbstractScreenComponentInfo.SIZE + 1, 2, needsButton);
        addComponent(binderSlider = new ScreenComponentVerticalSlider(153, 57, 100).setClickConsumer(binderWrapper.getSliderClickedConsumer()).setDragConsumer(binderWrapper.getSliderDraggedConsumer()));
        if (needsButton) {
            binderSlider.setVisible(false);
        }
    }

    public abstract void updateNonSelectorVisibility(boolean visible);

    @Override
    protected void containerTick() {
        super.containerTick();
        if (binderSlider.isVisible()) {
            binderWrapper.tick();
        }
    }

    @Override
    protected void initializeComponents() {
        super.initializeComponents();
        playerInvLabel.setVisible(false);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (binderWrapper != null) {
            if (scrollY > 0) {
                // scroll up
                binderWrapper.handleMouseScroll(-1);
            } else if (scrollY < 0) {
                // scroll down
                binderWrapper.handleMouseScroll(1);
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (binderSlider != null && binderSlider.isVisible()) {
            binderSlider.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (binderSlider != null && binderSlider.isVisible()) {
            binderSlider.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (binderSlider.isVisible()) {
            return binderSlider.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

}
