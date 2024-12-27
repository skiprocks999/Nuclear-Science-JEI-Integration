package nuclearscience.client.screen;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentCustomRender;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import nuclearscience.client.screen.util.GenericInterfaceBoundScreen;
import nuclearscience.common.inventory.container.ContainerMonitorModule;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileMonitorModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFissionInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFusionInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileMSInterface;
import nuclearscience.common.tile.reactor.moltensalt.TileFreezePlug;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.prefab.utils.NuclearTextUtils;

import java.util.List;

public class ScreenMonitorModule extends GenericInterfaceBoundScreen<ContainerMonitorModule> {

    private boolean hidden = false;

    private static final ITexture EMPTY_FUEL = NuclearIconTypes.FUEL_CELL_DARK;

    public ScreenMonitorModule(ContainerMonitorModule container, Inventory inv, Component title) {
        super(container, inv, title, true, false);

        for (int i = 0; i < getMenu().slots.size(); i++) {

            ((SlotGeneric) getMenu().slots.get(i)).setActive(false);

        }

        addComponent(new ScreenComponentCustomRender(0, 0, graphics -> {
            if(hidden) {
                return;
            }

            TileMonitorModule tile = menu.getSafeHost();

            if(tile == null) {
                return;
            }

            GenericTileInterface.InterfaceType type = GenericTileInterface.InterfaceType.values()[tile.interfaceType.get()];

            Font font = getFontRenderer();

            int guiWidth = (int) getGuiWidth();
            int guiHeight = (int) getGuiHeight();

            graphics.fill(guiWidth + 17, guiHeight + 17, guiWidth + 159, guiHeight + 149, new Color(112, 112, 112, 255).color());

            if(!tile.linked.get() || type == GenericTileInterface.InterfaceType.NONE || tile.interfaceLocation.get().equals(BlockEntityUtils.OUT_OF_REACH)) {
                graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                return;
            }

            BlockEntity blockEntity = tile.getLevel().getBlockEntity(tile.interfaceLocation.get());

            switch (type) {
                case FISSION :

                    if(!(blockEntity instanceof TileFissionInterface)) {
                        graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                        return;
                    }

                    TileFissionInterface fissionInterface = (TileFissionInterface) blockEntity;

                    if(fissionInterface.reactor == null || !fissionInterface.reactor.valid() || !(fissionInterface.reactor.getSafe() instanceof TileFissionReactorCore)) {
                        graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                        return;
                    }

                    TileFissionReactorCore fissionCore = fissionInterface.reactor.getSafe();

                    graphics.renderItem(GenericTileInterface.getItemFromType(type), guiWidth + 80, guiHeight + 20);

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.temperature", ChatFormatter.getChatDisplayShort(TileFissionReactorCore.getActualTemp(fissionCore.temperature.get()), DisplayUnit.TEMPERATURE_CELCIUS).withStyle(ChatFormatting.GOLD)), guiWidth + 20, guiHeight + 45, Color.TEXT_GRAY.color(), false);

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.fuel"), guiWidth + 20, guiHeight + 65, Color.TEXT_GRAY.color(), false);

                    ComponentInventory inventory = fissionCore.getComponent(IComponentType.Inventory);

                    List<ItemStack> fuels = inventory.getItems().subList(0, 4);

                    int i = 0;

                    int empty = 0;

                    for(ItemStack item : fuels) {
                        if(item.isEmpty()) {
                            graphics.blit(EMPTY_FUEL.getLocation(), guiWidth + 20 + i * 20 + 2, guiHeight + 75 + 2, EMPTY_FUEL.textureU(), EMPTY_FUEL.textureV(), EMPTY_FUEL.textureWidth(), EMPTY_FUEL.textureHeight(), EMPTY_FUEL.imageWidth(), EMPTY_FUEL.imageHeight());
                            empty++;
                        } else {
                            graphics.renderItem(item, guiWidth + 20 + i * 20, guiHeight + 75);
                            graphics.renderItemDecorations(font, item, guiWidth + 20 + i * 20, guiHeight + 75);
                        }
                        i++;
                    }

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.other"), guiWidth + 110, guiHeight + 65, Color.TEXT_GRAY.color(), false);

                    ItemStack deuterium = inventory.getItem(TileFissionReactorCore.DUETERIUM_SLOT);

                    if(deuterium.isEmpty()) {
                        graphics.blit(EMPTY_FUEL.getLocation(), guiWidth + 110 + 2, guiHeight + 75 + 2, EMPTY_FUEL.textureU(), EMPTY_FUEL.textureV(), EMPTY_FUEL.textureWidth(), EMPTY_FUEL.textureHeight(), EMPTY_FUEL.imageWidth(), EMPTY_FUEL.imageHeight());
                    } else {
                        graphics.renderItem(deuterium, guiWidth + 110, guiHeight + 75);
                        graphics.renderItemDecorations(font, deuterium, guiWidth + 110, guiHeight + 75);
                    }

                    ItemStack tritium = inventory.getOutputContents().get(0);

                    if(tritium.isEmpty()) {
                        graphics.blit(EMPTY_FUEL.getLocation(), guiWidth + 130 + 2, guiHeight + 75 + 2, EMPTY_FUEL.textureU(), EMPTY_FUEL.textureV(), EMPTY_FUEL.textureWidth(), EMPTY_FUEL.textureHeight(), EMPTY_FUEL.imageWidth(), EMPTY_FUEL.imageHeight());
                    } else {
                        graphics.renderItem(tritium, guiWidth + 130, guiHeight + 75);
                        graphics.renderItemDecorations(font, tritium, guiWidth + 130, guiHeight + 75);
                    }

                    Component status = NuclearTextUtils.gui("logisticsnetwork.statusgood").withStyle(ChatFormatting.GREEN);

                    if(empty == 4) {
                        status = NuclearTextUtils.gui("logisticsnetwork.statusnofuel").withStyle(ChatFormatting.YELLOW);
                    } else if (fissionCore.temperature.get() > TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL) {
                        status = NuclearTextUtils.gui("logisticsnetwork.statusoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
                    }

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.status", status), guiWidth + 20, guiHeight + 105, Color.TEXT_GRAY.color(), false);

                    break;
                case MS:

                    if(!(blockEntity instanceof TileMSInterface)) {
                        graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                        return;
                    }

                    TileMSInterface msInterface = (TileMSInterface) blockEntity;

                    if(msInterface.reactor == null || !msInterface.reactor.valid() || !(msInterface.reactor.getSafe() instanceof TileMSReactorCore)) {
                        graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                        return;
                    }

                    TileMSReactorCore msCore = msInterface.reactor.getSafe();

                    graphics.renderItem(GenericTileInterface.getItemFromType(type), guiWidth + 80, guiHeight + 20);

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.temperature", ChatFormatter.getChatDisplayShort(msCore.temperature.get(), DisplayUnit.TEMPERATURE_CELCIUS).withStyle(ChatFormatting.GOLD)), guiWidth + 20, guiHeight + 45, Color.TEXT_GRAY.color(), false);

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.fuel"), guiWidth + 20, guiHeight + 65, Color.TEXT_GRAY.color(), false);

                    graphics.drawString(font, ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(msCore.currentFuel.get() / 1000.0, DisplayUnit.BUCKETS), ChatFormatter.getChatDisplayShort(TileMSReactorCore.FUEL_CAPACITY / 1000.0, DisplayUnit.BUCKETS)), guiWidth + 30, guiHeight + 75, Color.WHITE.color(), false);

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.waste"), guiWidth + 20, guiHeight + 90, Color.TEXT_GRAY.color(), false);

                    graphics.drawString(font, ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(msCore.currentWaste.get() / 1000.0, DisplayUnit.BUCKETS), ChatFormatter.getChatDisplayShort(TileMSReactorCore.WASTE_CAP / 1000.0, DisplayUnit.BUCKETS)), guiWidth + 30, guiHeight + 100, Color.WHITE.color(), false);

                    status = NuclearTextUtils.gui("logisticsnetwork.statusgood").withStyle(ChatFormatting.GREEN);

                    if (!(msCore.clientPlugCache.getSafe() instanceof TileFreezePlug)) {
                        status = NuclearTextUtils.gui("msreactor.status.nofreezeplug").withStyle(ChatFormatting.RED);
                    } else if (msCore.clientPlugCache.getSafe() instanceof TileFreezePlug plug && !plug.isFrozen()) {
                        status = NuclearTextUtils.gui("msreactor.warning.freezeoff").withStyle(ChatFormatting.YELLOW);
                    } else if (msCore.wasteIsFull.get()) {
                        status = NuclearTextUtils.gui("msreactor.status.wastefull").withStyle(ChatFormatting.YELLOW);
                    } else if (msCore.temperature.get() > TileMSReactorCore.MELTDOWN_TEMPERATURE) {
                        status = NuclearTextUtils.gui("logisticsnetwork.statusoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
                    }

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.status", status), guiWidth + 20, guiHeight + 115, Color.TEXT_GRAY.color(), false);


                    break;
                case FUSION:

                    if(!(blockEntity instanceof TileFusionInterface)) {
                        graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                        return;
                    }

                    TileFusionInterface fusionInterface = (TileFusionInterface) blockEntity;

                    if(fusionInterface.reactor == null || !fusionInterface.reactor.valid() || !(fusionInterface.reactor.getSafe() instanceof TileFusionReactorCore)) {
                        graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.unlinked"), guiWidth + 20, guiHeight + 20, Color.TEXT_GRAY.color(), false);
                        return;
                    }

                    TileFusionReactorCore fusionCore = fusionInterface.reactor.getSafe();
                    ComponentElectrodynamic electro = fusionCore.getComponent(IComponentType.Electrodynamic);

                    graphics.renderItem(GenericTileInterface.getItemFromType(type), guiWidth + 80, guiHeight + 20);

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.deuterium"), guiWidth + 20, guiHeight + 45, Color.TEXT_GRAY.color(), false);

                    graphics.drawString(font, ElectroTextUtils.ratio(Component.literal(fusionCore.deuterium.get() + ""), Component.literal(Constants.FUSIONREACTOR_MAXSTORAGE + "")), guiWidth + 30, guiHeight + 55, Color.WHITE.color(), false);

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.tritium"), guiWidth + 20, guiHeight + 70, Color.TEXT_GRAY.color(), false);

                    graphics.drawString(font, ElectroTextUtils.ratio(Component.literal(fusionCore.tritium.get() + ""), Component.literal(Constants.FUSIONREACTOR_MAXSTORAGE + "")), guiWidth + 30, guiHeight + 80, Color.WHITE.color(), false);

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.power"), guiWidth + 20, guiHeight + 95, Color.TEXT_GRAY.color(), false);

                    graphics.drawString(font, ChatFormatter.getChatDisplayShort(Math.min(1.0, electro.getJoulesStored() / Constants.FUSIONREACTOR_USAGE_PER_TICK) * 100.0, DisplayUnit.PERCENTAGE), guiWidth + 30, guiHeight + 105, Color.WHITE.color(), false);

                    status = NuclearTextUtils.gui("logisticsnetwork.statusgood").withStyle(ChatFormatting.GREEN);

                    if (fusionCore.tritium.get() < 1 || fusionCore.deuterium.get() < 1) {
                        status = NuclearTextUtils.gui("logisticsnetwork.statusnofuel").withStyle(ChatFormatting.RED);
                    } else if (electro.getJoulesStored() < Constants.FUSIONREACTOR_USAGE_PER_TICK) {
                        status = NuclearTextUtils.gui("logisticsnetwork.statusnopower").withStyle(ChatFormatting.YELLOW);
                    }

                    graphics.drawString(font, NuclearTextUtils.gui("logisticsnetwork.status", status), guiWidth + 20, guiHeight + 120, Color.TEXT_GRAY.color(), false);



                    break;
                default:
                    break;
            }

        }));

    }

    @Override
    public void updateNonSelectorVisibility(boolean visible) {
        hidden = !visible;
    }
}
