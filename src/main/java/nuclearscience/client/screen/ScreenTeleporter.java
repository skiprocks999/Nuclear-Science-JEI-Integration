package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerTeleporter;

public class ScreenTeleporter extends GenericScreen<ContainerTeleporter> {
    public ScreenTeleporter(ContainerTeleporter container, Inventory inv, Component title) {
        super(container, inv, title);
    }
}
