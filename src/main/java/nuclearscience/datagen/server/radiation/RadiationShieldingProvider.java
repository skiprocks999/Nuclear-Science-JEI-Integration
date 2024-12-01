package nuclearscience.datagen.server.radiation;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import nuclearscience.References;
import nuclearscience.api.radiation.util.RadiationShielding;
import nuclearscience.common.reloadlistener.RadiationShieldingRegister;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class RadiationShieldingProvider implements DataProvider {

    public static final String LOC = "data/" + References.ID + "/" + RadiationShieldingRegister.FOLDER + "/" + RadiationShieldingRegister.FILE_NAME;

    private final PackOutput output;

    public RadiationShieldingProvider(PackOutput output) {
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

    }

    @SuppressWarnings("unused")
    private void addItem(Block block, double radiationAmount, double radiationLevel, JsonObject json) {
        JsonObject data = new JsonObject();
        RadiationShielding.CODEC.encode(new RadiationShielding(radiationLevel, radiationAmount), JsonOps.INSTANCE, data);
        json.add(BuiltInRegistries.BLOCK.getKey(block).toString(), data);
    }

    private void addTag(TagKey<Block> tag, double radiationAmount, double radiationLevel, JsonObject json) {
        JsonObject data = new JsonObject();
        RadiationShielding.CODEC.encode(new RadiationShielding(radiationLevel, radiationAmount), JsonOps.INSTANCE, data);
        json.add("#" + tag.location().toString(), data);
    }

    @Override
    public String getName() {
        return "Nuclear Science Radiation Shielding Provider";
    }


}
