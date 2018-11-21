/**
 * This file is part of XtraPunish, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2018 XtraStudio <https://github.com/XtraStudio>
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

package io.github.narikiro.xtrapunish.punish;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
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

import io.github.narikiro.xtrapunish.util.CmdUtil;
import io.github.narikiro.xtrapunish.util.CommandBase;
import io.github.narikiro.xtrapunish.util.CmdUtil.UndoSuccess;

public class PlayerPopular implements CommandBase {

    private List<PopularStore> history = new ArrayList<>();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if (!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: ", TextColors.GOLD, "/punish popular <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();

        List<Player> players = new ArrayList<>();
        List<Location<World>> locs = new ArrayList<>();

        for (Player player2 : Sponge.getServer().getOnlinePlayers()) {
            if (!player2.equals(player)) {
                players.add(player2);
                locs.add(player2.getLocation());
                player2.setLocation(player.getLocation());
            }
        }

        this.history.add(new PopularStore(players, locs));

        src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Player ", TextColors.BLUE, player.getName(), TextColors.GOLD,
                " is making new friends."));
        return CommandResult.success();
    }

    @Override
    public String description() {
        return "Teleports all players in a server to the specified player.";
    }

    @Override
    public String[] command() {
        return new String[] {"popular"};
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
        if (this.history.isEmpty()) {
            return UndoSuccess.FAILURE_NO_HISTORY;
        }

        PopularStore store = this.history.get(this.history.size() - 1);
        for (int i = 0; i < store.players.size(); i++) {
            store.players.get(i).setLocationSafely(store.locs.get(i));
        }
        this.history.remove(store);
        return CmdUtil.UndoSuccess.SUCCESS;
    }

    @Override
    public boolean supportsUndo() {
        return true;
    }

    @Override
    public String permission() {
        return "xtrapunish.popular";
    }

    public class PopularStore {

        public List<Player> players;
        public List<Location<World>> locs;

        public PopularStore(List<Player> players, List<Location<World>> locs) {
            this.players = players;
            this.locs = locs;
        }
    }

    @Override
    public Optional<String> argText() {
        return Optional.of("[player]");
    }
}
