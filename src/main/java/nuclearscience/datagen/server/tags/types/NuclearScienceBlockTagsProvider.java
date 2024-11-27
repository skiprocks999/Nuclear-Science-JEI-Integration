package nuclearscience.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceBlocks;

public class NuclearScienceBlockTagsProvider extends BlockTagsProvider {

    public NuclearScienceBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, References.ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                //
                NuclearScienceBlocks.BLOCK_ATOMICASSEMBLER.get(),
                //
                NuclearScienceBlocks.BLOCK_CHEMICALEXTRACTOR.get(),
                //
                NuclearScienceBlocks.BLOCK_CONTROLROD.get(),
                //
                NuclearScienceBlocks.BLOCK_ELECTROMAGNET.get(),
                //
                NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(),
                //
                NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGLASS.get(),
                //
                NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get(),
                //
                NuclearScienceBlocks.BLOCK_MSRFREEZEPLUG.get(),
                //
                NuclearScienceBlocks.BLOCK_FUELREPROCESSOR.get(),
                //
                NuclearScienceBlocks.BLOCK_FUSIONREACTORCORE.get(),
                //
                NuclearScienceBlocks.BLOCK_GASCENTRIFUGE.get(),
                //
                NuclearScienceBlocks.BLOCK_HEATEXCHANGER.get(),
                //
                NuclearScienceBlocks.BLOCK_LEAD.get(),
                //
                NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get(),
                //
                NuclearScienceBlocks.BLOCK_MOLTENSALTSUPPLIER.get(),
                //
                NuclearScienceBlocks.BLOCK_MSRFUELPREPROCESSOR.get(),
                //
                NuclearScienceBlocks.BLOCK_MSREACTORCORE.get(),
                //
                NuclearScienceBlocks.BLOCK_NUCLEARBOILER.get(),
                //
                NuclearScienceBlocks.BLOCK_PARTICLEINJECTOR.get(),
                //
                NuclearScienceBlocks.BLOCK_QUANTUMCAPACITOR.get(),
                //
                NuclearScienceBlocks.BLOCK_RADIOACTIVEPROCESSOR.get(),
                //
                NuclearScienceBlocks.BLOCK_RADIOISOTOPEGENERATOR.get(),
                //
                NuclearScienceBlocks.BLOCK_FISSIONREACTORCORE.get(),
                //
                NuclearScienceBlocks.BLOCK_SIREN.get(),
                //
                NuclearScienceBlocks.BLOCK_TELEPORTER.get(),
                //
                NuclearScienceBlocks.BLOCK_TURBINE.get());

        tag(BlockTags.NEEDS_STONE_TOOL).add(
                //
                NuclearScienceBlocks.BLOCK_ATOMICASSEMBLER.get(),
                //
                NuclearScienceBlocks.BLOCK_CHEMICALEXTRACTOR.get(),
                //
                NuclearScienceBlocks.BLOCK_CONTROLROD.get(),
                //
                NuclearScienceBlocks.BLOCK_ELECTROMAGNET.get(),
                //
                NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(),
                //
                NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGLASS.get(),
                //
                NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get(),
                //
                NuclearScienceBlocks.BLOCK_MSRFREEZEPLUG.get(),
                //
                NuclearScienceBlocks.BLOCK_FUELREPROCESSOR.get(),
                //
                NuclearScienceBlocks.BLOCK_FUSIONREACTORCORE.get(),
                //
                NuclearScienceBlocks.BLOCK_GASCENTRIFUGE.get(),
                //
                NuclearScienceBlocks.BLOCK_HEATEXCHANGER.get(),
                //
                NuclearScienceBlocks.BLOCK_LEAD.get(),
                //
                NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get(),
                //
                NuclearScienceBlocks.BLOCK_MOLTENSALTSUPPLIER.get(),
                //
                NuclearScienceBlocks.BLOCK_MSRFUELPREPROCESSOR.get(),
                //
                NuclearScienceBlocks.BLOCK_MSREACTORCORE.get(),
                //
                NuclearScienceBlocks.BLOCK_NUCLEARBOILER.get(),
                //
                NuclearScienceBlocks.BLOCK_PARTICLEINJECTOR.get(),
                //
                NuclearScienceBlocks.BLOCK_QUANTUMCAPACITOR.get(),
                //
                NuclearScienceBlocks.BLOCK_RADIOACTIVEPROCESSOR.get(),
                //
                NuclearScienceBlocks.BLOCK_RADIOISOTOPEGENERATOR.get(),
                //
                NuclearScienceBlocks.BLOCK_FISSIONREACTORCORE.get(),
                //
                NuclearScienceBlocks.BLOCK_SIREN.get(),
                //
                NuclearScienceBlocks.BLOCK_TELEPORTER.get(),
                //
                NuclearScienceBlocks.BLOCK_TURBINE.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(NuclearScienceBlocks.BLOCK_RADIOACTIVESOIL.get());

        tag(Tags.Blocks.NEEDS_WOOD_TOOL).add(NuclearScienceBlocks.BLOCK_RADIOACTIVESOIL.get());

    }

}
