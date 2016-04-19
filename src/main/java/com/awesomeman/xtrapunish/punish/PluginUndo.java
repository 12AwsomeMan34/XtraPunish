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

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.awesomeman.xtrapunish.XtraPunish;
import com.awesomeman.xtrapunish.util.CommandBase;
import com.awesomeman.xtrapunish.util.UndoSuccess;

public class PluginUndo implements CommandBase {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<String> optional = args.<String>getOne("command");
        if (!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Command argument not specified! Correct usage: /punish undo <command>"));
            return CommandResult.empty();
        }
        String command = optional.get();

        for (CommandBase commandBase : XtraPunish.instance.commandBases) {
            for (String command2 : commandBase.command()) {
                if (command2.equals(command)) {
                    switch (commandBase.undoRecent()) {
                        case SUCCESS:
                            src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Command ", command2, " has been undone."));
                            break;
                        case FAILUE_NOT_SUPPORTED:
                            src.sendMessage(Text.of(TextColors.RED, "Command ", command2, " does not support undo!"));
                            break;
                        case FAILURE_NO_HISTORY:
                            src.sendMessage(Text.of(TextColors.RED, "Command ", command2, " has no history that can be undone!"));
                            break;
                        case FAILUE_FOOD_DATA:
                            src.sendMessage(Text.of(TextColors.RED, "Unable to successfully offer data for the command, ", command2, "!"));
                            break;
                        case FAILUE_NO_PLAYER:
                            src.sendMessage(Text.of(TextColors.RED, "A player necessary to undo the command ", command2,
                                    " is unavailable. Removing undo entry."));
                            break;
                        case FAILUE_UNKNOWN:
                            src.sendMessage(Text.of(TextColors.RED, "An error occured while attempting to undo command ", command2, "!"));
                            break;
                    }
                }
            }
        }
        return CommandResult.success();
    }

    @Override
    public String[] command() {
        return new String[] {"undo"};
    }

    @Override
    public String description() {
        return "Undoes any damage caused by a specified command.";
    }

    @Override
    public CommandSpec commandSpec() {
        return CommandSpec.builder()
                .permission("xtrapunish.undo")
                .description(Text.of(description()))
                .arguments(GenericArguments.optional(
                        GenericArguments.string(Text.of("command"))))
                .executor(this)
                .build();
    }

    @Override
    public UndoSuccess undoRecent() {
        return UndoSuccess.FAILUE_NOT_SUPPORTED;
    }
}
