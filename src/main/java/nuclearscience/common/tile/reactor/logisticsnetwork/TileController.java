package nuclearscience.common.tile.reactor.logisticsnetwork;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
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
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileLogisticsMember;
import nuclearscience.registers.NuclearScienceSounds;
import nuclearscience.registers.NuclearScienceTiles;

public class TileController extends GenericTileLogisticsMember implements ITickableSound {

    public static final double USAGE = 100;

    public final Property<Boolean> active = property(new Property<>(PropertyTypes.BOOLEAN, "active", false));
    private Direction relativeBack;

    private boolean isSoundPlaying = false;

    public TileController(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_LOGISTICSCONTROLLER.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
        addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE).maxJoules(USAGE * 20));
        relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    @Override
    public void tickServer(ComponentTickable tickable) {
        super.tickServer(tickable);

        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

        boolean canRun = electro.getJoulesStored() >= USAGE;

        if (BlockEntityUtils.isLit(this) ^ canRun) {
            BlockEntityUtils.updateLit(this, canRun);
        }

        if (canRun) {
            electro.setJoulesStored(electro.getJoulesStored() - USAGE);
            active.set(canRun);
        }

    }

    public void tickClient(ComponentTickable tickable) {
        if (!isSoundPlaying && shouldPlaySound()) {
            isSoundPlaying = true;
            SoundBarrierMethods.playTileSound(NuclearScienceSounds.SOUND_LOGISTICSCONTROLLER.get(), this, true);
        }
    }

    @Override
    public Direction getCableLocation() {
        return relativeBack;
    }

    @Override
    public boolean canConnect(ReactorLogisticsNetwork network) {
        return network.getController() == null || network.getController().getBlockPos().equals(getBlockPos());
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

    public boolean isActive() {
        return active.get();
    }

    @Override
    public void setNotPlaying() {
        isSoundPlaying = false;
    }

    @Override
    public boolean shouldPlaySound() {
        return active.get();
    }
}
