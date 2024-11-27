package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MenuType.MenuSupplier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerAtomicAssembler;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.inventory.container.ContainerMSRFuelPreProcessor;
import nuclearscience.common.inventory.container.ContainerMSRReactorCore;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.inventory.container.ContainerNuclearBoiler;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.inventory.container.ContainerQuantumCapacitor;
import nuclearscience.common.inventory.container.ContainerRadioactiveProcessor;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.inventory.container.ContainerReactorCore;

public class NuclearScienceMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, References.ID);

	public static final DeferredHolder<MenuType<?>, MenuType<ContainerGasCentrifuge>> CONTAINER_GASCENTRIFUGE = register("gascentrifuge", ContainerGasCentrifuge::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerNuclearBoiler>> CONTAINER_NUCLEARBOILER = register("nuclearboiler", ContainerNuclearBoiler::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerChemicalExtractor>> CONTAINER_CHEMICALEXTRACTOR = register("chemicalextractor", ContainerChemicalExtractor::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerRadioisotopeGenerator>> CONTAINER_RADIOISOTOPEGENERATOR = register("radioisotopegenerator", ContainerRadioisotopeGenerator::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerFreezePlug>> CONTAINER_FREEZEPLUG = register("freezeplug", ContainerFreezePlug::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerReactorCore>> CONTAINER_REACTORCORE = register("reactorcore", ContainerReactorCore::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerParticleInjector>> CONTAINER_PARTICLEINJECTOR = register("particleinjetor", ContainerParticleInjector::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerQuantumCapacitor>> CONTAINER_QUANTUMCAPACITOR = register("quantumcapacitor", ContainerQuantumCapacitor::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerRadioactiveProcessor>> CONTAINER_RADIOACTIVEPROCESSOR = register("radioactiveprocessor", ContainerRadioactiveProcessor::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerMSRFuelPreProcessor>> CONTAINER_MSRFUELPREPROCESSOR = register("msrfuelpreprocessor", ContainerMSRFuelPreProcessor::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerMSRReactorCore>> CONTAINER_MSRREACTORCORE = register("msrreactorcore", ContainerMSRReactorCore::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerMoltenSaltSupplier>> CONTAINER_MOLTENSALTSUPPLIER = register("moltensaltsupplier", ContainerMoltenSaltSupplier::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerAtomicAssembler>> CONTAINER_ATOMICASSEMBLER = register("atomicassembler", ContainerAtomicAssembler::new);

	private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(String id, MenuSupplier<T> supplier) {
		return MENU_TYPES.register(id, () -> new MenuType<>(supplier, FeatureFlags.VANILLA_SET));
	}

}
