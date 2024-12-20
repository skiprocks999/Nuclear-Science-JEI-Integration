package nuclearscience.datagen.server.recipe.custom;

import electrodynamics.api.gas.Gas;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.server.recipe.types.custom.ElectrodynamicsChemicalReactorRecipes;
import electrodynamics.datagen.utils.recipe.builders.ElectrodynamicsRecipeBuilder;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
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

        newRecipe(0, 100, 1000, "decontamination_foam", modID)
                //
                .setFluidOutput(new FluidStack(NuclearScienceFluids.FLUID_DECONTAMINATIONFOAM, 1000))
                //
                .addGasTagInput(ElectrodynamicsTags.Gases.AMMONIA, new ElectrodynamicsRecipeBuilder.GasIngWrapper(1000, Gas.ROOM_TEMPERATURE, 4))
                //
                .addFluidTagInput(FluidTags.WATER, 2000)
                //
                .addItemStackInput(new ItemStack(ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.potassiumchloride)))
                //
                .addItemTagInput(ElectrodynamicsTags.Items.DUST_SULFUR, 2)
                //
                .save(output);

    }
}
