package nuclearscience.datagen.server;

import java.util.List;

import electrodynamics.datagen.server.ElectrodynamicsLootTablesProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.registers.NuclearScienceTiles;
import nuclearscience.registers.NuclearScienceBlocks;

public class NuclearScienceLootTablesProvider extends ElectrodynamicsLootTablesProvider {

	public NuclearScienceLootTablesProvider(HolderLookup.Provider provider) {
		super(References.ID, provider);
	}

	@Override
	protected void generate() {

		addMachineTable(NuclearScienceBlocks.BLOCK_ATOMICASSEMBLER.get(), NuclearScienceTiles.TILE_ATOMICASSEMBLER, true, false, false, true, false);
		addMachineTable(NuclearScienceBlocks.BLOCK_CHEMICALEXTRACTOR.get(), NuclearScienceTiles.TILE_CHEMICALEXTRACTOR, true, true, false, true, false);
		addSimpleBlock(NuclearScienceBlocks.BLOCK_CONTROLROD.get());
		addSimpleBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNET.get());
		addSimpleBlock(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get());
		addSimpleBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGLASS.get());
		addSimpleBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get());
		addMachineTable(NuclearScienceBlocks.BLOCK_MSRFREEZEPLUG.get(), NuclearScienceTiles.TILE_FREEZEPLUG, true, false, false, false, false);
		addMachineTable(NuclearScienceBlocks.BLOCK_FUELREPROCESSOR.get(), NuclearScienceTiles.TILE_FUELREPROCESSOR, true, false, false, true, false);
		addSimpleBlock(NuclearScienceBlocks.BLOCK_FUSIONREACTORCORE.get());
		addMachineTable(NuclearScienceBlocks.BLOCK_GASCENTRIFUGE.get(), NuclearScienceTiles.TILE_GASCENTRIFUGE, true, true, false, true, false);
		addSimpleBlock(NuclearScienceBlocks.BLOCK_HEATEXCHANGER.get());
		addSimpleBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base));
		addSimpleBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door));
		addSimpleBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.trapdoor));
		addSimpleBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.glass));
		addSimpleBlock(NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get());
		addMachineTable(NuclearScienceBlocks.BLOCK_MOLTENSALTSUPPLIER.get(), NuclearScienceTiles.TILE_MOLTENSALTSUPPLIER, true, false, false, false, false);
		addMachineTable(NuclearScienceBlocks.BLOCK_MSRFUELPREPROCESSOR.get(), NuclearScienceTiles.TILE_MSRFUELPREPROCESSOR, true, true, false, true, false);
		addSimpleBlock(NuclearScienceBlocks.BLOCK_MSREACTORCORE.get());
		addMachineTable(NuclearScienceBlocks.BLOCK_NUCLEARBOILER.get(), NuclearScienceTiles.TILE_CHEMICALBOILER, true, true, false, true, false);
		addMachineTable(NuclearScienceBlocks.BLOCK_PARTICLEINJECTOR.get(), NuclearScienceTiles.TILE_PARTICLEINJECTOR, true, false, false, true, false);
		addMachineTable(NuclearScienceBlocks.BLOCK_QUANTUMTUNNEL.get(), NuclearScienceTiles.TILE_QUANTUMCAPACITOR, false, false, false, true, false);
		addMachineTable(NuclearScienceBlocks.BLOCK_RADIOACTIVEPROCESSOR.get(), NuclearScienceTiles.TILE_RADIOACTIVEPROCESSOR, true, true, false, false, false);
		addSimpleBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil));
		addSimpleBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood));
		add(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass), createSilkTouchOnlyTable(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass)));
		addMachineTable(NuclearScienceBlocks.BLOCK_RADIOISOTOPEGENERATOR.get(), NuclearScienceTiles.TILE_RADIOISOTOPEGENERATOR, true, false, false, false, false);
		addMachineTable(NuclearScienceBlocks.BLOCK_FISSIONREACTORCORE.get(), NuclearScienceTiles.TILE_REACTORCORE, true, false, false, false, false);
		addSimpleBlock(NuclearScienceBlocks.BLOCK_SIREN.get());
		addMachineTable(NuclearScienceBlocks.BLOCK_TELEPORTER.get(), NuclearScienceTiles.TILE_TELEPORTER, false, false, false, true, false);
		addSimpleBlock(NuclearScienceBlocks.BLOCK_TURBINE.get());
		addSimpleBlock(NuclearScienceBlocks.BLOCKS_MOLTENSALTPIPE.getValue(SubtypeMoltenSaltPipe.vanadiumsteelceramic));
		addSimpleBlock(NuclearScienceBlocks.BLOCK_STEAMFUNNEL.get());

	}

	@Override
	public List<Block> getExcludedBlocks() {
		return List.of(NuclearScienceBlocks.BLOCK_PLASMA.get(), NuclearScienceBlocks.BLOCK_RADIOACTIVEAIR.get());
	}

}
