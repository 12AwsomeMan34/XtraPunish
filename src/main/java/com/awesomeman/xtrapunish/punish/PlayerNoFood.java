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

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.awesomeman.xtrapunish.util.CommandBase;
import com.awesomeman.xtrapunish.util.UndoSuccess;

public class PlayerNoFood implements CommandBase {

    private List<NoFoodStore> history = new ArrayList<>();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if (!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish starve <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();

        history.add(new NoFoodStore(player, player.foodLevel().get()));

        player.offer(player.foodLevel().set(0));
        src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Player " + player.getName() + " is now starving!"));
        return CommandResult.success();
    }

    @Override
    public String description() {
        return "Sets a player's hunger to zero!";
    }

    @Override
    public String[] command() {
        return new String[] {"starve"};
    }

    @Override
    public CommandSpec commandSpec() {
        return CommandSpec.builder()
                .permission("xtrapunish.nofood")
                .description(Text.of(Text.of(description())))
                .arguments(GenericArguments.optional(GenericArguments
                        .onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(this)
                .build();
    }

    @Override
    public UndoSuccess undoRecent() {
        NoFoodStore store = history.get(history.size() - 1);

        if (!store.player.isOnline()) {
            return UndoSuccess.FAILUE_NO_PLAYER;
        }
        history.remove(store);
        if (store.player.offer(store.player.foodLevel().set(store.hunger))
                .equals(DataTransactionResult.Type.SUCCESS)) {
            return UndoSuccess.SUCCESS;
        }
        return UndoSuccess.FAILUE_UNKNOWN;
    }

    private class NoFoodStore {

        public Player player;
        public Integer hunger;

        public NoFoodStore(Player player, Integer hunger) {
            this.player = player;
            this.hunger = hunger;
        }
    }
}
