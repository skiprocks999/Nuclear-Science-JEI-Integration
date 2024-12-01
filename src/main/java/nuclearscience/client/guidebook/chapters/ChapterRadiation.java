package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import nuclearscience.References;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.common.reloadlistener.RadioactiveItemRegister;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceItems;

import java.util.Map;

public class ChapterRadiation extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/item/uranium235.png"));

	public ChapterRadiation(Module module) {
		super(module);
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return NuclearTextUtils.guidebook("chapter.radiation");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l1")).setIndentions(1));
		blankLine();
		for (Map.Entry<Item, RadioactiveObject> entry : RadioactiveItemRegister.getValues().entrySet()) {
			pageData.add(new TextWrapperObject(entry.getKey().getDescription()).setSeparateStart());
			pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.radrating", entry.getValue().amount())).setSeparateStart().setIndentions(1));
		}
		blankLine();
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l2")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.hazmatsuit").withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l3")));
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_PLATE.getValue(SubtypePlate.lead).getDescription().copy().withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l4")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l5")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l6")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_ANTIDOTE.get().getDescription().copy().withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.radiation.l7")));

	}

}
