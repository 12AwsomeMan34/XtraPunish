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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.awesomeman.xtrapunish.punish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.awesomeman.xtrapunish.util.AffectedBlocks;
import com.awesomeman.xtrapunish.util.CmdUtil;
import com.awesomeman.xtrapunish.util.CommandBase;

public class PlayerCobweb implements CommandBase {

    private List<AffectedBlocks> history = new ArrayList<>();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if (!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish cobweb <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();
        Location<World> loc = player.getLocation();

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

        List<Location<World>> locs = new ArrayList<>();

        locs.addAll(Arrays.asList(loc, loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc_1,
                loc1_1, loc1_2, loc1_3, loc1_4, loc1_5, loc1_6, loc1_7, loc1_8));

        List<BlockState> states = new ArrayList<>();

        for (Location<World> location : locs) {
            states.add(location.getBlock());
            location.setBlockType(BlockTypes.WEB);
        }
        history.add(new AffectedBlocks(locs, states));

        src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Player " + player.getName() + " will be engulfed in cobwebs!"));

        return CommandResult.success();
    }

    @Override
    public String description() {
        return "Spawns cobwebs around a player.";
    }

    @Override
    public String[] command() {
        return new String[] {"cobweb"};
    }

    @Override
    public CommandSpec commandSpec() {
        return CommandSpec.builder()
                .permission("xtrapunish.cobweb")
                .description(Text.of(description()))
                .arguments(GenericArguments.optional(GenericArguments
                        .onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(this)
                .build();
    }

    @Override
    public CmdUtil.UndoSuccess undoRecent() {
        return CmdUtil.removeBlockHistory(history);
    }

    @Override
    public boolean supportsUndo() {
        return true;
    }
}
