package com.awesomeman.xtrapunish.punish;

import java.util.Optional;

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
        Optional<Player> optional = args.<Player>getOne("player");
        if(!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish unstuck Player"));
            return CommandResult.empty();
        }
        Player player = optional.get();
        
        if(XtraPunish.instance.stuckManager.setPlayerUnstuck(player)) {
            src.sendMessage(Text.of(TextColors.GREEN, "Player " + player.getName() + " is free once more!"));
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Player already free!"));
        }
        return CommandResult.success();
    }
}
