package nuclearscience.datagen.client;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceSounds;

public class NuclearScienceSoundProvider extends SoundDefinitionsProvider {

	public NuclearScienceSoundProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, References.ID, helper);
	}

	@Override
	public void registerSounds() {
		add(NuclearScienceSounds.SOUND_GASCENTRIFUGE);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_1);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_2);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_3);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_4);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_5);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_6);
		add(NuclearScienceSounds.SOUND_NUCLEARBOILER);
		add(NuclearScienceSounds.SOUND_SIREN);
		add(NuclearScienceSounds.SOUND_TURBINE);
		add(NuclearScienceSounds.SOUND_LOGISTICSCONTROLLER);
	}

	private void add(DeferredHolder<SoundEvent, SoundEvent> sound) {
		add(sound.get(), SoundDefinition.definition().subtitle("subtitles." + References.ID + "." + sound.getId().getPath()).with(SoundDefinition.Sound.sound(sound.getId(), SoundDefinition.SoundType.SOUND)));
	}

}
