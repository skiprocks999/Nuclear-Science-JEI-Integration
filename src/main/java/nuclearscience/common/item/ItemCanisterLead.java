package nuclearscience.common.item;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.fluid.IRadioactiveFluid;
import nuclearscience.registers.NuclearScienceItems;

public class ItemCanisterLead extends ItemCanister {

    public static final double RAD_RANGE = 10.0;
    public static final double RAD_STRENGTH = 4.0;

    public static List<ResourceLocation> TAG_NAMES = new ArrayList<>();

    public ItemCanisterLead(Properties oroperties, Holder<CreativeModeTab> creativeTab) {
        super(oroperties, creativeTab);
        // The regular canister now emits radiation if it has radioactive fluids in it
        INVENTORY_TICK_CONSUMERS.add((stack, world, entity, slot, isSelected) -> {

            if (ItemUtils.testItems(stack.getItem(), NuclearScienceItems.ITEM_CANISTERLEAD.get())) {
                return;
            }

            IFluidHandlerItem cap = stack.getCapability(Capabilities.FluidHandler.ITEM);

            if (cap == null) {
                return;
            }

            FluidStack fluidStack = cap.getFluidInTank(0);

            if (fluidStack.getFluid() instanceof IRadioactiveFluid) {

                double radiationMultiplier =
                        (double) fluidStack.getAmount() / (double) cap.getTankCapacity(0);

                RadiationSystem.emitRadiationFromLocation(world,
                        new Location(entity.getX(), entity.getY(), entity.getZ()),
                        radiationMultiplier * RAD_RANGE, radiationMultiplier * RAD_STRENGTH);

            }

        });
    }

}
