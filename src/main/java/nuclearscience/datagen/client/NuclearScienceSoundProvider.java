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
		add(NuclearScienceSounds.SOUND_GEIGER);
		add(NuclearScienceSounds.SOUND_NUCLEARBOILER);
		add(NuclearScienceSounds.SOUND_SIREN);
		add(NuclearScienceSounds.SOUND_TURBINE);
	}

	private void add(DeferredHolder<SoundEvent, SoundEvent> sound) {
		add(sound.get(), SoundDefinition.definition().subtitle("subtitles." + References.ID + "." + sound.getId().getPath()).with(SoundDefinition.Sound.sound(sound.getId(), SoundDefinition.SoundType.SOUND)));
	}

}
