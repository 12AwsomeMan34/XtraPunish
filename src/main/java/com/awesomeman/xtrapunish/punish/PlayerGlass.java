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
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.awesomeman.xtrapunish.util.AffectedBlocks;
import com.awesomeman.xtrapunish.util.CmdUtil;
import com.awesomeman.xtrapunish.util.CommandBase;

public class PlayerGlass implements CommandBase {

    private List<AffectedBlocks> history = new ArrayList<>();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if (!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish glass <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();
        Location<World> location = player.getLocation();

        if (location.add(0, 99, 0).getY() > 255) {
            src.sendMessage(Text.of(TextColors.RED, "Cannot set a block above the height limit! Try again at a lower altitude."));
            return CommandResult.empty();
        }

        List<Location<World>> locs = new ArrayList<>();
        List<BlockState> states = new ArrayList<>();
        locs.add(location.add(0, 99, 0));
        states.add(location.add(0, 99, 0).getBlock());
        history.add(new AffectedBlocks(locs, states));

        location.add(0, 99, 0).setBlockType(BlockTypes.GLASS);
        player.setLocation(location.add(0, 100, 0));

        src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.BLUE, player.getName(), TextColors.GOLD, " is now walking in the clouds."));
        return CommandResult.success();
    }

    @Override
    public String description() {
        return "Teleports a player into the sky and places them on a glass block.";
    }

    @Override
    public String[] command() {
        return new String[] {"glass"};
    }

    @Override
    public CommandSpec commandSpec() {
        return CommandSpec.builder()
                .permission(permission())
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

    @Override
    public String permission() {
        return "xtrapunish.glass";
    }

    @Override
    public Optional<String> argText() {
        return Optional.of("[player]");
    }
}
