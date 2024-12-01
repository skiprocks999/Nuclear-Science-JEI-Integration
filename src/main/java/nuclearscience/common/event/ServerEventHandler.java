package nuclearscience.common.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import nuclearscience.References;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.RadioactiveFluidRegister;
import nuclearscience.common.reloadlistener.RadioactiveGasRegister;
import nuclearscience.common.reloadlistener.RadioactiveItemRegister;

@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.GAME)
public class ServerEventHandler {

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		event.addListener(RadioactiveItemRegister.INSTANCE);
		event.addListener(AtomicAssemblerBlacklistRegister.INSTANCE);
		event.addListener(RadioactiveFluidRegister.INSTANCE);
		event.addListener(RadioactiveGasRegister.INSTANCE);
	}

	@SubscribeEvent
	public static void serverStartedHandler(ServerStartedEvent event) {
		RadioactiveItemRegister.INSTANCE.generateTagValues();
		AtomicAssemblerBlacklistRegister.INSTANCE.generateTagValues();
		RadioactiveFluidRegister.INSTANCE.generateTagValues();
		RadioactiveGasRegister.INSTANCE.generateTagValues();
	}

}
