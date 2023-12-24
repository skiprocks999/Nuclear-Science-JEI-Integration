package nuclearscience.common.tile;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceSounds;

public class TileTurbine extends GenericTile implements ITickableSound, ISteamReceiver {

	public static final double MAX_STEAM = 3000000;
	public Property<Integer> spinSpeed = property(new Property<>(PropertyType.Integer, "spinSpeed", 0));
	public Property<Boolean> hasCore = property(new Property<>(PropertyType.Boolean, "hasCore", false));
	public Property<Boolean> isCore = property(new Property<>(PropertyType.Boolean, "isCore", false));
	public Property<BlockPos> coreLocation = property(new Property<>(PropertyType.BlockPos, "coreLocation", new BlockPos(0, -1000, 0)));
	public Property<Integer> currentVoltage = property(new Property<>(PropertyType.Integer, "turbinecurvoltage", 0));
	public Property<Double> steam = property(new Property<>(PropertyType.Double, "steam", 0.0));
	public Property<Integer> wait = property(new Property<>(PropertyType.Integer, "wait", 30));
	protected CachedTileOutput output;

	private boolean isSoundPlaying = false;

	private boolean destroyed = false;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return isCore.get() ? super.getRenderBoundingBox().inflate(1, 0, 1) : super.getRenderBoundingBox();
	}

	public TileTurbine() {
		super(NuclearScienceBlockTypes.TILE_TURBINE.get());
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, true, false).setOutputDirections(Direction.UP).setCapabilityTest(() -> (!hasCore.get() || isCore.get())));
	}

	public void constructStructure() {
		int radius = 1;
		for (int i = -radius; i <= radius; i++) {
			for (int j = -radius; j <= radius; j++) {
				if (i != 0 || j != 0) {
					TileEntity tile = level.getBlockEntity(new BlockPos(worldPosition.getX() + i, worldPosition.getY(), worldPosition.getZ() + j));
					if (tile instanceof TileTurbine ? ((TileTurbine) tile).hasCore.get() : true) {
						return;
					}
				}
			}
		}
		isCore.set(true);
		for (int i = -radius; i <= radius; i++) {
			for (int j = -radius; j <= radius; j++) {
				BlockPos offset = new BlockPos(worldPosition.getX() + i, worldPosition.getY(), worldPosition.getZ() + j);
				((TileTurbine) level.getBlockEntity(offset)).addToStructure(this);
				BlockState state = level.getBlockState(offset);
				level.setBlockAndUpdate(offset, state.setValue(BlockTurbine.RENDER, false));
			}
		}
	}

	public void deconstructStructure() {
		if (isCore.get()) {
			int radius = 1;
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					if (i != 0 || j != 0) {
						BlockPos offset = new BlockPos(worldPosition.getX() + i, worldPosition.getY(), worldPosition.getZ() + j);
						TileEntity tile = level.getBlockEntity(offset);
						if (tile instanceof TileTurbine) {
							TileTurbine turbine = (TileTurbine) tile;
							turbine.hasCore.set(false);
							turbine.coreLocation.set(new BlockPos(0, 0, 0));
							BlockState state = level.getBlockState(offset);
							if (state.hasProperty(BlockTurbine.RENDER)) {
								level.setBlockAndUpdate(offset, state.setValue(BlockTurbine.RENDER, true));
							}
						}
					}
				}
			}
			isCore.set(false);
			hasCore.set(false);
			coreLocation.set(new BlockPos(0, -1000, 0));
			BlockState state = getBlockState();
			if (state.hasProperty(BlockTurbine.RENDER) && !destroyed) {
				level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockTurbine.RENDER, true));
			}
		} else if (hasCore.get()) {
			TileTurbine core = (TileTurbine) level.getBlockEntity(coreLocation.get());
			if (core != null) {
				core.deconstructStructure();
			}
		}

	}

	protected void addToStructure(TileTurbine core) {
		coreLocation.set(core.worldPosition);
		hasCore.set(true);
	}

	public void tickServer(ComponentTickable tickable) {
		this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).voltage(currentVoltage.get());
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(Direction.UP));
		}
		spinSpeed.set(currentVoltage.get() / 120);
		output.update(worldPosition.relative(Direction.UP));
		if (hasCore.get() && !isCore.get()) {
			currentVoltage.set(0);
			return;
		}
		if (steam.get() > 0 && currentVoltage.get() > 0) {
			wait.set(30);
			if (output.valid()) {
				TransferPack transfer = TransferPack.joulesVoltage(steam.get() * (hasCore.get() ? 1.111 : 1), currentVoltage.get());
				ElectricityUtils.receivePower(output.getSafe(), Direction.DOWN, transfer, false);
				steam.set(Math.max(steam.get() - Math.max(75, steam.get()), 0));
			}
		} else {
			if (wait.get() <= 0) {
				currentVoltage.set(0);
				wait.set(30);
			}
			wait.set(wait.get() - 1);
		}

	}

	public void tickClient(ComponentTickable tickable) {
		if (!isSoundPlaying && shouldPlaySound()) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(NuclearScienceSounds.SOUND_TURBINE.get(), this, true);
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return spinSpeed.get() > 0;
	}

	@Override
	public ActionResultType use(PlayerEntity arg0, Hand arg1, BlockRayTraceResult arg2) {
		return ActionResultType.PASS;
	}

	@Override
	public double receiveSteam(double temperature, double amount) {
		double room = MAX_STEAM * (isCore.get() ? 9 : 1) - steam.get();
		double accepted = room < amount ? room : amount;
		this.steam.set(steam.get() + accepted);
		if (temperature < 4300) {
			currentVoltage.set(120);
		} else if (temperature < 6000) {
			currentVoltage.set(240);
		} else {
			currentVoltage.set(480);
		}
		if (!isCore.get() && hasCore.get()) {
			TileEntity core = level.getBlockEntity(coreLocation.get());
			if (core instanceof TileTurbine && ((TileTurbine) core).isCore.get()) {
				accepted = ((TileTurbine) core).receiveSteam(temperature, amount);
				this.steam.set(0);
			}
		}
		return accepted;
	}

	@Override
	public boolean isStillValid() {
		return isRemoved();
	}

	@Override
	public void onBlockDestroyed() {
		super.onBlockDestroyed();
		if (level.isClientSide) {
			return;
		}
		destroyed = true;
		deconstructStructure();

	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(!getBlockState().getValue(BlockTurbine.RENDER) && !isCore.get()) {
			return LazyOptional.empty();
		}
		return super.getCapability(cap, side);
	}
}