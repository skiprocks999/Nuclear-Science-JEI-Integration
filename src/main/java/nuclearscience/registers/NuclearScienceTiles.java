package nuclearscience.registers;

import com.google.common.collect.Sets;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.tile.*;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.fission.TileMeltedReactor;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.common.tile.reactor.fusion.TilePlasma;
import nuclearscience.common.tile.reactor.moltensalt.TileFreezePlug;
import nuclearscience.common.tile.reactor.moltensalt.TileHeatExchanger;
import nuclearscience.common.tile.reactor.moltensalt.TileMSRFuelPreProcessor;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.common.tile.reactor.moltensalt.TileMoltenSaltSupplier;
import nuclearscience.common.tile.reactor.moltensalt.TileMoltenSaltPipe;

public class NuclearScienceTiles {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, References.ID);

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileGasCentrifuge>> TILE_GASCENTRIFUGE = BLOCK_ENTITY_TYPES.register("gascentrifuge", () -> new BlockEntityType<>(TileGasCentrifuge::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_GASCENTRIFUGE.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileNuclearBoiler>> TILE_CHEMICALBOILER = BLOCK_ENTITY_TYPES.register("nuclearboiler", () -> new BlockEntityType<>(TileNuclearBoiler::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_NUCLEARBOILER.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileChemicalExtractor>> TILE_CHEMICALEXTRACTOR = BLOCK_ENTITY_TYPES.register("chemicalextractor", () -> new BlockEntityType<>(TileChemicalExtractor::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_CHEMICALEXTRACTOR.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileRadioisotopeGenerator>> TILE_RADIOISOTOPEGENERATOR = BLOCK_ENTITY_TYPES.register("radioisotopegenerator", () -> new BlockEntityType<>(TileRadioisotopeGenerator::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_RADIOISOTOPEGENERATOR.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMoltenSaltSupplier>> TILE_MOLTENSALTSUPPLIER = BLOCK_ENTITY_TYPES.register("moltensaltsupplier", () -> new BlockEntityType<>(TileMoltenSaltSupplier::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_MOLTENSALTSUPPLIER.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFreezePlug>> TILE_FREEZEPLUG = BLOCK_ENTITY_TYPES.register("freezeplug", () -> new BlockEntityType<>(TileFreezePlug::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_MSRFREEZEPLUG.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileTurbine>> TILE_TURBINE = BLOCK_ENTITY_TYPES.register("turbine", () -> new BlockEntityType<>(TileTurbine::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_TURBINE.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFissionReactorCore>> TILE_REACTORCORE = BLOCK_ENTITY_TYPES.register("reactorcore", () -> new BlockEntityType<>(TileFissionReactorCore::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_FISSIONREACTORCORE.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFusionReactorCore>> TILE_FUSIONREACTORCORE = BLOCK_ENTITY_TYPES.register("fusionreactorcore", () -> new BlockEntityType<>(TileFusionReactorCore::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_FUSIONREACTORCORE.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileParticleInjector>> TILE_PARTICLEINJECTOR = BLOCK_ENTITY_TYPES.register("particleinjector", () -> new BlockEntityType<>(TileParticleInjector::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_PARTICLEINJECTOR.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileElectromagneticSwitch>> TILE_ELECTROMAGNETICSWITCH = BLOCK_ENTITY_TYPES.register("electromagneticswitch", () -> new BlockEntityType<>(TileElectromagneticSwitch::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TilePlasma>> TILE_PLASMA = BLOCK_ENTITY_TYPES.register("plasma", () -> new BlockEntityType<>(TilePlasma::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_PLASMA.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMeltedReactor>> TILE_MELTEDREACTOR = BLOCK_ENTITY_TYPES.register("meltedreactor", () -> new BlockEntityType<>(TileMeltedReactor::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileQuantumTunnel>> TILE_QUANTUMCAPACITOR = BLOCK_ENTITY_TYPES.register("quantumcapacitor", () -> new BlockEntityType<>(TileQuantumTunnel::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_QUANTUMTUNNEL.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileFuelReprocessor>> TILE_FUELREPROCESSOR = BLOCK_ENTITY_TYPES.register("fuelreprocessor", () -> new BlockEntityType<>(TileFuelReprocessor::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_FUELREPROCESSOR.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileRadioactiveProcessor>> TILE_RADIOACTIVEPROCESSOR = BLOCK_ENTITY_TYPES.register("radioactiveprocessor", () -> new BlockEntityType<>(TileRadioactiveProcessor::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_RADIOACTIVEPROCESSOR.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMSRFuelPreProcessor>> TILE_MSRFUELPREPROCESSOR = BLOCK_ENTITY_TYPES.register("msrfuelpreprocessor", () -> new BlockEntityType<>(TileMSRFuelPreProcessor::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_MSRFUELPREPROCESSOR.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMSReactorCore>> TILE_MSRREACTORCORE = BLOCK_ENTITY_TYPES.register("msrreactorcore", () -> new BlockEntityType<>(TileMSReactorCore::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_MSREACTORCORE.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileHeatExchanger>> TILE_HEATEXCHANGER = BLOCK_ENTITY_TYPES.register("heatexchanger", () -> new BlockEntityType<>(TileHeatExchanger::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_HEATEXCHANGER.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileTeleporter>> TILE_TELEPORTER = BLOCK_ENTITY_TYPES.register("teleporter", () -> new BlockEntityType<>(TileTeleporter::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_TELEPORTER.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileControlRod.TileFissionControlRod>> TILE_FISSIONCONTROLROD = BLOCK_ENTITY_TYPES.register("fissioncontrolrod", () -> new BlockEntityType<>(TileControlRod.TileFissionControlRod::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_FISSIONCONTROLROD.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileControlRod.TileMSControlRod>> TILE_MSCONTROLROD = BLOCK_ENTITY_TYPES.register("mscontrolrod", () -> new BlockEntityType<>(TileControlRod.TileMSControlRod::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_MSCONTROLROD.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMoltenSaltPipe>> TILE_MOLTENSALTPIPE = BLOCK_ENTITY_TYPES.register("moltensaltpipegenerictile", () -> new BlockEntityType<>(TileMoltenSaltPipe::new, BlockMoltenSaltPipe.PIPESET, null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileSiren>> TILE_SIREN = BLOCK_ENTITY_TYPES.register("siren", () -> new BlockEntityType<>(TileSiren::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_SIREN.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileAtomicAssembler>> TILE_ATOMICASSEMBLER = BLOCK_ENTITY_TYPES.register("atomicassembler", () -> new BlockEntityType<>(TileAtomicAssembler::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_ATOMICASSEMBLER.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileChunkloader>> TILE_CHUNKLOADER = BLOCK_ENTITY_TYPES.register("chunkloader", () -> new BlockEntityType<>(TileChunkloader::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_CHUNKLOADER.get()), null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileSteamFunnel>> TILE_STEAMFUNNEL = BLOCK_ENTITY_TYPES.register("steamfunnel", () -> new BlockEntityType<>(TileSteamFunnel::new, Sets.newHashSet(NuclearScienceBlocks.BLOCK_STEAMFUNNEL.get()), null));

}
