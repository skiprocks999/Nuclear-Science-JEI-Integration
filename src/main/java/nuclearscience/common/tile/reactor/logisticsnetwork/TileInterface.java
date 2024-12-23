package nuclearscience.common.tile.reactor.logisticsnetwork;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.common.tile.reactor.fission.IFissionControlRod;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.common.tile.reactor.moltensalt.IMSControlRod;
import nuclearscience.prefab.NuclearPropertyTypes;
import nuclearscience.registers.NuclearScienceTiles;

import java.util.*;

public abstract class TileInterface extends GenericTile implements ILogisticsMember {

    private final Direction relativeBack = Direction.DOWN;

    public CachedTileOutput reactor;
    public CachedTileOutput networkCable;

    public Property<ArrayList<Integer>> queuedAnimations = property(new Property<>(NuclearPropertyTypes.INTEGER_LIST, "queuedanimations", new ArrayList<>()));

    public final HashMap<InterfaceAnimation, Long> clientAnimations = new HashMap<>();
    //Nothing is rendered with this map; it is used to keep track of what the interface is doing only
    protected final HashMap<InterfaceAnimation, Long> serverAnimations = new HashMap<>();


    public TileInterface(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
        super(tileEntityTypeIn, worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
    }

    public void tickServer(ComponentTickable tickable) {

        queuedAnimations.set(new ArrayList<>());
        queuedAnimations.forceDirty();

        if (reactor == null) {
            reactor = new CachedTileOutput(getLevel(), getBlockPos().relative(getReactorDirection()));
        }

        if (networkCable == null) {
            networkCable = new CachedTileOutput(getLevel(), getBlockPos().relative(Direction.DOWN));
        }

        if (tickable.getTicks() % 20 == 0) {
            if (!reactor.valid()) {
                reactor.update(getBlockPos().relative(getReactorDirection()));
            }
            if (!networkCable.valid()) {
                networkCable.update(getBlockPos().relative(Direction.DOWN));
            }

        }

    }

    private void tickClient(ComponentTickable tickable) {

        if (reactor == null) {
            reactor = new CachedTileOutput(getLevel(), getBlockPos().relative(getReactorDirection()));
        }

        if (networkCable == null) {
            networkCable = new CachedTileOutput(getLevel(), getBlockPos().relative(Direction.DOWN));
        }

        if (tickable.getTicks() % 20 == 0) {
            if (!reactor.valid()) {
                reactor.update(getBlockPos().relative(getReactorDirection()));
            }
            if (!networkCable.valid()) {
                networkCable.update(getBlockPos().relative(Direction.DOWN));
            }

        }

        long currTime = tickable.getTicks();

        queuedAnimations.get().forEach(val -> {

            clientAnimations.put(InterfaceAnimation.values()[val], currTime);

        });

        Iterator<Map.Entry<InterfaceAnimation, Long>> it = clientAnimations.entrySet().iterator();

        Map.Entry<InterfaceAnimation, Long> entry;

        while (it.hasNext()) {
            entry = it.next();

            if (currTime - entry.getValue() > entry.getKey().animationTime) {
                it.remove();
            }

        }

    }

    @Override
    public boolean isValidConnection(Direction dir) {
        return dir == relativeBack;
    }

    @Override
    public boolean canConnect(ReactorLogisticsNetwork network) {
        return true;
    }

    public abstract Direction getReactorDirection();

    protected void handleServerAnimations(ComponentTickable tickable) {
        long currTime = tickable.getTicks();

        queuedAnimations.get().forEach(val -> {

            serverAnimations.put(InterfaceAnimation.values()[val], currTime);

        });

        Iterator<Map.Entry<InterfaceAnimation, Long>> it = serverAnimations.entrySet().iterator();

        Map.Entry<InterfaceAnimation, Long> entry;

        while (it.hasNext()) {
            entry = it.next();

            if (currTime - entry.getValue() > entry.getKey().animationTime) {
                it.remove();
            }

        }
    }

    public static class TileFissionInterface extends TileInterface implements IFissionControlRod {

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

            if (network.controller == null || !network.controller.isActive()) {
                insertion.set(0);
                return;
            }

            if (network.controlRod == null) {
                insertion.set(0);
            } else {
                insertion.set(network.controlRod.insertion.get());
            }

            if (!reactor.valid() || network.supplyModules.isEmpty()) {
                return;
            }

            if (reactor.getSafe() instanceof TileFissionReactorCore core) {
                HashSet<TileSupplyModule> supplyModules = network.supplyModules;

                ComponentInventory coreInv = core.getComponent(IComponentType.Inventory);
                ComponentInventory supplyInv;

                HashMap<InterfaceAnimation, Long> serverAnimations = super.serverAnimations;

                // set these flags to avoid checking needlessly

                boolean areSpentCellsRemoved = false;
                boolean fuelCellsAreFull = false;

                boolean isExtractingSpentCell = serverAnimations.containsKey(InterfaceAnimation.FISSION_WASTE_1) || serverAnimations.containsKey(InterfaceAnimation.FISSION_WASTE_2) || serverAnimations.containsKey(InterfaceAnimation.FISSION_WASTE_3) || serverAnimations.containsKey(InterfaceAnimation.FISSION_WASTE_4);
                boolean isInsertingFuelCell = serverAnimations.containsKey(InterfaceAnimation.FISSION_FUEL_1) || serverAnimations.containsKey(InterfaceAnimation.FISSION_FUEL_2) || serverAnimations.containsKey(InterfaceAnimation.FISSION_FUEL_3) || serverAnimations.containsKey(InterfaceAnimation.FISSION_FUEL_4);
                boolean isExtractingTritium = serverAnimations.containsKey(InterfaceAnimation.FISSION_TRITIUM_EXTRACT);
                boolean isInsertingDeuterium = serverAnimations.containsKey(InterfaceAnimation.FISSION_DEUTERIUM_INSERT);

                for (TileSupplyModule supplyModule : supplyModules) {

                    supplyInv = supplyModule.getComponent(IComponentType.Inventory);

                    ItemStack deuterium = coreInv.getItem(TileFissionReactorCore.DUETERIUM_SLOT);

                    // Check if all four conditions are met so we can skip loop

                    if (areSpentCellsRemoved && coreInv.areOutputsEmpty() && fuelCellsAreFull && (deuterium.is(NuclearScienceTags.Items.CELL_DEUTERIUM) && deuterium.getCount() >= deuterium.getMaxStackSize())) {
                        break;
                    }

                    // Check if there are any spent cells in the fission core

                    if (!isInsertingFuelCell && !areSpentCellsRemoved) {

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
                        if (cleared >= 4) {

                            areSpentCellsRemoved = true;

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

                    if (!isExtractingSpentCell && !fuelCellsAreFull && !supplyInv.areInputsEmpty()) {

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
                        if (taken == 4) {
                            fuelCellsAreFull = true;
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

                }
            }

            super.handleServerAnimations(tickable);

        }

        @Override
        public int getInsertion() {
            return insertion.get();
        }

        @Override
        public Direction getReactorDirection() {
            return Direction.UP;
        }
    }

    public static class TileMSInterface extends TileInterface implements IMSControlRod {

        public final Property<Integer> insertion = property(new Property<>(PropertyTypes.INTEGER, "insertion", 0));

        public TileMSInterface(BlockPos worldPos, BlockState blockState) {
            super(NuclearScienceTiles.TILE_MSINTERFACE.get(), worldPos, blockState);
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

            if (network.controller == null || !network.controller.isActive()) {
                insertion.set(0);
                return;
            }

            if (network.controlRod == null) {
                insertion.set(0);
            } else {
                insertion.set(network.controlRod.insertion.get());
            }
        }

        @Override
        public int getInsertion() {
            return insertion.get();
        }

        @Override
        public Direction facingDir() {
            return getReactorDirection();
        }

        @Override
        public Direction getReactorDirection() {
            return getFacing();
        }
    }

    public static class TileFusionInterface extends TileInterface {

        public TileFusionInterface(BlockPos worldPos, BlockState blockState) {
            super(NuclearScienceTiles.TILE_FUSIONINTERFACE.get(), worldPos, blockState);
        }

        @Override
        public void tickServer(ComponentTickable tickable) {
            super.tickServer(tickable);

            if (!networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
                return;
            }

            TileReactorLogisticsCable cable = networkCable.getSafe();

            if (cable.isRemoved()) {
                return;
            }

            ReactorLogisticsNetwork network = cable.getNetwork();

            if (network.controller == null || !network.controller.isActive()) {
                return;
            }

            if (reactor.getSafe() instanceof TileFusionReactorCore core) {

                ComponentInventory supplyInv;

                for (TileSupplyModule supplyModule : network.supplyModules) {

                    supplyInv = supplyModule.getComponent(IComponentType.Inventory);

                    boolean checkDeuterium = !core.isDeuteriumFull();
                    boolean checkTritium = !core.isTritiumFull();

                    if (!checkDeuterium && !checkTritium) {
                        break;
                    }

                    ItemStack stack;

                    if (checkDeuterium) {

                        boolean inserted = false;

                        for (int i = 0; i < 9; i++) {
                            stack = supplyInv.getItem(i);

                            if (stack.isEmpty() || !stack.is(NuclearScienceTags.Items.CELL_DEUTERIUM)) {
                                continue;
                            }

                            int accepted = core.addDeuteriumCells(stack.getCount());

                            stack.shrink(accepted);

                            if (accepted > 0) {
                                inserted = true;
                            }

                            if (core.isDeuteriumFull()) {
                                break;
                            }
                        }

                        if (inserted && !queuedAnimations.get().contains(InterfaceAnimation.FUSION_DEUTERIUM_INSERT.ordinal())) {
                            queuedAnimations.get().add(InterfaceAnimation.FUSION_TRITIUM_INSERT.ordinal());
                            queuedAnimations.forceDirty();
                        }
                    }

                    if (checkTritium) {

                        boolean inserted = false;

                        for (int i = 0; i < 9; i++) {
                            stack = supplyInv.getItem(i);

                            if (stack.isEmpty() || !stack.is(NuclearScienceTags.Items.CELL_TRITIUM)) {
                                continue;
                            }

                            int accepted = core.addTritiumCells(stack.getCount());

                            stack.shrink(accepted);

                            if (accepted > 0) {
                                inserted = true;
                            }

                            if (core.isTritiumFull()) {
                                break;
                            }
                        }

                        if (inserted && !queuedAnimations.get().contains(InterfaceAnimation.FUSION_TRITIUM_INSERT.ordinal())) {
                            queuedAnimations.get().add(InterfaceAnimation.FUSION_TRITIUM_INSERT.ordinal());
                            queuedAnimations.forceDirty();
                        }
                    }


                }
            }


        }

        @Override
        public Direction getReactorDirection() {
            return Direction.UP;
        }
    }

    public static enum InterfaceAnimation {

        FISSION_WASTE_1(80),//
        FISSION_WASTE_2(80),//
        FISSION_WASTE_3(80),//
        FISSION_WASTE_4(80),//
        FISSION_TRITIUM_EXTRACT(80),//
        FISSION_FUEL_1(80),//
        FISSION_FUEL_2(80),//
        FISSION_FUEL_3(80),//
        FISSION_FUEL_4(80),//
        FISSION_DEUTERIUM_INSERT(80),//
        FUSION_DEUTERIUM_INSERT(80),//
        FUSION_TRITIUM_INSERT(80)//
        ;


        public final int animationTime;

        private InterfaceAnimation(int timeTicks) {
            animationTime = timeTicks;
        }


    }


}
