package nuclearscience.common.reloadlistener;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import electrodynamics.Electrodynamics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import nuclearscience.api.radiation.util.RadiationShielding;
import nuclearscience.common.packet.type.client.PacketSetClientRadiationShielding;
import nuclearscience.common.packet.type.client.PacketSetClientRadioactiveFluids;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;

public class RadiationShieldingRegister extends SimplePreparableReloadListener<JsonObject> {

    public static RadiationShieldingRegister INSTANCE = null;

    public static final String FOLDER = "radiation";
    public static final String FILE_NAME = "radiation_shielding";

    protected static final String JSON_EXTENSION = ".json";
    protected static final int JSON_EXTENSION_LENGTH = JSON_EXTENSION.length();

    private static final Gson GSON = new Gson();

    private final HashMap<TagKey<Block>, RadiationShielding> tags = new HashMap<>();

    private final HashMap<Block, RadiationShielding> radiationShieldingMap = new HashMap<>();

    private final Logger logger = Electrodynamics.LOGGER;

    @Override
    protected JsonObject prepare(ResourceManager manager, ProfilerFiller profiler) {
        JsonObject combined = new JsonObject();

        List<Map.Entry<ResourceLocation, Resource>> resources = new ArrayList<>(manager.listResources(FOLDER, RadiationShieldingRegister::isJson).entrySet());
        Collections.reverse(resources);

        for (Map.Entry<ResourceLocation, Resource> entry : resources) {
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
            RadiationShielding value = RadiationShielding.CODEC.decode(JsonOps.INSTANCE, set.getValue()).getOrThrow().getFirst();

            if (key.contains("#")) {

                key = key.substring(1);

                tags.put(BlockTags.create(ResourceLocation.parse(key)), value);

            } else {

                radiationShieldingMap.put(BuiltInRegistries.BLOCK.get(ResourceLocation.parse(key)), value);


            }

        });

    }

    public void generateTagValues() {

        tags.forEach((tag, value) -> {
            BuiltInRegistries.BLOCK.getTag(tag).get().forEach(block -> {

                radiationShieldingMap.put(block.value(), value);

            });
        });

        tags.clear();
    }

    public RadiationShieldingRegister subscribeAsSyncable() {
        NeoForge.EVENT_BUS.addListener(getDatapackSyncListener());
        return this;
    }

    private Consumer<OnDatapackSyncEvent> getDatapackSyncListener() {
        return event -> {
            generateTagValues();
            ServerPlayer player = event.getPlayer();
            PacketSetClientRadiationShielding packet = new PacketSetClientRadiationShielding(radiationShieldingMap);
            if (player == null) {
                PacketDistributor.sendToAllPlayers(packet);
            } else {
                PacketDistributor.sendToPlayer(player, packet);
            }
        };
    }

    public void setClientValues(HashMap<Block, RadiationShielding> mappedValues) {
        this.radiationShieldingMap.clear();
        this.radiationShieldingMap.putAll(mappedValues);
    }

    public static HashMap<Block, RadiationShielding> getValues() {
        return INSTANCE.radiationShieldingMap;
    }

    public static RadiationShielding getValue(Block block) {
        return INSTANCE.radiationShieldingMap.getOrDefault(block, RadiationShielding.ZERO);
    }

    private static boolean isJson(final ResourceLocation filename) {
        return filename.getPath().contains(FILE_NAME + JSON_EXTENSION);
    }

}
