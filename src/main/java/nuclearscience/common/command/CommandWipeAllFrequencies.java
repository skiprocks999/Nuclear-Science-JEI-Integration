package nuclearscience.common.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import nuclearscience.References;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;

public class CommandWipeAllFrequencies {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal(References.ID).requires(source -> source.hasPermission(4)).then(Commands.literal("wipeallfrequencies").executes(source -> {

            TunnelFrequencyManager.wipeAllFrequencies();
            source.getSource().sendSuccess(() -> Component.literal("wiped"), true);
            return 1;
        })));


    }


}
