package nuclearscience.common.tile.reactor.logisticsnetwork;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerThermometerModule;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.registers.NuclearScienceTiles;

public class TileThermometerModule extends GenericTileInterfaceBound {

    private Direction relativeBack;

    public final Property<Integer> mode = property(new Property<>(PropertyTypes.INTEGER, "comparitormode", Mode.CONSTANT.ordinal()));
    public final Property<Boolean> inverted = property(new Property<>(PropertyTypes.BOOLEAN, "inverted", false));
    public final Property<Double> targetTemperature = property(new Property<>(PropertyTypes.DOUBLE, "targettemperature", 0.0));
    public final Property<Double> trackedTemperature = property(new Property<>(PropertyTypes.DOUBLE, "trackedtemperature", 0.0));
    public final Property<Integer> redstoneSignal = property(new Property<>(PropertyTypes.INTEGER, "redstonesignal", 0));

    public static final int MAX_REDSTONE = 15;

    public TileThermometerModule(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_THERMOMETERMODULE.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentContainerProvider("container.thermometermodule", this).createMenu((id, player) -> new ContainerThermometerModule(id, player, new SimpleContainer(0), getCoordsArray())));
        relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    @Override
    public void tickServer(ComponentTickable tickable) {
        super.tickServer(tickable);

        GenericTileInterface.InterfaceType type = GenericTileInterface.InterfaceType.values()[interfaceType.get()];

        if (type == GenericTileInterface.InterfaceType.NONE || interfaceLocation.get().equals(BlockEntityUtils.OUT_OF_REACH)) {
            redstoneSignal.set(0);
            trackedTemperature.set(0.0);
            return;
        }

        if (!networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
            redstoneSignal.set(0);
            trackedTemperature.set(0.0);
            return;
        }

        TileReactorLogisticsCable cable = networkCable.getSafe();

        if (cable.isRemoved()) {
            redstoneSignal.set(0);
            trackedTemperature.set(0.0);
            return;
        }

        ReactorLogisticsNetwork network = cable.getNetwork();

        if (!network.isControllerActive()) {
            redstoneSignal.set(0);
            trackedTemperature.set(0.0);
            return;
        }

        GenericTileInterface genericInterface = network.getInterface(interfaceLocation.get());

        if (genericInterface == null || genericInterface.getInterfaceType() != type) {
            redstoneSignal.set(0);
            trackedTemperature.set(0.0);
            return;
        }

        if (genericInterface.reactor == null || !genericInterface.reactor.valid()) {
            redstoneSignal.set(0);
            trackedTemperature.set(0.0);
            return;
        }

        double temp = -1;

        if (genericInterface.reactor.getSafe() instanceof TileFissionReactorCore core) {

            temp = TileFissionReactorCore.getActualTemp(core.temperature.get());

        } else if (genericInterface.reactor.getSafe() instanceof TileMSReactorCore core) {

            temp = core.temperature.get();

        }

        if (temp < 0) {
            redstoneSignal.set(0);
            trackedTemperature.set(0.0);
            return;
        }

        double perc = 0;

        trackedTemperature.set(temp);

        switch (Mode.values()[mode.get()]) {
            case CONSTANT:
                if (inverted.get()) {
                    if (temp < targetTemperature.get()) {
                        perc = 1;
                    } else {
                        perc = 0;
                    }
                } else {
                    if (temp > targetTemperature.get()) {
                        perc = 1;
                    } else {
                        perc = 0;
                    }
                }

                break;
            case BUILD_UP:

                if (inverted.get()) {
                    if (temp == 0 || targetTemperature.get() == 0) {
                        perc = 1;
                    } else {

                        perc = 1.0 - (temp / targetTemperature.get());

                    }
                } else {
                    if (temp == 0 || targetTemperature.get() == 0) {
                        perc = 0;
                    } else {

                        perc = temp / targetTemperature.get();

                    }

                }

                break;
        }

        redstoneSignal.set((int) (MAX_REDSTONE * perc));


    }

    @Override
    public Direction getCableLocation() {
        return relativeBack;
    }

    @Override
    public void onBlockStateUpdate(BlockState oldState, BlockState newState) {
        super.onBlockStateUpdate(oldState, newState);
        if (!level.isClientSide() && oldState.hasProperty(ElectrodynamicsBlockStates.FACING) && newState.hasProperty(ElectrodynamicsBlockStates.FACING) && oldState.getValue(ElectrodynamicsBlockStates.FACING) != newState.getValue(ElectrodynamicsBlockStates.FACING)) {
            relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("relativeback", relativeBack.ordinal());
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        relativeBack = Direction.values()[compound.getInt("relativeback")];
    }

    @Override
    public GenericTileInterface.InterfaceType[] getValidInterfaces() {
        return TEMPERATURE;
    }

    @Override
    public int getComparatorSignal() {
        return redstoneSignal.get();
    }

    public static enum Mode {

        BUILD_UP, CONSTANT;

    }


}
