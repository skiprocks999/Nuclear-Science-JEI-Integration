package nuclearscience.client.guidebook;

import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.client.guidebook.chapters.*;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ModuleNuclearScience extends Module {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/nuclearsciencelogo.png"));

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return NuclearTextUtils.guidebook(References.ID);
	}

	@Override
	public void addChapters() {
		chapters.add(new ChapterRadiation(this));
		chapters.add(new ChapterTurbines(this));
		chapters.add(new ChapterSteamFunnel(this));
		chapters.add(new ChapterGasCentrifuge(this));
		chapters.add(new ChapterFissionReactor(this));
		chapters.add(new ChapterRadioGenerator(this));
		chapters.add(new ChapterMSReactor(this));
		chapters.add(new ChapterFusionReactor(this));
		chapters.add(new ChapterParticleAccelerator(this));
		chapters.add(new ChapterQuantumTunnel(this));
		chapters.add(new ChapterOtherMachines(this));
		chapters.add(new ChapterMisc(this));
	}

}
