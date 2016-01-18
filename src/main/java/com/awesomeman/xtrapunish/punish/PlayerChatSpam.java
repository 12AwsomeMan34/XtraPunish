package com.awesomeman.xtrapunish.punish;

import java.util.Random;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * Spams a player's chat with random characters.
 */
public class PlayerChatSpam implements CommandExecutor {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = args.<Player>getOne("player").get();
        Random random = new Random();
        String message = "";
        char letter;
        
        // We send 40 messages
        for(int i = 0; i < 40; i++) {
            // With 50 chars in each
            for(int i2 = 0; i2 < 50; i2++) {
                letter = (char) (random.nextInt(26) + 'a');
                message += letter;
            }
            player.sendMessage(Text.of(message));
            message = "";
        }
        
        src.sendMessage(Text.of(TextColors.GREEN, "Success! Player " + player.getName() + " cannot see his chat!"));
        return CommandResult.success();
    }
}
