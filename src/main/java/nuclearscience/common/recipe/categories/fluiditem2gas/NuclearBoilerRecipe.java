package nuclearscience.common.recipe.categories.fluiditem2gas;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.categories.fluiditem2gas.FluidItem2GasRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableGas;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import nuclearscience.NuclearScience;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

import java.util.List;

public class NuclearBoilerRecipe extends FluidItem2GasRecipe {

	public static final String RECIPE_GROUP = "nuclear_boiler_recipe";
	public static final ResourceLocation RECIPE_ID = NuclearScience.rl(RECIPE_GROUP);

	public NuclearBoilerRecipe(String group, List<CountableIngredient> inputItems, List<FluidIngredient> inputFluids, GasStack outputGas, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
		super(group, inputItems, inputFluids, outputGas, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return NuclearScienceRecipeInit.NUCLEAR_BOILER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE.get();
	}

}
