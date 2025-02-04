package net.earthcomputer.clientcommands.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.earthcomputer.clientcommands.features.ClientCommandFunctions;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class CFunctionCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("cfunction")
            .then(argument("function", greedyString())
                .suggests((context, builder) -> CommandSource.suggestMatching(ClientCommandFunctions.allFunctions(), builder))
                .executes(ctx -> run(ctx.getSource(), getString(ctx, "function")))));
    }

    private static int run(FabricClientCommandSource source, String function) throws CommandSyntaxException {
        return ClientCommandFunctions.executeFunction(ClientCommandManager.getActiveDispatcher(), source, function, result -> source.sendFeedback(Text.translatable("commands.cfunction.success", result, function)));
    }
}
