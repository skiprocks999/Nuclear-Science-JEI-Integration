package nuclearscience.datagen;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import electrodynamics.datagen.client.ElectrodynamicsLangKeyProvider.Locale;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import nuclearscience.References;
import nuclearscience.datagen.client.NuclearScienceBlockStateProvider;
import nuclearscience.datagen.client.NuclearScienceItemModelsProvider;
import nuclearscience.datagen.client.NuclearScienceLangKeyProvider;
import nuclearscience.datagen.client.NuclearScienceSoundProvider;
import nuclearscience.datagen.server.AtomicAssemblerBlacklistProvider;
import nuclearscience.datagen.server.NuclearScienceLootTablesProvider;
import nuclearscience.datagen.server.radiation.RadioactiveItemsProvider;
import nuclearscience.datagen.server.recipe.NuclearScienceRecipeProvider;
import nuclearscience.datagen.server.tags.NuclearScienceTagsProvider;
import nuclearscience.registers.NuclearScienceDamageTypes;

@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

		DataGenerator generator = event.getGenerator();

		PackOutput output = generator.getPackOutput();

		ExistingFileHelper helper = event.getExistingFileHelper();

		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		if (event.includeServer()) {

			generator.addProvider(true, new LootTableProvider(output, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(NuclearScienceLootTablesProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
			generator.addProvider(true, new NuclearScienceRecipeProvider(output, lookupProvider));
			generator.addProvider(true, new RadioactiveItemsProvider(output));
			generator.addProvider(true, new AtomicAssemblerBlacklistProvider(output));

			DatapackBuiltinEntriesProvider datapacks = new DatapackBuiltinEntriesProvider(output, lookupProvider, new RegistrySetBuilder()
					//
					.add(Registries.DAMAGE_TYPE, NuclearScienceDamageTypes::registerTypes),
					//
					Set.of(References.ID));

			generator.addProvider(true, datapacks);
			NuclearScienceTagsProvider.addTagProviders(generator, output, datapacks.getRegistryProvider(), helper);

		}
		if (event.includeClient()) {
			generator.addProvider(true, new NuclearScienceBlockStateProvider(output, helper));
			generator.addProvider(true, new NuclearScienceItemModelsProvider(output, helper));
			generator.addProvider(true, new NuclearScienceLangKeyProvider(output, Locale.EN_US));
			generator.addProvider(true, new NuclearScienceSoundProvider(output, helper));
		}
	}

}
