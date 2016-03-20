/**
 * This file is part of XtraPunish, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2016 XtraStudio <https://github.com/XtraStudio>
 * Copyright (c) Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.awesomeman.xtrapunish.punish;

import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.awesomeman.xtrapunish.api.punish.Punishment;
import com.flowpowered.math.vector.Vector3d;

/**
 * Spawns bedrock around a player.
 */
public class PlayerBedrock implements Punishment {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if(!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish trap <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();
        
        Location<World> loc = player.getLocation();
        // Store the bottom so we can set the player's location later
        Location<World> bottomLoc = loc.getRelative(Direction.DOWN);
        bottomLoc.setBlockType(BlockTypes.BEDROCK);
        loc.getRelative(Direction.UP).getRelative(Direction.UP).setBlockType(BlockTypes.BEDROCK);
        // Sides have to be two high, so we just create a location object for the initial layer
        Location<World> sideLoc1 = player.getLocation().getRelative(Direction.NORTH);
        Location<World> sideLoc2 = player.getLocation().getRelative(Direction.EAST);
        Location<World> sideLoc3 = player.getLocation().getRelative(Direction.SOUTH);
        Location<World> sideLoc4 = player.getLocation().getRelative(Direction.WEST);
        sideLoc1.setBlockType(BlockTypes.BEDROCK);
        sideLoc2.setBlockType(BlockTypes.BEDROCK);
        sideLoc3.setBlockType(BlockTypes.BEDROCK);
        sideLoc4.setBlockType(BlockTypes.BEDROCK);
        // Now for the second layer, we can just set it on the location
        sideLoc1.getRelative(Direction.UP).setBlockType(BlockTypes.BEDROCK);
        sideLoc2.getRelative(Direction.UP).setBlockType(BlockTypes.BEDROCK);
        sideLoc3.getRelative(Direction.UP).setBlockType(BlockTypes.BEDROCK);
        sideLoc4.getRelative(Direction.UP).setBlockType(BlockTypes.BEDROCK);
        
        // Set the player's location to prevent the player being stuck in the bedrock, or out of the trap
        player.setLocation(player.getLocation().setPosition(new Vector3d(
                Math.floor(bottomLoc.getX()) + 0.5,
                Math.floor(player.getLocation().getY()),
                Math.floor(bottomLoc.getZ()) + 0.5)));
        
        src.sendMessage(Text.of(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Player " + player.getName() + " is now encased in bedrock!")));
        return CommandResult.success();
    }

    @Override
    public String permission() {
        return "xtrapunish.trap";
    }

    @Override
    public Text description() {
        return Text.of("Traps a player in cold bedrock!");
    }

    @Override
    public Text helpDescription() {
        return Text.of(TextColors.GREEN, "/punish trap <player> - ", TextColors.GOLD, "Traps a player in bedrock!");
    }

    @Override
    public Optional<CommandElement> arguments() {
        return Optional.of(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))));
    }

    @Override
    public String[] command() {
        return new String[] { "trap" };
    }
}
