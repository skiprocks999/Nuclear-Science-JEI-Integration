package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.tile.TileChunkloader;

public class BlockChunkloader extends GenericMachineBlock {
    public BlockChunkloader() {
        super(TileChunkloader::new);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }
}
