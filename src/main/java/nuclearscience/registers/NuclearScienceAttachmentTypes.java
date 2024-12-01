package nuclearscience.registers;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import nuclearscience.References;
import nuclearscience.api.radiation.RadiationManager;

public class NuclearScienceAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, References.ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<RadiationManager>> RADIATION_MANAGER = ATTACHMENT_TYPES.register("radiationmanager", () -> AttachmentType.builder(RadiationManager::new).serialize(RadiationManager.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> RECIEVED_RADIATIONAMOUNT = ATTACHMENT_TYPES.register("recievedradiationamount", () -> AttachmentType.builder(() -> Double.valueOf(0.0)).serialize(Codec.DOUBLE).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> RECIEVED_RADIATIONSTRENGTH = ATTACHMENT_TYPES.register("recievedradiationstrength", () -> AttachmentType.builder(() -> Double.valueOf(0.0)).serialize(Codec.DOUBLE).build());


}
