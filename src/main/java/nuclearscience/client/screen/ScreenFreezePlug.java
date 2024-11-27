package nuclearscience.client.screen;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.msreactor.TileFreezePlug;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ScreenFreezePlug extends GenericScreen<ContainerFreezePlug> {

	public ScreenFreezePlug(ContainerFreezePlug container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.FREEZEPLUG_USAGE_PER_TICK * 20));
		addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {
			TileFreezePlug plug = menu.getSafeHost();
			if (plug == null) {
				return;
			}
			if (plug.isFrozen()) {
				graphics.drawString(font, NuclearTextUtils.gui("freezeplug.status", NuclearTextUtils.gui("freezeplug.frozen").withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText(), 40, 30, 0, false);

			} else {
				graphics.drawString(font, NuclearTextUtils.gui("freezeplug.status", NuclearTextUtils.gui("freezeplug.off").withStyle(ChatFormatting.RED)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText(), 40, 30, 0, false);
			}
			graphics.drawString(font, NuclearTextUtils.gui("freezeplug.saltbonus", ChatFormatter.getChatDisplayShort(plug.getSaltBonus() * 100.0, DisplayUnit.PERCENTAGE).withStyle(ChatFormatting.BLACK)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText(), 40, 50, 0, false);
		}));

	}
}