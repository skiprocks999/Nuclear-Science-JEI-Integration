package nuclearscience.common.tile.reactor.logisticsnetwork;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.registers.NuclearScienceTiles;

public class TileController extends GenericTile implements ILogisticsMember {

    private static final double USAGE = 100;

    private boolean active = false;
    private Direction relativeBack;

    public TileController(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_LOGISTICSCONTROLLER.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE).maxJoules(USAGE * 20));
        relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    private void tickServer(ComponentTickable tickable) {
        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

        boolean canRun = electro.getJoulesStored() >= USAGE;

        if (BlockEntityUtils.isLit(this) ^ canRun) {
            BlockEntityUtils.updateLit(this, canRun);
        }

        if(canRun) {
            electro.setJoulesStored(electro.getJoulesStored() - USAGE);
            active = canRun;
        }

    }

    @Override
    public boolean isValidConnection(Direction dir) {
        return dir == relativeBack;
    }

    @Override
    public boolean canConnect(ReactorLogisticsNetwork network) {
        return network.controller == null || network.controller.getBlockPos().equals(getBlockPos());
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
        compound.putBoolean("active", active);
        compound.putInt("relativeback", relativeBack.ordinal());
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        active = compound.getBoolean("active");
        relativeBack = Direction.values()[compound.getInt("relativeback")];
    }

    public boolean isActive() {
        return active;
    }


}
