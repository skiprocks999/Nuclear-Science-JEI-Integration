package nuclearscience.common.packet.type.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

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
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.AtomicAssemblerWhitelistRegister;
import nuclearscience.common.reloadlistener.RadiationShieldingRegister;
import nuclearscience.common.reloadlistener.RadioactiveFluidRegister;
import nuclearscience.common.reloadlistener.RadioactiveGasRegister;
import nuclearscience.common.reloadlistener.RadioactiveItemRegister;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;

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

    public static void handleSetAtomicAssemblerClientWhitelistValues(HashSet<Item> items) {
        AtomicAssemblerWhitelistRegister.INSTANCE.setClientValues(items);
    }
}
