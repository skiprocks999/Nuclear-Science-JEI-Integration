package nuclearscience.registers;

import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.neoforged.bus.api.IEventBus;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class UnifiedNuclearScienceRegister {

	public static void register(IEventBus bus) {
		NuclearScienceArmorMaterials.ARMOR_MATERIALS.register(bus);
		NuclearScienceAttachmentTypes.ATTACHMENT_TYPES.register(bus);
		NuclearScienceBlocks.BLOCKS.register(bus);
		NuclearScienceItems.ITEMS.register(bus);
		NuclearScienceTiles.BLOCK_ENTITY_TYPES.register(bus);
		NuclearScienceMenuTypes.MENU_TYPES.register(bus);
		NuclearScienceFluids.FLUIDS.register(bus);
		NuclearScienceFluidTypes.FLUID_TYPES.register(bus);
		NuclearScienceEntities.ENTITIES.register(bus);
		NuclearScienceSounds.SOUNDS.register(bus);
		NuclearScienceGases.GASES.register(bus);
		NuclearScienceCreativeTabs.CREATIVE_TABS.register(bus);
		NuclearScienceRecipeInit.RECIPE_TYPES.register(bus);
		NuclearScienceRecipeInit.RECIPE_SERIALIZER.register(bus);
		NuclearScienceEffects.EFFECTS.register(bus);
	}

	static {
		// Machines
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_GASCENTRIFUGE, ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_NUCLEARBOILER, ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_CHEMICALEXTRACTOR, ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_PARTICLEINJECTOR, ElectroTextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_TELEPORTER, ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_FUELREPROCESSOR, ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_RADIOACTIVEPROCESSOR, ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_MSRFUELPREPROCESSOR, ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_MOLTENSALTSUPPLIER, ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_FUSIONREACTORCORE, ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_MSRFREEZEPLUG, ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_ATOMICASSEMBLER, ElectroTextUtils.voltageTooltip(480));

		// Generators
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_RADIOISOTOPEGENERATOR, ElectroTextUtils.voltageTooltip(120));

		// Misc
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_QUANTUMTUNNEL, ElectroTextUtils.voltageTooltip(1920));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCK_STEAMFUNNEL, NuclearTextUtils.tooltip("steamfunneluse"));
	}

}
