package nuclearscience.common.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import nuclearscience.References;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.RadioactiveItemLoader;

@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.GAME)
public class ServerEventHandler {

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		event.addListener(RadioactiveItemLoader.INSTANCE);
		event.addListener(AtomicAssemblerBlacklistRegister.INSTANCE);
	}

	@SubscribeEvent
	public static void serverStartedHandler(ServerStartedEvent event) {
		RadioactiveItemLoader.INSTANCE.generateTagValues();
		AtomicAssemblerBlacklistRegister.INSTANCE.generateTagValues();
	}

}
