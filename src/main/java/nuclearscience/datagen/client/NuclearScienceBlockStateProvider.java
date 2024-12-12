package nuclearscience.datagen.client;

import electrodynamics.datagen.client.ElectrodynamicsBlockStateProvider;
import electrodynamics.prefab.block.GenericEntityBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nuclearscience.References;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.facing.FacingDirection;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.registers.NuclearScienceBlocks;

public class NuclearScienceBlockStateProvider extends ElectrodynamicsBlockStateProvider {

	public NuclearScienceBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, exFileHelper, References.ID);
	}

	@Override
	protected void registerStatesAndModels() {

		simpleBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base), blockLoc("leadlinedblock"), true);
		doorBlock((DoorBlock) NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door), blockLoc("leadlineddoor_bottom"), blockLoc("leadlineddoor_top"));
		glassBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.glass), blockLoc("leadlinedglass"), true);
		trapdoorBlock((TrapDoorBlock) NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.trapdoor), blockLoc("leadlinedtrapdoor"), true);

		simpleColumnBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNET.get(), blockLoc("electromagnet"), blockLoc("electromagnettop"), true);
		glassBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGLASS, blockLoc("electromagneticglass"), true);
		simpleBlock(NuclearScienceBlocks.BLOCK_MSRFREEZEPLUG, existingBlock(NuclearScienceBlocks.BLOCK_MSRFREEZEPLUG), true);
		simpleBlockCustomRenderType(NuclearScienceBlocks.BLOCK_PLASMA, blockLoc("plasma"), ResourceLocation.parse("translucent"), true);
		airBlock(NuclearScienceBlocks.BLOCK_RADIOACTIVEAIR, "block/plasma", true);
		simpleBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil), blockLoc("irradiatedblocksoil"), true);
		simpleColumnBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood), modLoc("block/irradiatedblockpetrifiedwood"), modLoc("block/irradiatedblockpetrifiedwoodtop"), true);

		Block block = NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass);

		BlockModelBuilder builder = models().cube(name(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass)), modLoc("block/irradiatedblocksoil"), modLoc("block/irradiatedblockgrass"), modLoc("block/irradiatedblockgrassside"), modLoc("block/irradiatedblockgrassside"), modLoc("block/irradiatedblockgrassside"), modLoc("block/irradiatedblockgrassside")).texture("particle", modLoc("block/irradiatedblocksoil"));
		getVariantBuilder(block).partialState().setModels(new ConfiguredModel[]{new ConfiguredModel(builder)});
		blockItem(block, builder);

		simpleBlock(NuclearScienceBlocks.BLOCK_MELTEDREACTOR, existingBlock(NuclearScienceBlocks.BLOCK_MELTEDREACTOR), true);
		simpleBlock(NuclearScienceBlocks.BLOCK_RADIOISOTOPEGENERATOR, existingBlock(NuclearScienceBlocks.BLOCK_RADIOISOTOPEGENERATOR), true);
		simpleBlock(NuclearScienceBlocks.BLOCK_SIREN, blockLoc("siren"), true);
		simpleBlock(NuclearScienceBlocks.BLOCK_TURBINE, existingBlock(blockLoc("turbinecasing")), false);
		simpleColumnBlock(NuclearScienceBlocks.BLOCK_STEAMFUNNEL.get(), blockLoc("steamfunnelside"), blockLoc("steamfunneltop"), true);
		simpleBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH, existingBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH), true);

		horrRotatedBlock(NuclearScienceBlocks.BLOCK_ATOMICASSEMBLER, existingBlock(NuclearScienceBlocks.BLOCK_ATOMICASSEMBLER), false);
		horrRotatedBlock(NuclearScienceBlocks.BLOCK_CHEMICALEXTRACTOR, existingBlock(NuclearScienceBlocks.BLOCK_CHEMICALEXTRACTOR), true);
		horrRotatedBlock(NuclearScienceBlocks.BLOCK_CONTROLROD, existingBlock(NuclearScienceBlocks.BLOCK_CONTROLROD), 180, 0, false);
		rotatedLeftRightBlock(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(), existingBlock(blockLoc("electromagneticbooster")), existingBlock(blockLoc("electromagneticboosterleft")), existingBlock(blockLoc("electromagneticboosterright")), 90, true);
		horrRotatedLitBlock(NuclearScienceBlocks.BLOCK_FUELREPROCESSOR, existingBlock(NuclearScienceBlocks.BLOCK_FUELREPROCESSOR), existingBlock(blockLoc("fuelreprocessoron")), true);
		horrRotatedLitBlock(NuclearScienceBlocks.BLOCK_FUSIONREACTORCORE, existingBlock(NuclearScienceBlocks.BLOCK_FUSIONREACTORCORE), existingBlock(blockLoc("fusionreactorcoreon")), true);
		horrRotatedBlock(NuclearScienceBlocks.BLOCK_GASCENTRIFUGE, existingBlock(blockLoc("gascentrifugeoutline")), 180, 0, false);
		horrRotatedBlock(NuclearScienceBlocks.BLOCK_HEATEXCHANGER, existingBlock(NuclearScienceBlocks.BLOCK_HEATEXCHANGER), true);
		horrRotatedLitBlock(NuclearScienceBlocks.BLOCK_MOLTENSALTSUPPLIER, existingBlock(NuclearScienceBlocks.BLOCK_MOLTENSALTSUPPLIER), existingBlock(blockLoc("moltensaltsupplieron")), true);
		horrRotatedBlock(NuclearScienceBlocks.BLOCK_MSRFUELPREPROCESSOR, existingBlock(NuclearScienceBlocks.BLOCK_MSRFUELPREPROCESSOR), true);
		horrRotatedBlock(NuclearScienceBlocks.BLOCK_MSREACTORCORE, existingBlock(NuclearScienceBlocks.BLOCK_MSREACTORCORE), 180, 0, true);
		horrRotatedBlock(NuclearScienceBlocks.BLOCK_NUCLEARBOILER, existingBlock(NuclearScienceBlocks.BLOCK_NUCLEARBOILER), 180, 0, true);
		horrRotatedBlock(NuclearScienceBlocks.BLOCK_PARTICLEINJECTOR, existingBlock(NuclearScienceBlocks.BLOCK_PARTICLEINJECTOR), 180, 0, false);
		horrRotatedBlock(NuclearScienceBlocks.BLOCK_QUANTUMTUNNEL, existingBlock(NuclearScienceBlocks.BLOCK_QUANTUMTUNNEL), true);
		horrRotatedLitBlock(NuclearScienceBlocks.BLOCK_RADIOACTIVEPROCESSOR, existingBlock(NuclearScienceBlocks.BLOCK_RADIOACTIVEPROCESSOR), existingBlock(blockLoc("radioactiveprocessoron")), true);
		horrRotatedBlock(NuclearScienceBlocks.BLOCK_FISSIONREACTORCORE, existingBlock(NuclearScienceBlocks.BLOCK_FISSIONREACTORCORE), true);
		horrRotatedLitBlock(NuclearScienceBlocks.BLOCK_TELEPORTER, existingBlock(NuclearScienceBlocks.BLOCK_TELEPORTER), existingBlock(blockLoc("teleporteron")), true);

		simpleBlock(NuclearScienceBlocks.BLOCK_CHUNKLOADER.get(), existingBlock(NuclearScienceBlocks.BLOCK_CHUNKLOADER.get()), true);

		genPipes();

	}

	private void genPipes() {

		String parent = "parent/";
		String name = "block/pipe/";
		String texture = "pipe/";

		for (SubtypeMoltenSaltPipe pipe : SubtypeMoltenSaltPipe.values()) {
			wire(NuclearScienceBlocks.BLOCKS_MOLTENSALTPIPE.getValue(pipe), models().withExistingParent(name + pipe.tag() + "_none", modLoc(parent + "pipe_none")).texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture").renderType("cutout"), models().withExistingParent(name + pipe.tag() + "_side", modLoc(parent + "pipe_side")).texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture").renderType("cutout"), false);
		}

	}

	public ItemModelBuilder rotatedLeftRightBlock(Block block, ModelFile none, ModelFile left, ModelFile right, boolean registerItem) {
		return rotatedLeftRightBlock(block, none, left, right, 0, registerItem);
	}

	public ItemModelBuilder rotatedLeftRightBlock(Block block, ModelFile none, ModelFile left, ModelFile right, int rotationOffset, boolean registerItem) {
		getVariantBuilder(block).partialState().with(GenericEntityBlock.FACING, Direction.NORTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((270 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.EAST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((0 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((90 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((180 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.NORTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((270 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.EAST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((0 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((90 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((180 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.NORTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((270 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.EAST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((0 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((90 + rotationOffset) % 360).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((180 + rotationOffset) % 360).addModel();

		if (registerItem) {
			return blockItem(block, none);
		}
		return null;

	}

}
