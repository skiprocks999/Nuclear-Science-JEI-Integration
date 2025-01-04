package nuclearscience.common.tile.reactor.logisticsnetwork;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerMonitorModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.registers.NuclearScienceTiles;

public class TileMonitorModule extends GenericTileInterfaceBound {

    private Direction relativeBack;

    public TileMonitorModule(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_MONITORMODULE.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentContainerProvider("container.monitormodule", this).createMenu((id, player) -> new ContainerMonitorModule(id, player, new SimpleContainer(0), getCoordsArray())));
        relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
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
    public boolean checkLinkedPosition(GenericTileInterface inter) {
        return true;
    }

    @Override
    public GenericTileInterface.InterfaceType[] getValidInterfaces() {
        return ALL;
    }
}
