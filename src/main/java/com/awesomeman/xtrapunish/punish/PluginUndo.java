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

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.awesomeman.xtrapunish.XtraPunish;
import com.awesomeman.xtrapunish.api.punish.Punishment;
import com.awesomeman.xtrapunish.util.AffectedBlocks;

public class PluginUndo implements Punishment {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<String> optional = args.<String>getOne("command");
        if(!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Command argument not specified! Correct usage: /punish undo <command>"));
            return CommandResult.empty();
        }
        String command = optional.get();
        Punishment punishment = null;
        
        // Cycle through commands list
        for(int i = 0; i < XtraPunish.instance.commandList.size(); i++) {
            // Cycle through any possible aliases
            for(String command2 : XtraPunish.instance.commandList.get(i)) {
                // Check if the possible alias matches our string
                if(command.toLowerCase().equals(command2.toLowerCase())) {
                    punishment = XtraPunish.instance.punishments.get(i);
                }
            }
        }
        
        if(punishment == null) {
            src.sendMessage(Text.of(TextColors.RED, "Command not found!"));
            return CommandResult.empty();
        }
        if(!punishment.affectedBlocks().isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Command does not support undo."));
            return CommandResult.empty();
        }
        if(punishment.affectedBlocks().get().isEmpty()) {
            src.sendMessage(Text.of(TextColors.RED, "No history found for command ", punishment.command()[0], "."));
            return CommandResult.empty();
        }
        List<AffectedBlocks> affected = punishment.affectedBlocks().get();
        AffectedBlocks blocks = affected.get(affected.size() - 1);
        
        for(int i = 0; i < blocks.loc.size(); i++) {
            blocks.loc.get(i).setBlock(blocks.oldState.get(i));
        }
        
        punishment.affectedBlocks().get().remove(blocks);
        
        src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Command has been undone."));
        
        return CommandResult.success();
    }
    
    @Override
    public String permission() {
        return "xtrapunish.undo";
    }
    
    @Override
    public Text description() {
        return Text.of("Undoes any damage caused by a specified command.");
    }
    
    @Override
    public Text helpDescription() {
        return Text.of(TextColors.GREEN, "/punish undo <command> - ", TextColors.GOLD, "Undoes any damage caused by the specified command.");
    }
    
    @Override
    public Optional<CommandElement> arguments() {
        return Optional.of(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("command")))));
    }
    
    @Override
    public String[] command() {
        return new String[] { "undo" };
    }

    @Override
    public Optional<List<AffectedBlocks>> affectedBlocks() {
        // TODO: redo command perhaps ?
        return Optional.empty();
    }
}
