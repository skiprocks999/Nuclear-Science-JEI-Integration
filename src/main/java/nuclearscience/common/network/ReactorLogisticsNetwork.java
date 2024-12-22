package nuclearscience.common.network;

import electrodynamics.common.network.NetworkRegistry;
import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.api.network.reactorlogistics.IReactorLogisticsCable;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ReactorLogisticsNetwork extends AbstractNetwork<IReactorLogisticsCable, SubtypeReactorLogisticsCable, BlockEntity, Void> {

    public TileController controller;
    public TileControlRodModule controlRod;
    public final HashSet<TileSupplyModule> supplyModules = new HashSet<>();
    public final HashSet<TileInterface> interfaces = new HashSet<>();

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
        controlRod = null;
        supplyModules.clear();
        super.refresh();
    }

    @Override
    public void updateRecieverStatistics(BlockEntity reciever, Direction dir) {

        if(reciever instanceof TileController controller) {
            this.controller = controller;
        } else if (reciever instanceof TileInterface reactorInterface) {
            interfaces.add(reactorInterface);
        } else if (reciever instanceof  TileControlRodModule controlRod) {
            this.controlRod = controlRod;
        } else if (reciever instanceof TileSupplyModule supplyModule) {
            supplyModules.add(supplyModule);
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

}
