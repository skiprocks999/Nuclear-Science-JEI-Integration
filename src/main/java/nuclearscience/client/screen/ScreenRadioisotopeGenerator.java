package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.utilities.math.Color;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import nuclearscience.api.radiation.IRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.Constants;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ScreenRadioisotopeGenerator extends GenericScreen<ContainerRadioisotopeGenerator> {

	public ScreenRadioisotopeGenerator(ContainerRadioisotopeGenerator container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentProgress(ProgressBars.COUNTDOWN_FLAME, () -> {
			ItemStack in = container.getSlot(0).getItem();
			IRadioactiveObject rad = RadiationRegister.get(in.getItem());
			double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
			if (currentOutput > 0) {
				return 1;
			}
			return 0;
		}, 25, 24));
		addComponent(new ScreenComponentElectricInfo(this::getEnergyInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));

		addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {
			ItemStack in = menu.getSlot(0).getItem();
			IRadioactiveObject rad = RadiationRegister.get(in.getItem());
			double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
			TransferPack transfer = TransferPack.ampsVoltage(currentOutput / Constants.RADIOISOTOPEGENERATOR_VOLTAGE, Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
			graphics.drawString(font, NuclearTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnit.AMPERE)), inventoryLabelX + 60, inventoryLabelY - 48, Color.TEXT_GRAY.color(), false);
			graphics.drawString(font, NuclearTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnit.WATT)), inventoryLabelX + 60, inventoryLabelY - 35, Color.TEXT_GRAY.color(), false);
			graphics.drawString(font, NuclearTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnit.VOLTAGE)), inventoryLabelX + 60, inventoryLabelY - 22, Color.TEXT_GRAY.color(), false);
		}));

		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

	private List<? extends FormattedCharSequence> getEnergyInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		ItemStack in = menu.getSlot(0).getItem();
		IRadioactiveObject rad = RadiationRegister.get(in.getItem());
		double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
		TransferPack transfer = TransferPack.ampsVoltage(currentOutput / Constants.RADIOISOTOPEGENERATOR_VOLTAGE, Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
		list.add(NuclearTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(NuclearTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnit.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(NuclearTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnit.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		return list;
	}

}