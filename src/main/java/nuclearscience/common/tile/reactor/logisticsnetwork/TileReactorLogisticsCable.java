package nuclearscience.common.tile.reactor.logisticsnetwork;

import com.google.common.collect.Sets;
import electrodynamics.prefab.network.AbstractNetwork;
import electrodynamics.prefab.tile.types.GenericConnectTile;
import electrodynamics.prefab.utilities.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.api.network.reactorlogistics.IReactorLogisticsCable;
import nuclearscience.common.block.connect.BlockReactorLogisticsCable;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.registers.NuclearScienceTiles;

import java.util.ArrayList;
import java.util.HashSet;

public class TileReactorLogisticsCable extends GenericConnectTile implements IReactorLogisticsCable {

    public ReactorLogisticsNetwork reactorNetwork;
    public SubtypeReactorLogisticsCable cable;

    public TileReactorLogisticsCable(BlockPos pos, BlockState state) {
        super(NuclearScienceTiles.TILE_REACTORLOGISTICSCABLE.get(), pos, state);
    }

    @Override
    public AbstractNetwork<?, ?, ?, ?> getAbstractNetwork() {
        return reactorNetwork;
    }

    private HashSet<IReactorLogisticsCable> getConnectedConductors() {
        HashSet<IReactorLogisticsCable> set = new HashSet<>();
        for (Direction dir : Direction.values()) {
            BlockEntity facing = level.getBlockEntity(new BlockPos(worldPosition).relative(dir));
            if (facing instanceof IReactorLogisticsCable pipe) {
                set.add(pipe);
            }
        }
        return set;
    }

    @Override
    public ReactorLogisticsNetwork getNetwork() {
        return getNetwork(true);
    }

    @Override
    public ReactorLogisticsNetwork getNetwork(boolean createIfNull) {
        if (reactorNetwork == null && createIfNull) {
            HashSet<IReactorLogisticsCable> adjacentCables = getConnectedConductors();
            HashSet<ReactorLogisticsNetwork> connectedNets = new HashSet<>();
            for (IReactorLogisticsCable wire : adjacentCables) {
                if (wire.getNetwork(false) != null && wire.getNetwork() instanceof ReactorLogisticsNetwork net) {
                    connectedNets.add(net);
                }
            }
            if (connectedNets.isEmpty()) {
                reactorNetwork = new ReactorLogisticsNetwork(Sets.newHashSet(this));
            } else {
                if (connectedNets.size() == 1) {
                    reactorNetwork = (ReactorLogisticsNetwork) connectedNets.toArray()[0];
                } else {
                    reactorNetwork = new ReactorLogisticsNetwork(connectedNets, false);
                }
                reactorNetwork.conductorSet.add(this);
            }
        }
        return reactorNetwork;
    }

    @Override
    public void setNetwork(AbstractNetwork<?, ?, ?, ?> network) {
        if (reactorNetwork != network && network instanceof ReactorLogisticsNetwork net) {
            removeFromNetwork();
            reactorNetwork = net;
        }
    }

    @Override
    public void refreshNetwork() {
        if (!level.isClientSide) {
            updateAdjacent();
            ArrayList<ReactorLogisticsNetwork> foundNetworks = new ArrayList<>();
            for (Direction dir : Direction.values()) {
                BlockEntity facing = level.getBlockEntity(new BlockPos(worldPosition).relative(dir));
                if (facing instanceof IReactorLogisticsCable pipe && pipe.getNetwork() instanceof ReactorLogisticsNetwork net) {
                    foundNetworks.add(net);
                }
            }
            if (!foundNetworks.isEmpty()) {
                foundNetworks.get(0).conductorSet.add(this);
                reactorNetwork = foundNetworks.get(0);
                if (foundNetworks.size() > 1) {
                    foundNetworks.remove(0);
                    for (ReactorLogisticsNetwork network : foundNetworks) {
                        getNetwork().merge(network);
                    }
                }
            }
            getNetwork().refresh();
        }
    }

    @Override
    public void removeFromNetwork() {
        if (reactorNetwork != null) {
            reactorNetwork.removeFromNetwork(this);
        }
    }

    private boolean[] connections = new boolean[6];
    private BlockEntity[] tileConnections = new BlockEntity[6];

    public boolean updateAdjacent() {
        boolean flag = false;
        for (Direction dir : Direction.values()) {
            BlockEntity tile = level.getBlockEntity(worldPosition.relative(dir));
            boolean is = tile instanceof IReactorLogisticsCable || (tile instanceof ILogisticsMember member && member.isValidConnection(dir.getOpposite()) && member.canConnect(getNetwork()));
            if (connections[dir.ordinal()] != is) {
                connections[dir.ordinal()] = is;
                tileConnections[dir.ordinal()] = tile;
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public BlockEntity[] getAdjacentConnections() {
        return tileConnections;
    }

    @Override
    public void refreshNetworkIfChange() {
        if (updateAdjacent()) {
            refreshNetwork();
        }
    }

    @Override
    public void destroyViolently() {
    }

    @Override
    public void setRemoved() {
        if (!level.isClientSide && reactorNetwork != null) {
            getNetwork().split(this);
        }
        super.setRemoved();
    }

    @Override
    public void onChunkUnloaded() {
        if (!level.isClientSide && reactorNetwork != null) {
            getNetwork().split(this);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        Scheduler.schedule(1, this::refreshNetwork);
    }

    public SubtypeReactorLogisticsCable getPipeType() {
        if (cable == null) {
            cable = ((BlockReactorLogisticsCable) getBlockState().getBlock()).cable;
        }
        return cable;
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("ord", getPipeType().ordinal());
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        cable = SubtypeReactorLogisticsCable.values()[compound.getInt("ord")];
    }
}
