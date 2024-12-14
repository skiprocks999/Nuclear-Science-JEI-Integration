package nuclearscience.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.reactor.moltensalt.TileMoltenSaltSupplier;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceItems;

public class ChapterMSReactor extends Chapter {

    private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceItems.ITEM_MSREACTORCORE.get());

    public ChapterMSReactor(Module module) {
        super(module);
    }

    @Override
    public ItemWrapperObject getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return NuclearTextUtils.guidebook("chapter.msreactor");
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l1")).setIndentions(1));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l2")).setIndentions(1).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_MSREACTORCORE.get().getDescription()).setIndentions(1).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_FREEZEPLUG.get().getDescription()).setIndentions(1).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_MOLTENSALTSUPPLIER.get().asItem().getDescription()).setIndentions(1).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l3")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor1.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor2.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l4")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor4-1.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor4-2.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l5", TileMoltenSaltSupplier.AMT_PER_SALT)).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor5-1.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor5-2.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l6")).setIndentions(1).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_MOLTENSALTPIPTE.getValue(SubtypeMoltenSaltPipe.vanadiumsteelceramic).getDescription()).setIndentions(1).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEM_HEATEXCHANGER.get().getDescription()).setIndentions(1).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l7")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor6.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor7.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor8.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor9.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor10.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor11.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l8")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/msreactor12.png")));

    }

}
