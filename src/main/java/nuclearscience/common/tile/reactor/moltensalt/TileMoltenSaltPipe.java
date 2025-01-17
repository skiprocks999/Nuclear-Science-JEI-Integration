package nuclearscience.common.tile.reactor.moltensalt;

import electrodynamics.prefab.tile.types.GenericRefreshingConnectTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.network.MoltenSaltNetwork;
import nuclearscience.registers.NuclearScienceTiles;

import java.util.Set;

public class TileMoltenSaltPipe extends GenericRefreshingConnectTile<SubtypeMoltenSaltPipe, TileMoltenSaltPipe, MoltenSaltNetwork> {

    public SubtypeMoltenSaltPipe pipe = null;

    public TileMoltenSaltPipe(BlockPos pos, BlockState state) {
        super(NuclearScienceTiles.TILE_MOLTENSALTPIPE.get(), pos, state);
    }

    @Override
    public SubtypeMoltenSaltPipe getCableType() {
        if (pipe == null) {
            pipe = ((BlockMoltenSaltPipe) getBlockState().getBlock()).pipe;
        }
        return pipe;
    }

    @Override
    public double getMaxTransfer() {
        return 0;
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("ord", getCableType().ordinal());
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        pipe = SubtypeMoltenSaltPipe.values()[compound.getInt("ord")];
    }

    @Override
    public MoltenSaltNetwork createInstanceConductor(Set<TileMoltenSaltPipe> set) {
        return new MoltenSaltNetwork(set);
    }

    @Override
    public MoltenSaltNetwork createInstance(Set<MoltenSaltNetwork> set) {
        return new MoltenSaltNetwork(set);
    }

    @Override
    public void destroyViolently() {

    }
}
