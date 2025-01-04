package nuclearscience.datagen.server.recipe.custom.fluiditem2item;

import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.builders.ElectrodynamicsRecipeBuilder;
import electrodynamics.datagen.utils.recipe.builders.FluidItem2ItemBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.recipe.categories.fluiditem2item.RadioactiveProcessorRecipe;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceRadioactiveProcessorRecipes extends AbstractRecipeGenerator {

	public static double RADIOACTIVEPROCESSOR_USAGE_PER_TICK = 480.0;
	public static int RADIOACTIVEPROCESSOR_REQUIRED_TICKS = 300;

	private final String modID;

	public NuclearScienceRadioactiveProcessorRecipes() {
		this(References.ID);
	}

	public NuclearScienceRadioactiveProcessorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(RecipeOutput output) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_PLUTONIUMOXIDE.get()), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "plutonium_oxide", modID)
				//
				.addItemTagInput(NuclearScienceTags.Items.DUST_FISSILE, 2)
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.IRON_SULFATE, 3000)
				//
				.save(output);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_THORIANITEDUST.get()), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "thorianite_dust", modID)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.RAW_ORE_THORIUM, 1)
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 1000)
				//
				.save(output);

		newRecipe(new ItemStack(Items.DIRT), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "dirt_from_irradiated_soil", modID)
				//
				.addItemStackInput(new ItemStack(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil)))
				//
				.addFluidTagInput(FluidTags.WATER, 100)
				//
				.save(output);

		newRecipe(new ItemStack(Items.COAL), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "coal_from_petrified_wood", modID)
				//
				.addItemStackInput(new ItemStack(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood)))
				//
				.addFluidTagInput(FluidTags.WATER, 100)
				//
				.save(output);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_ACTINIUM225.get()), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "actinium225", modID)
				//
				.addItemTagInput(NuclearScienceTags.Items.OXIDE_ACTINIUM, 1)
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.HYDROFLUORIC_ACID, 100)
				//
				.save(output);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_THORIANITEDUST.get()), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "thorianite_dust_from_monazite", modID)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_MONAZITE, 1)
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 1000)
				//
				.save(output);

	}

	public FluidItem2ItemBuilder<RadioactiveProcessorRecipe> newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name, String group) {
		return new FluidItem2ItemBuilder<>(RadioactiveProcessorRecipe::new, stack, ElectrodynamicsRecipeBuilder.RecipeCategory.FLUID_ITEM_2_ITEM, modID, "radioactive_processor/" + name, group, xp, ticks, usagePerTick);
	}

}
