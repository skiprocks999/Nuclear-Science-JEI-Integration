package nuclearscience.datagen.server.recipe.custom;

import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.server.recipe.types.custom.ElectrodynamicsChemicalReactorRecipes;
import electrodynamics.datagen.utils.recipe.builders.ElectrodynamicsRecipeBuilder;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.FluidTags;
import net.neoforged.neoforge.fluids.FluidStack;
import nuclearscience.References;

public class NuclearScienceChemicalReactorRecipes extends ElectrodynamicsChemicalReactorRecipes {

    public NuclearScienceChemicalReactorRecipes(String modID) {
        super(modID);
    }

    public NuclearScienceChemicalReactorRecipes() {
        this(References.ID);
    }

    @Override
    public void addRecipes(RecipeOutput output) {

        newRecipe(0, 200, 700, "hydrochloric_acid", modID)
                //
                .setFluidOutput(new FluidStack(ElectrodynamicsFluids.FLUID_HYDROCHLORICACID.get(), 500))
                //
                .addFluidTagInput(FluidTags.WATER, 1000)
                //
                .addItemTagInput(ElectrodynamicsTags.Items.DUST_SALT, 5)
                //
                .addGasTagInput(ElectrodynamicsTags.Gases.HYDROGEN, new ElectrodynamicsRecipeBuilder.GasIngWrapper(1000, 500, 8))
                //
                .save(output);

    }
}
