package nuclearscience.common.tile;

import java.util.UUID;

import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic.LoadProfile;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerQuantumCapacitor;
import nuclearscience.common.world.QuantumCapacitorData;
import nuclearscience.registers.NuclearScienceTiles;
import org.jetbrains.annotations.Nullable;

public class TileQuantumCapacitor extends GenericTile implements IEnergyStorage {
	public static final double DEFAULT_MAX_JOULES = Double.MAX_VALUE;
	public static final double DEFAULT_VOLTAGE = 1920.0;
	public Property<Double> outputJoules = property(new Property<>(PropertyTypes.DOUBLE, "outputJoules", 359.0));
	public Property<Integer> frequency = property(new Property<>(PropertyTypes.INTEGER, "frequency", 0));
	public Property<Double> storedJoules = property(new Property<>(PropertyTypes.DOUBLE, "capjoules", 0.0));// Work around for now until we make a capability for the overworld
	public Property<UUID> uuid = property(new Property<>(PropertyTypes.UUID, "uuid", UUID.randomUUID()));
	private CachedTileOutput outputCache;
	private CachedTileOutput outputCache2;

	private final CapabilityUtils.FEInputDispatcher inputDispatcher = new CapabilityUtils.FEInputDispatcher(this);
	private final CapabilityUtils.FEOutputDispatcher outputDispatcher = new CapabilityUtils.FEOutputDispatcher(this);

	public TileQuantumCapacitor(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_QUANTUMCAPACITOR.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, true, true).voltage(16 * ElectrodynamicsCapabilities.DEFAULT_VOLTAGE).setOutputDirections(BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.BOTTOM)
				//
				.setInputDirections(BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.BACK, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.RIGHT).receivePower(this::receivePower).setJoules(this::setJoulesStored).getJoules(this::getJoulesStored).getConnectedLoad(this::getConnectedLoad));
		addComponent(new ComponentInventory(this));
		addComponent(new ComponentContainerProvider("container.quantumcapacitor", this).createMenu((id, player) -> new ContainerQuantumCapacitor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

	}

	public double getOutputJoules() {
		return outputJoules.get();
	}

	public void tickServer(ComponentTickable tickable) {
		if (outputCache == null) {
			outputCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.UP));
		}
		if (outputCache2 == null) {
			outputCache2 = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.DOWN));
		}
		if (tickable.getTicks() % 40 == 0) {
			outputCache.update(new BlockPos(worldPosition).relative(Direction.UP));
			outputCache2.update(new BlockPos(worldPosition).relative(Direction.DOWN));
		}
		double joules = getJoulesStored();
		if (joules > 0 && outputCache.valid()) {
			double sent = ElectricityUtils.receivePower(outputCache.getSafe(), Direction.DOWN, TransferPack.joulesVoltage(Math.min(joules, outputJoules.get()), DEFAULT_VOLTAGE), false).getJoules();
			QuantumCapacitorData.get(level).setJoules(uuid.get(), frequency.get(), getJoulesStored() - sent);
		}
		joules = getJoulesStored();
		if (joules > 0 && outputCache2.valid()) {
			double sent = ElectricityUtils.receivePower(outputCache2.getSafe(), Direction.UP, TransferPack.joulesVoltage(Math.min(joules, outputJoules.get()), DEFAULT_VOLTAGE), false).getJoules();
			QuantumCapacitorData.get(level).setJoules(uuid.get(), frequency.get(), getJoulesStored() - sent);
		}
		storedJoules.set(getJoulesStored());
	}

	@Nullable
	public IEnergyStorage getFECapability(@Nullable Direction side) {
		if (side == null) {
			return null;
		} else if(side == Direction.UP || side == Direction.DOWN ){
			return inputDispatcher;
		} else {
			return outputDispatcher;
		}

	}

	public TransferPack receivePower(TransferPack transfer, boolean debug) {
		double joules = getJoulesStored();
		double received = Math.min(Math.min(DEFAULT_MAX_JOULES, transfer.getJoules()), DEFAULT_MAX_JOULES - joules);
		if (!debug) {
			if (transfer.getVoltage() == DEFAULT_VOLTAGE) {
				joules += received;

			}
			QuantumCapacitorData.get(level).setJoules(uuid.get(), frequency.get(), joules);
			if (transfer.getVoltage() > DEFAULT_VOLTAGE) {
				level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
				level.explode(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), (float) Math.log10(10 + transfer.getVoltage() / DEFAULT_VOLTAGE), ExplosionInteraction.BLOCK);
				return TransferPack.EMPTY;
			}
		}
		return TransferPack.joulesVoltage(received, transfer.getVoltage());
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int calVoltage = 120;
		TransferPack pack = receivePower(TransferPack.joulesVoltage(maxReceive, calVoltage), simulate);
		return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int calVoltage = 120;
		TransferPack pack = this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).extractPower(TransferPack.joulesVoltage(maxExtract, calVoltage), simulate);
		return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
	}

	@Override
	public int getEnergyStored() {
		return (int) Math.min(Integer.MAX_VALUE, getJoulesStored());
	}

	@Override
	public int getMaxEnergyStored() {
		return (int) Math.min(Integer.MAX_VALUE, DEFAULT_MAX_JOULES);
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return true;
	}

	public void setJoulesStored(double joules) {
		QuantumCapacitorData data = QuantumCapacitorData.get(level);
		if (data != null) {
			data.setJoules(uuid.get(), frequency.get(), joules);
		}
	}

	public double getJoulesStored() {
		QuantumCapacitorData data = QuantumCapacitorData.get(level);
		return data == null ? 0 : data.getJoules(uuid.get(), frequency.get());
	}

	public double getMaxJoulesStored() {
		return DEFAULT_MAX_JOULES;
	}

	public TransferPack getConnectedLoad(LoadProfile loadProfile, Direction dir) {
		return TransferPack.joulesVoltage(getMaxJoulesStored() - getJoulesStored(), this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).getVoltage());
	}

}
