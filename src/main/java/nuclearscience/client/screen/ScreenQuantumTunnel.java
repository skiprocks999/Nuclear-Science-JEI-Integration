package nuclearscience.client.screen;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ScreenQuantumTunnel extends GenericScreen<ContainerQuantumTunnel> {

	private ScreenComponentEditBox outputField;
	private ScreenComponentEditBox frequencyField;

	private boolean needsUpdate = true;

	public ScreenQuantumTunnel(ContainerQuantumTunnel container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addEditBox(frequencyField = new ScreenComponentEditBox(115, 14, 46, 13, getFontRenderer()).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(6).setResponder(this::updateFreq).setFilter(ScreenComponentEditBox.INTEGER));
		addEditBox(outputField = new ScreenComponentEditBox(115, 34, 46, 13, getFontRenderer()).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(6).setResponder(this::updateOutput).setFilter(ScreenComponentEditBox.POSITIVE_DECIMAL));
		addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {
			TileQuantumTunnel box = menu.getSafeHost();
			if (box == null) {
				return;
			}
			//graphics.drawString(font, NuclearTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(box.getOutputJoules() * 20.0 / TileQuantumTunnel.DEFAULT_VOLTAGE, DisplayUnit.AMPERE)), inventoryLabelX, inventoryLabelY - 55, 4210752, false);
			//graphics.drawString(font, NuclearTextUtils.gui("machine.transfer", ChatFormatter.getChatDisplayShort(box.getOutputJoules() * 20.0, DisplayUnit.WATT)), inventoryLabelX, inventoryLabelY - 42, Color.TEXT_GRAY.color(), false);
			//graphics.drawString(font, NuclearTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(TileQuantumTunnel.DEFAULT_VOLTAGE, DisplayUnit.VOLTAGE)), inventoryLabelX, inventoryLabelY - 29, Color.TEXT_GRAY.color(), false);
			//graphics.drawString(font, NuclearTextUtils.gui("machine.stored", ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(box.storedJoules.get(), DisplayUnit.JOULES), ChatFormatter.getChatDisplayShort(TileQuantumTunnel.DEFAULT_MAX_JOULES, DisplayUnit.JOULES))), inventoryLabelX, inventoryLabelY - 16, 4210752, false);
		}));
	}

	private void updateFreq(String val) {
		frequencyField.setFocus(true);
		outputField.setFocus(false);
		handleFrequency(val);
	}

	private void updateOutput(String val) {
		frequencyField.setFocus(false);
		outputField.setFocus(true);
		handleOutput(val);
	}

	private void handleFrequency(String freq) {
		if (freq.isEmpty()) {
			return;
		}

		Integer frequency = 0;

		try {
			frequency = Integer.parseInt(frequencyField.getValue());
		} catch (Exception e) {
			// Not required
		}

		TileQuantumTunnel cap = menu.getSafeHost();

		if (cap == null) {
			return;
		}

		cap.frequency.set(frequency);

	}

	private void handleOutput(String out) {

		if (out.isEmpty()) {
			return;
		}

		Double output = 0.0;
		try {
			output = Double.parseDouble(outputField.getValue());
		} catch (Exception e) {
			// Not required
		}

		TileQuantumTunnel cap = menu.getSafeHost();

		if (cap == null) {
			return;
		}

		//cap.outputJoules.set(output);

		//cap.outputJoules.toString();

	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.render(graphics, mouseX, mouseY, partialTicks);
		if (needsUpdate && menu.getSafeHost() != null) {
			needsUpdate = false;
			//outputField.setValue("" + menu.getSafeHost().outputJoules);
			//frequencyField.setValue("" + menu.getSafeHost().frequency);
		}
	}

	protected void initializeComponents() {
		addComponent(guiTitle = new ScreenComponentSimpleLabel(this.titleLabelX, this.titleLabelY, 10, Color.TEXT_GRAY, this.title));
	}

}