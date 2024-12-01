package nuclearscience.common.packet.type.client;

import electrodynamics.api.gas.Gas;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import nuclearscience.api.radiation.util.RadiationShielding;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.common.reloadlistener.*;

import java.util.HashMap;
import java.util.HashSet;

public class ClientBarrierMethods {
    public static void handleSetAtomicAssemblerClientValues(HashSet<Item> items) {
        AtomicAssemblerBlacklistRegister.INSTANCE.setClientValues(items);
    }

    public static void handleSetClientRadioactiveItems(HashMap<Item, RadioactiveObject> items) {
        RadioactiveItemRegister.INSTANCE.setClientValues(items);
    }

    public static void handleSetClientRadioactiveFluids(HashMap<Fluid, RadioactiveObject> fluids) {
        RadioactiveFluidRegister.INSTANCE.setClientValues(fluids);
    }

    public static void handleSetClientRadioactiveGases(HashMap<Gas, RadioactiveObject> gases) {
        RadioactiveGasRegister.INSTANCE.setClientValues(gases);
    }

    public static void handleSetClientRadiationShielding(HashMap<Block, RadiationShielding> shielding) {
        RadiationShieldingRegister.INSTANCE.setClientValues(shielding);
    }
}
