package nuclearscience.common.tile.reactor.logisticsnetwork;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.common.network.ReactorLogisticsNetwork;

public class TileMonitorModule extends GenericTile implements ILogisticsMember {
    public TileMonitorModule(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
        super(tileEntityTypeIn, worldPos, blockState);
    }

    @Override
    public boolean isValidConnection(Direction dir) {
        return false;
    }

    @Override
    public boolean canConnect(ReactorLogisticsNetwork network) {
        return true;
    }
}
