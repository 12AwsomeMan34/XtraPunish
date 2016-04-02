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

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.data.manipulator.mutable.entity.IgniteableData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.awesomeman.xtrapunish.util.AffectedBlocks;
import com.awesomeman.xtrapunish.util.CommandBase;

public class PlayerBurning implements CommandBase {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        boolean flag = args.hasAny("a");
        if(!optional.isPresent() && !flag) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish burn <player> [-a]"));
            return CommandResult.empty();
        }
        
        IgniteableData data = Sponge.getGame().getDataManager().getManipulatorBuilder(IgniteableData.class).get().create();
        if(flag) {
            for(Player player : Sponge.getServer().getOnlinePlayers()) {
                if(!src.equals(player)) {
                    player.offer(data.fireTicks().set(data.fireTicks().getMaxValue()));
                }
            }
            src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "All players on the server are now a little warm!"));
        } else {
            Player player = optional.get();
            player.offer(data.fireTicks().set(data.fireTicks().getMaxValue()));
            src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Player " + player.getName() + " is now a little warm!"));
        }
        return CommandResult.success();
    }

    @Override
    public String permission() {
        return "xtrapunish.burning";
    }

    @Override
    public Text description() {
        return Text.of("Sets a player on fire!");
    }

    @Override
    public Text helpDescription() {
        return Text.of(TextColors.GREEN, "/punish burn <player> [-a] - ", TextColors.GOLD, "Sets a player on fire!");
    }

    @Override
    public Optional<CommandElement> arguments() {
        return Optional.of(GenericArguments.flags().flag("a")
                .buildWith(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))));
    }

    @Override
    public String[] command() {
        return new String[] { "burn" };
    }

    @Override
    public Optional<List<AffectedBlocks>> affectedBlocks() {
        return Optional.empty();
    }
}
