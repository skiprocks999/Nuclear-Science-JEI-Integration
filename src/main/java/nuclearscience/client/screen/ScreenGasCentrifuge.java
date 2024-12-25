package nuclearscience.client.screen;

import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentGasPressure;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentGasTemperature;
import electrodynamics.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.prefab.screen.component.ScreenComponentGasCentrifuge;

public class ScreenGasCentrifuge extends GenericMaterialScreen<ContainerGasCentrifuge> {

	public ScreenGasCentrifuge(ContainerGasCentrifuge container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		addComponent(new ScreenComponentGasGauge(() -> {
			TileGasCentrifuge boiler = container.getSafeHost();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerMulti>getComponent(IComponentType.GasHandler).getInputTanks()[0];
			}
			return null;
		}, 18, 19));
		addComponent(new ScreenComponentGasCentrifuge(() -> {
			TileGasCentrifuge box = menu.getSafeHost();
			if (box != null && box.isRunning.get()) {
				// return (box.ticks % 100) / 100.0;
				return 13;
			}
			return 0;
		}, () -> {
			TileGasCentrifuge boiler = container.getSafeHost();
			if (boiler != null) {
				return boiler.stored235.get() / TileGasCentrifuge.REQUIRED;
			}
			return 0;
		}, () -> {
			TileGasCentrifuge boiler = container.getSafeHost();
			if (boiler != null) {
				return boiler.stored238.get() / TileGasCentrifuge.REQUIRED;
			}
			return 0;
		}, () -> {
			TileGasCentrifuge boiler = container.getSafeHost();
			if (boiler != null) {
				return boiler.storedWaste.get() / TileGasCentrifuge.REQUIRED;
			}
			return 0;
		}, 34, 14));

		addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1, 2 + 3 * AbstractScreenComponentInfo.SIZE));
		addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1, 2 + 2 * AbstractScreenComponentInfo.SIZE));
		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));

		addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {
			TileGasCentrifuge centrifuge = menu.getSafeHost();
			if (centrifuge == null) {
				return;
			}
			graphics.drawString(font, Component.literal("U235 " + getIntString(centrifuge.stored235.get()) + "%"), 54, 17, 4210752, false);
			graphics.drawString(font, Component.literal("U238 " + getIntString(centrifuge.stored238.get()) + "%"), 54, 37, 4210752, false);
			graphics.drawString(font, Component.literal("DUST " + getIntString(centrifuge.storedWaste.get()) + "%"), 54, 58, 4210752, false);
		}));
	}

	private static String getIntString(double value) {
		int perc = (int) (value / (float) TileGasCentrifuge.REQUIRED * 100);
		if (perc < 10) {
			return "0" + perc;
		}
		return "" + perc;
	}

}