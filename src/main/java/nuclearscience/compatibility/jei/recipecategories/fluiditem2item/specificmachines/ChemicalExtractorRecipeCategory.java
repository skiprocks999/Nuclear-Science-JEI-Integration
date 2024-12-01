package nuclearscience.compatibility.jei.recipecategories.fluiditem2item.specificmachines;

import electrodynamics.compatibility.jei.recipecategories.fluiditem2item.FluidItem2ItemRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.compatibility.jei.utils.gui.types.ItemSlotObject;
import electrodynamics.compatibility.jei.utils.gui.types.fluidgauge.FluidGaugeObject;
import electrodynamics.compatibility.jei.utils.label.types.PowerLabelWrapperElectroRecipe;
import electrodynamics.compatibility.jei.utils.label.types.TimeLabelWrapperElectroRecipe;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import nuclearscience.References;
import nuclearscience.common.recipe.categories.fluiditem2item.ChemicalExtractorRecipe;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChemicalExtractorRecipeCategory extends FluidItem2ItemRecipeCategory<ChemicalExtractorRecipe> {

	// JEI Window Parameters
	public static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 64);

	public static final ItemSlotObject INPUT_SLOT = new ItemSlotObject(SlotType.NORMAL, 57, 16, RecipeIngredientRole.INPUT);
	public static final ItemSlotObject INPUT_BUCKET_SLOT = new ItemSlotObject(SlotType.NORMAL, IconType.FLUID_DARK, 57, 36, RecipeIngredientRole.INPUT);
	public static final ItemSlotObject OUTPUT_SLOT = new ItemSlotObject(SlotType.NORMAL, 107, 16, RecipeIngredientRole.OUTPUT);

	public static final ArrowAnimatedObject ANIM_RIGHT_ARROW_1 = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_RIGHT, 30, 17, StartDirection.LEFT);
	public static final ArrowAnimatedObject ANIM_RIGHT_ARROW_2 = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_RIGHT, 80, 17, StartDirection.LEFT);
	public static final ArrowAnimatedObject ANIM_LEFT_ARROW = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_LEFT, 30, 37, StartDirection.RIGHT);

	public static final FluidGaugeObject IN_GAUGE = new FluidGaugeObject(10, 5, 5000);

	public static final PowerLabelWrapperElectroRecipe POWER_LABEL = new PowerLabelWrapperElectroRecipe(2, 55, 240);
	public static final TimeLabelWrapperElectroRecipe TIME_LABEL = new TimeLabelWrapperElectroRecipe(130, 55);

	public static final int ANIM_TIME = 50;

	public static ItemStack INPUT_MACHINE = new ItemStack(NuclearScienceBlocks.BLOCK_CHEMICALEXTRACTOR.get());

	public static final RecipeType<ChemicalExtractorRecipe> RECIPE_TYPE = RecipeType.create(References.ID, ChemicalExtractorRecipe.RECIPE_GROUP, ChemicalExtractorRecipe.class);

	public ChemicalExtractorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, NuclearTextUtils.jeiTranslated(ChemicalExtractorRecipe.RECIPE_GROUP), INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);
		setInputSlots(guiHelper, INPUT_SLOT, INPUT_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUTPUT_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_LEFT_ARROW, ANIM_RIGHT_ARROW_1, ANIM_RIGHT_ARROW_2);
		setLabels(POWER_LABEL, TIME_LABEL);
	}

}
