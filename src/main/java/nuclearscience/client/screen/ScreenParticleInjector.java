package nuclearscience.client.screen;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentGuiTab;
import electrodynamics.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.accelerator.TileParticleInjector;
import nuclearscience.prefab.screen.component.NuclearArrows;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.prefab.utils.NuclearTextUtils;

import java.util.ArrayList;
import java.util.List;

public class ScreenParticleInjector extends GenericScreen<ContainerParticleInjector> {

    public ScreenParticleInjector(ContainerParticleInjector container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);

        imageHeight += 10;
        inventoryLabelY += 10;

        addComponent(new ScreenComponentGeneric(NuclearArrows.PARTICLE_INJECTOR_ARROWS, 44, 24));
        addComponent(new ScreenComponentSimpleLabel(titleLabelX, titleLabelY + 20, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("particleinjector.matter")));
        addComponent(new ScreenComponentSimpleLabel(titleLabelX, titleLabelY + 56, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("particleinjector.cells")));
		/*
		addComponent(new ScreenComponentSimpleLabel(titleLabelX, titleLabelY + 38, 10, Color.TEXT_GRAY, () -> {
			TileParticleInjector injector = menu.getSafeHost();
			if (injector == null) {
				return Component.empty();
			}
			ComponentElectrodynamic electro = injector.getComponent(IComponentType.Electrodynamic);
			return NuclearTextUtils.gui("particleinjector.charge", ChatFormatter.getChatDisplayShort((int) (electro.getJoulesStored() / Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE * 100.0), DisplayUnit.PERCENTAGE));
		}));

		 */
        addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE));
        addComponent(new ScreenComponentButton<>(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR, -AbstractScreenComponentInfo.SIZE + 1, 2 * AbstractScreenComponentInfo.SIZE + 2)
                .setOnPress(button -> {
                    TileParticleInjector injector = menu.getSafeHost();
                    if (injector == null) {
                        return;
                    }
                    injector.usingGateway.set(!injector.usingGateway.get());
                })
                .setIcon(NuclearIconTypes.GATEWAY)
                .onTooltip((graphics, button, x, y) -> {

					ArrayList<FormattedCharSequence> list = new ArrayList<>();

					TileParticleInjector injector = menu.getSafeHost();
					if (injector == null) {
						return;
					}

					list.add(NuclearTextUtils.tooltip("particleinjector.gatewaymode").withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
					if(injector.usingGateway.get()) {
						list.add(NuclearTextUtils.tooltip("particleinjector.gatewayenabled").withStyle(ChatFormatting.GREEN).getVisualOrderText());
					} else {
						list.add(NuclearTextUtils.tooltip("particleinjector.gatewaydisabled").withStyle(ChatFormatting.RED).getVisualOrderText());
					}

					graphics.renderTooltip(getFontRenderer(), list, x, y);

                }));

        new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
    }

    private List<? extends FormattedCharSequence> getElectricInformation() {
        ArrayList<FormattedCharSequence> list = new ArrayList<>();

        TileParticleInjector injector = menu.getSafeHost();
        if (injector == null) {
            return list;
        }

        ComponentElectrodynamic el = injector.getComponent(IComponentType.Electrodynamic);
        list.add(NuclearTextUtils.tooltip("particleinjector.charge", ChatFormatter.getChatDisplayShort(el.getJoulesStored(), DisplayUnit.JOULES).withStyle(ChatFormatting.GRAY), ChatFormatter.getChatDisplayShort(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE, DisplayUnit.JOULES).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
        list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(el.getVoltage(), DisplayUnit.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());

        return list;
    }

}