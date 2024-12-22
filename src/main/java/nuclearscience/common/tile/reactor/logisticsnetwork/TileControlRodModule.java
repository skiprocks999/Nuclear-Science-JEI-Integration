package nuclearscience.common.tile.reactor.logisticsnetwork;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.registers.NuclearScienceTiles;

public class TileControlRodModule extends TileControlRod implements ILogisticsMember {

    private Direction relativeBack;

    public final Property<Integer> redstoneSignal = property(new Property<>(PropertyTypes.INTEGER, "redstonesignal", 0)).onChange((prop, oldVal) -> {
        if(!level.isClientSide && prop.get() != oldVal) {

            int newInset = (int) ((((double) prop.get() / 15.0) * (double) EXTENSION_PER_CLICK) / EXTENSION_PER_CLICK);

            insertion.set(newInset);

        }
    });

    public TileControlRodModule(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_CONTROLRODMODULE.get(), worldPos, blockState);
        relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    @Override
    public boolean isValidConnection(Direction dir) {
        return dir == relativeBack;
    }

    @Override
    public boolean canConnect(ReactorLogisticsNetwork network) {
        return network.controlRod == null || network.controlRod.getBlockPos().equals(getBlockPos());
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
    public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
        super.onNeightborChanged(neighbor, blockStateTrigger);
        if (!level.isClientSide) {
            redstoneSignal.set(getLevel().getBestNeighborSignal(getBlockPos()));
        }
    }
}
