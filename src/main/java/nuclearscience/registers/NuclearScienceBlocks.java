package nuclearscience.registers;

import electrodynamics.api.registration.BulkDeferredHolder;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;
import nuclearscience.common.block.BlockControlRodAssembly;
import nuclearscience.common.block.BlockElectromagnet;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.BlockElectromagneticSwitch;
import nuclearscience.common.block.BlockFissionReactorCore;
import nuclearscience.common.block.BlockFuelReprocessor;
import nuclearscience.common.block.BlockFusionReactorCore;
import nuclearscience.common.block.BlockMSRFuelPreprocessor;
import nuclearscience.common.block.BlockMeltedReactor;
import nuclearscience.common.block.BlockMoltenSaltSupplier;
import nuclearscience.common.block.BlockPlasma;
import nuclearscience.common.block.BlockQuantumCapacitor;
import nuclearscience.common.block.BlockRadioactiveAir;
import nuclearscience.common.block.BlockRadioactiveProcessor;
import nuclearscience.common.block.BlockRadioactiveSoil;
import nuclearscience.common.block.BlockTeleporter;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.TileAtomicAssembler;
import nuclearscience.common.tile.TileChemicalExtractor;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.common.tile.TileSiren;
import nuclearscience.common.tile.TileSteamFunnel;
import nuclearscience.common.tile.msreactor.TileFreezePlug;
import nuclearscience.common.tile.msreactor.TileHeatExchanger;
import nuclearscience.common.tile.msreactor.TileMSRFuelPreProcessor;
import nuclearscience.common.tile.msreactor.TileMSReactorCore;

public class NuclearScienceBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, References.ID);

	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_GASCENTRIFUGE = BLOCKS.register("gascentrifuge", () -> new GenericMachineBlock(TileGasCentrifuge::new));
	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_NUCLEARBOILER = BLOCKS.register("nuclearboiler", () -> new GenericMachineBlock(TileNuclearBoiler::new));
	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_CHEMICALEXTRACTOR = BLOCKS.register("chemicalextractor", () -> new GenericMachineBlock(TileChemicalExtractor::new));
	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_RADIOISOTOPEGENERATOR = BLOCKS.register("radioisotopegenerator", () -> new GenericMachineBlock(TileRadioisotopeGenerator::new));
	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_MSRFREEZEPLUG = BLOCKS.register("freezeplug", () -> new GenericMachineBlock(TileFreezePlug::new));
	public static final DeferredHolder<Block, BlockTurbine> BLOCK_TURBINE = BLOCKS.register("turbine", () -> new BlockTurbine());
	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_STEAMFUNNEL = BLOCKS.register("steamfunnel", () -> new GenericMachineBlock(TileSteamFunnel::new));
	public static final DeferredHolder<Block, BlockFissionReactorCore> BLOCK_FISSIONREACTORCORE = BLOCKS.register("fissionreactorcore", () -> new BlockFissionReactorCore());
	public static final DeferredHolder<Block, BlockElectromagnet> BLOCK_ELECTROMAGNET = BLOCKS.register("electromagnet", () -> new BlockElectromagnet(Blocks.IRON_BLOCK.properties(), false));
	public static final DeferredHolder<Block, BlockElectromagnet> BLOCK_ELECTROMAGNETICGLASS = BLOCKS.register("electromagneticglass", () -> new BlockElectromagnet(Blocks.GLASS.properties(), true));
	public static final DeferredHolder<Block, BlockElectromagneticBooster> BLOCK_ELECTORMAGNETICBOOSTER = BLOCKS.register("electromagneticbooster", () -> new BlockElectromagneticBooster());
	public static final DeferredHolder<Block, BlockElectromagneticSwitch> BLOCK_ELECTROMAGNETICSWITCH = BLOCKS.register("electromagneticswitch", () -> new BlockElectromagneticSwitch());
	public static final DeferredHolder<Block, BlockFusionReactorCore> BLOCK_FUSIONREACTORCORE = BLOCKS.register("fusionreactorcore", () -> new BlockFusionReactorCore());
	public static final DeferredHolder<Block, BlockPlasma> BLOCK_PLASMA = BLOCKS.register("plasma", () -> new BlockPlasma());
	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_PARTICLEINJECTOR = BLOCKS.register("particleinjector", () -> new GenericMachineBlock(TileParticleInjector::new));
	public static final DeferredHolder<Block, BlockQuantumCapacitor> BLOCK_QUANTUMCAPACITOR = BLOCKS.register("quantumcapacitor", () -> new BlockQuantumCapacitor());
	public static final DeferredHolder<Block, BlockTeleporter> BLOCK_TELEPORTER = BLOCKS.register("teleporter", () -> new BlockTeleporter());
	public static final DeferredHolder<Block, BlockControlRodAssembly> BLOCK_CONTROLROD = BLOCKS.register("controlrodassembly", () -> new BlockControlRodAssembly());
	public static final DeferredHolder<Block, BlockFuelReprocessor> BLOCK_FUELREPROCESSOR = BLOCKS.register("fuelreprocessor", () -> new BlockFuelReprocessor());
	public static final DeferredHolder<Block, BlockRadioactiveProcessor> BLOCK_RADIOACTIVEPROCESSOR = BLOCKS.register("radioactiveprocessor", () -> new BlockRadioactiveProcessor());
	public static final DeferredHolder<Block, BlockMSRFuelPreprocessor> BLOCK_MSRFUELPREPROCESSOR = BLOCKS.register("msrfuelpreprocessor", () -> new BlockMSRFuelPreprocessor(TileMSRFuelPreProcessor::new));
	public static final DeferredHolder<Block, Block> BLOCK_LEAD = BLOCKS.register("blocklead", () -> new Block(Blocks.NETHERITE_BLOCK.properties().strength(5.0f, 3.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));
	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_MSREACTORCORE = BLOCKS.register("msreactorcore", () -> new GenericMachineBlock(TileMSReactorCore::new));
	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_HEATEXCHANGER = BLOCKS.register("heatexchanger", () -> new GenericMachineBlock(TileHeatExchanger::new));
	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_SIREN = BLOCKS.register("siren", () -> new GenericMachineBlock(TileSiren::new));
	public static final DeferredHolder<Block, GenericMachineBlock> BLOCK_ATOMICASSEMBLER = BLOCKS.register("atomicassembler", () -> new GenericMachineBlock(TileAtomicAssembler::new));
	public static final DeferredHolder<Block, BlockMoltenSaltSupplier> BLOCK_MOLTENSALTSUPPLIER = BLOCKS.register("moltensaltsupplier", () -> new BlockMoltenSaltSupplier());
	public static final BulkDeferredHolder<Block, BlockMoltenSaltPipe, SubtypeMoltenSaltPipe> BLOCKS_MOLTENSALTPIPE = new BulkDeferredHolder<>(SubtypeMoltenSaltPipe.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockMoltenSaltPipe(subtype)));
	public static final DeferredHolder<Block, BlockMeltedReactor> BLOCK_MELTEDREACTOR = BLOCKS.register("meltedreactor", () -> new BlockMeltedReactor());
	public static final DeferredHolder<Block, BlockRadioactiveAir> BLOCK_RADIOACTIVEAIR = BLOCKS.register("radioactiveair", () -> new BlockRadioactiveAir());
	public static final DeferredHolder<Block, BlockRadioactiveSoil> BLOCK_RADIOACTIVESOIL = BLOCKS.register("radioactivesoil", () -> new BlockRadioactiveSoil());


}
