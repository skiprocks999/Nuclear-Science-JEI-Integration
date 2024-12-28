package nuclearscience.common.tile.accelerator;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileElectromagneticGateway extends GenericTile {

    public TileElectromagneticGateway(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
        super(tileEntityTypeIn, worldPos, blockState);
    }

    public boolean mayPassThrough(double speed) {
        return false;
    }

}
