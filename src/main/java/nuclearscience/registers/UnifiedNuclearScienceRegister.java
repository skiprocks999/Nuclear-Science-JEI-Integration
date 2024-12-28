package nuclearscience.registers;

import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.neoforged.bus.api.IEventBus;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
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
		NuclearScienceParticles.PARTICLES.register(bus);
		NuclearScienceEffects.EFFECTS.register(bus);
	}

	static {
		// Machines
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.gascentrifuge), ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.nuclearboiler), ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.chemicalextractor), ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.particleinjector), ElectroTextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.teleporter), ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.fuelreprocessor), ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.radioactiveprocessor), ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.msrfuelpreprocessor), ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.moltensaltsupplier), ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.fusionreactorcore), ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.freezeplug), ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.atomicassembler), ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.cloudchamber), ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.falloutscrubber), ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.logisticscontroller), ElectroTextUtils.voltageTooltip(120));

		// Generators
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.radioisotopegenerator), ElectroTextUtils.voltageTooltip(120));

		// Misc
		BlockItemDescriptable.addDescription(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getHolder(SubtypeNuclearMachine.steamfunnel), NuclearTextUtils.tooltip("steamfunneluse"));
	}

}
