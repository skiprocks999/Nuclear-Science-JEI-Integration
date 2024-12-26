package nuclearscience.prefab.screen.component.logisticsnetwork;

import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentVerticalSlider;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentGuiTab;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import nuclearscience.client.screen.util.GenericInterfaceBoundScreen;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.prefab.utils.NuclearTextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WrapperBindInterface {

    private final GenericInterfaceBoundScreen<?> screen;
    public ScreenComponentButton<?> button;

    private ScreenComponentBoundInterface boundInterface;

    private ScreenComponentSimpleLabel label;

    private ScreenComponentButton<?> enable;
    private ScreenComponentButton<?> disable;

    private ButtonInterfaceType[] interfaceButtons = new ButtonInterfaceType[BUTTON_COUNT];


    private int topRowIndex = 0;
    private int lastRowCount = 0;


    private static final int BUTTON_COUNT = 5;

    public WrapperBindInterface(GenericInterfaceBoundScreen<?> screen, int x, int y, int tabX, int tabY, boolean needsButton, boolean updateSlots) {
        this.screen = screen;

        if (needsButton) {
            screen.addComponent(button = (ScreenComponentButton<?>) new ScreenComponentButton<>(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR, tabX, tabY).setOnPress(but -> {
                //
                ScreenComponentButton<?> button = (ScreenComponentButton<?>) but;
                button.isPressed = !button.isPressed;

                if (button.isPressed) {

                    screen.updateNonSelectorVisibility(false);

                    updateVisibility(true);

                    if(updateSlots) {
                        hideSlots();
                        screen.playerInvLabel.setVisible(false);
                    }

                    screen.binderSlider.setVisible(true);

                } else {

                    screen.updateNonSelectorVisibility(true);

                    updateVisibility(false);

                    if(updateSlots) {
                        showSlots();
                        screen.playerInvLabel.setVisible(true);
                    }

                    screen.binderSlider.setVisible(false);

                }

            }).onTooltip((graphics, but, xAxis, yAxis) -> {
                //
                ScreenComponentButton<?> button = (ScreenComponentButton<?>) but;
                List<Component> tooltips = new ArrayList<>();
                tooltips.add(NuclearTextUtils.tooltip("logisticsnetwork.linkinterface").withStyle(ChatFormatting.DARK_GRAY));
                if (!button.isPressed) {
                    tooltips.add(ElectroTextUtils.tooltip("inventoryio.presstoshow").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                } else {
                    tooltips.add(ElectroTextUtils.tooltip("inventoryio.presstohide").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                }

                graphics.renderComponentTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);

            }).setIcon(NuclearIconTypes.LINK));
        }


        boundInterface = new ScreenComponentBoundInterface(x + 10, y + 19, 114, 20);

        label = new ScreenComponentSimpleLabel(x + 10, y + 45, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("logisticsnetwork.network"));

        enable = (ScreenComponentButton<?>) new ScreenComponentButton<>(x + 127, y + 19, 20, 20).setOnPress(but -> {
            GenericTileInterfaceBound tile = screen.getMenu().getSafeHost();
            if (tile == null || boundInterface.getInterface() == null || tile.interfaceLocation.get().equals(boundInterface.getInterface().pos())) {
                return;
            }
            tile.interfaceLocation.set(boundInterface.getInterface().pos());
            tile.interfaceType.set(boundInterface.getInterface().type().ordinal());

        }).onTooltip((graphics, button, xAxis, yAxis) -> graphics.renderTooltip(screen.getFontRenderer(), NuclearTextUtils.gui("quantumtunnel.enable"), xAxis, yAxis)).setIcon(NuclearIconTypes.ENABLE);

        disable = (ScreenComponentButton<?>) new ScreenComponentButton<>(x + 150, y + 19, 20, 20).setOnPress(but -> {
            GenericTileInterfaceBound tile = screen.getMenu().getSafeHost();
            if (tile == null) {
                return;
            }
            tile.interfaceLocation.set(BlockEntityUtils.OUT_OF_REACH);
            tile.interfaceType.set(GenericTileInterface.InterfaceType.NONE.ordinal());

        }).onTooltip((graphics, button, xAxis, yAxis) -> graphics.renderTooltip(screen.getFontRenderer(), NuclearTextUtils.gui("quantumtunnel.disable"), xAxis, yAxis)).setIcon(NuclearIconTypes.DISABLE);

        int butOffX = 10;
        int butOffY = 57;

        for (int i = 0; i < BUTTON_COUNT; i++) {
            interfaceButtons[i] = new ButtonInterfaceType(x + butOffX, y + butOffY + 20 * i, 139, 20).setOnPress(but -> {
                ButtonInterfaceType button = (ButtonInterfaceType) but;

                GenericTileInterfaceBound tile = screen.getMenu().getSafeHost();
                if (tile == null || button.getInterface() == null) {
                    boundInterface.setInterface(null);
                    return;
                }

                boundInterface.setInterface(button.getInterface());

            });
        }


        screen.addComponent(boundInterface);

        screen.addComponent(enable);
        screen.addComponent(disable);

        screen.addComponent(label);

        for (int i = 0; i < 5; i++) {
            screen.addComponent(interfaceButtons[i]);
        }

        if (needsButton) {
            updateVisibility(false);
        }

    }

    public void tick() {

        if(!screen.binderSlider.isVisible()) {
            return;
        }

        GenericTileInterfaceBound tile = screen.getMenu().getSafeHost();
        if (tile == null) {
            return;
        }

        Player player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }


        lastRowCount = tile.clientInterfaces.size();

        for (int i = 0; i < BUTTON_COUNT; i++) {

            ButtonInterfaceType button = interfaceButtons[i];

            int index = topRowIndex + i;

            if (index < tile.clientInterfaces.size()) {
                button.setInterface(tile.clientInterfaces.get(index));
            } else {
                button.setInterface(null);
            }

        }

        ScreenComponentVerticalSlider slider = screen.binderSlider;
        if (lastRowCount > BUTTON_COUNT) {
            slider.updateActive(true);
            if (!slider.isSliderHeld()) {
                int moveRoom = screen.binderSlider.height - 15 - 2;

                // int moveRoom = 102 - 2;
                double moved = (double) topRowIndex / (double) (lastRowCount - (double) BUTTON_COUNT);
                slider.setSliderYOffset((int) ((double) moveRoom * moved));
            }
        } else {
            slider.updateActive(false);
            slider.setSliderYOffset(0);
            topRowIndex = 0;
        }

    }

    // pos for down, neg for up
    public void handleMouseScroll(int dir) {
        if (Screen.hasControlDown()) {
            dir *= 4;
        }
        int lastRowIndex = lastRowCount - 1;
        if (lastRowCount > BUTTON_COUNT) {
            // check in case something borked
            if (topRowIndex >= lastRowCount) {
                topRowIndex = lastRowIndex - (BUTTON_COUNT - 1);
            }
            topRowIndex = Mth.clamp(topRowIndex += dir, 0, lastRowIndex - (BUTTON_COUNT - 1));
        } else {
            topRowIndex = 0;
        }
    }

    public Consumer<Integer> getSliderClickedConsumer() {
        return (mouseY) -> {
            ScreenComponentVerticalSlider slider = screen.binderSlider;
            if (slider.isSliderActive()) {
                int sliderY = slider.yLocation;
                int sliderHeight = slider.height;
                int mouseHeight = mouseY - sliderY;
                if (mouseHeight >= sliderHeight - 2 - 15) {
                    topRowIndex = lastRowCount - BUTTON_COUNT;
                    slider.setSliderYOffset(sliderHeight - 2 - 15);
                } else if (mouseHeight <= 2) {
                    topRowIndex = 0;
                    slider.setSliderYOffset(0);
                } else {
                    double heightRatio = (double) mouseHeight / (double) sliderHeight;
                    topRowIndex = (int) Math.round((lastRowCount - BUTTON_COUNT) * heightRatio);
                    int moveRoom = slider.height - 15 - 2;
                    double moved = (double) topRowIndex / (double) (lastRowCount - (double) BUTTON_COUNT);
                    slider.setSliderYOffset((int) ((double) moveRoom * moved));
                }
            }
        };
    }

    public Consumer<Integer> getSliderDraggedConsumer() {
        return (mouseY) -> {
            ScreenComponentVerticalSlider slider = screen.binderSlider;
            if (slider.isSliderActive()) {
                int sliderY = slider.yLocation;
                int sliderHeight = slider.height;
                if (mouseY <= sliderY + 2) {
                    topRowIndex = 0;
                    slider.setSliderYOffset(0);
                } else if (mouseY >= sliderY + sliderHeight - 2 - 15) {
                    topRowIndex = lastRowCount - BUTTON_COUNT;
                    slider.setSliderYOffset(sliderHeight - 2 - 15);
                } else {
                    int mouseHeight = mouseY - sliderY;
                    slider.setSliderYOffset(mouseHeight);
                    double heightRatio = (double) mouseHeight / (double) sliderHeight;
                    topRowIndex = (int) Math.round((lastRowCount - BUTTON_COUNT) * heightRatio);
                }
            }
        };
    }

    public void updateVisibility(boolean show) {
        boundInterface.setVisible(show);
        label.setVisible(show);
        enable.setVisible(show);
        disable.setVisible(show);

        for (ButtonInterfaceType button : interfaceButtons) {
            button.setVisible(show);
        }
    }

    public void hideSlots() {
        for (int i = 0; i < this.screen.getMenu().slots.size(); i++) {

            ((SlotGeneric) this.screen.getMenu().slots.get(i)).setActive(false);

        }

    }

    public void showSlots() {
        for (int i = 0; i < this.screen.getMenu().slots.size(); i++) {

            ((SlotGeneric) this.screen.getMenu().slots.get(i)).setActive(true);

        }
    }
}
