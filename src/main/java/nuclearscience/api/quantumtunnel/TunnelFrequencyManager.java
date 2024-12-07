package nuclearscience.api.quantumtunnel;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import nuclearscience.registers.NuclearScienceAttachmentTypes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class TunnelFrequencyManager {

    public static void addPlayerFrequency(UUID player, TunnelFrequency frequency) {
        ServerLevel level = getOverworld();
        HashMap<UUID, HashSet<TunnelFrequency>> map = level.getData(NuclearScienceAttachmentTypes.CHANNEL_MAP);
        if(!map.containsKey(player)) {
            map.put(player, new HashSet<>());
        }
        map.get(player).add(frequency);
        level.setData(NuclearScienceAttachmentTypes.CHANNEL_MAP, map);
    }

    public static void addPublicFrequency(TunnelFrequency frequency) {
        addPlayerFrequency(TunnelFrequency.PUBLIC_ID, frequency);
    }

    public static void removePlayerFrequency(UUID player, UUID requester, TunnelFrequency frequency) {
        if(!frequency.getCreatorId().equals(requester)) {
            return;
        }
        ServerLevel level = getOverworld();
        HashMap<UUID, HashSet<TunnelFrequency>> map = level.getData(NuclearScienceAttachmentTypes.CHANNEL_MAP);
        if(!map.containsKey(player)) {
            return;
        }
        map.get(player).remove(frequency);
        level.setData(NuclearScienceAttachmentTypes.CHANNEL_MAP, map);
    }

    public static void removePublicFrequency(UUID requester, TunnelFrequency frequency) {
        removePlayerFrequency(TunnelFrequency.PUBLIC_ID, requester, frequency);
    }

    public static boolean isValidTunnelID(UUID proposedFrequencyID) {
        ServerLevel level = getOverworld();
        HashMap<UUID, HashSet<TunnelFrequency>> map = level.getData(NuclearScienceAttachmentTypes.CHANNEL_MAP);
        for(Map.Entry<UUID, HashSet<TunnelFrequency>> entry : map.entrySet()) {
            for(TunnelFrequency id : entry.getValue()) {
                if(id.getId().equals(proposedFrequencyID)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void updatePlayerFrequencyName(UUID player, UUID requester, TunnelFrequency frequency) {
        if(!frequency.getCreatorId().equals(requester)) {
            return;
        }
        ServerLevel level = getOverworld();
        HashMap<UUID, HashSet<TunnelFrequency>> map = level.getData(NuclearScienceAttachmentTypes.CHANNEL_MAP);
        if(!map.containsKey(player)) {
            map.put(player, new HashSet<>());
        }
        map.get(player).remove(frequency);
        map.get(player).add(frequency);

        HashMap<TunnelFrequency, TunnelFrequencyBuffer> connectionMap = level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP);
        TunnelFrequencyBuffer buffer = connectionMap.getOrDefault(frequency, new TunnelFrequencyBuffer());
        connectionMap.remove(frequency);
        connectionMap.put(frequency, buffer);

        level.setData(NuclearScienceAttachmentTypes.CHANNEL_MAP, map);
        level.setData(NuclearScienceAttachmentTypes.TUNNEL_MAP, connectionMap);
    }

    public static void updatePublicFrequencyName(UUID requester, TunnelFrequency frequency) {
        updatePlayerFrequencyName(TunnelFrequency.PUBLIC_ID, requester, frequency);
    }

    public static HashMap<UUID, HashSet<TunnelFrequency>> getFrequenciesForPlayerClient(UUID playerID) {
        HashMap<UUID, HashSet<TunnelFrequency>> values = new HashMap<>();
        ServerLevel level = getOverworld();
        HashMap<UUID, HashSet<TunnelFrequency>> map = level.getData(NuclearScienceAttachmentTypes.CHANNEL_MAP);

        values.put(TunnelFrequency.PUBLIC_ID, map.getOrDefault(TunnelFrequency.PUBLIC_ID, new HashSet<>()));
        values.put(playerID, map.getOrDefault(playerID, new HashSet<>()));

        return values;


    }

    private static ServerLevel getOverworld() {
        return ServerLifecycleHooks.getCurrentServer().overworld();
    }

}
