package nuclearscience.client.screen;

import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerElectromagneticGateway;
import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;
import nuclearscience.prefab.utils.NuclearDisplayUnits;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ScreenElectromagneticGateway extends GenericScreen<ContainerElectromagneticGateway> {

    private boolean needsUpdate = true;
    private ScreenComponentEditBox box;

    public ScreenElectromagneticGateway(ContainerElectromagneticGateway container, Inventory inv, Component title) {
        super(container, inv, title);

        addComponent(new ScreenComponentSimpleLabel(10, 25, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("electromagneticswitch.targetspeed")));

        addEditBox(box = new ScreenComponentEditBox(10, 40, 120, 15, getFontRenderer()).setFilter(ScreenComponentEditBox.POSITIVE_DECIMAL).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(20).setResponder(val -> {

            TileElectromagneticGateway tile = menu.getSafeHost();

            if(tile == null) {
                return;
            }

            float temp = 0.0F;

            try {
                temp = Float.parseFloat(val);
            } catch (Exception e) {

            }

            if(temp < 0.0F) {
                temp = 0.0F;
            } else if (temp > 100.0F) {
                temp = 100.0F;
            }

            tile.targetSpeed.set(temp);

        }));

        addComponent(new ScreenComponentSimpleLabel(132, 43, 10, Color.TEXT_GRAY, DisplayUnit.PERCENTAGE.getSymbol().copy().append(NuclearDisplayUnits.SPEEDOFLIGHT.getSymbol())));

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);

        if(needsUpdate && getMenu().getSafeHost() instanceof TileElectromagneticGateway gateway) {
            box.setValue(gateway.targetSpeed.get() + "");
            needsUpdate = false;
        }
    }


}
