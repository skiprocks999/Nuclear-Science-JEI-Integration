package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.registers.NuclearScienceTiles;

public class TileChemicalExtractor extends GenericTile {

	public static final int DEFAULT_RAD_STRENGTH = 300;
	public static final int RAD_RADIUS = 2;
	public static final int MAX_TANK_CAPACITY = 5000;

	public TileChemicalExtractor(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_CHEMICALEXTRACTOR.get(), pos, state);
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(1, MAX_TANK_CAPACITY)
				//
				.setInputDirections(BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.BACK, BlockEntityUtils.MachineDirection.LEFT).setRecipeType(NuclearScienceRecipeInit.CHEMICAL_EXTRACTOR_TYPE.get()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 1, 1, 0).bucketInputs(1).upgrades(3))
				//
				.setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.BACK)
				//
				.setDirectionsBySlot(1, BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.BACK).validUpgrades(ContainerChemicalExtractor.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(component -> component.consumeBucket().canProcessFluidItem2ItemRecipe(component, NuclearScienceRecipeInit.CHEMICAL_EXTRACTOR_TYPE.get())).process(component -> component.processFluidItem2ItemRecipe(component)));
		addComponent(new ComponentContainerProvider("container.chemicalextractor", this).createMenu((id, player) -> new ContainerChemicalExtractor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	protected void tickClient(ComponentTickable tickable) {
		if (this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive() && level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.8 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getComparatorSignal() {
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive() ? 15 : 0;
	}
}
