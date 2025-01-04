package nuclearscience.common.inventory.container.util;

import electrodynamics.prefab.inventory.container.types.GenericContainerBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.network.PacketDistributor;
import nuclearscience.common.packet.type.client.PacketSetClientInterfaces;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;

public abstract class GenericInterfaceBoundContainer<T extends GenericTileInterfaceBound> extends GenericContainerBlockEntity<T> {

    public GenericInterfaceBoundContainer(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
        super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        if(!getLevel().isClientSide() && getPlayer() != null && getSafeHost() != null) {

            GenericTileInterfaceBound bound = getSafeHost();

            PacketSetClientInterfaces packet = new PacketSetClientInterfaces(bound.getBlockPos(), bound.getInterfacesForClient());

            PacketDistributor.sendToPlayer((ServerPlayer) getPlayer(), packet);

        }

    }
}
