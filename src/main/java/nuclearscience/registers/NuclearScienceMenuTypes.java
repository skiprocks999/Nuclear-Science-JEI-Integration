package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MenuType.MenuSupplier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;
import nuclearscience.common.inventory.container.*;

public class NuclearScienceMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, References.ID);

	public static final DeferredHolder<MenuType<?>, MenuType<ContainerGasCentrifuge>> CONTAINER_GASCENTRIFUGE = register("gascentrifuge", ContainerGasCentrifuge::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerNuclearBoiler>> CONTAINER_NUCLEARBOILER = register("nuclearboiler", ContainerNuclearBoiler::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerChemicalExtractor>> CONTAINER_CHEMICALEXTRACTOR = register("chemicalextractor", ContainerChemicalExtractor::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerRadioisotopeGenerator>> CONTAINER_RADIOISOTOPEGENERATOR = register("radioisotopegenerator", ContainerRadioisotopeGenerator::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerFreezePlug>> CONTAINER_FREEZEPLUG = register("freezeplug", ContainerFreezePlug::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerFissionReactorCore>> CONTAINER_REACTORCORE = register("reactorcore", ContainerFissionReactorCore::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerParticleInjector>> CONTAINER_PARTICLEINJECTOR = register("particleinjetor", ContainerParticleInjector::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerQuantumTunnel>> CONTAINER_QUANTUMTUNNEL = register("quantumcapacitor", ContainerQuantumTunnel::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerRadioactiveProcessor>> CONTAINER_RADIOACTIVEPROCESSOR = register("radioactiveprocessor", ContainerRadioactiveProcessor::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerMSRFuelPreProcessor>> CONTAINER_MSRFUELPREPROCESSOR = register("msrfuelpreprocessor", ContainerMSRFuelPreProcessor::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerMSReactorCore>> CONTAINER_MSRREACTORCORE = register("msrreactorcore", ContainerMSReactorCore::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerMoltenSaltSupplier>> CONTAINER_MOLTENSALTSUPPLIER = register("moltensaltsupplier", ContainerMoltenSaltSupplier::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerAtomicAssembler>> CONTAINER_ATOMICASSEMBLER = register("atomicassembler", ContainerAtomicAssembler::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerTeleporter>> CONTAINER_TELEPORTER = register("teleporter", ContainerTeleporter::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerCloudChamber>> CONTAINER_CLOUDCHAMBER = register("cloudchamber", ContainerCloudChamber::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerFalloutScrubber>> CONTAINER_FALLOUTSCRUBBER = register("falloutscrubber", ContainerFalloutScrubber::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerSupplyModule>> CONTAINER_SUPPLYMODULE = register("supplymodule", ContainerSupplyModule::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerControlRodModule>> CONTAINER_CONTROLRODMODULE = register("controlrodmodule", ContainerControlRodModule::new);
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerMonitorModule>> CONTAINER_MONITORMODULE = register("monitormodule", ContainerMonitorModule::new);

	private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(String id, MenuSupplier<T> supplier) {
		return MENU_TYPES.register(id, () -> new MenuType<>(supplier, FeatureFlags.VANILLA_SET));
	}

}
