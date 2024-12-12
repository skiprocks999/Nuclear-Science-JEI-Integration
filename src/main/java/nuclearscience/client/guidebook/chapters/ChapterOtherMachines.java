package nuclearscience.client.guidebook.chapters;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.common.settings.Constants;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceItems;

public class ChapterOtherMachines extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceItems.ITEM_ATOMICASSEMBLER.get());

	public ChapterOtherMachines(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return NuclearTextUtils.guidebook("chapter.othermachines");
	}

	@Override
	public void addData() {

		// Teleporter
		pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_TELEPORTER.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, NuclearScienceItems.ITEM_TELEPORTER.get()));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.teleporter1", ChatFormatter.getChatDisplayShort(Constants.TELEPORTER_USAGE_PER_TELEPORT, DisplayUnit.JOULES))).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/teleporter1.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.teleporter2", NuclearScienceItems.ITEM_FREQUENCYCARD.get().getDescription().copy().withStyle(ChatFormatting.BOLD))).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.teleporter3")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/teleporter2.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.teleporter4")).setSeparateStart());

		// Chunkloader
		pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_CHUNKLOADER.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, NuclearScienceItems.ITEM_CHUNKLOADER.get()));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.chunkloader1")).setIndentions(1).setSeparateStart());

		// Atomic Assembler
		pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_ATOMICASSEMBLER.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, NuclearScienceItems.ITEM_ATOMICASSEMBLER.get()));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.atomicassembler1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.atomicassembler2")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/atomicassembler1.png")));
		pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.othermachines.atomicassembler3")).setIndentions(1).setSeparateStart());
	}

}
