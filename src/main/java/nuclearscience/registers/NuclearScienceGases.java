package nuclearscience.registers;

import electrodynamics.api.gas.Gas;
import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class NuclearScienceGases {

	public static final DeferredRegister<Gas> GASES = DeferredRegister.create(ElectrodynamicsGases.GAS_REGISTRY_KEY, References.ID);

	public static final DeferredHolder<Gas, Gas> URANIUM_HEXAFLUORIDE = GASES.register("uraniumhexafluoride", () -> new Gas(Holder.direct(Items.AIR), NuclearTextUtils.gas("uraniumhexafluoride")));

}
