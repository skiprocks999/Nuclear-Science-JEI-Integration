package nuclearscience.datagen.server.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import nuclearscience.datagen.server.recipe.custom.fluiditem2fluid.NuclearScienceChemicalMixerRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2gas.NuclearScienceNuclaerBoilerRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceChemicalExtractorRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceMSRFuelPreprocessorRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceRadioactiveProcessorRecipes;
import nuclearscience.datagen.server.recipe.custom.item2item.NuclearScienceFissionReactorRecipes;
import nuclearscience.datagen.server.recipe.custom.item2item.NuclearScienceFuelReprocessorRecipes;
import nuclearscience.datagen.server.recipe.vanilla.NuclearScienceCraftingTableRecipes;

public class NuclearScienceRecipeProvider extends RecipeProvider {

	public final List<AbstractRecipeGenerator> GENERATORS = new ArrayList<>();

	public NuclearScienceRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
		addRecipes();
	}

	public void addRecipes() {
		GENERATORS.add(new NuclearScienceCraftingTableRecipes());
		GENERATORS.add(new NuclearScienceChemicalMixerRecipes());
		GENERATORS.add(new NuclearScienceNuclaerBoilerRecipes());
		GENERATORS.add(new NuclearScienceChemicalExtractorRecipes());
		GENERATORS.add(new NuclearScienceMSRFuelPreprocessorRecipes());
		GENERATORS.add(new NuclearScienceRadioactiveProcessorRecipes());
		GENERATORS.add(new NuclearScienceFissionReactorRecipes());
		GENERATORS.add(new NuclearScienceFuelReprocessorRecipes());
	}

	@Override
	protected void buildRecipes(RecipeOutput output) {
		for (AbstractRecipeGenerator generator : GENERATORS) {
			generator.addRecipes(output);
		}
	}

}
