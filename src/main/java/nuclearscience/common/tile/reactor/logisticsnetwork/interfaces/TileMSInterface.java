package nuclearscience.common.tile.reactor.logisticsnetwork.interfaces;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileControlRodModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;
import nuclearscience.common.tile.reactor.moltensalt.IMSControlRod;
import nuclearscience.registers.NuclearScienceTiles;

public class TileMSInterface extends GenericTileInterface implements IMSControlRod {

    public final Property<Integer> insertion = property(new Property<>(PropertyTypes.INTEGER, "insertion", 0));

    public TileMSInterface(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_MSINTERFACE.get(), worldPos, blockState);
    }

    @Override
    public void tickServer(ComponentTickable tickable) {
        super.tickServer(tickable);

        if (!networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
            insertion.set(0);
            return;
        }

        TileReactorLogisticsCable cable = networkCable.getSafe();

        if (cable.isRemoved()) {
            insertion.set(0);
            return;
        }

        ReactorLogisticsNetwork network = cable.getNetwork();

        if (!network.isControllerActive()) {
            insertion.set(0);
            return;
        }

        TileControlRodModule controlRod = network.getControlRod(controlRodLocation.get());

        if (controlRod == null) {
            insertion.set(0);
        } else {
            insertion.set(controlRod.insertion.get());
        }

    }

    @Override
    public int getInsertion() {
        return insertion.get();
    }

    @Override
    public Direction facingDir() {
        return getReactorDirection();
    }

    @Override
    public Direction getReactorDirection() {
        return getFacing().getOpposite();
    }

    @Override
    public Direction getCableLocation() {
        return Direction.DOWN;
    }

    @Override
    public InterfaceType getInterfaceType() {
        return InterfaceType.MS;
    }
}
