package nuclearscience.common.tile;

import electrodynamics.common.inventory.container.tile.ContainerO2OProcessor;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.registers.NuclearScienceTiles;

public class TileFuelReprocessor extends GenericTile implements ITickableSound {

	private boolean isSoundPlaying = false;

	public TileFuelReprocessor(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_FUELREPROCESSOR.get(), pos, state);

		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4).setInputDirections(BlockEntityUtils.MachineDirection.BACK));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 1, 1, 1).upgrades(3)).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0).setDirectionsBySlot(1, BlockEntityUtils.MachineDirection.BOTTOM)
				//
				.setSlotsByDirection(BlockEntityUtils.MachineDirection.BOTTOM, 2).validUpgrades(ContainerO2OProcessor.VALID_UPGRADES).valid(machineValidator()));
		addProcessor(new ComponentProcessor(this).canProcess(this::shouldProcessRecipe).process(component -> component.processItem2ItemRecipe(component)));
		addComponent(new ComponentContainerProvider("container.fuelreprocessor", this).createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private boolean shouldProcessRecipe(ComponentProcessor component) {
		boolean canProcess = component.canProcessItem2ItemRecipe(component, NuclearScienceRecipeInit.FUEL_REPROCESSOR_TYPE.get());
		if (BlockEntityUtils.isLit(this) ^ canProcess) {
			BlockEntityUtils.updateLit(this, canProcess);
		}
		return canProcess;
	}

	public void tickClient(ComponentTickable tickable) {
		if (!isSoundPlaying && shouldPlaySound()) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_HUM.get(), this, true);
		}
		if(isProcessorActive() && getLevel().getRandom().nextFloat() < 0.3) {
			this.level.addParticle(ParticleTypes.SMOKE, this.worldPosition.getX() + level.random.nextFloat(), this.worldPosition.getY() + level.random.nextFloat(), this.worldPosition.getZ() + level.random.nextFloat(), 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return isProcessorActive();
	}

	@Override
	public int getComparatorSignal() {
		return isProcessorActive() ? 15 : 0;
	}
}
