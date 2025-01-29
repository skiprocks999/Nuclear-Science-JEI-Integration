package nuclearscience.common.reloadlistener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import nuclearscience.common.packet.type.client.PacketSetClientAtomicAssemblerWhitelistVals;

public class AtomicAssemblerWhitelistRegister extends SimplePreparableReloadListener<JsonObject> {

    public static AtomicAssemblerWhitelistRegister INSTANCE = null;

    public static final String KEY = "values";
    public static final String FOLDER = "machines";
    public static final String FILE_NAME = "atomic_assembler_whitelist";

    protected static final String JSON_EXTENSION = ".json";
    protected static final int JSON_EXTENSION_LENGTH = JSON_EXTENSION.length();

    private static final Gson GSON = new Gson();

    private final HashSet<Item> whitelistedItems = new HashSet<>();

    private final HashSet<TagKey<Item>> tags = new HashSet<>();

    private final Logger logger = Electrodynamics.LOGGER;

    @Override
    protected JsonObject prepare(ResourceManager manager, ProfilerFiller profiler) {
        JsonObject blacklistedItems = new JsonObject();

        List<Map.Entry<ResourceLocation, Resource>> resources = new ArrayList<>(manager.listResources(FOLDER, AtomicAssemblerWhitelistRegister::isJson).entrySet());
        Collections.reverse(resources);
        JsonArray combinedArray = new JsonArray();
        for (Map.Entry<ResourceLocation, Resource> entry : resources) {
            ResourceLocation loc = entry.getKey();
            final String namespace = loc.getNamespace();
            final String filePath = loc.getPath();
            final String dataPath = filePath.substring(FOLDER.length() + 1, filePath.length() - JSON_EXTENSION_LENGTH);

            final ResourceLocation jsonFile = ResourceLocation.fromNamespaceAndPath(namespace, dataPath);

            Resource resource = entry.getValue();
            try (final InputStream inputStream = resource.open(); final Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));) {
                final JsonObject json = (JsonObject) GsonHelper.fromJson(GSON, reader, JsonElement.class);
                combinedArray.addAll(json.get(KEY).getAsJsonArray());
            } catch (RuntimeException | IOException exception) {
                logger.error("Data loader for {} could not read data {} from file {} in data pack {}", FOLDER, jsonFile, loc, resource.sourcePackId(), exception);
            }

        }
        blacklistedItems.add(KEY, combinedArray);

        return blacklistedItems;
    }

    @Override
    protected void apply(JsonObject json, ResourceManager manager, ProfilerFiller profiler) {
        whitelistedItems.clear();
        tags.clear();
        ArrayList<String> list = GSON.fromJson(json.get(KEY).getAsJsonArray(), ArrayList.class);
        list.forEach(key -> {
            if (key.charAt(0) == '#') {
                tags.add(ItemTags.create(ResourceLocation.parse(key.substring(1))));
            } else {
                whitelistedItems.add(BuiltInRegistries.ITEM.get(ResourceLocation.parse(key)));
            }
        });

    }

    public void generateTagValues() {
        tags.forEach(tag -> {
            for (ItemStack item : Ingredient.of(tag).getItems()) {
                whitelistedItems.add(item.getItem());
            }
        });
        tags.clear();
    }

    public void setClientValues(HashSet<Item> fuels) {
        this.whitelistedItems.clear();
        this.whitelistedItems.addAll(fuels);
    }

    public AtomicAssemblerWhitelistRegister subscribeAsSyncable() {
        NeoForge.EVENT_BUS.addListener(getDatapackSyncListener());
        return this;
    }

    public HashSet<Item> getWhitelist() {
        return whitelistedItems;
    }

    public boolean isWhitelist(Item item) {
        return whitelistedItems.contains(item);
    }

    private Consumer<OnDatapackSyncEvent> getDatapackSyncListener() {
        return event -> {
            generateTagValues();
            ServerPlayer player = event.getPlayer();
            PacketSetClientAtomicAssemblerWhitelistVals packet = new PacketSetClientAtomicAssemblerWhitelistVals(whitelistedItems);
            if(player == null) {
                PacketDistributor.sendToAllPlayers(packet);
            } else {
                PacketDistributor.sendToPlayer(player, packet);
            }
        };
    }

    private static boolean isJson(final ResourceLocation filename) {
        return filename.getPath().contains(FILE_NAME + JSON_EXTENSION);
    }
}
