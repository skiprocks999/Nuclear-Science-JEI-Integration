package nuclearscience.registers;

import com.google.common.collect.Sets;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.connect.BlockReactorLogisticsCable;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.tile.TileAtomicAssembler;
import nuclearscience.common.tile.TileChemicalExtractor;
import nuclearscience.common.tile.TileChunkloader;
import nuclearscience.common.tile.TileCloudChamber;
import nuclearscience.common.tile.TileFalloutScrubber;
import nuclearscience.common.tile.TileFuelReprocessor;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.common.tile.TileRadioactiveProcessor;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.common.tile.TileSiren;
import nuclearscience.common.tile.TileSteamFunnel;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.common.tile.TileTurbine;
import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;
import nuclearscience.common.tile.accelerator.TileParticleInjector;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.fission.TileMeltedReactor;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.common.tile.reactor.fusion.TilePlasma;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileControlRodModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileController;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileMonitorModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileSupplyModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileThermometerModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFissionInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFusionInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileMSInterface;
import nuclearscience.common.tile.reactor.moltensalt.TileFreezePlug;
import nuclearscience.common.tile.reactor.moltensalt.TileHeatExchanger;
import nuclearscience.common.tile.reactor.moltensalt.TileMSRFuelPreProcessor;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.common.tile.reactor.moltensalt.TileMoltenSaltPipe;
import nuclearscience.common.tile.reactor.moltensalt.TileMoltenSaltSupplier;

public class NuclearScienceTiles {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, References.ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileGasCentrifuge>> TILE_GASCENTRIFUGE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.gascentrifuge.tag(), () -> new BlockEntityType<>(TileGasCentrifuge::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileNuclearBoiler>> TILE_CHEMICALBOILER = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.nuclearboiler.tag(), () -> new BlockEntityType<>(TileNuclearBoiler::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.nuclearboiler)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileChemicalExtractor>> TILE_CHEMICALEXTRACTOR = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.chemicalextractor.tag(), () -> new BlockEntityType<>(TileChemicalExtractor::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chemicalextractor)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileRadioisotopeGenerator>> TILE_RADIOISOTOPEGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.radioisotopegenerator.tag(), () -> new BlockEntityType<>(TileRadioisotopeGenerator::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioisotopegenerator)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMoltenSaltSupplier>> TILE_MOLTENSALTSUPPLIER = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.moltensaltsupplier.tag(), () -> new BlockEntityType<>(TileMoltenSaltSupplier::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.moltensaltsupplier)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFreezePlug>> TILE_FREEZEPLUG = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.freezeplug.tag(), () -> new BlockEntityType<>(TileFreezePlug::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.freezeplug)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileTurbine>> TILE_TURBINE = BLOCK_ENTITY_TYPES.register("turbine", () -> new BlockEntityType<>(TileTurbine::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_TURBINE.get()), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFissionReactorCore>> TILE_REACTORCORE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.fissionreactorcore.tag(), () -> new BlockEntityType<>(TileFissionReactorCore::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFusionReactorCore>> TILE_FUSIONREACTORCORE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.fusionreactorcore.tag(), () -> new BlockEntityType<>(TileFusionReactorCore::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileParticleInjector>> TILE_PARTICLEINJECTOR = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.particleinjector.tag(), () -> new BlockEntityType<>(TileParticleInjector::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileElectromagneticGateway>> TILE_ELECTROMAGNETICGATEWAY = BLOCK_ENTITY_TYPES.register("electromagneticgateway", () -> new BlockEntityType<>(TileElectromagneticGateway::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGATEWAY.get()), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TilePlasma>> TILE_PLASMA = BLOCK_ENTITY_TYPES.register("plasma", () -> new BlockEntityType<>(TilePlasma::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_PLASMA.get()), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMeltedReactor>> TILE_MELTEDREACTOR = BLOCK_ENTITY_TYPES.register("meltedreactor", () -> new BlockEntityType<>(TileMeltedReactor::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get()), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileQuantumTunnel>> TILE_QUANTUMCAPACITOR = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.quantumcapacitor.tag(), () -> new BlockEntityType<>(TileQuantumTunnel::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.quantumcapacitor)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFuelReprocessor>> TILE_FUELREPROCESSOR = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.fuelreprocessor.tag(), () -> new BlockEntityType<>(TileFuelReprocessor::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fuelreprocessor)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileRadioactiveProcessor>> TILE_RADIOACTIVEPROCESSOR = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.radioactiveprocessor.tag(), () -> new BlockEntityType<>(TileRadioactiveProcessor::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioactiveprocessor)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMSRFuelPreProcessor>> TILE_MSRFUELPREPROCESSOR = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.msrfuelpreprocessor.tag(), () -> new BlockEntityType<>(TileMSRFuelPreProcessor::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msrfuelpreprocessor)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMSReactorCore>> TILE_MSRREACTORCORE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.msreactorcore.tag(), () -> new BlockEntityType<>(TileMSReactorCore::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileHeatExchanger>> TILE_HEATEXCHANGER = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.heatexchanger.tag(), () -> new BlockEntityType<>(TileHeatExchanger::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.heatexchanger)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileTeleporter>> TILE_TELEPORTER = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.teleporter.tag(), () -> new BlockEntityType<>(TileTeleporter::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileControlRod.TileFissionControlRod>> TILE_FISSIONCONTROLROD = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.fissioncontrolrod.tag(), () -> new BlockEntityType<>(TileControlRod.TileFissionControlRod::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileControlRod.TileMSControlRod>> TILE_MSCONTROLROD = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.mscontrolrod.tag(), () -> new BlockEntityType<>(TileControlRod.TileMSControlRod::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMoltenSaltPipe>> TILE_MOLTENSALTPIPE = BLOCK_ENTITY_TYPES.register("moltensaltpipegenerictile", () -> new BlockEntityType<>(TileMoltenSaltPipe::new, BlockMoltenSaltPipe.PIPESET, null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileSiren>> TILE_SIREN = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.siren.tag(), () -> new BlockEntityType<>(TileSiren::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.siren)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileAtomicAssembler>> TILE_ATOMICASSEMBLER = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.atomicassembler.tag(), () -> new BlockEntityType<>(TileAtomicAssembler::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileChunkloader>> TILE_CHUNKLOADER = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.chunkloader.tag(), () -> new BlockEntityType<>(TileChunkloader::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chunkloader)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileCloudChamber>> TILE_CLOUDCHAMBER = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.cloudchamber.tag(), () -> new BlockEntityType<>(TileCloudChamber::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileSteamFunnel>> TILE_STEAMFUNNEL = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.steamfunnel.tag(), () -> new BlockEntityType<>(TileSteamFunnel::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.steamfunnel)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFalloutScrubber>> TILE_FALLOUTSCRUBBER = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.falloutscrubber.tag(), () -> new BlockEntityType<>(TileFalloutScrubber::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.falloutscrubber)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileReactorLogisticsCable>> TILE_REACTORLOGISTICSCABLE = BLOCK_ENTITY_TYPES.register("reactorlogisticscable", () -> new BlockEntityType<>(TileReactorLogisticsCable::new, BlockReactorLogisticsCable.PIPESET, null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileController>> TILE_LOGISTICSCONTROLLER = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.logisticscontroller.tag(), () -> new BlockEntityType<>(TileController::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFissionInterface>> TILE_FISSIONINTERFACE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.fissioninterface.tag(), () -> new BlockEntityType<>(TileFissionInterface::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMSInterface>> TILE_MSINTERFACE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.msinterface.tag(), () -> new BlockEntityType<>(TileMSInterface::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFusionInterface>> TILE_FUSIONINTERFACE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.fusioninterface.tag(), () -> new BlockEntityType<>(TileFusionInterface::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusioninterface)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileControlRodModule>> TILE_CONTROLRODMODULE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.controlrodmodule.tag(), () -> new BlockEntityType<>(TileControlRodModule::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.controlrodmodule)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileSupplyModule>> TILE_SUPPLYMODULE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.supplymodule.tag(), () -> new BlockEntityType<>(TileSupplyModule::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.supplymodule)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMonitorModule>> TILE_MONITORMODULE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.monitormodule.tag(), () -> new BlockEntityType<>(TileMonitorModule::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.monitormodule)), null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileThermometerModule>> TILE_THERMOMETERMODULE = BLOCK_ENTITY_TYPES.register(SubtypeNuclearMachine.thermometermodule.tag(), () -> new BlockEntityType<>(TileThermometerModule::new, Sets.newHashSet(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.thermometermodule)), null));

}
