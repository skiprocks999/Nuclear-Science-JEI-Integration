package nuclearscience.prefab.utils;

import electrodynamics.api.electricity.formatting.IDisplayUnit;
import net.minecraft.network.chat.Component;

public class NuclearDisplayUnits {

    public static final IDisplayUnit RAD = new IDisplayUnit() {
        @Override
        public Component getSymbol() {
            return NuclearTextUtils.gui("displayunit.radsymbol");
        }

        @Override
        public Component getName() {
            return NuclearTextUtils.gui("displayunit.radname");
        }

        @Override
        public Component getNamePlural() {
            return NuclearTextUtils.gui("displayunit.radnameplural");
        }

        @Override
        public Component getDistanceFromValue() {
            return Component.literal(" ");
        }
    };

}
