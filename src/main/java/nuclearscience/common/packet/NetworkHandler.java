package nuclearscience.common.packet;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import nuclearscience.References;
import nuclearscience.common.packet.type.client.PacketSetClientAtomicAssemblerBlacklistVals;

@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {
	private static final String PROTOCOL_VERSION = "1";
	@SubscribeEvent
	public static void registerPackets(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registry = event.registrar(electrodynamics.api.References.ID).versioned(PROTOCOL_VERSION).optional();

		//CLIENT
		registry.playToClient(PacketSetClientAtomicAssemblerBlacklistVals.TYPE, PacketSetClientAtomicAssemblerBlacklistVals.CODEC, (packet, context) -> PacketSetClientAtomicAssemblerBlacklistVals.handle(packet, context));

	}
	/*
	public static void init() {
		CHANNEL.registerMessage(disc++, PacketSetClientRadRegisterItemVals.class, PacketSetClientRadRegisterItemVals::encode, PacketSetClientRadRegisterItemVals::decode, PacketSetClientRadRegisterItemVals::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSetClientAtomicAssemblerBlacklistVals.class, PacketSetClientAtomicAssemblerBlacklistVals::encode, PacketSetClientAtomicAssemblerBlacklistVals::decode, PacketSetClientAtomicAssemblerBlacklistVals::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	}

	 */

	public static ResourceLocation id(String name) {
		return ResourceLocation.fromNamespaceAndPath(References.ID, name);
	}

}
