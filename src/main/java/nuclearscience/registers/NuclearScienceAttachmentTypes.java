package nuclearscience.registers;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import nuclearscience.References;
import nuclearscience.api.radiation.RadiationManager;
import nuclearscience.api.radiation.SimpleRadiationSource;
import nuclearscience.api.radiation.util.BlockPosVolume;
import nuclearscience.api.radiation.util.IRadiationManager;
import nuclearscience.common.settings.Constants;

import java.util.HashMap;

public class NuclearScienceAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, References.ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<BlockPos, SimpleRadiationSource>>> PERMANENT_RADIATION_SOURCES = ATTACHMENT_TYPES.register("permanentradiationsources", () -> AttachmentType.builder(() -> new HashMap<BlockPos, SimpleRadiationSource>()).serialize(Codec.unboundedMap(BlockPos.CODEC, SimpleRadiationSource.CODEC).xmap(map -> {
        HashMap<BlockPos, SimpleRadiationSource> values = new HashMap<>();
        values.putAll(map);
        return values;
    }, value -> value)).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<BlockPos, IRadiationManager.TemporaryRadiationSource>>> TEMPORARY_RADIATION_SOURCES = ATTACHMENT_TYPES.register("temporaryradiationsources", () -> AttachmentType.builder(() -> new HashMap<BlockPos, IRadiationManager.TemporaryRadiationSource>()).serialize(Codec.unboundedMap(BlockPos.CODEC, IRadiationManager.TemporaryRadiationSource.CODEC).xmap(map -> {
        HashMap<BlockPos, IRadiationManager.TemporaryRadiationSource> values = new HashMap<>();
        values.putAll(map);
        return values;
    }, value -> value)).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<BlockPos, IRadiationManager.FadingRadiationSource>>> FADING_RADIATION_SOURCES = ATTACHMENT_TYPES.register("fadingradiationsources", () -> AttachmentType.builder(() -> new HashMap<BlockPos, IRadiationManager.FadingRadiationSource>()).serialize(Codec.unboundedMap(BlockPos.CODEC, IRadiationManager.FadingRadiationSource.CODEC).xmap(map -> {
        HashMap<BlockPos, IRadiationManager.FadingRadiationSource> values = new HashMap<>();
        values.putAll(map);
        return values;
    }, value -> value)).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<BlockPosVolume, Double>>> LOCALIZED_DISSIPATIONS = ATTACHMENT_TYPES.register("localizeddissipations", () -> AttachmentType.builder(() -> new HashMap<BlockPosVolume, Double>()).serialize(Codec.unboundedMap(BlockPosVolume.CODEC, Codec.DOUBLE).xmap(map -> {
        HashMap<BlockPosVolume, Double> values = new HashMap<>();
        values.putAll(map);
        return values;
    }, value -> value)).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> DEFAULT_DISSIPATION = ATTACHMENT_TYPES.register("defaultdissipation", () -> AttachmentType.builder(() -> Constants.BACKROUND_RADIATION_DISSIPATION).serialize(Codec.DOUBLE).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<RadiationManager>> RADIATION_MANAGER = ATTACHMENT_TYPES.register("radiationmanager", () -> AttachmentType.builder(RadiationManager::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> RECIEVED_RADIATIONAMOUNT = ATTACHMENT_TYPES.register("recievedradiationamount", () -> AttachmentType.builder(() -> Double.valueOf(0.0)).serialize(Codec.DOUBLE).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> RECIEVED_RADIATIONSTRENGTH = ATTACHMENT_TYPES.register("recievedradiationstrength", () -> AttachmentType.builder(() -> Double.valueOf(0.0)).serialize(Codec.DOUBLE).build());


}
