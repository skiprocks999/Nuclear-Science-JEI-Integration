package nuclearscience.registers;

import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import nuclearscience.References;

public class NuclearScienceFluidTypes {
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, References.ID);

	public static final DeferredHolder<FluidType, FluidType> FLUID_TYPE_IODINESOLUTION = FLUID_TYPES.register("iodinesolution", () -> NuclearScienceFluids.FLUID_IODINESOLUTION.get().getFluidType());

}
