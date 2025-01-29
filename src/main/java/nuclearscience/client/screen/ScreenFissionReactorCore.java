package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentTemperature;
import electrodynamics.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.NuclearScience;
import nuclearscience.common.inventory.container.ContainerFissionReactorCore;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ScreenFissionReactorCore extends GenericScreen<ContainerFissionReactorCore> {
	public static final ResourceLocation SCREEN_BACKGROUND = NuclearScience.rl("textures/gui/fissionreactor.png");

	public ScreenFissionReactorCore(ContainerFissionReactorCore container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		imageHeight += 10;
		inventoryLabelY += 10;

		addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_OFF, 77, 38));

		addComponent(new ScreenComponentTemperature(() -> {

			List<FormattedCharSequence> list = new ArrayList<>();

			TileFissionReactorCore core = menu.getSafeHost();
			if (core == null) {
				return list;
			}

			list.add(NuclearTextUtils.tooltip("fissionreactor.maxtemp", ChatFormatter.getChatDisplayShort( TileFissionReactorCore.getActualTemp(TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL), DisplayUnit.TEMPERATURE_CELCIUS).withStyle(ChatFormatting.BOLD, ChatFormatting.YELLOW)).withStyle(ChatFormatting.GRAY).getVisualOrderText());

			MutableComponent text = ChatFormatter.getChatDisplayShort(TileFissionReactorCore.getActualTemp(core.temperature.get()), DisplayUnit.TEMPERATURE_CELCIUS);

			if (core.temperature.get() > TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL) {
				text = text.withStyle(ChatFormatting.RED);
			} else if (core.temperature.get() > TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL - 100) {
				text = text.withStyle(ChatFormatting.YELLOW);
			} else {
				text = text.withStyle(ChatFormatting.GREEN);
			}

			list.add(NuclearTextUtils.tooltip("fissionreactor.currtemp", text).withStyle(ChatFormatting.GRAY).getVisualOrderText());
			if (core.temperature.get() > TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL && System.currentTimeMillis() % 1000 < 500) {
				list.add(NuclearTextUtils.tooltip("fissionreactor.warning").withStyle(ChatFormatting.BOLD, ChatFormatting.RED).getVisualOrderText());
			}

			return list;
		}, -AbstractScreenComponentInfo.SIZE + 1, 2));

		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82 + 10, 8, 72 + 10);

	}

}