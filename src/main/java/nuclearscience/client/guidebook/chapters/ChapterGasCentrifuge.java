package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import nuclearscience.References;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceBlocks;

public class ChapterGasCentrifuge extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceBlocks.blockGasCentrifuge.asItem());

	public ChapterGasCentrifuge(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public IFormattableTextComponent getTitle() {
		return NuclearTextUtils.guidebook("chapter.centrifuge");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.centrifuge.l1")).setIndentions(1));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.centrifuge.l2")).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/gascentrifuge1.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/gascentrifuge2.png")));
	}

}
