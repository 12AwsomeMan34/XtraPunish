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
import com.awesomeman.xtrapunish.util.CmdUtil;
import com.awesomeman.xtrapunish.util.CmdUtil.UndoSuccess;
import com.awesomeman.xtrapunish.util.CommandBase;

public class PluginRedo implements CommandBase {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<String> optional = args.<String>getOne("command");
        if (!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Command argument not specified! Correct usage: ", TextColors.GOLD, "/punish redo <command>"));
            return CommandResult.empty();
        }
        String command = optional.get();

        boolean found = false;
        for (CommandBase commandBase : XtraPunish.instance.commandBases) {
            for (String command2 : commandBase.command()) {
                if (command2.equals(command)) {
                    found = true;
                }
            }
        }
        if (!found) {
            src.sendMessage(Text.of(TextColors.RED, "Unable to find the command ", TextColors.BLUE, command, TextColors.RED, "!"));
            return CommandResult.empty();
        }
        return CommandResult.success();
    }

    @Override
    public String description() {
        return "Redoes any command that was undone.";
    }

    @Override
    public String[] command() {
        return new String[] {"redo"};
    }

    @Override
    public CommandSpec commandSpec() {
        return CommandSpec.builder()
                .permission(permission())
                .description(Text.of(description()))
                .arguments(GenericArguments.optional(
                        GenericArguments.string(Text.of("command"))))
                .executor(this)
                .build();
    }

    @Override
    public UndoSuccess undoRecent() {
        return UndoSuccess.FAILURE_NOT_SUPPORTED;
    }

    @Override
    public boolean supportsUndo() {
        return false;
    }

    @Override
    public String permission() {
        return "xtrapunish.redo";
    }

    @Override
    public Optional<String> argText() {
        return Optional.of("[command]");
    }

    @Override
    public UndoSuccess redoRecent() {
        return CmdUtil.UndoSuccess.FAILURE_NOT_SUPPORTED;
    }
}
