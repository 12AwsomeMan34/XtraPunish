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

import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.explosion.Explosion;

import com.awesomeman.xtrapunish.manager.Managers;
import com.awesomeman.xtrapunish.util.CmdUtil;
import com.awesomeman.xtrapunish.util.CmdUtil.UndoSuccess;
import com.awesomeman.xtrapunish.util.CommandBase;

public class PlayerExplode implements CommandBase {

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if (!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish explode <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();

        Explosion explosion = Explosion.builder()
                .world(player.getWorld()).origin(player.getLocation().getPosition())
                .radius(4)
                .shouldDamageEntities(true)
                .shouldBreakBlocks(true).build();
        Managers.explosionManager.addExplosion(explosion);
        player.getWorld().triggerExplosion(explosion);
        return CommandResult.success();
    }

    @Override
    public String description() {
        return "Creates an explosion on a player!";
    }

    @Override
    public String[] command() {
        return new String[] {"explode"};
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
        if (Managers.explosionManager.explosionHistory.isEmpty()) {
            return UndoSuccess.FAILURE_NO_HISTORY;
        }

        Set<Transaction<BlockSnapshot>> snapshots = Managers.explosionManager.explosionHistory
                .get(Managers.explosionManager.explosionHistory.size() - 1);
        for (Transaction<BlockSnapshot> snapshot : snapshots) {
            if (snapshot.getOriginal().getLocation().isPresent()) {
                snapshot.getOriginal().getLocation().get().setBlock(snapshot.getOriginal().getState());
            }
        }
        return CmdUtil.UndoSuccess.SUCCESS;
    }

    @Override
    public boolean supportsUndo() {
        return true;
    }

    @Override
    public String permission() {
        return "xtrapunish.explode";
    }

    @Override
    public Optional<String> argText() {
        return Optional.of("[player]");
    }
}
