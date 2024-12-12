package nuclearscience.datagen.server;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import nuclearscience.References;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class AtomicAssemblerBlacklistProvider implements DataProvider {

	public static final String LOC = "data/" + References.ID + "/" + AtomicAssemblerBlacklistRegister.FOLDER + "/" + AtomicAssemblerBlacklistRegister.FILE_NAME;

	private final PackOutput output;

	public AtomicAssemblerBlacklistProvider(PackOutput output) {
		this.output = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		JsonObject json = new JsonObject();
		getFuels(json);

		Path parent = output.getOutputFolder().resolve(LOC + ".json");

		return CompletableFuture.allOf(DataProvider.saveStable(cache, json, parent));
	}

	private void getFuels(JsonObject object) {
		JsonArray json = new JsonArray();

		addItem(Items.AIR, json);
		addTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "air")), json); // dummy tag for demonstration purposes

		addItem(NuclearScienceItems.ITEM_TELEPORTER.get(), json);
		addItem(NuclearScienceItems.ITEM_QUANTUMTUNNEL.get(), json);
		addItem(NuclearScienceItems.ITEM_CHUNKLOADER.get(), json);

		object.add(AtomicAssemblerBlacklistRegister.KEY, json);
	}

	private void addTag(TagKey<Item> item, JsonArray json) {
		json.add("#" + item.location().toString());
	}

	private void addItem(Item item, JsonArray json) {
		json.add(BuiltInRegistries.ITEM.getKey(item).toString());
	}

	@Override
	public String getName() {
		return "Nuclear Science Atomic Assembler Blacklist Provider";
	}

}
