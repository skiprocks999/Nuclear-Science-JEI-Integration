package nuclearscience.common.tile.reactor.logisticsnetwork;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerSupplyModule;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.prefab.utils.RadiationUtils;
import nuclearscience.registers.NuclearScienceTiles;

public class TileSupplyModule extends GenericTileInterfaceBound {

    private Direction relativeBack;

    public TileSupplyModule(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_SUPPLYMODULE.get(), worldPos, blockState);
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(9).outputs(9))
                //
                .setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0, 1, 2, 3, 4, 5, 6, 7, 8)
                //
                .setSlotsByDirection(BlockEntityUtils.MachineDirection.FRONT, 0, 1, 2, 3, 4, 5, 6, 7, 8)
                //
                .setSlotsByDirection(BlockEntityUtils.MachineDirection.BOTTOM, 9, 10, 11, 12, 13, 14, 15, 16, 17)
                //
                .setSlotsByDirection(BlockEntityUtils.MachineDirection.LEFT, 9, 10, 11, 12, 13, 14, 15, 16, 17)
                //
                .setSlotsByDirection(BlockEntityUtils.MachineDirection.RIGHT, 9, 10, 11, 12, 13, 14, 15, 16, 17).valid(machineValidator()));
        addComponent(new ComponentContainerProvider("container.supplymodule", this).createMenu((id, player) -> new ContainerSupplyModule(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
        relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    @Override
    public void tickServer(ComponentTickable tickable) {
        super.tickServer(tickable);
        RadiationUtils.handleRadioactiveItems(this, (ComponentInventory) getComponent(IComponentType.Inventory), Constants.RADIOACTIVE_PROCESSOR_RADIATION_RADIUS, true, 0, false);
    }

    @Override
    public boolean checkLinkedPosition(GenericTileInterface inter) {
        return inter.supplyModuleLocation.get().equals(getBlockPos());
    }

    @Override
    public GenericTileInterface.InterfaceType[] getValidInterfaces() {
        return SUPPLIES;
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
    public void onInterfacePropChange(Property<BlockPos> prop, BlockPos old) {

        super.onInterfacePropChange(prop, old);

        boolean oldInval = old.equals(BlockEntityUtils.OUT_OF_REACH);
        boolean newInval = prop.get().equals(BlockEntityUtils.OUT_OF_REACH);

        if(oldInval && newInval) {
            return;
        }

        if (networkCable == null || !networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
            return;
        }

        TileReactorLogisticsCable cable = networkCable.getSafe();

        if (cable.isRemoved()) {
            return;
        }

        ReactorLogisticsNetwork network = cable.getNetwork();

        if(oldInval && !newInval) {
            GenericTileInterface inter = network.getInterface(prop.get());

            if(inter != null) {
                inter.supplyModuleLocation.set(getBlockPos());
            }
        } else if (!oldInval && newInval) {
            GenericTileInterface inter = network.getInterface(old);

            if(inter != null) {
                inter.supplyModuleLocation.set(BlockEntityUtils.OUT_OF_REACH);
            }
        }

    }

    @Override
    public void onBlockDestroyed() {
        super.onBlockDestroyed();
        if (!level.isClientSide()) {

            if (networkCable == null || !networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
                return;
            }

            TileReactorLogisticsCable cable = networkCable.getSafe();

            if (cable.isRemoved()) {
                return;
            }

            ReactorLogisticsNetwork network = cable.getNetwork();

            GenericTileInterface inter = network.getInterface(interfaceLocation.get());

            if (inter == null) {
                return;
            }

            inter.supplyModuleLocation.set(BlockEntityUtils.OUT_OF_REACH);
        }
    }
}
