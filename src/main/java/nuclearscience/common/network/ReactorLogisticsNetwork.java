package nuclearscience.common.network;

import electrodynamics.common.network.NetworkRegistry;
import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.api.network.reactorlogistics.IReactorLogisticsCable;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.*;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;

import javax.annotation.Nullable;
import java.util.*;

public class ReactorLogisticsNetwork extends AbstractNetwork<IReactorLogisticsCable, SubtypeReactorLogisticsCable, BlockEntity, Void> {

    private TileController controller;
    private final HashMap<BlockPos, TileControlRodModule> controlRods = new HashMap<>();
    private final HashMap<BlockPos, TileSupplyModule> supplyModules = new HashMap<>();
    private final HashMap<BlockPos, GenericTileInterface> interfaces = new HashMap<>();
    private final HashMap<BlockPos, TileMonitorModule> monitors = new HashMap<>();
    private final HashMap<BlockPos, TileThermometerModule> thermometers = new HashMap<>();

    public ReactorLogisticsNetwork() {
        this(new HashSet<IReactorLogisticsCable>());
    }

    public ReactorLogisticsNetwork(Collection<? extends IReactorLogisticsCable> varCables) {
        conductorSet.addAll(varCables);
        NetworkRegistry.register(this);
    }

    public ReactorLogisticsNetwork(Set<AbstractNetwork<IReactorLogisticsCable, SubtypeReactorLogisticsCable, BlockEntity, Void>> networks) {
        for (AbstractNetwork<IReactorLogisticsCable, SubtypeReactorLogisticsCable, BlockEntity, Void> net : networks) {
            if (net != null) {
                conductorSet.addAll(net.conductorSet);
                net.deregister();
            }
        }
        refresh();
        NetworkRegistry.register(this);
    }

    public ReactorLogisticsNetwork(Set<ReactorLogisticsNetwork> networks, boolean special) {
        for (ReactorLogisticsNetwork net : networks) {
            if (net != null) {
                conductorSet.addAll(net.conductorSet);
                net.deregister();
            }
        }
        refresh();
        NetworkRegistry.register(this);
    }

    @Override
    public void refresh() {
        controller = null;
        interfaces.clear();
        controlRods.clear();
        supplyModules.clear();
        monitors.clear();
        thermometers.clear();
        super.refresh();
    }

    @Override
    public void updateRecieverStatistics(BlockEntity reciever, Direction dir) {

        if(reciever instanceof TileController controller) {
            this.controller = controller;
        } else if (reciever instanceof GenericTileInterface reactorInterface) {
            interfaces.put(reactorInterface.getBlockPos(), reactorInterface);
        } else if (reciever instanceof  TileControlRodModule controlRod) {
            controlRods.put(controlRod.getBlockPos(), controlRod);
        } else if (reciever instanceof TileSupplyModule supplyModule) {
            supplyModules.put(supplyModule.getBlockPos(), supplyModule);
        } else if (reciever instanceof TileMonitorModule monitor) {
            monitors.put(monitor.getBlockPos(), monitor);
        } else if (reciever instanceof TileThermometerModule thermometer) {
            thermometers.put(thermometer.getBlockPos(), thermometer);
        }

    }

    @Override
    public Void emit(Void transfer, ArrayList<BlockEntity> ignored, boolean debug) {
        throw new UnsupportedOperationException("The Reactor Logistics Network does not emit, what are you doing?");
    }

    @Override
    public boolean isConductor(BlockEntity blockEntity, IReactorLogisticsCable iReactorLogisticsCable) {
        return blockEntity instanceof IReactorLogisticsCable;
    }

    @Override
    public boolean isConductorClass(BlockEntity blockEntity) {
        return blockEntity instanceof IReactorLogisticsCable;
    }

    @Override
    public boolean isAcceptor(BlockEntity blockEntity, Direction direction) {
        return isConductorClass(blockEntity) || validateConnection(blockEntity, direction.getOpposite());
    }

    @Override
    public boolean canConnect(BlockEntity blockEntity, Direction direction) {
        return isConductorClass(blockEntity) || validateConnection(blockEntity, direction.getOpposite());
    }

    @Override
    public AbstractNetwork<IReactorLogisticsCable, SubtypeReactorLogisticsCable, BlockEntity, Void> createInstance() {
        return new ReactorLogisticsNetwork();
    }

    @Override
    public AbstractNetwork<IReactorLogisticsCable, SubtypeReactorLogisticsCable, BlockEntity, Void> createInstanceConductor(Set<IReactorLogisticsCable> set) {
        return new ReactorLogisticsNetwork(set);
    }

    @Override
    public AbstractNetwork<IReactorLogisticsCable, SubtypeReactorLogisticsCable, BlockEntity, Void> createInstance(Set<AbstractNetwork<IReactorLogisticsCable, SubtypeReactorLogisticsCable, BlockEntity, Void>> set) {
        return new ReactorLogisticsNetwork(set);
    }

    @Override
    public SubtypeReactorLogisticsCable[] getConductorTypes() {
        return SubtypeReactorLogisticsCable.values();
    }

    private boolean validateConnection(BlockEntity blockEntity, Direction dir) {
        if(blockEntity instanceof ILogisticsMember member) {
            return member.isValidConnection(dir) && member.canConnect(this);
        }
        return false;
    }

    @Nullable
    public TileController getController() {
        return controller;
    }

    @Nullable
    public TileControlRodModule getControlRod(BlockPos pos) {
        return controlRods.getOrDefault(pos, null);
    }

    @Nullable
    public TileSupplyModule getSupplyModule(BlockPos pos) {
        return supplyModules.getOrDefault(pos, null);
    }

    @Nullable
    public GenericTileInterface getInterface(BlockPos pos) {
        return interfaces.getOrDefault(pos, null);
    }

    public List<GenericTileInterface> getInterfacesForType(GenericTileInterface.InterfaceType... types) {
        List<GenericTileInterface> interfaces = new ArrayList<>();

        this.interfaces.forEach((pos, tile) -> {
            for(GenericTileInterface.InterfaceType type : types) {
                if(tile.getInterfaceType() == type) {
                    interfaces.add(tile);
                }
            }
        });


        return interfaces;
    }

    public boolean isControllerActive() {
        return controller != null && controller.isActive();
    }

}
