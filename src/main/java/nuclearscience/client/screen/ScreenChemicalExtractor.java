package nuclearscience.client.screen;

import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.tile.TileChemicalExtractor;

public class ScreenChemicalExtractor extends GenericMaterialScreen<ContainerChemicalExtractor> {
	public ScreenChemicalExtractor(ContainerChemicalExtractor container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getSafeHost();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
				if (processor.operatingTicks.get() > 0) {
					return Math.min(1.0, processor.operatingTicks.get() / (processor.requiredTicks.get() / 2.0));
				}
			}
			return 0;
		}, 42, 30));
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getSafeHost();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
				if (processor.operatingTicks.get() > processor.requiredTicks.get() / 2.0) {
					return Math.min(1.0, (processor.operatingTicks.get() - processor.requiredTicks.get() / 2.0) / (processor.requiredTicks.get() / 2.0));
				}
			}
			return 0;
		}, 98, 30));
		addComponent(new ScreenComponentGeneric(ProgressTextures.ARROW_LEFT_OFF, 46, 50));
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileChemicalExtractor boiler = container.getSafeHost();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, 21, 18));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));

		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

}