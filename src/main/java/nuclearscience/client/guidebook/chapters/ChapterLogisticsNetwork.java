package nuclearscience.client.guidebook.chapters;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.compatibility.jei.JeiBuffer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileController;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceItems;

import java.util.ArrayList;
import java.util.List;

public class ChapterLogisticsNetwork extends Chapter {

    private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller));

    public ChapterLogisticsNetwork(Module module) {
        super(module);
    }

    @Override
    public AbstractGraphicWrapper<?> getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return NuclearTextUtils.guidebook("chapter.reactorlogistics");
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.l1", NuclearTextUtils.guidebook("chapter.reactorlogistics.network").withStyle(ChatFormatting.BOLD))).setIndentions(1));

        // Logistics Cable

        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
        pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, NuclearScienceItems.ITEMS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base)).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    List<FormattedCharSequence> tooltips = new ArrayList<>();
                    tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(NuclearScienceItems.ITEMS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base));
            }

        }));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.logisticscable")).setSeparateStart());

        // RL Controller

        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
        pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller)).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    List<FormattedCharSequence> tooltips = new ArrayList<>();
                    tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller));
            }

        }));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.logisticscontroller", ChatFormatter.getChatDisplayShort(TileController.USAGE * 20, DisplayUnit.WATT), ChatFormatter.getChatDisplayShort(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE, DisplayUnit.VOLTAGE))).setSeparateStart());

        // Fission Interface

        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
        pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface)).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    List<FormattedCharSequence> tooltips = new ArrayList<>();
                    tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface));
            }

        }));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.fissioninterface")).setSeparateStart());

        // MS Interface

        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
        pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface)).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    List<FormattedCharSequence> tooltips = new ArrayList<>();
                    tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface));
            }

        }));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.msinterface")).setSeparateStart());

        // Fusion Interface

        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusioninterface).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
        pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusioninterface)).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    List<FormattedCharSequence> tooltips = new ArrayList<>();
                    tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusioninterface));
            }

        }));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.fusioninterface")).setSeparateStart());

        // Linking GUI

        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.linkinggui").withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.linkinggui.l1")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/linkguitab.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.linkinggui.l2")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/linkgui.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.linkinggui.l3")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/linkguiselection.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.linkinggui.l4")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 150, 150, 150, ResourceLocation.fromNamespaceAndPath(References.ID, "textures/screen/guidebook/linkguideselection.png")));

        // Control Rod Module

        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.controlrodmodule).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
        pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.controlrodmodule)).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    List<FormattedCharSequence> tooltips = new ArrayList<>();
                    tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.controlrodmodule));
            }

        }));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.controlrodmodule")).setSeparateStart());

        // Supply Module

        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.supplymodule).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
        pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.supplymodule)).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    List<FormattedCharSequence> tooltips = new ArrayList<>();
                    tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.supplymodule));
            }

        }));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.supplymodule")).setSeparateStart());

        // Monitor Module

        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.monitormodule).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
        pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.monitormodule)).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    List<FormattedCharSequence> tooltips = new ArrayList<>();
                    tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.monitormodule));
            }

        }));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.monitormodule")).setSeparateStart());

        // Thermometer Module

        pageData.add(new TextWrapperObject(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.thermometermodule).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
        pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.thermometermodule)).onTooltip(new OnTooltip() {

            @Override
            public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
                if (JeiBuffer.isJeiInstalled()) {
                    List<FormattedCharSequence> tooltips = new ArrayList<>();
                    tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                    graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
                }

            }
        }).onKeyPress(new OnKeyPress() {

            @Override
            public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

            }

            @Override
            public Object getJeiLookup() {
                return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.thermometermodule));
            }

        }));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.reactorlogistics.thermometermodule")).setSeparateStart());

    }
}
