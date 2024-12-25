package nuclearscience.common.tile.reactor.logisticsnetwork;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.common.network.ReactorLogisticsNetwork;

public abstract class TileAlarmModule extends GenericTile implements ILogisticsMember {

    public TileAlarmModule(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
        super(tileEntityTypeIn, worldPos, blockState);
    }

    public CachedTileOutput networkCable;

    @Override
    public boolean isValidConnection(Direction dir) {
        return false;
    }

    @Override
    public boolean canConnect(ReactorLogisticsNetwork network) {
        return true;
    }
}
