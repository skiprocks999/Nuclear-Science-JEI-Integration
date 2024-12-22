package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerSupplyModule;

public class ScreenSupplyModule extends GenericScreen<ContainerSupplyModule> {
    public ScreenSupplyModule(ContainerSupplyModule container, Inventory inv, Component title) {
        super(container, inv, title);
    }
}
