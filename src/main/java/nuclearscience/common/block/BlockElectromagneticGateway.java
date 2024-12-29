package nuclearscience.common.block;

import electrodynamics.common.block.voxelshapes.VoxelShapeProvider;
import electrodynamics.prefab.block.GenericMachineBlock;
import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;

public class BlockElectromagneticGateway extends GenericMachineBlock {

    public BlockElectromagneticGateway() {
        super(TileElectromagneticGateway::new, VoxelShapeProvider.DEFAULT);
    }
}
