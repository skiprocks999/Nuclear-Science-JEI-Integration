package nuclearscience.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nuclearscience.References;

public class NuclearScienceFluidTagsProvider extends FluidTagsProvider {

    public NuclearScienceFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, References.ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {


    }

}
