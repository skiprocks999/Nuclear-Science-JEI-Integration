package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerReactorCore;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.prefab.screen.component.NuclearArrows;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ScreenFissionReactorCore extends GenericScreen<ContainerReactorCore> {
	public static final ResourceLocation SCREEN_BACKGROUND = ResourceLocation.parse(References.ID + ":textures/gui/fissionreactor.png");

	public ScreenFissionReactorCore(ContainerReactorCore container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		imageHeight += 10;
		inventoryLabelY += 10;

		addComponent(new ScreenComponentGeneric(NuclearArrows.FISSION_REACTOR_ARROW_LR, 59, 29));
		addComponent(new ScreenComponentGeneric(NuclearArrows.FISSION_REACTOR_ARROW_DOWN, 117, 53));

		addComponent(new ScreenComponentSimpleLabel(titleLabelX, titleLabelY + 24, height, Color.TEXT_GRAY, NuclearTextUtils.gui("fissionreactor.deuterium")));
		addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {
			TileFissionReactorCore core = menu.getSafeHost();
			if (core == null) {
				return;
			}
			graphics.drawString(font, NuclearTextUtils.gui("fissionreactor.temperature", core.temperature.get().intValue() / 4 + 15 + " C"), titleLabelX, titleLabelY + 55, 4210752, false);
			if (core.temperature.get() > TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL && System.currentTimeMillis() % 1000 < 500) {
				graphics.drawString(font, NuclearTextUtils.gui("fissionreactor.warning"), titleLabelX, titleLabelY + 65, 16711680, false);
			}
		}));
		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, 2, 75, 82 + 10, 8, 72 + 10);

	}

}