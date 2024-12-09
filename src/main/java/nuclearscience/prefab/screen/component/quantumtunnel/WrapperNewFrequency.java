package nuclearscience.prefab.screen.component.quantumtunnel;

import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import nuclearscience.api.quantumtunnel.FrequencyType;
import nuclearscience.client.screen.ScreenQuantumTunnel;
import nuclearscience.common.packet.type.server.PacketCreateNewFreqeuency;
import nuclearscience.prefab.utils.NuclearTextUtils;

import java.util.ArrayList;
import java.util.List;

public class WrapperNewFrequency {

    private ScreenComponentSimpleLabel titleLabel;
    private ScreenComponentSimpleLabel typeLabel;
    private ButtonFrequencySelecter publicButton;
    private ButtonFrequencySelecter privateButton;

    private ScreenComponentSimpleLabel nameLabel;
    public ScreenComponentEditBox nameEditBox;
    private ScreenComponentButton<?> createButton;
    private ScreenComponentButton<?> cancelButton;

    public WrapperNewFrequency(ScreenQuantumTunnel screen, int tabX, int tabY, int x, int y) {

        screen.addComponent(new ButtonNewFrequency(tabX, tabY).setOnPress(but -> {
            //
            ButtonNewFrequency button = (ButtonNewFrequency) but;
            button.isPressed = !button.isPressed;

            if (button.isPressed) {

                screen.frequencyWrapper.updateVisibility(false);
                screen.ioWrapper.updateVisibility(false);

                updateVisibility(true);

                screen.slider.setVisible(false);

            } else {

                screen.frequencyWrapper.updateVisibility(true);
                screen.ioWrapper.updateVisibility(false);

                updateVisibility(false);

                screen.slider.setVisible(true);

            }

        }).onTooltip((graphics, but, xAxis, yAxis) -> {
            //
            ButtonNewFrequency button = (ButtonNewFrequency) but;
            List<Component> tooltips = new ArrayList<>();
            tooltips.add(NuclearTextUtils.tooltip("quantumtunnel.createnew").withStyle(ChatFormatting.DARK_GRAY));
            if (!button.isPressed) {
                tooltips.add(ElectroTextUtils.tooltip("inventoryio.presstoshow").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            } else {
                tooltips.add(ElectroTextUtils.tooltip("inventoryio.presstohide").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            }

            graphics.renderComponentTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);

        }));

        screen.addComponent(titleLabel = new ScreenComponentSimpleLabel(x + 15, y + 20, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("quantumtunnel.newfrequency")));

        screen.addComponent(typeLabel = new ScreenComponentSimpleLabel(x + 15, y + 40, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("quantumtunnel.frequencytype")));
        screen.addComponent(privateButton = new ButtonFrequencySelecter(x + 13, y + 55, 70, 20).setOnPress(button -> {

            privateButton.isSelected = true;
            publicButton.isSelected = false;

        }).setLabel(NuclearTextUtils.gui("quantumtunnel.private")));

        screen.addComponent(publicButton = new ButtonFrequencySelecter(x + 93, y + 55, 70, 20).setOnPress(button -> {

            privateButton.isSelected = false;
            publicButton.isSelected = true;

        }).setLabel(NuclearTextUtils.gui("quantumtunnel.public")));


        screen.addComponent(nameLabel = new ScreenComponentSimpleLabel(x + 15, y + 80, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("quantumtunnel.name")));
        screen.addEditBox(nameEditBox = new ScreenComponentEditBox(x + 15, y + 90, 120, 15, screen.getFontRenderer()).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(50));

        screen.addComponent(createButton = new ScreenComponentButton<>(x + 13, y + 120, 70, 20).setOnPress(button -> {

            if(nameEditBox.getValue().isEmpty() || nameEditBox.getValue().isBlank()) {
                return;
            }

            Player player = Minecraft.getInstance().player;

            if(player == null) {
                return;
            }

            FrequencyType type;

            if(privateButton.isSelected) {
                type = FrequencyType.PRIVATE;
            } else if (publicButton.isSelected) {
                type = FrequencyType.PUBLIC;
            } else {
                return;
            }

            PacketDistributor.sendToServer(new PacketCreateNewFreqeuency(player.getUUID(), nameEditBox.getValue(), type));

            updateVisibility(false);
            screen.frequencyWrapper.updateVisibility(true);

        }).setLabel(NuclearTextUtils.gui("quantumtunnel.create")));

        screen.addComponent(cancelButton = new ScreenComponentButton<>(x + 93, y + 120, 70, 20).setOnPress(button -> {

            screen.frequencyWrapper.updateVisibility(true);

            updateVisibility(false);

            screen.slider.setVisible(true);

        }).setLabel(NuclearTextUtils.gui("quantumtunnel.cancel")));

        updateVisibility(false);
    }

    public void updateVisibility(boolean show) {
        titleLabel.setVisible(show);
        typeLabel.setVisible(show);
        publicButton.setVisible(show);
        publicButton.isSelected = false;
        privateButton.setVisible(show);
        privateButton.isSelected = false;
        nameLabel.setVisible(show);
        nameEditBox.setVisible(show);
        nameEditBox.setActive(show);
        createButton.setVisible(show);
        cancelButton.setVisible(show);
    }
}
