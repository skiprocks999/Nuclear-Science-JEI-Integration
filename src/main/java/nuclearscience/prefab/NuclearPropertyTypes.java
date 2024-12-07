package nuclearscience.prefab;

import electrodynamics.prefab.properties.PropertyType;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.api.quantumtunnel.TunnelFrequency;

public class NuclearPropertyTypes {

    public static final PropertyType<TunnelFrequency, ByteBuf> TUNNEL_FREQUENCY = new PropertyType<>(
            //
            ResourceLocation.fromNamespaceAndPath(References.ID, "tunnelfrequency"),
            //
            null,
            //
            TunnelFrequency.STREAM_CODEC,
            //
            writer -> writer.tag().put(writer.prop().getName(), TunnelFrequency.CODEC.encode(writer.prop().get(), NbtOps.INSTANCE, new CompoundTag()).getOrThrow()),
            //
            reader -> TunnelFrequency.CODEC.decode(NbtOps.INSTANCE, reader.tag().getCompound(reader.prop().getName())).getOrThrow().getFirst()
            //
    );
}
