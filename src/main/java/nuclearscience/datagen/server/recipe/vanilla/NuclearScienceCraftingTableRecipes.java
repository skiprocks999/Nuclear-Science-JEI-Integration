package nuclearscience.datagen.server.recipe.vanilla;

import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.ShapedCraftingRecipeBuilder;
import electrodynamics.datagen.utils.recipe.ShapelessCraftingRecipeBuilder;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceCraftingTableRecipes extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(RecipeOutput output) {

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ANTIDOTE.get(), 3)
				//
				.addIngredient(Items.GLASS_BOTTLE)
				//
				.addIngredient(Items.GLASS_BOTTLE)
				//
				.addIngredient(Items.GLASS_BOTTLE)
				//
				.addIngredient(ItemTags.FISHES)
				//
				.complete(References.ID, "antidote", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base), 2)
				//
				.addPattern("LLL")
				//
				.addPattern("CCC")
				//
				.addPattern("LLL")
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))
				//
				.complete(References.ID, "leadlined_block", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door), 1)
				//
				.addPattern("BB")
				//
				.addPattern("BB")
				//
				.addPattern("BB")
				//
				.addKey('B', NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base))
				//
				.complete(References.ID, "leadlined_door", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.trapdoor), 1)
				//
				.addPattern("BB")
				//
				.addPattern("BB")
				//
				.addKey('B', NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base))
				//
				.complete(References.ID, "leadlined_trapdoor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.glass), 8)
				//
				.addPattern("GGG")
				//
				.addPattern("GBG")
				//
				.addPattern("GGG")
				//
				.addKey('B', NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base))
				//
				.addKey('G', Tags.Items.GLASS_BLOCKS)
				//
				.complete(References.ID, "leadlined_glass", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CELLANTIMATTERLARGE.get(), 1)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.complete(References.ID, "cellantimatter_large", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CELLANTIMATTERVERYLARGE.get(), 1)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_LARGE)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_LARGE)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_LARGE)
				//
				.complete(References.ID, "cellantimatter_verylarge", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CELLELECTROMAGNETIC.get(), 1)
				//
				.addPattern(" C ")
				//
				.addPattern("CEC")
				//
				.addPattern(" C ")
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.complete(References.ID, "cellelectromagnetic", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CELLEMPTY.get(), 4)
				//
				.addPattern("GTG")
				//
				.addPattern("T T")
				//
				.addPattern("GTG")
				//
				.addKey('G', Tags.Items.GLASS_BLOCKS)
				//
				.addKey('T', ElectrodynamicsTags.Items.INGOT_TIN)
				//
				.complete(References.ID, "cellempty_glass", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CELLEMPTY.get(), 6)
				//
				.addPattern("GTG")
				//
				.addPattern("T T")
				//
				.addPattern("GTG")
				//
				.addKey('G', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear))
				//
				.addKey('T', ElectrodynamicsTags.Items.INGOT_TIN)
				//
				.complete(References.ID, "cellempty_clearglass", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ELECTROMAGNET.get(), 1)
				//
				.addPattern("BSB")
				//
				.addPattern("SMS")
				//
				.addPattern("BSB")
				//
				.addKey('B', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "electromagnet_steelbronze", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ELECTROMAGNET.get(), 15)
				//
				.addPattern("THT")
				//
				.addPattern("HMH")
				//
				.addPattern("THT")
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUM)
				//
				.addKey('H', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "electromagnet_hslatitanium", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ELECTROMAGNETICBOOSTER.get(), 1)
				//
				.addPattern("EGE")
				//
				.addKey('E', NuclearScienceItems.ITEM_ELECTROMAGNET.get())
				//
				.addKey('G', NuclearScienceItems.ITEM_ELECTROMAGNETICGLASS.get())
				//
				.complete(References.ID, "electromagneticbooster", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ELECTROMAGNETICGLASS.get(), 1)
				//
				.addIngredient(NuclearScienceItems.ITEM_ELECTROMAGNET.get())
				//
				.addIngredient(Tags.Items.GLASS_BLOCKS)
				//
				.complete(References.ID, "electromagneticglass", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ELECTROMAGNETICSWITCH.get(), 1)
				//
				.addIngredient(NuclearScienceItems.ITEM_ELECTROMAGNETICBOOSTER.get())
				//
				.addIngredient(ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "electromagneticswitch", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FREQUENCYCARD.get(), 1)
				//
				.addPattern(" P ")
				//
				.addPattern("WCW")
				//
				.addPattern(" P ")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_IRON)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "frequencycard_new", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FREQUENCYCARD.get(), 1)
				//
				.addIngredient(NuclearScienceItems.ITEM_FREQUENCYCARD.get())
				//
				.complete(References.ID, "frequencycard_reset", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FUELHEUO2.get(), 1)
				//
				.addPattern("GLG")
				//
				.addPattern("GHG")
				//
				.addPattern("GLG")
				//
				.addKey('G', Tags.Items.GLASS_BLOCKS)
				//
				.addKey('L', NuclearScienceTags.Items.PELLET_URANIUM238)
				//
				.addKey('H', NuclearScienceTags.Items.PELLET_URANIUM235)
				//
				.complete(References.ID, "fuelrod_uranium_highenrich", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FUELLEUO2.get(), 1)
				//
				.addPattern("GLG")
				//
				.addPattern("GLG")
				//
				.addPattern("GLG")
				//
				.addKey('G', Tags.Items.GLASS_BLOCKS)
				//
				.addKey('L', NuclearScienceTags.Items.PELLET_URANIUM238)
				//
				.complete(References.ID, "fuelrod_uranium_lowenrich", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FUELPLUTONIUM.get(), 1)
				//
				.addPattern("GLG")
				//
				.addPattern("GPG")
				//
				.addPattern("GLG")
				//
				.addKey('G', Tags.Items.GLASS_BLOCKS)
				//
				.addKey('L', NuclearScienceTags.Items.PELLET_URANIUM238)
				//
				.addKey('P', NuclearScienceTags.Items.PELLET_PLUTONIUM)
				//
				.complete(References.ID, "fuelrod_plutonium", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_MOLTENSALTPIPTE.getValue(SubtypeMoltenSaltPipe.vanadiumsteelceramic), 2)
				//
				.addPattern("CCC")
				//
				.addPattern("VVV")
				//
				.addPattern("CCC")
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.complete(References.ID, "moltensaltpipe_ceramicvanadium", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_POLONIUM210.get(), 1)
				//
				.addPattern("PP")
				//
				.addPattern("PP")
				//
				.addKey('P', NuclearScienceTags.Items.NUGGET_POLONIUM)
				//
				.complete(References.ID, "poloniumpellet_from_nuggets", output);

		addGear(output);
		addMachines(output);

	}

	public void addGear(RecipeOutput output) {
		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CANISTERLEAD.get(), 1)
				//
				.addPattern("VLV")
				//
				.addPattern("LCL")
				//
				.addPattern("VLV")
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get())
				//
				.complete(References.ID, "canisterlead", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_GEIGERCOUNTER.get(), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("PBP")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('B', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(References.ID, "geigercounter", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_HAZMATHELMET.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LHL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('H', Items.LEATHER_HELMET)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "hazmathelmet", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_HAZMATPLATE.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LcL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('c', Items.LEATHER_CHESTPLATE)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "hazmatchestplate", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_HAZMATLEGS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LlL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('l', Items.LEATHER_LEGGINGS)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "hazmatleggings", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_HAZMATBOOTS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LBL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('B', Items.LEATHER_BOOTS)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "hazmatboots", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATHELMET.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LHL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('H', NuclearScienceItems.ITEM_HAZMATHELMET.get())
				//
				.complete(References.ID, "reinforcedhazmathelmet", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATPLATE.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LcL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('c', NuclearScienceItems.ITEM_HAZMATPLATE.get())
				//
				.complete(References.ID, "reinforcedhazmatchestplate", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATLEGS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LlL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('l', NuclearScienceItems.ITEM_HAZMATLEGS.get())
				//
				.complete(References.ID, "reinforcedhazmatleggings", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATBOOTS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LBL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('B', NuclearScienceItems.ITEM_HAZMATBOOTS.get())
				//
				.complete(References.ID, "reinforcedhazmatboots", output);

	}

	public void addMachines(RecipeOutput output) {

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ATOMICASSEMBLER.get(), 1)
				//
				.addPattern("CCC")
				//
				.addPattern("SGS")
				//
				.addPattern("SSS")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', NuclearScienceItems.ITEM_GASCENTRIFUGE.get())
				//
				.complete(References.ID, "atomicassembler", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CHEMICALEXTRACTOR.get(), 1)
				//
				.addPattern("SPS")
				//
				.addPattern("MCM")
				//
				.addPattern("SPS")
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "chemicalextractor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CONTROLROD.get(), 1)
				//
				.addPattern("SsS")
				//
				.addPattern(" P ")
				//
				.addPattern("SCS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('s', ElectrodynamicsTags.Items.INGOT_SILVER)
				//
				.addKey('P', Items.PISTON)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(References.ID, "controlrod", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FREEZEPLUG.get(), 1)
				//
				.addPattern("SCS")
				//
				.addPattern("SCS")
				//
				.addPattern("SBS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('C', Tags.Items.STORAGE_BLOCKS_COPPER)
				//
				.addKey('B', ElectrodynamicsTags.Items.STORAGE_BLOCK_VANADIUMSTEEL)
				//
				.complete(References.ID, "freezeplug", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FUELREPROCESSOR.get(), 1)
				//
				.addPattern("VSV")
				//
				.addPattern("STS")
				//
				.addPattern("VSV")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.complete(References.ID, "fuelreprocessor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FISSIONREACTORCORE.get(), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("MEM")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.complete(References.ID, "fissionreactorcore", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FUSIONREACTORCORE.get(), 1)
				//
				.addPattern("UEU")
				//
				.addPattern("ECE")
				//
				.addPattern("UEU")
				//
				.addKey('U', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('E', NuclearScienceItems.ITEM_ELECTROMAGNET.get())
				//
				.addKey('C', NuclearScienceItems.ITEM_MSREACTORCORE.get())
				//
				.complete(References.ID, "fusionreactorcore", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_GASCENTRIFUGE.get(), 1)
				//
				.addPattern("SES")
				//
				.addPattern("CGC")
				//
				.addPattern("BMB")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('E', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('C', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_STEEL)
				//
				.addKey('B', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "gascentrifuge", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_HEATEXCHANGER.get(), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("PPP")
				//
				.addPattern("SCS")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_SILVER)
				//
				.addKey('C', Tags.Items.STORAGE_BLOCKS_COPPER)
				//
				.complete(References.ID, "heatexchanger", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_MOLTENSALTSUPPLIER.get(), 1)
				//
				.addPattern("SVS")
				//
				.addPattern("TTT")
				//
				.addPattern("SVS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.complete(References.ID, "moltensaltsupplier", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_MSRFUELPREPROCESSOR.get(), 1)
				//
				.addPattern("VLV")
				//
				.addPattern("LCL")
				//
				.addPattern("VEV")
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chemicalmixer))
				//
				.addKey('E', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.complete(References.ID, "msrfuelpreprocessor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_MSREACTORCORE.get(), 1)
				//
				.addPattern("SVS")
				//
				.addPattern("VRV")
				//
				.addPattern("SPS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('R', NuclearScienceItems.ITEM_FISSIONREACTORCORE.get())
				//
				.addKey('P', NuclearScienceTags.Items.PELLET_PLUTONIUM)
				//
				.complete(References.ID, "msrreactorcore", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_NUCLEARBOILER.get(), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("EFE")
				//
				.addPattern("PMP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.addKey('F', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnace))
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "nuclearboiler", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_PARTICLEINJECTOR.get(), 1)
				//
				.addPattern("MTM")
				//
				.addPattern("UDU")
				//
				.addPattern("MTM")
				//
				.addKey('M', NuclearScienceItems.ITEM_ELECTROMAGNET.get())
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer))
				//
				.addKey('U', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('D', Items.DISPENSER)
				//
				.complete(References.ID, "particleinjector", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_QUANTUMCAPACITOR.get(), 1)
				//
				.addPattern("DCD")
				//
				.addPattern("RDR")
				//
				.addPattern("DCD")
				//
				.addKey('D', NuclearScienceTags.Items.CELL_DARK_MATTER)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('R', NuclearScienceItems.ITEM_FUSIONREACTORCORE.get())
				//
				.complete(References.ID, "quantumcapacitor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_RADIOACTIVEPROCESSOR.get(), 1)
				//
				.addPattern("VTV")
				//
				.addPattern("VMV")
				//
				.addPattern("VCV")
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.addKey('M', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chemicalmixer))
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.complete(References.ID, "radioactiveprocessor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_RADIOISOTOPEGENERATOR.get(), 1)
				//
				.addPattern("VCV")
				//
				.addPattern("LEL")
				//
				.addPattern("VCV")
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.complete(References.ID, "radioisotopegenerator", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_SIREN.get(), 1)
				//
				.addPattern("NPN")
				//
				.addKey('N', Items.NOTE_BLOCK)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.complete(References.ID, "siren", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_STEAMFUNNEL.get(), 1)
				//
				.addPattern(" P ")
				//
				.addPattern("P P")
				//
				.addPattern("P P")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.complete(References.ID, "steamfunnel_bronze", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_STEAMFUNNEL.get(), 1)
				//
				.addPattern(" P ")
				//
				.addPattern("P P")
				//
				.addPattern("P P")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.complete(References.ID, "steamfunnel_steel", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_TELEPORTER.get(), 1)
				//
				.addPattern("TCT")
				//
				.addPattern("HEH")
				//
				.addPattern("PDP")
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.fuse))
				//
				.addKey('H', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('E', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('P', Items.ENDER_PEARL)
				//
				.addKey('D', NuclearScienceTags.Items.CELL_DARK_MATTER)
				//
				.complete(References.ID, "teleporter", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_TURBINE.get(), 1)
				//
				.addPattern(" W ")
				//
				.addPattern("PMP")
				//
				.addPattern(" P ")
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.gold))
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "turbine", output);

	}

}
