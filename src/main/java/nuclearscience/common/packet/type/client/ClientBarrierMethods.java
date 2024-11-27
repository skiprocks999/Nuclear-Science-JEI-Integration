package nuclearscience.common.packet.type.client;

import net.minecraft.world.item.Item;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;

import java.util.HashSet;

public class ClientBarrierMethods {
    public static void handleSetAtomicAssemblerClientValues(HashSet<Item> items) {
        AtomicAssemblerBlacklistRegister.INSTANCE.setClientValues(items);
    }
}
