package nuclearscience;

import electrodynamics.prefab.configuration.ConfigurationHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.block.voxelshapes.NuclearScienceVoxelShapeRegistry;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.RadioactiveItemLoader;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.UnifiedNuclearScienceRegister;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.MOD)
public class NuclearScience {

	public NuclearScience(IEventBus bus) {
		ConfigurationHandler.registerConfig(Constants.class);
		UnifiedNuclearScienceRegister.register(bus);
		RadiationRegister.init();
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClientRegister.setup();
		});
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		NuclearScienceTags.init();
		RadioactiveItemLoader.INSTANCE = new RadioactiveItemLoader().subscribeAsSyncable(NetworkHandler.CHANNEL);
		AtomicAssemblerBlacklistRegister.INSTANCE = new AtomicAssemblerBlacklistRegister().subscribeAsSyncable();
		NuclearScienceVoxelShapeRegistry.init();
	}

}
