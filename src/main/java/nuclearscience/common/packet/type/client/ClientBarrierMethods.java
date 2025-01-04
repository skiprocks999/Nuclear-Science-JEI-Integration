package nuclearscience.common.packet.type.client;

import electrodynamics.api.gas.Gas;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import nuclearscience.api.network.reactorlogistics.Interface;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;
import nuclearscience.api.radiation.util.RadiationShielding;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.common.reloadlistener.*;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
        if (Minecraft.getInstance().level.getBlockEntity(tilePos) instanceof TileQuantumTunnel tunnel) {
            tunnel.clientFrequencies = frequencies;
            tunnel.clientBuffer = buffer;
        }
    }

    public static void handleSetClientInterfaces(BlockPos pos, List<Interface> interfaces) {
        if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof GenericTileInterfaceBound tunnel) {
            tunnel.clientInterfaces.clear();
            tunnel.clientInterfaces.addAll(interfaces);
            //tunnel.clientInterfaces.add(new Interface(new BlockPos(-31000000, -31000000, -31000000), GenericTileInterface.InterfaceType.MS));
        }
    }
}
