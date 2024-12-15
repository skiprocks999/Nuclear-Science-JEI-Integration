package nuclearscience.common.tile.reactor.moltensalt;

import java.util.ArrayList;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.api.radiation.SimpleRadiationSource;
import nuclearscience.common.inventory.container.ContainerMSReactorCore;
import nuclearscience.common.network.MoltenSaltNetwork;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceTiles;

public class TileMSReactorCore extends GenericTile {

	public static final int MELTDOWN_TEMPERATURE = 1000;
	public static final double FUEL_CAPACITY = 1000;
	public static final double FUEL_USAGE_RATE = 0.01;

	public static final double WASTE_CAP = 1000;
	public static final double WASTE_PER_MB = 0.01;

	public Property<Double> temperature = property(new Property<>(PropertyTypes.DOUBLE, "temperature", TileFissionReactorCore.AIR_TEMPERATURE));
	public Property<Double> currentFuel = property(new Property<>(PropertyTypes.DOUBLE, "currentfuel", 0.0));
	public Property<Double> currentWaste = property(new Property<>(PropertyTypes.DOUBLE, "currentwaste", 0.0));
	public Property<Boolean> wasteIsFull = property(new Property<>(PropertyTypes.BOOLEAN, "wasteisfull", false));
	public Property<Boolean> hasPlug = property(new Property<>(PropertyTypes.BOOLEAN, "hasplug", false));

	private CachedTileOutput outputCache;
	private CachedTileOutput plugCache;
	private CachedTileOutput controlRodCache;


	public CachedTileOutput clientPlugCache;

	public TileMSReactorCore(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_MSRREACTORCORE.get(), pos, state);

		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentContainerProvider("container.msrreactorcore", this).createMenu((id, player) -> new ContainerMSReactorCore(id, player, null, getCoordsArray())));
	}

	public void tickServer(ComponentTickable tick) {

		double change = (temperature.get() - TileFissionReactorCore.AIR_TEMPERATURE) / 3000.0 + (temperature.get() - TileFissionReactorCore.AIR_TEMPERATURE) / 5000.0;
		if (change != 0) {
			temperature.set(temperature.get() - (change < 0.001 && change > 0 ? 0.001 : change > -0.001 && change < 0 ? -0.001 : change));
		}

		if (outputCache == null) {
			outputCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.UP));
		}
		if (plugCache == null) {
			plugCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.DOWN));
		}
		if(controlRodCache == null) {
			controlRodCache = new CachedTileOutput(getLevel(), getBlockPos().relative(getFacing()));
		}

		if (tick.getTicks() % 40 == 0) {
			if(!outputCache.valid()) {
				outputCache.update(new BlockPos(worldPosition).relative(Direction.UP));
			}
			if(!plugCache.valid()) {
				plugCache.update(new BlockPos(worldPosition).relative(Direction.DOWN));
			}
		}

		if(!controlRodCache.valid() && tick.getTicks() % 10 == 0) {
			controlRodCache.update(getBlockPos().relative(getFacing().getOpposite()));
		}

		if (!plugCache.valid() || !(plugCache.getSafe() instanceof TileFreezePlug freeze && freeze.isFrozen())) {
			return;
		}

		if (currentFuel.get() < FUEL_USAGE_RATE) {
			return;
		}

		int insertion = 0;

		if(controlRodCache.valid() && level.getBlockState(controlRodCache.getPos()).is(NuclearScienceBlocks.BLOCK_MSCONTROLROD.get())) {

			TileControlRod.TileMSControlRod rod = controlRodCache.getSafe();

			if(rod.getFacing().getOpposite() == getFacing()) {
				insertion = rod.insertion.get();
			}

		}

		double insertDecimal = 1.0 - insertion / (double) TileControlRod.MAX_EXTENSION;

		double fuelUse = Math.min(currentFuel.get(), FUEL_USAGE_RATE * insertDecimal * Math.pow(2, Math.pow(temperature.get() / (MELTDOWN_TEMPERATURE - 100), 4)));

		double wasteProduced = Math.min(currentFuel.get(), WASTE_PER_MB * insertDecimal * Math.pow(2, Math.pow(temperature.get() / (MELTDOWN_TEMPERATURE - 100), 4)));

		if (currentWaste.get() > WASTE_CAP - wasteProduced) {
			wasteIsFull.set(true);
			return;
		}

		wasteIsFull.set(false);

		currentWaste.set(currentWaste.get() + wasteProduced);

		currentFuel.set(currentFuel.get() - fuelUse);
		temperature.set((temperature.get() + (MELTDOWN_TEMPERATURE * insertDecimal * (1.2 + level.random.nextDouble() / 5.0) - temperature.get()) / 600.0));
		if (outputCache.valid() && outputCache.getSafe() instanceof IMoltenSaltPipe) {

			MoltenSaltNetwork net = (MoltenSaltNetwork) outputCache.<IMoltenSaltPipe>getSafe().getNetwork();
			net.emit(temperature.get() * ((TileFreezePlug) plugCache.getSafe()).getSaltBonus(), new ArrayList<>(), false);
		}

		double totstrength = temperature.get() * Math.pow(3, Math.pow(temperature.get() / MELTDOWN_TEMPERATURE, 9));
		int range = (int) (Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2);
		RadiationSystem.addRadiationSource(getLevel(), new SimpleRadiationSource(totstrength, 1, range, true, 0, getBlockPos(), false));

	}

	public void tickClient(ComponentTickable tickable) {
		if (clientPlugCache == null) {
			clientPlugCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.DOWN));
		}
		if(tickable.getTicks() % 40 == 0 && !clientPlugCache.valid()) {
			clientPlugCache.update(new BlockPos(worldPosition).relative(Direction.DOWN));
		}

	}

}