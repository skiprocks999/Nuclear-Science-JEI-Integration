package nuclearscience.common.tile.reactor.logisticsnetwork.interfaces;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.common.tile.reactor.fission.IFissionControlRod;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileControlRodModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileSupplyModule;
import nuclearscience.registers.NuclearScienceTiles;

public class TileFissionInterface extends GenericTileInterface implements IFissionControlRod {

    public final Property<Integer> insertion = property(new Property<>(PropertyTypes.INTEGER, "insertion", 0));

    public TileFissionInterface(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_FISSIONINTERFACE.get(), worldPos, blockState);
    }

    @Override
    public void tickServer(ComponentTickable tickable) {
        super.tickServer(tickable);

        if (!networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
            insertion.set(0);
            return;
        }

        TileReactorLogisticsCable cable = networkCable.getSafe();

        if (cable.isRemoved()) {
            insertion.set(0);
            return;
        }

        ReactorLogisticsNetwork network = cable.getNetwork();

        if (!network.isControllerActive()) {
            insertion.set(0);
            return;
        }

        TileControlRodModule controlRod = network.getControlRod(controlRodLocation.get());

        if (controlRod == null) {
            insertion.set(0);
        } else {
            insertion.set(controlRod.insertion.get());
        }

        TileSupplyModule supplyModule = network.getSupplyModule(supplyModuleLocation.get());

        if (!reactor.valid() || supplyModule == null || !(reactor.getSafe() instanceof TileFissionReactorCore)) {
            return;
        }

        TileFissionReactorCore core = reactor.getSafe();

        ComponentInventory coreInv = core.getComponent(IComponentType.Inventory);
        ComponentInventory supplyInv = supplyModule.getComponent(IComponentType.Inventory);

        boolean isExtractingSpentCell = serverAnimations.containsKey(InterfaceAnimation.FISSION_WASTE_1) || serverAnimations.containsKey(InterfaceAnimation.FISSION_WASTE_2) || serverAnimations.containsKey(InterfaceAnimation.FISSION_WASTE_3) || serverAnimations.containsKey(InterfaceAnimation.FISSION_WASTE_4);
        boolean isInsertingFuelCell = serverAnimations.containsKey(InterfaceAnimation.FISSION_FUEL_1) || serverAnimations.containsKey(InterfaceAnimation.FISSION_FUEL_2) || serverAnimations.containsKey(InterfaceAnimation.FISSION_FUEL_3) || serverAnimations.containsKey(InterfaceAnimation.FISSION_FUEL_4);
        boolean isExtractingTritium = serverAnimations.containsKey(InterfaceAnimation.FISSION_TRITIUM_EXTRACT);
        boolean isInsertingDeuterium = serverAnimations.containsKey(InterfaceAnimation.FISSION_DEUTERIUM_INSERT);

        ItemStack deuterium = coreInv.getItem(TileFissionReactorCore.DUETERIUM_SLOT);

        // Check if there are any spent cells in the fission core

        if (!isInsertingFuelCell) {

            ItemStack item;

            int cleared = 0;

            for (int i = 0; i < 4; i++) {

                item = coreInv.getItem(i);

                if (item.isEmpty()) {
                    cleared++;
                } else if (item.is(NuclearScienceTags.Items.FUELROD_SPENT)) {

                    boolean inserted = false;

                    for (int j = 9; j < 18; j++) {

                        if (supplyInv.getItem(j).isEmpty()) {

                            supplyInv.setItem(j, item.copy());
                            coreInv.setItem(i, ItemStack.EMPTY);
                            inserted = true;
                            cleared++;

                            break;

                        }

                    }

                    if (inserted) {
                        switch (i) {
                            case 0:
                                queuedAnimations.get().add(InterfaceAnimation.FISSION_WASTE_1.ordinal());
                                break;
                            case 1:
                                queuedAnimations.get().add(InterfaceAnimation.FISSION_WASTE_2.ordinal());
                                break;
                            case 2:
                                queuedAnimations.get().add(InterfaceAnimation.FISSION_WASTE_3.ordinal());
                                break;
                            case 3:
                                queuedAnimations.get().add(InterfaceAnimation.FISSION_WASTE_4.ordinal());
                                break;
                        }

                        isExtractingSpentCell = true;

                        queuedAnimations.forceDirty();
                    }
                }
            }
        }

        // Check if tritium needs to be extracted

        if (!isInsertingDeuterium && !coreInv.areOutputsEmpty()) {

            ItemStack item = coreInv.getItem(5);
            ItemStack destItem;

            boolean extracted = false;

            for (int j = 9; j < 18; j++) {

                if (coreInv.areOutputsEmpty() || item.isEmpty()) {
                    break;
                }

                destItem = supplyInv.getItem(j);

                if (destItem.isEmpty()) {

                    supplyInv.setItem(j, item.copy());
                    coreInv.setItem(5, ItemStack.EMPTY);
                    extracted = true;

                } else if (destItem.is(NuclearScienceTags.Items.CELL_TRITIUM) && destItem.getCount() < destItem.getMaxStackSize()) {

                    int taken = Math.min(destItem.getMaxStackSize() - destItem.getCount(), item.getCount());
                    destItem.grow(taken);
                    item.shrink(taken);
                    extracted = true;

                }

            }
            if (extracted) {

                queuedAnimations.get().add(InterfaceAnimation.FISSION_TRITIUM_EXTRACT.ordinal());
                queuedAnimations.forceDirty();

                isExtractingTritium = true;

            }
        }

        // Check if fuel cells need to be inserted

        if (!isExtractingSpentCell && !supplyInv.areInputsEmpty()) {

            ItemStack item;
            ItemStack supplyItem;

            int taken = 0;

            for (int i = 0; i < 4; i++) {

                item = coreInv.getItem(i);

                if (item.is(NuclearScienceTags.Items.FUELROD_URANIUM_LOW_EN) || item.is(NuclearScienceTags.Items.FUELROD_URANIUM_HIGH_EN) || item.is(NuclearScienceTags.Items.FUELROD_PLUTONIUM)) {
                    taken++;
                    continue;
                } else if (item.isEmpty()) {

                    boolean inserted = false;

                    for (int j = 0; j < 9; j++) {

                        supplyItem = supplyInv.getItem(j);

                        if (supplyItem.is(NuclearScienceTags.Items.FUELROD_URANIUM_LOW_EN) || supplyItem.is(NuclearScienceTags.Items.FUELROD_URANIUM_HIGH_EN) || supplyItem.is(NuclearScienceTags.Items.FUELROD_PLUTONIUM)) {
                            coreInv.setItem(i, supplyItem.copy());
                            supplyInv.setItem(j, ItemStack.EMPTY);
                            taken++;
                            inserted = true;
                            break;
                        }
                    }

                    if (inserted) {
                        switch (i) {
                            case 0:
                                queuedAnimations.get().add(InterfaceAnimation.FISSION_FUEL_1.ordinal());
                                break;
                            case 1:
                                queuedAnimations.get().add(InterfaceAnimation.FISSION_FUEL_2.ordinal());
                                break;
                            case 2:
                                queuedAnimations.get().add(InterfaceAnimation.FISSION_FUEL_3.ordinal());
                                break;
                            case 3:
                                queuedAnimations.get().add(InterfaceAnimation.FISSION_FUEL_4.ordinal());
                                break;
                        }

                        queuedAnimations.forceDirty();
                    }

                }

            }
        }

        // Check if Deuterium needs to be inserted

        if (!isExtractingTritium && (deuterium.isEmpty() || (deuterium.is(NuclearScienceTags.Items.CELL_DEUTERIUM) && deuterium.getCount() < deuterium.getMaxStackSize()))) {

            ItemStack item;

            boolean taken = false;

            for (int j = 0; j < 9; j++) {

                deuterium = coreInv.getItem(TileFissionReactorCore.DUETERIUM_SLOT);

                if (deuterium.is(NuclearScienceTags.Items.CELL_DEUTERIUM) && deuterium.getCount() >= deuterium.getMaxStackSize()) {
                    break;
                }

                item = supplyInv.getItem(j).copy();

                if (!item.is(NuclearScienceTags.Items.CELL_DEUTERIUM)) {
                    continue;
                }

                if (deuterium.isEmpty()) {
                    coreInv.setItem(TileFissionReactorCore.DUETERIUM_SLOT, item.copy());
                    supplyInv.setItem(j, ItemStack.EMPTY);
                    taken = true;
                } else if (deuterium.getCount() < deuterium.getMaxStackSize()) {
                    int amt = Math.min(item.getCount(), deuterium.getMaxStackSize() - deuterium.getCount());
                    supplyInv.removeItem(j, amt);
                    deuterium.grow(amt);
                    taken = true;
                }
            }

            if (taken) {
                queuedAnimations.get().add(InterfaceAnimation.FISSION_DEUTERIUM_INSERT.ordinal());
                queuedAnimations.forceDirty();
            }

        }

        handleServerAnimations(tickable);

    }

    @Override
    public int getInsertion() {
        return insertion.get();
    }

    @Override
    public Direction getReactorDirection() {
        return Direction.UP;
    }

    @Override
    public InterfaceType getInterfaceType() {
        return InterfaceType.FISSION;
    }

    @Override
    public Direction getCableLocation() {
        return Direction.DOWN;
    }
}
