package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.types.GenericContainerBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileSupplyModule;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerSupplyModule extends GenericContainerBlockEntity<TileSupplyModule> {

    public ContainerSupplyModule(int id, Inventory playerinv) {
        this(id, playerinv, new SimpleContainer(18), new SimpleContainerData(3));
    }

    public ContainerSupplyModule(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
        super(NuclearScienceMenuTypes.CONTAINER_SUPPLYMODULE.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container container, Inventory inventory) {

    }
}
