package nuclearscience.common.tile;

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
import electrodynamics.prefab.utilities.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import nuclearscience.common.inventory.container.ContainerAtomicAssembler;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.settings.Constants;
import nuclearscience.prefab.utils.RadiationUtils;
import nuclearscience.registers.NuclearScienceTiles;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class TileAtomicAssembler extends GenericTile {

	public final Property<Integer> progress = property(new Property<>(PropertyTypes.INTEGER, "progress", 0));

	public TileAtomicAssembler(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_ATOMICASSEMBLER.get(), pos, state);

		addComponent(new ComponentTickable(this).tickCommon(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(Constants.ATOMICASSEMBLER_USAGE_PER_TICK * 20).voltage(Constants.ATOMICASSEMBLER_VOLTAGE).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
		// The slot == 6 has to be there to allow items into the input slot.
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().inputs(7).outputs(1)).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0, 1, 2, 3, 4, 5).setDirectionsBySlot(6, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.BACK)
				//
				.setDirectionsBySlot(7, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.FRONT).valid((slot, stack, i) -> slot == 6 || slot < 6 && stack.is(NuclearScienceItems.ITEM_CELLDARKMATTER.get())));
		addComponent(new ComponentContainerProvider("container.atomicassembler", this).createMenu((id, player) -> new ContainerAtomicAssembler(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tickable) {

		ComponentInventory inv = getComponent(IComponentType.Inventory);

		ItemStack input = inv.getItem(6);

		if (input.isEmpty()) {
			progress.set(0);
			return;
		}

		RadiationUtils.handleRadioactiveItems(this, inv, Constants.ATOMIC_ASSEMBLER_RADIATION_RADIUS, true, 0, false);

		ItemStack output = inv.getItem(7);

		boolean validItem = validateDupeItem(input) && (output.isEmpty() || ItemStack.isSameItem(input, output) && output.getCount() + 1 <= output.getMaxStackSize());

		if (!validItem) {
			progress.set(0);
			return;
		}

		for (int index = 0; index < 6; index++) {

			ItemStack dmCell = inv.getItem(index);

			if (dmCell.isEmpty() || dmCell.getItem() != NuclearScienceItems.ITEM_CELLDARKMATTER.get()) {
				progress.set(0);
				return;
			}

			if (dmCell.getDamageValue() >= dmCell.getMaxDamage()) {
				progress.set(0);
				inv.setItem(index, ItemStack.EMPTY);
				return;
			}

		}

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		if (electro.getJoulesStored() < Constants.ATOMICASSEMBLER_USAGE_PER_TICK) {
			return;
		}

		progress.set(progress.get() + 1);

		electro.joules(electro.getJoulesStored() - Constants.ATOMICASSEMBLER_USAGE_PER_TICK);

		if (progress.get() < Constants.ATOMICASSEMBLER_REQUIRED_TICKS) {
			return;
		}

		progress.set(0);

		for (int index = 0; index < 6; index++) {

			ItemStack dmCell = inv.getItem(index);

			dmCell.setDamageValue(dmCell.getDamageValue() + 1);

			if (dmCell.getDamageValue() >= dmCell.getMaxDamage()) {
				inv.setItem(index, ItemStack.EMPTY);
			}
		}

		if (output.isEmpty()) {

			inv.setItem(7, new ItemStack(input.getItem()));

		} else {

			output.setCount(output.getCount() + 1);

		}
	}

	private boolean validateDupeItem(ItemStack stack) {

		if (stack.has(DataComponents.CONTAINER)) { // this should filter out shulker boxes with items
			return false;
		}

		if (ItemUtils.testItems(stack.getItem(), NuclearScienceItems.ITEM_CELLDARKMATTER.get()) && stack.getCapability(Capabilities.ItemHandler.ITEM) != null) {
			return false;
		}

		if (stack.getItem() instanceof BlockItem blockItem) {

			if (blockItem.getBlock() == NuclearScienceBlocks.BLOCK_QUANTUMTUNNEL.get()) {
				return false;
			}

		}

		if (AtomicAssemblerBlacklistRegister.INSTANCE.isBlacklisted(stack.getItem())) {
			return false;
		}

		return true;

	}

}
