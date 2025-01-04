package nuclearscience.common.reloadlistener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import nuclearscience.api.radiation.util.RadioactiveObject;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
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
import nuclearscience.common.packet.type.client.PacketSetClientRadioactiveItems;

public class RadioactiveItemRegister extends SimplePreparableReloadListener<JsonObject> {

    public static RadioactiveItemRegister INSTANCE = null;

    public static final String FOLDER = "radiation";
    public static final String FILE_NAME = "radioactive_items";

    protected static final String JSON_EXTENSION = ".json";
    protected static final int JSON_EXTENSION_LENGTH = JSON_EXTENSION.length();

    private static final Gson GSON = new Gson();

    private final HashMap<TagKey<Item>, RadioactiveObject> tags = new HashMap<>();

    private final HashMap<Item, RadioactiveObject> radioactiveItemMap = new HashMap<>();

    private final Logger logger = Electrodynamics.LOGGER;

    @Override
    protected JsonObject prepare(ResourceManager manager, ProfilerFiller profiler) {
        JsonObject combined = new JsonObject();

        List<Entry<ResourceLocation, Resource>> resources = new ArrayList<>(manager.listResources(FOLDER, RadioactiveItemRegister::isJson).entrySet());
        Collections.reverse(resources);

        for (Entry<ResourceLocation, Resource> entry : resources) {
            ResourceLocation loc = entry.getKey();
            final String namespace = loc.getNamespace();
            final String filePath = loc.getPath();
            final String dataPath = filePath.substring(FOLDER.length() + 1, filePath.length() - JSON_EXTENSION_LENGTH);

            final ResourceLocation jsonFile = ResourceLocation.fromNamespaceAndPath(namespace, dataPath);

            Resource resource = entry.getValue();
            try (final InputStream inputStream = resource.open(); final Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));) {
                final JsonObject json = (JsonObject) GsonHelper.fromJson(GSON, reader, JsonElement.class);

                json.entrySet().forEach(set -> {

                    if (combined.has(set.getKey())) {
                        combined.remove(set.getKey());
                    }

                    combined.add(set.getKey(), set.getValue());
                });

            } catch (RuntimeException | IOException exception) {
                logger.error("Data loader for {} could not read data {} from file {} in data pack {}", FOLDER, jsonFile, loc, resource.sourcePackId(), exception);
            }

        }
        return combined;
    }

    @Override
    protected void apply(JsonObject json, ResourceManager manager, ProfilerFiller profiler) {
        tags.clear();

        json.entrySet().forEach(set -> {

            String key = set.getKey();
            RadioactiveObject value = RadioactiveObject.CODEC.decode(JsonOps.INSTANCE, set.getValue()).getOrThrow().getFirst();

            if (key.contains("#")) {

                key = key.substring(1);

                tags.put(ItemTags.create(ResourceLocation.parse(key)), value);

            } else {

                radioactiveItemMap.put(BuiltInRegistries.ITEM.get(ResourceLocation.parse(key)), value);


            }

        });

    }

    public void generateTagValues() {

        tags.forEach((tag, value) -> {
            BuiltInRegistries.ITEM.getTag(tag).get().forEach(item -> {

                radioactiveItemMap.put(item.value(), value);

            });
        });

        tags.clear();
    }

    public RadioactiveItemRegister subscribeAsSyncable() {
        NeoForge.EVENT_BUS.addListener(getDatapackSyncListener());
        return this;
    }

    private Consumer<OnDatapackSyncEvent> getDatapackSyncListener() {
        return event -> {
            generateTagValues();
            ServerPlayer player = event.getPlayer();
            PacketSetClientRadioactiveItems packet = new PacketSetClientRadioactiveItems(radioactiveItemMap);
            if(player == null) {
                PacketDistributor.sendToAllPlayers(packet);
            } else {
                PacketDistributor.sendToPlayer(player, packet);
            }
        };
    }

    public void setClientValues(HashMap<Item, RadioactiveObject> mappedValues) {
        this.radioactiveItemMap.clear();
        this.radioactiveItemMap.putAll(mappedValues);
    }

    public static HashMap<Item, RadioactiveObject> getValues() {
        return INSTANCE.radioactiveItemMap;
    }

    public static RadioactiveObject getValue(Item item) {
        return INSTANCE.radioactiveItemMap.getOrDefault(item, RadioactiveObject.ZERO);
    }

    private static boolean isJson(final ResourceLocation filename) {
        return filename.getPath().contains(FILE_NAME + JSON_EXTENSION);
    }

}
