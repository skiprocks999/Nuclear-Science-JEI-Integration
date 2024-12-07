package nuclearscience.common.packet.type.client;

import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.common.packet.NetworkHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class PacketSetClientTunnelFrequencies implements CustomPacketPayload {

    public static final ResourceLocation PACKET_SETCLIENTRADIOACTIVEGASES_PACKETID = NetworkHandler.id("packetsetclienttunnelfrequencies");
    public static final Type<PacketSetClientTunnelFrequencies> TYPE = new Type<>(PACKET_SETCLIENTRADIOACTIVEGASES_PACKETID);

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketSetClientTunnelFrequencies> CODEC = new StreamCodec<RegistryFriendlyByteBuf, PacketSetClientTunnelFrequencies>() {
        @Override
        public PacketSetClientTunnelFrequencies decode(RegistryFriendlyByteBuf buf) {
            HashMap<UUID, HashSet<TunnelFrequency>> data = new HashMap<>();

            int size = buf.readInt();
            for (int i = 0; i < size; i++) {

                UUID id = UUIDUtil.STREAM_CODEC.decode(buf);
                HashSet<TunnelFrequency> set = new HashSet<>();

                for (int j = 0; j < buf.readInt(); j++) {
                    set.add(TunnelFrequency.STREAM_CODEC.decode(buf));
                }

                data.put(id, set);
            }


            return new PacketSetClientTunnelFrequencies(data, BlockPos.STREAM_CODEC.decode(buf));
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, PacketSetClientTunnelFrequencies packet) {
            buf.writeInt(packet.frequencies.size());
            for (Map.Entry<UUID, HashSet<TunnelFrequency>> entry : packet.frequencies.entrySet()) {
                UUIDUtil.STREAM_CODEC.encode(buf, entry.getKey());
                buf.writeInt(entry.getValue().size());
                for (TunnelFrequency freq : entry.getValue()) {
                    TunnelFrequency.STREAM_CODEC.encode(buf, freq);
                }
            }
            BlockPos.STREAM_CODEC.encode(buf, packet.tilePos);
        }
    };

    private final HashMap<UUID, HashSet<TunnelFrequency>> frequencies;
    private final BlockPos tilePos;

    public PacketSetClientTunnelFrequencies(HashMap<UUID, HashSet<TunnelFrequency>> frequencies, BlockPos tilePos) {
        this.frequencies = frequencies;
        this.tilePos = tilePos;
    }

    public static void handle(PacketSetClientTunnelFrequencies message, IPayloadContext context) {
        ClientBarrierMethods.handleSetClientTunnelFrequencies(message.frequencies, message.tilePos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
