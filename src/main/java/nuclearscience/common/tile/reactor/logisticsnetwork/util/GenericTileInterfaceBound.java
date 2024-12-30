package nuclearscience.common.tile.reactor.logisticsnetwork.util;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.Interface;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GenericTileInterfaceBound extends GenericTileLogisticsMember {

    public static final GenericTileInterface.InterfaceType[] CONTROL_RODS = {GenericTileInterface.InterfaceType.FISSION, GenericTileInterface.InterfaceType.MS};
    public static final GenericTileInterface.InterfaceType[] TEMPERATURE = {GenericTileInterface.InterfaceType.FISSION, GenericTileInterface.InterfaceType.MS};
    public static final GenericTileInterface.InterfaceType[] SUPPLIES = {GenericTileInterface.InterfaceType.FISSION, GenericTileInterface.InterfaceType.FUSION};
    public static final GenericTileInterface.InterfaceType[] ALL = {GenericTileInterface.InterfaceType.FISSION, GenericTileInterface.InterfaceType.MS, GenericTileInterface.InterfaceType.FUSION};

    public final Property<Boolean> linked = property(new Property<>(PropertyTypes.BOOLEAN, "islinked", false)).onChange((prop, old) -> {

        if (level == null || level.isClientSide) {
            return;
        }

        if (BlockEntityUtils.isLit(this) ^ prop.get()) {
            BlockEntityUtils.updateLit(this, prop.get());
        }


    });

    public final Property<BlockPos> interfaceLocation = property(new Property<>(PropertyTypes.BLOCK_POS, "interfacelocation", BlockEntityUtils.OUT_OF_REACH)).onChange((prop, old) -> {

        if (level == null || level.isClientSide) {
            return;
        }

        onInterfacePropChange(prop, old);

    });
    public final Property<Integer> interfaceType = property(new Property<>(PropertyTypes.INTEGER, "interfacetype", GenericTileInterface.InterfaceType.NONE.ordinal()));

    public final List<Interface> clientInterfaces = new ArrayList<>();

    public GenericTileInterfaceBound(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
        super(tileEntityTypeIn, worldPos, blockState);
    }

    @Override
    public void tickServer(ComponentTickable tickable) {
        super.tickServer(tickable);

        if (!networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
            linked.set(false);
            return;
        }

        TileReactorLogisticsCable cable = networkCable.getSafe();

        if (cable.isRemoved()) {
            linked.set(false);
            return;
        }

        ReactorLogisticsNetwork network = cable.getNetwork();

        GenericTileInterface inter = network.getInterface(interfaceLocation.get());

        if (!network.isControllerActive() || inter == null) {
            linked.set(false);
            return;
        }

        if (inter.getInterfaceType() != GenericTileInterface.InterfaceType.values()[interfaceType.get()] || !checkLinkedPosition(inter)) {
            interfaceLocation.set(BlockEntityUtils.OUT_OF_REACH);
            interfaceType.set(GenericTileInterface.InterfaceType.NONE.ordinal());
            linked.set(false);
        }

        linked.set(true);

    }

    public abstract boolean checkLinkedPosition(GenericTileInterface inter);

    public abstract GenericTileInterface.InterfaceType[] getValidInterfaces();

    public List<Interface> getInterfacesForClient() {
        if (networkCable == null || !networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
            return Collections.emptyList();
        }

        TileReactorLogisticsCable cable = networkCable.getSafe();

        if (cable.isRemoved()) {
            return Collections.emptyList();
        }

        ReactorLogisticsNetwork network = cable.getNetwork();

        List<GenericTileInterface> interfaces = network.getInterfacesForType(getValidInterfaces());

        List<Interface> list = new ArrayList<>();

        interfaces.forEach(tile -> {
            list.add(new Interface(tile.getBlockPos(), tile.getInterfaceType()));
        });

        return list;
    }

    public void onInterfacePropChange(Property<BlockPos> prop, BlockPos old) {

    }

}
