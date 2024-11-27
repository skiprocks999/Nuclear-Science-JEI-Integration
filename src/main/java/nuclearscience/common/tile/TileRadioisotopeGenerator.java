package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.Location;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.radiation.IRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceTiles;

public class TileRadioisotopeGenerator extends GenericTile {

	public static final double RAD_RADIUS = 10;

	protected CachedTileOutput output1;
	protected CachedTileOutput output2;

	public TileRadioisotopeGenerator(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_RADIOISOTOPEGENERATOR.get(), pos, state);

		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, true, false).voltage(Constants.RADIOISOTOPEGENERATOR_VOLTAGE).extractPower((x, y) -> TransferPack.EMPTY).setOutputDirections(BlockEntityUtils.MachineDirection.BOTTOM, BlockEntityUtils.MachineDirection.TOP));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().inputs(1)).setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.values()).valid((slot, stack, i) -> !RadiationRegister.get(stack.getItem()).isNull()));
		addComponent(new ComponentContainerProvider("container.radioisotopegenerator", this).createMenu((id, player) -> new ContainerRadioisotopeGenerator(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
		if (output1 == null) {
			output1 = new CachedTileOutput(level, worldPosition.relative(Direction.UP));
		}
		if (output2 == null) {
			output2 = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
		}
		if (tickable.getTicks() % 40 == 0) {
			output1.update(worldPosition.relative(Direction.UP));
			output2.update(worldPosition.relative(Direction.DOWN));
		}
		ItemStack in = this.<ComponentInventory>getComponent(IComponentType.Inventory).getItem(0);
		IRadioactiveObject rad = RadiationRegister.get(in.getItem());
		double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();

		RadiationSystem.emitRadiationFromLocation(getLevel(), new Location(getBlockPos()), ((double) in.getCount() / (double) in.getMaxStackSize()) * RAD_RADIUS, rad.getRadiationStrength());

		if (currentOutput > 0) {
			TransferPack transfer = TransferPack.ampsVoltage(currentOutput / (Constants.RADIOISOTOPEGENERATOR_VOLTAGE * 2.0), Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
			if (output1.valid()) {
				ElectricityUtils.receivePower(output1.getSafe(), Direction.DOWN, transfer, false);
			}
			if (output2.valid()) {
				ElectricityUtils.receivePower(output2.getSafe(), Direction.UP, transfer, false);
			}
		}
	}

	@Override
	public int getComparatorSignal() {

		ItemStack stack = this.<ComponentInventory>getComponent(IComponentType.Inventory).getItem(0);

		if (stack.isEmpty()) {
			return 0;
		}

		return (int) (((double) stack.getCount() / (double) stack.getMaxStackSize()) * 15.0);
	}

}
