package nuclearscience.datagen.client;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.datagen.client.ElectrodynamicsBlockStateProvider;
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
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
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
        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.freezeplug), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.freezeplug)), true);
        simpleBlockCustomRenderType(NuclearScienceBlocks.BLOCK_PLASMA, blockLoc("plasma"), ResourceLocation.parse("translucent"), true);
        airBlock(NuclearScienceBlocks.BLOCK_RADIOACTIVEAIR, "block/plasma", true);
        simpleBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil), blockLoc("irradiatedblocksoil"), true);
        simpleColumnBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood), modLoc("block/irradiatedblockpetrifiedwood"), modLoc("block/irradiatedblockpetrifiedwoodtop"), true);

        Block block = NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass);
        BlockModelBuilder builder = models().cube(
                //
                name(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass)),
                //
                modLoc("block/irradiatedblocksoil"),
                //
                modLoc("block/irradiatedblockgrass"),
                //
                modLoc("block/irradiatedblockgrassside"),
                //
                modLoc("block/irradiatedblockgrassside"),
                //
                modLoc("block/irradiatedblockgrassside"),
                //
                modLoc("block/irradiatedblockgrassside")
                //
        ).texture("particle", modLoc("block/irradiatedblocksoil"));
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel[]{new ConfiguredModel(builder)});
        blockItem(block, builder);

        simpleBlock(NuclearScienceBlocks.BLOCK_MELTEDREACTOR, existingBlock(NuclearScienceBlocks.BLOCK_MELTEDREACTOR), true);
        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioisotopegenerator), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioisotopegenerator)), true);
        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.siren), blockLoc("siren"), true);
        simpleBlock(NuclearScienceBlocks.BLOCK_TURBINE, existingBlock(blockLoc("turbinecasing")), false);
        simpleColumnBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.steamfunnel), blockLoc("steamfunnelside"), blockLoc("steamfunneltop"), true);
        simpleBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH, existingBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH), true);

        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler)), false);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chemicalextractor), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chemicalextractor)), true);
        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod)), false);
        rotatedLeftRightBlock(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(), existingBlock(blockLoc("electromagneticbooster")), existingBlock(blockLoc("electromagneticboosterleft")), existingBlock(blockLoc("electromagneticboosterright")), 90, true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fuelreprocessor), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fuelreprocessor)), existingBlock(blockLoc("fuelreprocessoron")), true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore)), existingBlock(blockLoc("fusionreactorcoreon")), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge), existingBlock(blockLoc("gascentrifugeoutline")), 180, 0, false);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.heatexchanger), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.heatexchanger)), true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.moltensaltsupplier), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.moltensaltsupplier)), existingBlock(blockLoc("moltensaltsupplieron")), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msrfuelpreprocessor), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msrfuelpreprocessor)), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore)), 180, 0, true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.nuclearboiler), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.nuclearboiler)), 180, 0, true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector)), 180, 0, false);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.quantumcapacitor), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.quantumcapacitor)), true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioactiveprocessor), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioactiveprocessor)), existingBlock(blockLoc("radioactiveprocessoron")), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore)), true);
        horrRotatedLitBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter)), existingBlock(blockLoc("teleporteron")), true);

        simpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chunkloader), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chunkloader)), true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod)), 180, 0, false);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber), existingBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber)), 0, 0, true);
        horrRotatedBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.falloutscrubber), existingBlock(blockLoc("falloutscrubberframe")), false);

        genPipes();

    }

    private void genPipes() {

        String parent = "parent/";
        String name = "block/pipe/";
        String texture = "pipe/";

        for (SubtypeMoltenSaltPipe pipe : SubtypeMoltenSaltPipe.values()) {
            wire(
                    //
                    NuclearScienceBlocks.BLOCKS_MOLTENSALTPIPE.getValue(pipe),
                    //
                    models().withExistingParent(name + pipe.tag() + "_none", modLoc(parent + "pipe_none")).texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture").renderType("cutout"),
                    //
                    models().withExistingParent(name + pipe.tag() + "_side", modLoc(parent + "pipe_side")).texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture").renderType("cutout"),
                    //
                    false);
        }

    }

    public ItemModelBuilder rotatedLeftRightBlock(Block block, ModelFile none, ModelFile left, ModelFile right, boolean registerItem) {
        return rotatedLeftRightBlock(block, none, left, right, 0, registerItem);
    }

    public ItemModelBuilder rotatedLeftRightBlock(Block block, ModelFile none, ModelFile left, ModelFile right, int rotationOffset, boolean registerItem) {
        getVariantBuilder(block)
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.NORTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((270 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.EAST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((0 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.SOUTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((90 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.WEST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.NONE).modelForState().modelFile(none).rotationY((180 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.NORTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((270 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.EAST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((0 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.SOUTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((90 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.WEST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.LEFT).modelForState().modelFile(left).rotationY((180 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.NORTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((270 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.EAST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((0 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.SOUTH).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((90 + rotationOffset) % 360).addModel()
                //
                .partialState().with(ElectrodynamicsBlockStates.FACING, Direction.WEST).with(BlockElectromagneticBooster.FACINGDIRECTION, FacingDirection.RIGHT).modelForState().modelFile(right).rotationY((180 + rotationOffset) % 360).addModel();

        if (registerItem) {
            return blockItem(block, none);
        }
        return null;

    }

}
