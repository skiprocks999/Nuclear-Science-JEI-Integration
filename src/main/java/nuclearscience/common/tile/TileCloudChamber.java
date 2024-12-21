package nuclearscience.common.tile;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.*;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.client.render.event.levelstage.HandlerCloudChamber;
import nuclearscience.common.inventory.container.ContainerCloudChamber;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceTiles;

import java.util.ArrayList;
import java.util.List;

public class TileCloudChamber extends GenericTile {

    public static final int HORR_RADIUS = 30;
    private static final int VERT_RADIUS = 30;

    public final Property<ArrayList<BlockPos>> sources = property(new Property<>(PropertyTypes.BLOCK_POS_LIST, "sources", new ArrayList<BlockPos>()));
    public final Property<Boolean> active = property(new Property<>(PropertyTypes.BOOLEAN, "active", false));
    private final Property<Boolean> hasRedstoneSignal = property(new Property(PropertyTypes.BOOLEAN, "redstonesignal", false));

    public TileCloudChamber(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_CLOUDCHAMBER.get(), worldPos, blockState);

        addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
        addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE).maxJoules(Constants.CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK * 20));
        addComponent(new ComponentFluidHandlerSimple(100, fluidStack -> fluidStack.getFluid().is(NuclearScienceTags.Fluids.METHANOL), this, "methanolstorage").setInputDirections(BlockEntityUtils.MachineDirection.BACK));
        addComponent(new ComponentContainerProvider("container.cloudchamber", this).createMenu((id, player) -> new ContainerCloudChamber(id, player, new SimpleContainer(), getCoordsArray())));

    }

    private void tickClient(ComponentTickable tickable) {
        if(active.get()) {
            HandlerCloudChamber.addSources(this);
        }
    }

    private void tickServer(ComponentTickable tickable) {

        if(hasRedstoneSignal.get()) {
            active.set(false);
            return;
        }

        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

        if(electro.getJoulesStored() < Constants.CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK) {
            active.set(false);
            return;
        }

        ComponentFluidHandlerSimple fluid = getComponent(IComponentType.FluidHandler);

        if(fluid.isEmpty() || fluid.getFluidAmount() < Constants.CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK) {
            active.set(false);
            return;
        }

        List<BlockPos> sources = RadiationSystem.getRadiationSources(getLevel());

        List<BlockPos> accepted = new ArrayList<>();

        BlockPos pos = getBlockPos();

        sources.forEach(source -> {

            int deltaX = source.getX() - pos.getX();
            int deltaY = source.getY() - pos.getY();
            int deltaZ = source.getZ() - pos.getZ();

            if(Math.abs(deltaY) > VERT_RADIUS || Math.abs(deltaX) > HORR_RADIUS || Math.abs(deltaZ) > HORR_RADIUS) {
                return;
            }

            accepted.add(source);

        });

        if(accepted.isEmpty()) {
            active.set(false);
            return;
        }

        active.set(true);

        this.sources.set(accepted);
        this.sources.forceDirty();

        electro.setJoulesStored(electro.getJoulesStored() - Constants.CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK);
        fluid.drain(Constants.CLOUD_CHAMBER_FLUID_USAGE_PER_TICK, IFluidHandler.FluidAction.EXECUTE);
    }

    public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
        if (!level.isClientSide) {
            hasRedstoneSignal.set(this.level.hasNeighborSignal(this.getBlockPos()));
        }
    }


}
