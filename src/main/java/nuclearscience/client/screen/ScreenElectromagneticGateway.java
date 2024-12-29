package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerElectromagneticGateway;
import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ScreenElectromagneticGateway extends GenericScreen<ContainerElectromagneticGateway> {
    public ScreenElectromagneticGateway(ContainerElectromagneticGateway container, Inventory inv, Component title) {
        super(container, inv, title);

        addComponent(new ScreenComponentSimpleLabel(20, 30, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("electromagneticswitch.targetspeed")));

        addEditBox(new ScreenComponentEditBox(20, 110, 120, 15, getFontRenderer()).setFilter(ScreenComponentEditBox.POSITIVE_DECIMAL).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(20).setResponder(val -> {

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
            }

            tile.targetSpeed.set(temp);

        }));

    }
}
