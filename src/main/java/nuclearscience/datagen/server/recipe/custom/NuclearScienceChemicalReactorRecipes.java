package nuclearscience.datagen.server.recipe.custom;

import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.server.recipe.types.custom.ElectrodynamicsChemicalReactorRecipes;
import electrodynamics.datagen.utils.recipe.builders.ElectrodynamicsRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceFluids;

public class NuclearScienceChemicalReactorRecipes extends ElectrodynamicsChemicalReactorRecipes {

    public NuclearScienceChemicalReactorRecipes(String modID) {
        super(modID);
    }

    public NuclearScienceChemicalReactorRecipes() {
        this(References.ID);
    }

    @Override
    public void addRecipes(RecipeOutput output) {

        newRecipe(0, 200, 1000, "methanol", modID)
                //
                .setFluidOutput(new FluidStack(NuclearScienceFluids.FLUID_METHANOL, 100))
                //
                .addGasTagInput(ElectrodynamicsTags.Gases.CARBON_DIOXIDE, new ElectrodynamicsRecipeBuilder.GasIngWrapper(200, 525, 128))
                //
                .addGasTagInput(ElectrodynamicsTags.Gases.HYDROGEN, new ElectrodynamicsRecipeBuilder.GasIngWrapper(200, 525, 128))
                //
                .save(output);

    }
}
