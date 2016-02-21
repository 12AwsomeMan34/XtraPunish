/*
The MIT License (MIT)

Copyright (c) 2016 12AwesomeMan34 / 12AwsomeMan34

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

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.extent.Extent;

import com.awesomeman.xtrapunish.api.punish.Punishment;

/**
 * Spawns tnt that will instantly explode on the player's location.
 */
public class PlayerExplode implements Punishment {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if(!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish explode <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();
        
        Extent extent = player.getLocation().getExtent();
        Optional<Entity> optional2 = extent.createEntity(EntityTypes.PRIMED_TNT, player.getLocation().getPosition());
        if(optional2.isPresent()) {
            PrimedTNT tnt = (PrimedTNT) optional2.get();
            // Temporary, update when spawncause is in api and impl
            extent.spawnEntity(tnt, Cause.of(src));
            src.sendMessage(Text.of(TextColors.GREEN, "Success!", TextColors.GOLD, " Kaboom!"));
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Unable to create tnt entity!"));
        }
        return CommandResult.success();
    }

    @Override
    public String permission() {
        return "xtrapunish.explode";
    }

    @Override
    public Text description() {
        return Text.of("Creates an explosion on a player!");
    }

    @Override
    public Text helpDescription() {
        return Text.of(TextColors.GREEN, "/punish explode <player> - ", TextColors.GOLD, "Creates an explosion at the target player's location.");
    }

    @Override
    public Optional<CommandElement> arguments() {
        return Optional.of(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))));
    }

    @Override
    public String command() {
        return "explode";
    }
}
