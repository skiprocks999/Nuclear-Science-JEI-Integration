package nuclearscience.prefab.screen.component.quantumtunnel;

import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.client.screen.ScreenQuantumTunnel;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.prefab.screen.component.ScreenComponentVerticalSlider;
import nuclearscience.prefab.utils.NuclearTextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WrapperQuantumTunnelFrequencies {

    private final ScreenQuantumTunnel screen;
    private final int x;
    private final int y;

    private ScreenComponentFillArea box;
    private ScreenComponentSimpleLabel frequencyLabel;

    private ButtonVanillaIcon enable;
    private ButtonVanillaIcon disable;

    private ButtonTunnelFrequency[] frequencyButtons = new ButtonTunnelFrequency[5];
    private ButtonVanillaIcon[] editButtons = new ButtonVanillaIcon[5];
    private ButtonVanillaIcon[] deleteButtons = new ButtonVanillaIcon[5];

    private ButtonFrequencySelecter publicSelector;
    private ButtonFrequencySelecter privateSelector;

    private int topRowIndex = 0;
    private int lastRowCount = 0;

    private boolean isPrivate = true;

    private TunnelFrequency selectedFrequency = null;

    public WrapperQuantumTunnelFrequencies(ScreenQuantumTunnel screen, int x, int y) {
        this.screen = screen;
        this.x = x;
        this.y = y;

        box = (ScreenComponentFillArea) new ScreenComponentFillArea(x + 5, y + 24, 120, 10, new Color(112, 112, 112, 255), new Color(28, 28, 28, 255));

        frequencyLabel = new ScreenComponentSimpleLabel(x + 6, y + 25, 10, Color.WHITE, () -> {
            TileQuantumTunnel tile = screen.getMenu().getSafeHost();
            Component frequencyName;
            if (selectedFrequency != null) {
                frequencyName = Component.literal(selectedFrequency.getName());
            } else if (tile == null || tile.frequency.get().equals(TunnelFrequency.NO_FREQUENCY)) {
                frequencyName = NuclearTextUtils.gui("quantumtunnel.none");
            } else {
                frequencyName = Component.literal(tile.frequency.get().getName());
            }

            return frequencyName;

        });

        enable = new ButtonVanillaIcon(x + 127, y + 19,20, 20, NuclearIconTypes.ENABLE).setOnPress(but -> {
            TileQuantumTunnel tile = screen.getMenu().getSafeHost();
            if(tile == null || selectedFrequency == null || tile.frequency.get().equals(selectedFrequency)) {
                return;
            }
            tile.frequency.set(selectedFrequency);

        }).onTooltip((graphics, button, xAxis, yAxis) -> graphics.renderTooltip(screen.getFontRenderer(), NuclearTextUtils.gui("quantumtunnel.enable"), xAxis, yAxis));

        disable = new ButtonVanillaIcon(x + 150, y + 19,20, 20, NuclearIconTypes.DISABLE).setOnPress(but -> {
            TileQuantumTunnel tile = screen.getMenu().getSafeHost();
            if(tile == null) {
                return;
            }
            tile.frequency.set(TunnelFrequency.NO_FREQUENCY);

        }).onTooltip((graphics, button, xAxis, yAxis) -> graphics.renderTooltip(screen.getFontRenderer(), NuclearTextUtils.gui("quantumtunnel.disable"), xAxis, yAxis));

        publicSelector = new ButtonFrequencySelecter(x + 16, y + 44, 70, 15).setOnPress(but -> {
            ButtonFrequencySelecter button = (ButtonFrequencySelecter) but;
            button.setSelected(true);
            privateSelector.setSelected(false);
            isPrivate = false;
        }).setLabel(NuclearTextUtils.gui("quantumtunnel.public"));

        privateSelector = new ButtonFrequencySelecter(x + 70 + 16 + 5, y + 44, 70, 15).setOnPress(but -> {
            ButtonFrequencySelecter button = (ButtonFrequencySelecter) but;
            button.setSelected(true);
            publicSelector.setSelected(false);
            isPrivate = true;
        }).setLabel(NuclearTextUtils.gui("quantumtunnel.private"));

        int butOffX = 19;
        int butOffY = 64;

        for (int i = 0; i < 5; i++) {
            frequencyButtons[i] = new ButtonTunnelFrequency(x + butOffX, y + butOffY + 25 * i,110, 25).setOnPress(but -> {
                ButtonTunnelFrequency button = (ButtonTunnelFrequency) but;

                TileQuantumTunnel tile = screen.getMenu().getSafeHost();
                if(tile == null || button.getFrequency() == null) {
                    selectedFrequency = null;
                    return;
                }

                selectedFrequency = button.getFrequency();

            });
        }

        for (int i = 0; i < 5; i++) {
            editButtons[i] = new ButtonVanillaIcon(x + butOffX + 111, y + 2 + butOffY + 25 * i,20, 20, NuclearIconTypes.PENCIL).setOnPress(but -> {
                ButtonVanillaIcon button = (ButtonVanillaIcon) but;



            }).onTooltip((graphics, button, xAxis, yAxis) -> graphics.renderTooltip(screen.getFontRenderer(), NuclearTextUtils.gui("quantumtunnel.edit"), xAxis, yAxis));
        }

        for (int i = 0; i < 5; i++) {

            final int index = i;

            deleteButtons[i] = new ButtonVanillaIcon(x + butOffX + 132, y + 2 + butOffY + 25 * i,20, 20, NuclearIconTypes.DELETE).setOnPress(but -> {

                ButtonTunnelFrequency tunnel = frequencyButtons[index];

                TileQuantumTunnel tile = screen.getMenu().getSafeHost();

                Player player = Minecraft.getInstance().player;

                if(player == null || tunnel.getFrequency() == null || tile == null) {
                    return;
                }

                TunnelFrequency frequency = tunnel.getFrequency();

                if(selectedFrequency != null && selectedFrequency.equals(frequency) && selectedFrequency.getCreatorId().equals(player)) {
                    selectedFrequency = null;
                }

                if(tile.frequency.get().equals(frequency)) {
                    tile.frequency.set(TunnelFrequency.NO_FREQUENCY);
                }






            }).onTooltip((graphics, button, xAxis, yAxis) -> graphics.renderTooltip(screen.getFontRenderer(), NuclearTextUtils.gui("quantumtunnel.delete"), xAxis, yAxis));
        }



        screen.addComponent(box);
        screen.addComponent(frequencyLabel);

        screen.addComponent(enable);
        screen.addComponent(disable);

        screen.addComponent(publicSelector);
        screen.addComponent(privateSelector);

        for (int i = 0; i < 5; i++) {
            screen.addComponent(frequencyButtons[i]);
        }
        for (int i = 0; i < 5; i++) {
            screen.addComponent(editButtons[i]);
        }
        for (int i = 0; i < 5; i++) {
            screen.addComponent(deleteButtons[i]);
        }

        privateSelector.setSelected(true);

    }

    public void tick() {
        TileQuantumTunnel tile = screen.getMenu().getSafeHost();
        if(tile == null) {
            return;
        }

        Player player = Minecraft.getInstance().player;

        if(player == null) {
            return;
        }

        List<TunnelFrequency> frequencies = new ArrayList<>();

        if(isPrivate) {
            frequencies.addAll(tile.clientFrequencies.get(player.getUUID()));
        } else {
           frequencies.addAll(tile.clientFrequencies.get(TunnelFrequency.PUBLIC_ID));
        }

        for(int i = 0; i < 5; i++) {

            ButtonTunnelFrequency button = frequencyButtons[i];

            int index = topRowIndex + i;

            if (index < frequencies.size()) {
                button.setFrequency(frequencies.get(index));
            } else {
                button.setFrequency(null);
            }

        }

        ScreenComponentVerticalSlider slider = screen.slider;
        if (lastRowCount > 4) {
            slider.updateActive(true);
            if (!slider.isSliderHeld()) {
                int moveRoom = screen.slider.height - 15 - 2;

                // int moveRoom = 102 - 2;
                double moved = (double) topRowIndex / (double) (lastRowCount - 4.0D);
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
        if (lastRowCount > 4) {
            // check in case something borked
            if (topRowIndex >= lastRowCount) {
                topRowIndex = lastRowIndex - 3;
            }
            topRowIndex = Mth.clamp(topRowIndex += dir, 0, lastRowIndex - 3);
        } else {
            topRowIndex = 0;
        }
    }

    public Consumer<Integer> getSliderClickedConsumer() {
        return (mouseY) -> {
            ScreenComponentVerticalSlider slider = screen.slider;
            if (slider.isSliderActive()) {
                int sliderY = slider.yLocation;
                int sliderHeight = slider.height;
                int mouseHeight = mouseY - sliderY;
                if (mouseHeight >= sliderHeight - 2 - 15) {
                    topRowIndex = lastRowCount - 4;
                    slider.setSliderYOffset(sliderHeight - 2 - 15);
                } else if (mouseHeight <= 2) {
                    topRowIndex = 0;
                    slider.setSliderYOffset(0);
                } else {
                    double heightRatio = (double) mouseHeight / (double) sliderHeight;
                    topRowIndex = (int) Math.round((lastRowCount - 4) * heightRatio);
                    int moveRoom = screen.slider.height - 15 - 2;
                    double moved = (double) topRowIndex / (double) (lastRowCount - 4.0D);
                    screen.slider.setSliderYOffset((int) ((double) moveRoom * moved));
                }
            }
        };
    }

    public Consumer<Integer> getSliderDraggedConsumer() {
        return (mouseY) -> {
            ScreenComponentVerticalSlider slider = screen.slider;
            if (slider.isSliderActive()) {
                int sliderY = slider.yLocation;
                int sliderHeight = slider.height;
                if (mouseY <= sliderY + 2) {
                    topRowIndex = 0;
                    slider.setSliderYOffset(0);
                } else if (mouseY >= sliderY + sliderHeight - 4 - 15) {
                    topRowIndex = lastRowCount - 4;
                    slider.setSliderYOffset(sliderHeight - 4 - 15);
                } else {
                    int mouseHeight = mouseY - sliderY;
                    slider.setSliderYOffset(mouseHeight);
                    double heightRatio = (double) mouseHeight / (double) sliderHeight;
                    topRowIndex = (int) Math.round((lastRowCount - 4) * heightRatio);
                }
            }
        };
    }

    public void updateVisibility(boolean show) {
        box.setVisible(show);
        frequencyLabel.setVisible(show);

        enable.setVisible(show);
        disable.setVisible(show);

        for(ButtonTunnelFrequency button : frequencyButtons) {
            button.setVisible(show);
        }

        for(ButtonVanillaIcon button : editButtons) {
            button.setVisible(show);
        }

        for(ButtonVanillaIcon button : deleteButtons) {
            button.setVisible(show);
        }

        publicSelector.setVisible(show);
        privateSelector.setVisible(show);
    }

}
