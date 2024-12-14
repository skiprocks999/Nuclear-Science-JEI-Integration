package nuclearscience.common.tile.reactor.moltensalt;

import electrodynamics.prefab.properties.PropertyTypes;
import net.minecraft.core.HolderLookup;

import electrodynamics.prefab.properties.Property;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.registers.NuclearScienceTiles;

public class TileMoltenSaltPipe extends GenericTileMoltenSaltPipe {
	public Property<Double> transmit = property(new Property<>(PropertyTypes.DOUBLE, "transmit", 0.0));

	public TileMoltenSaltPipe(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_MOLTENSALTPIPE.get(), pos, state);
	}

	public SubtypeMoltenSaltPipe pipe = null;

	@Override
	public SubtypeMoltenSaltPipe getPipeType() {
		if (pipe == null) {
			pipe = ((BlockMoltenSaltPipe) getBlockState().getBlock()).pipe;
		}
		return pipe;
	}

	@Override
	protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		super.saveAdditional(compound, registries);
		compound.putInt("ord", getPipeType().ordinal());
	}

	@Override
	protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		super.loadAdditional(compound, registries);
		pipe = SubtypeMoltenSaltPipe.values()[compound.getInt("ord")];
	}

}
