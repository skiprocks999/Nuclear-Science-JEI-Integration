package nuclearscience.common.packet.type.client;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nuclearscience.common.packet.NetworkHandler;

import java.util.HashSet;

public class PacketSetClientAtomicAssemblerWhitelistVals implements CustomPacketPayload {

    public static final ResourceLocation PACKET_SETCLIENTATOMICASSEMBLERBLACKLISTVALS_PACKETID = NetworkHandler.id("packetsetclientatomicassemblerwhitelistvals");
    public static final CustomPacketPayload.Type<PacketSetClientAtomicAssemblerWhitelistVals> TYPE = new CustomPacketPayload.Type<>(PACKET_SETCLIENTATOMICASSEMBLERBLACKLISTVALS_PACKETID);

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketSetClientAtomicAssemblerWhitelistVals> CODEC = new StreamCodec<RegistryFriendlyByteBuf, PacketSetClientAtomicAssemblerWhitelistVals>() {
        @Override
        public PacketSetClientAtomicAssemblerWhitelistVals decode(RegistryFriendlyByteBuf buf) {
            int count = buf.readInt();
            HashSet<Item> items = new HashSet<>();
            for (int i = 0; i < count; i++) {
                items.add(ItemStack.OPTIONAL_STREAM_CODEC.decode(buf).getItem());
            }
            return new PacketSetClientAtomicAssemblerWhitelistVals(items);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, PacketSetClientAtomicAssemblerWhitelistVals packet) {
            buf.writeInt(packet.items.size());
            packet.items.forEach(item -> {
                ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, new ItemStack(item));
            });

        }

    };

    private final HashSet<Item> items;

    public PacketSetClientAtomicAssemblerWhitelistVals(HashSet<Item> items) {
        this.items = items;
    }

    public static void handle(PacketSetClientAtomicAssemblerWhitelistVals message, IPayloadContext context) {
        ClientBarrierMethods.handleSetAtomicAssemblerClientWhitelistValues(message.items);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}