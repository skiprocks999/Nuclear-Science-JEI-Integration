package nuclearscience.common.packet.type.client;

import electrodynamics.api.gas.Gas;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;
import nuclearscience.api.radiation.util.RadiationShielding;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.common.reloadlistener.*;
import nuclearscience.common.tile.TileQuantumTunnel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

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

    public static void handleSetClientTunnelFrequencies(HashMap<UUID, HashSet<TunnelFrequency>> frequencies, TunnelFrequencyBuffer buffer, BlockPos tilePos) {
        if(Minecraft.getInstance().level.getBlockEntity(tilePos) instanceof TileQuantumTunnel tunnel) {
            tunnel.clientFrequencies = frequencies;
            tunnel.clientBuffer = buffer;
        }
    }
}
