package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerCloudChamber;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.TileCloudChamber;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ScreenCloudChamber extends GenericScreen<ContainerCloudChamber> {
    public ScreenCloudChamber(ContainerCloudChamber container, Inventory inv, Component title) {
        super(container, inv, title);

        addComponent(new ScreenComponentFluidGauge(() -> {
            TileCloudChamber boiler = container.getSafeHost();
            if (boiler != null) {
                return boiler.<ComponentFluidHandlerSimple>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
            }
            return null;
        }, 21, 18));

        addComponent(new ScreenComponentSimpleLabel(60, 25, 10, Color.TEXT_GRAY, () -> {
            TileCloudChamber boiler = container.getSafeHost();
            if (boiler == null) {
                return NuclearTextUtils.gui("cloudchamber.status", NuclearTextUtils.gui("cloudchamber.offline").withStyle(ChatFormatting.DARK_RED));
            }
            MutableComponent status = boiler.active.get() ? NuclearTextUtils.gui("cloudchamber.active").withStyle(ChatFormatting.GREEN) : NuclearTextUtils.gui("cloudchamber.offline").withStyle(ChatFormatting.DARK_RED);
            return NuclearTextUtils.gui("cloudchamber.status", status);
        }));

        addComponent(new ScreenComponentSimpleLabel(60, 45, 10, Color.TEXT_GRAY, () -> {
            TileCloudChamber boiler = container.getSafeHost();
            if (boiler == null) {
                return NuclearTextUtils.gui("cloudchamber.detected", 0);
            }
            int count = boiler.active.get() ? boiler.sources.get().size() : 0;
            return NuclearTextUtils.gui("cloudchamber.detected", Component.literal("" + count).withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY));
        }));

        addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK * 20));

    }
}
