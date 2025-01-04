package nuclearscience.datagen.server.recipe.custom.fluiditem2gas;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.builders.ElectrodynamicsRecipeBuilder;
import electrodynamics.datagen.utils.recipe.builders.FluidItem2GasBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import nuclearscience.References;
import nuclearscience.common.recipe.categories.fluiditem2gas.NuclearBoilerRecipe;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceGases;

public class NuclearScienceNuclaerBoilerRecipes extends AbstractRecipeGenerator {

	public static double CHEMICALBOILER_USAGE_PER_TICK = 750.0;
	public static int CHEMICALBOILER_REQUIRED_TICKS = 800;

	private final String modID;

	public NuclearScienceNuclaerBoilerRecipes() {
		this(References.ID);
	}

	public NuclearScienceNuclaerBoilerRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(RecipeOutput output) {

		newRecipe(new GasStack(NuclearScienceGases.URANIUM_HEXAFLUORIDE.get(), 2000, 293, 1), 0, CHEMICALBOILER_REQUIRED_TICKS, CHEMICALBOILER_USAGE_PER_TICK, "uraniumhexafluoride_from_uraniumpellets", this.modID)
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.HYDROCHLORIC_ACID, 1600)
				//
				.addItemTagInput(NuclearScienceTags.Items.PELLET_URANIUM238, 1)
				//
				.save(output);

		newRecipe(new GasStack(NuclearScienceGases.URANIUM_HEXAFLUORIDE.get(), 2500, 293, 1), 0.25F, CHEMICALBOILER_REQUIRED_TICKS, CHEMICALBOILER_USAGE_PER_TICK, "uraniumhexafluoride_from_yellowcake", this.modID)
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.HYDROFLUORIC_ACID, 800)
				//
				.addItemTagInput(NuclearScienceTags.Items.YELLOW_CAKE, 1)
				//
				.save(output);

	}

	public FluidItem2GasBuilder<NuclearBoilerRecipe> newRecipe(GasStack stack, float xp, int ticks, double usagePerTick, String name, String group) {
		return new FluidItem2GasBuilder<>(NuclearBoilerRecipe::new, stack, ElectrodynamicsRecipeBuilder.RecipeCategory.FLUID_ITEM_2_GAS, modID, "nuclear_boiler/" + name, group, xp, ticks, usagePerTick);
	}

}
