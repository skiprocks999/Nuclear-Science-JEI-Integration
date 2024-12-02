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
import nuclearscience.registers.NuclearScienceBlocks;

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
        getRadiationShielding(json);

        Path parent = output.getOutputFolder().resolve(LOC + ".json");

        return CompletableFuture.allOf(DataProvider.saveStable(cache, json, parent));
    }

    private void getRadiationShielding(JsonObject json) {
        addBlock(NuclearScienceBlocks.BLOCK_LEAD.get(), 20000, 1, json);

    }

    @SuppressWarnings("unused")
    private void addBlock(Block block, double radiationAmount, double radiationLevel, JsonObject json) {
        JsonObject data = new JsonObject();
        json.add(BuiltInRegistries.BLOCK.getKey(block).toString(), RadiationShielding.CODEC.encode(new RadiationShielding(radiationAmount, radiationLevel), JsonOps.INSTANCE, data).getOrThrow());
    }

    private void addTag(TagKey<Block> tag, double radiationAmount, double radiationLevel, JsonObject json) {
        JsonObject data = new JsonObject();
        json.add("#" + tag.location().toString(), RadiationShielding.CODEC.encode(new RadiationShielding(radiationAmount, radiationLevel), JsonOps.INSTANCE, data).getOrThrow());
    }

    @Override
    public String getName() {
        return "Nuclear Science Radiation Shielding Provider";
    }


}
