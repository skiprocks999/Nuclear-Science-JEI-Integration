package nuclearscience.common.tile.reactor.moltensalt;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceTiles;
import nuclearscience.registers.NuclearScienceItems;

public class TileFreezePlug extends GenericTile {

	public final Property<Boolean> isFrozen = property(new Property<>(PropertyTypes.BOOLEAN, "isfrozen", false));
	public final Property<Double> saltBonus = property(new Property<>(PropertyTypes.DOUBLE, "saltbonus", 1.0));

	public TileFreezePlug(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_FREEZEPLUG.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE).extractPower((x, y) -> TransferPack.EMPTY).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).maxJoules(Constants.FREEZEPLUG_USAGE_PER_TICK * 20));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().inputs(1)).valid((slot, stack, i) -> stack.getItem() == NuclearScienceItems.ITEM_FLINAK.get()));
		addComponent(new ComponentContainerProvider("container.freezeplug", this).createMenu((id, player) -> new ContainerFreezePlug(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		ComponentInventory inv = getComponent(IComponentType.Inventory);

		ItemStack stack = inv.getItem(0);

		if (stack.isEmpty()) {
			isFrozen.set(false);
			saltBonus.set(0.0);
			return;
		}

		if (electro.getJoulesStored() < Constants.FREEZEPLUG_USAGE_PER_TICK) {
			isFrozen.set(false);
			saltBonus.set(0.0);
			return;
		}

		electro.joules(electro.getJoulesStored() - Constants.FREEZEPLUG_USAGE_PER_TICK);

		isFrozen.set(true);

		double bonus = 1.0 + ((stack.getCount() - 1) / 63.0);

		saltBonus.set(bonus);

	}

	public boolean isFrozen() {
		return isFrozen.get();
	}

	public double getSaltBonus() {
		return saltBonus.get();
	}

}
