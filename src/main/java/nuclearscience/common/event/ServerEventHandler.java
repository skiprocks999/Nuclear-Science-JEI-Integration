package nuclearscience.common.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import nuclearscience.References;
import nuclearscience.common.command.CommandWipeAllFrequencies;
import nuclearscience.common.command.CommandWipePublicFrequencies;
import nuclearscience.common.command.CommandWipeRadiationSources;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.AtomicAssemblerWhitelistRegister;
import nuclearscience.common.reloadlistener.RadiationShieldingRegister;
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
		event.addListener(RadiationShieldingRegister.INSTANCE);
		event.addListener(AtomicAssemblerWhitelistRegister.INSTANCE);
	}

	@SubscribeEvent
	public static void serverStartedHandler(ServerStartedEvent event) {
		RadioactiveItemRegister.INSTANCE.generateTagValues();
		AtomicAssemblerBlacklistRegister.INSTANCE.generateTagValues();
		RadioactiveFluidRegister.INSTANCE.generateTagValues();
		RadioactiveGasRegister.INSTANCE.generateTagValues();
		RadiationShieldingRegister.INSTANCE.generateTagValues();
		AtomicAssemblerWhitelistRegister.INSTANCE.generateTagValues();
	}

	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		CommandWipeAllFrequencies.register(event.getDispatcher());
		CommandWipePublicFrequencies.register(event.getDispatcher());
		CommandWipeRadiationSources.register(event.getDispatcher());
	}


}
