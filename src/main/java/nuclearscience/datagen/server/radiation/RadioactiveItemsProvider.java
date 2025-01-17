package nuclearscience.datagen.server.radiation;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import com.mojang.serialization.JsonOps;
import electrodynamics.common.tags.ElectrodynamicsTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import nuclearscience.References;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.common.reloadlistener.RadioactiveItemRegister;
import nuclearscience.common.tags.NuclearScienceTags;

public class RadioactiveItemsProvider implements DataProvider {

	public static final String LOC = "data/" + References.ID + "/" + RadioactiveItemRegister.FOLDER + "/" + RadioactiveItemRegister.FILE_NAME;

	private final PackOutput output;

	public RadioactiveItemsProvider(PackOutput output) {
		this.output = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		JsonObject json = new JsonObject();
		getRadioactiveItems(json);

		Path parent = output.getOutputFolder().resolve(LOC + ".json");

		return CompletableFuture.allOf(DataProvider.saveStable(cache, json, parent));
	}

	private void getRadioactiveItems(JsonObject json) {

		addTag(NuclearScienceTags.Items.PELLET_URANIUM235, 1000, 1, json);
		addTag(NuclearScienceTags.Items.PELLET_URANIUM238, 500, 1, json);
		addTag(NuclearScienceTags.Items.PELLET_PLUTONIUM, 4500, 1, json);
		addTag(NuclearScienceTags.Items.PELLET_POLONIUM, 2500, 1, json);
		addTag(NuclearScienceTags.Items.PELLET_ACTINIUM225, 5000, 1, json);

		addTag(NuclearScienceTags.Items.NUGGET_POLONIUM, 625, 1, json);

		addTag(NuclearScienceTags.Items.FUELROD_URANIUM_HIGH_EN, 3000, 1, json);
		addTag(NuclearScienceTags.Items.FUELROD_URANIUM_LOW_EN, 2000, 1, json);
		addTag(NuclearScienceTags.Items.FUELROD_SPENT, 3500, 1, json);
		addTag(NuclearScienceTags.Items.FUELROD_PLUTONIUM, 2500, 1, json);

		addTag(NuclearScienceTags.Items.YELLOW_CAKE, 300, 1, json);
		addTag(NuclearScienceTags.Items.DUST_FISSILE, 2000, 1, json);
		addTag(NuclearScienceTags.Items.SALT_FISSILE, 200, 1, json);
		addTag(NuclearScienceTags.Items.OXIDE_PLUTONIUM, 4000, 1, json);
		addTag(NuclearScienceTags.Items.DUST_THORIUM, 2000, 1, json);
		addTag(NuclearScienceTags.Items.OXIDE_ACTINIUM, 400, 1, json);

		addTag(ElectrodynamicsTags.Items.ORE_THORIUM, 500, 1, json);
		addTag(ElectrodynamicsTags.Items.ORE_URANIUM, 100, 1, json);

		addTag(ElectrodynamicsTags.Items.RAW_ORE_THORIUM, 150, 1, json);
		addTag(ElectrodynamicsTags.Items.RAW_ORE_URANIUM, 50, 1, json);

		addTag(ElectrodynamicsTags.Items.BLOCK_RAW_ORE_THORIUM, 500, 1, json);
		addTag(ElectrodynamicsTags.Items.BLOCK_RAW_ORE_URANIUM, 450, 1, json);

	}

	@SuppressWarnings("unused")
	private void addItem(Item item, double radiationAmount, double radiationStrength, JsonObject json) {
		JsonObject data = new JsonObject();
		json.add(BuiltInRegistries.ITEM.getKey(item).toString(), RadioactiveObject.CODEC.encode(new RadioactiveObject(radiationStrength, radiationAmount), JsonOps.INSTANCE, data).getOrThrow());
	}

	private void addTag(TagKey<Item> tag, double radiationAmount, double radiationStrength, JsonObject json) {
		JsonObject data = new JsonObject();
		json.add("#" + tag.location().toString(), RadioactiveObject.CODEC.encode(new RadioactiveObject(radiationStrength, radiationAmount), JsonOps.INSTANCE, data).getOrThrow());
	}

	@Override
	public String getName() {
		return "Nuclear Science Radioactive Items Provider";
	}

}
