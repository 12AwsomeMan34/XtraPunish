/*
The MIT License (MIT)

Copyright © 2016 12AwesomeMan34 / 12AwsomeMan34

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

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
        src.sendMessage(Text.of(TextColors.GREEN, "/punish burn <player> - ", TextColors.GOLD, "Sets a player on fire!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish spam <player> - ", TextColors.GOLD, "Spams a player's chat with random charactors."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish creeper <player> - ", TextColors.GOLD, "Spawns a very powerful charged creeper onto the target player!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish explode <player> - ", TextColors.GOLD, "Creates an explosion at the target player's location."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish stuck <player> - ", TextColors.GOLD, "Prevents a player from moving at all!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish unstuck <player> - ", TextColors.GOLD, "Frees a player if they were stuck!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish broadcast <message> - ", TextColors.GOLD, "Broadcasts a message to the server non-stop!"));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish stop - ", TextColors.GOLD, "Stops the broadcast."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish starve <player> - ", TextColors.GOLD, "Sets a player's food to zero."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish anvil <player> - ", TextColors.GOLD, "Drops three anvils onto the player."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish horde <player> - ", TextColors.GOLD, "Spawns a horde of creepers onto the player."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish cobweb <player> - ", TextColors.GOLD, "Spawns cobweb around the player. The cobweb will disappear when the player exits the cobwebs."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish drop <player> - ", TextColors.GOLD, "Drops a player from 150 blocks in the air."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish strike <player> - ", TextColors.GOLD, "Strikes a player with lightning."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish glass <player> - ", TextColors.GOLD, "Teleports a player high into the sky with a glass block beneath them."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish popular <player> - ", TextColors.GOLD, "Teleports all player's in a server to the specified player."));
        src.sendMessage(Text.of(TextColors.GREEN, "/punish version - ", TextColors.GOLD, "Displays the current version running and other information."));
        return CommandResult.success();
    }
}
