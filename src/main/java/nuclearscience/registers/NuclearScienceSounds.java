package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;

public class NuclearScienceSounds {

	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, References.ID);

	public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_TURBINE = sound("turbine");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_NUCLEARBOILER = sound("nuclearboiler");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_GASCENTRIFUGE = sound("gascentrifuge");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_SIREN = sound("siren");

	public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_GEIGERCOUNTER_1 = sound("geigercountersound1");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_GEIGERCOUNTER_2 = sound("geigercountersound2");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_GEIGERCOUNTER_3 = sound("geigercountersound3");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_GEIGERCOUNTER_4 = sound("geigercountersound4");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_GEIGERCOUNTER_5 = sound("geigercountersound5");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_GEIGERCOUNTER_6 = sound("geigercountersound6");
	//public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_GEIGER = sound("geiger");

	private static DeferredHolder<SoundEvent, SoundEvent> sound(String name) {
		return SOUNDS.register(name, () -> SoundEvent.createFixedRangeEvent(ResourceLocation.parse(References.ID + ":" + name), 16.0F));
	}
}
