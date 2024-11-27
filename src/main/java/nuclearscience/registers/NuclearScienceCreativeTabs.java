package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class NuclearScienceCreativeTabs {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, References.ID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder().title(NuclearTextUtils.creativeTab("main")).icon(() -> new ItemStack(NuclearScienceBlocks.BLOCK_GASCENTRIFUGE.get())).build());

}
