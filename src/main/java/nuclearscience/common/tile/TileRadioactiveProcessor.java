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
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerRadioactiveProcessor;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.registers.NuclearScienceTiles;

public class TileRadioactiveProcessor extends GenericTile {

    public static final int MAX_TANK_CAPACITY = 5000;

    public TileRadioactiveProcessor(BlockPos pos, BlockState state) {
        super(NuclearScienceTiles.TILE_RADIOACTIVEPROCESSOR.get(), pos, state);
        addComponent(new ComponentTickable(this));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentElectrodynamic(this, false, true).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4).setInputDirections(BlockEntityUtils.MachineDirection.BACK));
        addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(1, MAX_TANK_CAPACITY).setInputDirections(BlockEntityUtils.MachineDirection.TOP).setRecipeType(NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_TYPE.get()));
        addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 1, 1, 0).bucketInputs(1).upgrades(3)).validUpgrades(ContainerRadioactiveProcessor.VALID_UPGRADES).valid(machineValidator())
                //
                .setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.LEFT).setDirectionsBySlot(1, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.BOTTOM));
        addComponent(new ComponentProcessor(this).canProcess(this::shouldProcessRecipe).process(component -> component.processFluidItem2ItemRecipe(component)));
        addComponent(new ComponentContainerProvider("container.radioactiveprocessor", this).createMenu((id, player) -> new ContainerRadioactiveProcessor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    private boolean shouldProcessRecipe(ComponentProcessor component) {
        component.consumeBucket();
        boolean canProcess = component.canProcessFluidItem2ItemRecipe(component, NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_TYPE.get());
        if (BlockEntityUtils.isLit(this) ^ canProcess) {
            BlockEntityUtils.updateLit(this, canProcess);
        }
        return canProcess;
    }

    @Override
    public int getComparatorSignal() {
        return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive() ? 15 : 0;
    }

}
