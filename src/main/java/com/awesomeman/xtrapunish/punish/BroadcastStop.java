package com.awesomeman.xtrapunish.punish;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.awesomeman.xtrapunish.XtraPunish;

/**
 * Stops that annoying broadcast!
 */
public class BroadcastStop implements CommandExecutor {
    
    private XtraPunish instance;
    
    public BroadcastStop(XtraPunish instance) {
        this.instance = instance;
    }
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(instance.cancelBroadcast()) {
            src.sendMessage(Text.of(TextColors.RED, "Broadcast is not running!"));
        } else {
            src.sendMessage(Text.of(TextColors.GOLD, "Broadcast successfully stopped."));
        }
        return CommandResult.success();
    }
}
