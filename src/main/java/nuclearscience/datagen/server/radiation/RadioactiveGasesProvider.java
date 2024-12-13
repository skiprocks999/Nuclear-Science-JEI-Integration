package nuclearscience.datagen.server.radiation;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import electrodynamics.api.gas.Gas;
import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import nuclearscience.References;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.common.reloadlistener.RadioactiveGasRegister;
import nuclearscience.common.tags.NuclearScienceTags;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class RadioactiveGasesProvider implements DataProvider {

    public static final String LOC = "data/" + References.ID + "/" + RadioactiveGasRegister.FOLDER + "/" + RadioactiveGasRegister.FILE_NAME;

    private final PackOutput output;

    public RadioactiveGasesProvider(PackOutput output) {
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

        addTag(NuclearScienceTags.Gases.URANIUM_HEXAFLUORIDE, 1, 1, json);

    }

    @SuppressWarnings("unused")
    private void addItem(Gas gas, double radiationAmount, double radiationStrength, JsonObject json) {
        JsonObject data = new JsonObject();
        json.add(ElectrodynamicsGases.GAS_REGISTRY.getKey(gas).toString(), RadioactiveObject.CODEC.encode(new RadioactiveObject(radiationStrength, radiationAmount), JsonOps.INSTANCE, data).getOrThrow());
    }

    private void addTag(TagKey<Gas> tag, double radiationAmount, double radiationStrength, JsonObject json) {
        JsonObject data = new JsonObject();
        json.add("#" + tag.location().toString(), RadioactiveObject.CODEC.encode(new RadioactiveObject(radiationStrength, radiationAmount), JsonOps.INSTANCE, data).getOrThrow());
    }

    @Override
    public String getName() {
        return "Nuclear Science Radioactive Gases Provider";
    }

}
