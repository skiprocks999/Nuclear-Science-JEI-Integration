package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.types.GenericContainerBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerTeleporter extends GenericContainerBlockEntity<TileTeleporter> {

    public ContainerTeleporter(int id, Inventory playerinv) {
        this(id, playerinv, new SimpleContainer(1), new SimpleContainerData(3));
    }

    public ContainerTeleporter(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
        super(NuclearScienceMenuTypes.CONTAINER_TELEPORTER.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container container, Inventory inventory) {

    }
}
