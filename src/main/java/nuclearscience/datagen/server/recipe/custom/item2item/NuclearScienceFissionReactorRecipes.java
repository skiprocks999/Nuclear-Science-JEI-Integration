package nuclearscience.datagen.server.recipe.custom.item2item;

import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.builders.ElectrodynamicsRecipeBuilder;
import electrodynamics.datagen.utils.recipe.builders.Item2ItemBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import nuclearscience.References;
import nuclearscience.common.recipe.categories.item2item.FissionReactorRecipe;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceFissionReactorRecipes extends AbstractRecipeGenerator {

	private final String modID;

	public NuclearScienceFissionReactorRecipes() {
		this(References.ID);
	}

	public NuclearScienceFissionReactorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(RecipeOutput output) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_CELLTRITIUM.get()), 0.0F, 1, 1, "cell_tritium", modID)
				//
				.addItemTagInput(NuclearScienceTags.Items.CELL_DEUTERIUM, 1)
				//
				.save(output);

	}

	public Item2ItemBuilder<FissionReactorRecipe> newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name, String group) {
		return new Item2ItemBuilder<>(FissionReactorRecipe::new, stack, ElectrodynamicsRecipeBuilder.RecipeCategory.ITEM_2_ITEM, modID, "fission_reactor/" + name, group, xp, ticks, usagePerTick);
	}

}
