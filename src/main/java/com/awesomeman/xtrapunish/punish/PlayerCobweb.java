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

import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.awesomeman.xtrapunish.XtraPunish;

public class PlayerCobweb implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if(!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish cobweb <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();
        Location<World> loc = player.getLocation();
        
        // We get all the locations and set them manually, because that's how we roll
        loc.setBlockType(BlockTypes.WEB);
        // Immediately around the player
        Location<World> loc1 = loc.getRelative(Direction.NORTH);
        Location<World> loc2 = loc.getRelative(Direction.EAST);
        Location<World> loc3 = loc.getRelative(Direction.SOUTH);
        Location<World> loc4 = loc.getRelative(Direction.WEST);
        // Corners
        Location<World> loc5 = loc1.getRelative(Direction.WEST);
        Location<World> loc6 = loc1.getRelative(Direction.EAST);
        Location<World> loc7 = loc3.getRelative(Direction.WEST);
        Location<World> loc8 = loc3.getRelative(Direction.EAST);
        // Tops
        Location<World> loc_1 = loc.getRelative(Direction.UP);
        Location<World> loc1_1 = loc1.getRelative(Direction.UP);
        Location<World> loc1_2 = loc2.getRelative(Direction.UP);
        Location<World> loc1_3 = loc3.getRelative(Direction.UP);
        Location<World> loc1_4 = loc4.getRelative(Direction.UP);
        Location<World> loc1_5 = loc5.getRelative(Direction.UP);
        Location<World> loc1_6 = loc6.getRelative(Direction.UP);
        Location<World> loc1_7 = loc7.getRelative(Direction.UP);
        Location<World> loc1_8 = loc8.getRelative(Direction.UP);
        
        loc1.setBlockType(BlockTypes.WEB);
        loc2.setBlockType(BlockTypes.WEB);
        loc3.setBlockType(BlockTypes.WEB);
        loc4.setBlockType(BlockTypes.WEB);
        loc5.setBlockType(BlockTypes.WEB);
        loc6.setBlockType(BlockTypes.WEB);
        loc7.setBlockType(BlockTypes.WEB);
        loc8.setBlockType(BlockTypes.WEB);
        loc_1.setBlockType(BlockTypes.WEB);
        loc1_1.setBlockType(BlockTypes.WEB);
        loc1_2.setBlockType(BlockTypes.WEB);
        loc1_3.setBlockType(BlockTypes.WEB);
        loc1_4.setBlockType(BlockTypes.WEB);
        loc1_5.setBlockType(BlockTypes.WEB);
        loc1_6.setBlockType(BlockTypes.WEB);
        loc1_7.setBlockType(BlockTypes.WEB);
        loc1_8.setBlockType(BlockTypes.WEB);
        // We made it to the end. Happy face. Now to track the player
        XtraPunish.instance.cobwebManager.addPlayer(player);
        
        src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Player " + player.getName() + " will be engulfed in cobwebs!"));
        
        return CommandResult.success();
    }
}
