package nuclearscience.common.tile;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.*;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.api.radiation.util.BlockPosVolume;
import nuclearscience.common.inventory.container.ContainerFalloutScrubber;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceTiles;

public class TileFalloutScrubber extends GenericTile {

    private static final int FLUID_USAGE_PER_TICK = 1;
    private static final int RANGE = 30;

    private static final double DISIPATION = 1.0;

    public final Property<Boolean> active = property(new Property<>(PropertyTypes.BOOLEAN, "active", false));
    private final Property<Boolean> hasRedstoneSignal = property(new Property(PropertyTypes.BOOLEAN, "redstonesignal", false));

    private final BlockPosVolume area;


    public TileFalloutScrubber(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_FALLOUTSCRUBBER.get(), worldPos, blockState);

        area = new BlockPosVolume(worldPos.offset(-RANGE, -RANGE, -RANGE), worldPos.offset(RANGE, RANGE, RANGE));

        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(Constants.FALLOUT_SCRUBBER_USAGE_PER_TICK * 20).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
        addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(2, 100, 100).setInputFluidTags(FluidTags.WATER, NuclearScienceTags.Fluids.DECONTAMINATION_FOAM).setInputDirections(BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.RIGHT));
        addComponent(new ComponentContainerProvider("container.falloutscrubber", this).createMenu((id, player) -> new ContainerFalloutScrubber(id, player, new SimpleContainer(), getCoordsArray())));
    }

    private void tickServer(ComponentTickable tickable) {

        if(hasRedstoneSignal.get()) {
            active.set(false);
            RadiationSystem.removeDisipation(getLevel(), area);
            return;
        }

        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

        if(electro.getJoulesStored() < Constants.FALLOUT_SCRUBBER_USAGE_PER_TICK) {
            active.set(false);
            RadiationSystem.removeDisipation(getLevel(), area);
            return;
        }

        ComponentFluidHandlerMulti multi = getComponent(IComponentType.FluidHandler);

        FluidTank[] tanks = multi.getInputTanks();

        if(tanks[0].isEmpty() || tanks[0].getFluidAmount() < FLUID_USAGE_PER_TICK || tanks[1].isEmpty() || tanks[1].getFluidAmount() < FLUID_USAGE_PER_TICK) {
            active.set(false);
            RadiationSystem.removeDisipation(getLevel(), area);
            return;
        }

        active.set(true);
        tanks[0].drain(FLUID_USAGE_PER_TICK, IFluidHandler.FluidAction.EXECUTE);
        tanks[1].drain(FLUID_USAGE_PER_TICK, IFluidHandler.FluidAction.EXECUTE);
        electro.setJoulesStored(electro.getJoulesStored() - Constants.FALLOUT_SCRUBBER_USAGE_PER_TICK);

        RadiationSystem.addDisipation(getLevel(), DISIPATION, area);

    }

    public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
        if (!level.isClientSide) {
            hasRedstoneSignal.set(this.level.hasNeighborSignal(this.getBlockPos()));
        }
    }
}
