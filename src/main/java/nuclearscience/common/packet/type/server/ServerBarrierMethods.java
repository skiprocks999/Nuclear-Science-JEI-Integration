package nuclearscience.common.packet.type.server;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import nuclearscience.api.quantumtunnel.FrequencyType;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;

import java.util.UUID;

public class ServerBarrierMethods {
    public static void createNewPacket(UUID creator, FrequencyType type, String name) {

        Level world = ServerLifecycleHooks.getCurrentServer().overworld();

        Player player = world.getPlayerByUUID(creator);

        if(player == null) {
            return;
        }

        UUID frequencyID = UUID.randomUUID();

        boolean valid = false;

        int tries = 0;

        while(!valid && tries < 10) {
            if(TunnelFrequencyManager.isValidTunnelID(frequencyID)) {
                valid = true;
                break;
            } else {
                frequencyID = UUID.randomUUID();
            }
            tries++;
        }

        if(valid) {
            if(type == FrequencyType.PRIVATE) {

                TunnelFrequencyManager.addPlayerFrequency(creator, TunnelFrequency.createPrivate(frequencyID, player, name));

            } else if(type == FrequencyType.PUBLIC) {

                TunnelFrequencyManager.addPublicFrequency(TunnelFrequency.createPublic(frequencyID, player, name));

            }
        }

    }
}
