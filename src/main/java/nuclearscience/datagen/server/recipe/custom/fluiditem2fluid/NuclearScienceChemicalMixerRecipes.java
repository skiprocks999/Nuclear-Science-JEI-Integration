package nuclearscience.datagen.server.recipe.custom.fluiditem2fluid;

import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid.ElectrodynamicsChemicalMixerRecipes;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceFluids;

public class NuclearScienceChemicalMixerRecipes extends ElectrodynamicsChemicalMixerRecipes {

    public NuclearScienceChemicalMixerRecipes() {
        super(References.ID);
    }

    @Override
    public void addRecipes(RecipeOutput output) {


        newRecipe(new FluidStack(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(SubtypeSulfateFluid.iron), 1000), 0, CHEMICALMIXER_REQUIRED_TICKS, CHEMICALMIXER_USAGE_PER_TICK, "ironsulfate_from_ironblock", this.modID)
                //
                .addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 1000)
                //
                .addItemTagInput(Tags.Items.STORAGE_BLOCKS_RAW_IRON, 1)
                //
                .save(output);

        newRecipe(new FluidStack(NuclearScienceFluids.FLUID_IODINESOLUTION.get(), 100), 0, CHEMICALMIXER_REQUIRED_TICKS, CHEMICALMIXER_USAGE_PER_TICK, "iodine_solution_from_eggs", this.modID)
                //
                .addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 200)
                //
                .addItemTagInput(Tags.Items.EGGS, 1)
                //
                .save(output);

        newRecipe(new FluidStack(NuclearScienceFluids.FLUID_IODINESOLUTION.get(), 100), 0, CHEMICALMIXER_REQUIRED_TICKS, CHEMICALMIXER_USAGE_PER_TICK, "iodine_solution_from_kelp", this.modID)
                //
                .addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 200)
                //
                .addItemTagInput(Tags.Items.STORAGE_BLOCKS_DRIED_KELP, 1)
                //
                .save(output);

    }

}
