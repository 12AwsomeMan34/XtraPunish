package com.awesomeman.xtrapunish.punish;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

// NOTE: Do NOT line wrap this class
public class HelpCommand implements CommandExecutor {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of(TextColors.GREEN, "/punish help - ", TextColors.GOLD, "Display's this help list!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish trap <player> - ", TextColors.GOLD, "Traps a player in bedrock!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish bounce <player> - ", TextColors.GOLD, "Bounces a player high into the sky!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish burn <player> - ", TextColors.GOLD, "Sets a player on fire!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish spam <player> - ", TextColors.GOLD, "Spams a player's chat with random charactors."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish creeper <player> - ", TextColors.GOLD, "Spawns a very powerful charged creeper onto the target player!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish explode <player> - ", TextColors.GOLD, "Creates an explosion at the target player's location."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish stuck <player> - ", TextColors.GOLD, "Prevents a player from moving at all!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish unstuck <player> - ", TextColors.GOLD, "Frees a player if they were stuck!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish broadcast <message> - ", TextColors.GOLD, "Broadcasts a message to the server non-stop!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish stop - ", TextColors.GOLD, "Stops the broadcast."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish starve <player> - ", TextColors.GOLD, "Sets a player's food to zero."));
        return CommandResult.success();
    }
}
