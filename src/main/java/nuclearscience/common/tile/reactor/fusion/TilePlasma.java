package nuclearscience.common.tile.reactor.fusion;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceTiles;
import nuclearscience.registers.NuclearScienceBlocks;

public class TilePlasma extends GenericTile {

	public final Property<Integer> ticksExisted = property(new Property<>(PropertyTypes.INTEGER, "existed", 0).setNoUpdateClient());
	public final Property<Integer> spread = property(new Property<>(PropertyTypes.INTEGER, "spread", 6).setNoUpdateClient());

	private CachedTileOutput output;

	public TilePlasma(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_PLASMA.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	}

	public void tickServer(ComponentTickable tickable) {

		ticksExisted.set(ticksExisted.get() + 1);

		if (ticksExisted.get() > 80) {
			level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
			return;
		}

		if (ticksExisted.get() == 1 && spread.get() > 0) {
			for (Direction dir : Direction.values()) {
				BlockPos offset = worldPosition.relative(dir);
				BlockState state = level.getBlockState(offset);
				boolean didntExist = false;
				if (state.getBlock() != getBlockState().getBlock()) {
					didntExist = true;
					if (state.getDestroySpeed(level, offset) != -1 && !(state.getBlock() instanceof IElectromagnet) && state.getBlock() != NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore)) {
						level.setBlockAndUpdate(offset, NuclearScienceBlocks.BLOCK_PLASMA.get().defaultBlockState());
					}
				}
				BlockEntity tile = level.getBlockEntity(offset);
				if (tile instanceof TilePlasma plasma) {
					if (plasma.ticksExisted.get() > 1 && plasma.spread.get() < spread.get()) {
						plasma.ticksExisted.set(ticksExisted.get() - 1);
					}
					if (didntExist) {
						plasma.spread.set(spread.get() - 1);
					}
				}
			}
		}
		if (ticksExisted.get() > 1 && level.getBlockState(getBlockPos().relative(Direction.UP)).getBlock() instanceof IElectromagnet && level.getBlockState(getBlockPos().relative(Direction.UP, 2)).getBlock() == Blocks.WATER) {
			if (output == null) {
				output = new CachedTileOutput(level, getBlockPos().relative(Direction.UP, 3));
			} else if (output.getSafe() instanceof ISteamReceiver) {
				ISteamReceiver turbine = output.getSafe();
				turbine.receiveSteam(Integer.MAX_VALUE, (int) (Constants.FUSIONREACTOR_MAXENERGYTARGET / (113.0 * 20.0)));
			}
		}
	}

}