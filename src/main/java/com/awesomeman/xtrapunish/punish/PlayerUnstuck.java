package com.awesomeman.xtrapunish.punish;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.awesomeman.xtrapunish.XtraPunish;

/**
 * Frees a player, allowing them to move once more.
 */
public class PlayerUnstuck implements CommandExecutor {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = args.<Player>getOne("player").get();
        if(XtraPunish.instance.stuckManager.setPlayerUnstuck(player)) {
            src.sendMessage(Text.of(TextColors.GREEN, "Player " + player.getName() + " is free once more!"));
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Player already free!"));
        }
        return CommandResult.success();
    }
}
