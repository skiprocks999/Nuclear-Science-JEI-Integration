package nuclearscience.common.packet;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import nuclearscience.References;
import nuclearscience.common.packet.type.client.PacketSetClientAtomicAssemblerBlacklistVals;
import nuclearscience.common.packet.type.client.PacketSetClientRadioactiveFluids;
import nuclearscience.common.packet.type.client.PacketSetClientRadioactiveGases;
import nuclearscience.common.packet.type.client.PacketSetClientRadioactiveItems;

@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {
	private static final String PROTOCOL_VERSION = "1";
	@SubscribeEvent
	public static void registerPackets(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registry = event.registrar(electrodynamics.api.References.ID).versioned(PROTOCOL_VERSION).optional();

		//CLIENT
		registry.playToClient(PacketSetClientAtomicAssemblerBlacklistVals.TYPE, PacketSetClientAtomicAssemblerBlacklistVals.CODEC, PacketSetClientAtomicAssemblerBlacklistVals::handle);
		registry.playToClient(PacketSetClientRadioactiveItems.TYPE, PacketSetClientRadioactiveItems.CODEC, PacketSetClientRadioactiveItems::handle);
		registry.playToClient(PacketSetClientRadioactiveFluids.TYPE, PacketSetClientRadioactiveFluids.CODEC, PacketSetClientRadioactiveFluids::handle);
		registry.playToClient(PacketSetClientRadioactiveGases.TYPE, PacketSetClientRadioactiveGases.CODEC, PacketSetClientRadioactiveGases::handle);

	}

	public static ResourceLocation id(String name) {
		return ResourceLocation.fromNamespaceAndPath(References.ID, name);
	}

}
