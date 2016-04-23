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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.extent.Extent;

import com.awesomeman.xtrapunish.XtraPunish;
import com.awesomeman.xtrapunish.util.CmdUtil;
import com.awesomeman.xtrapunish.util.CmdUtil.UndoSuccess;
import com.awesomeman.xtrapunish.util.CommandBase;

public class PlayerHorde implements CommandBase {

    private List<Set<Entity>> history = new ArrayList<>();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if (!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish horde <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();
        Extent extent = player.getLocation().getExtent();

        Set<Entity> entities = new HashSet<>();

        // 20 creepers
        for (int i = 0; i < 20; i++) {
            Optional<Entity> optional2 = extent.createEntity(EntityTypes.CREEPER, player.getLocation().getPosition());
            if (optional2.isPresent()) {
                extent.spawnEntity(optional2.get(), Cause.of(NamedCause.of("plugin", XtraPunish.instance)));
                entities.add(optional2.get());
            }
        }
        history.add(entities);

        src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Player ", TextColors.BLUE, player.getName(), TextColors.GOLD,
                " has been engulfed by the horde!"));
        return CommandResult.success();
    }

    @Override
    public String description() {
        return "Spawns a horde of creepers onto the player!";
    }

    @Override
    public String[] command() {
        return new String[] {"horde"};
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
        if (history.isEmpty()) {
            return UndoSuccess.FAILURE_NO_HISTORY;
        }

        Set<Entity> entitySet = history.get(history.size() - 1);
        for (Entity entity : entitySet) {
            entity.remove();
        }
        history.remove(entitySet);
        return CmdUtil.UndoSuccess.SUCCESS;
    }

    @Override
    public boolean supportsUndo() {
        return true;
    }

    @Override
    public String permission() {
        return "xtrapunish.horde";
    }

    @Override
    public Optional<String> argText() {
        return Optional.of("[player]");
    }
}
