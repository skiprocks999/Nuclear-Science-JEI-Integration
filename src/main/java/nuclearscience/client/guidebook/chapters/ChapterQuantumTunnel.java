package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceItems;

public class ChapterQuantumTunnel extends Chapter {

    private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceItems.ITEM_QUANTUMTUNNEL.get());

    public ChapterQuantumTunnel(Module module) {
        super(module);
    }

    @Override
    public ItemWrapperObject getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return NuclearTextUtils.guidebook("chapter.quantumtunnel");
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l1")).setIndentions(1));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l2.1")).setSeparateStart().setIndentions(1));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.items")).setSeparateStart().setIndentions(1));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.fluids")).setSeparateStart().setIndentions(1));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.gases")).setSeparateStart().setIndentions(1));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.electricity")).setSeparateStart().setIndentions(1));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l2.2")).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l3.1")).setSeparateStart().setIndentions(1));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/quantumtunnel1.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l3.2")).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l4.1")).setSeparateStart().setIndentions(1));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/quantumtunnel2.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l4.2")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/quantumtunnel3.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l4.3")).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l5.1")).setSeparateStart().setIndentions(1));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/quantumtunnel4.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l5.2")).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l6.1")).setSeparateStart().setIndentions(1));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/quantumtunnel5.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l6.2")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/quantumtunnel6.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/quantumtunnel7.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l6.3")).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l7.1")).setSeparateStart().setIndentions(1));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/quantumtunnel8.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l7.2")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/quantumtunnel9.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l7.3")).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l8.1")).setSeparateStart().setIndentions(1));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/quantumtunnel10.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.quantumtunnel.l8.2")).setSeparateStart());
    }

}
