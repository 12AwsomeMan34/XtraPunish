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

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import io.github.narikiro.xtrapunish.XtraPunish;
import io.github.narikiro.xtrapunish.manager.Managers;
import io.github.narikiro.xtrapunish.util.CmdUtil;
import io.github.narikiro.xtrapunish.util.CommandBase;

public class BroadcastOverlord implements CommandBase {

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<String> optional = args.<String>getOne("broadcast");
        if (!optional.isPresent()) {
            src.sendMessage(
                    Text.of(TextColors.RED, "Message argument not specified! Correct usage: ", TextColors.GOLD, "/punish broadcast <broadcast>"));
            return CommandResult.empty();
        }
        String broadcast = optional.get();

        if (Managers.broadcastManager.getBroadcast() != null) {
            src.sendMessage(Text.of(TextColors.RED, "Broadcast already running! Cannot create another one."));
            return CommandResult.empty();
        }

        // Allow our message to be formatted with &
        Text broadcastMessage = TextSerializers.FORMATTING_CODE.deserialize(broadcast);
        MessageChannel channel = MessageChannel.TO_ALL;

        Scheduler scheduler = Sponge.getScheduler();
        Task.Builder taskBuilder = scheduler.createTaskBuilder();
        // Runs an asynchronous task that displays the broadcast message twice a
        // second
        Task broadcastTask = taskBuilder.execute(
                task -> {
                    channel.send(broadcastMessage);
                }).async().interval(500, TimeUnit.MILLISECONDS).name("XtraPunish broadcast message command.").submit(XtraPunish.instance);
        Managers.broadcastManager.storeBroadcast(broadcastTask);
        src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Broadcast has started."));
        return CommandResult.success();
    }

    @Override
    public String description() {
        return "Broadcasts a message to the server non-stop!";
    }

    @Override
    public String[] command() {
        return new String[] {"broadcast"};
    }

    @Override
    public CommandSpec commandSpec() {
        return CommandSpec.builder()
                .permission(permission())
                .description(Text.of(description()))
                .arguments(GenericArguments.optional(
                        GenericArguments.remainingJoinedStrings(Text.of("broadcast"))))
                .executor(this)
                .build();
    }

    @Override
    public CmdUtil.UndoSuccess undoRecent() {
        return CmdUtil.UndoSuccess.FAILUE_NOT_SUPPORTED;
    }

    @Override
    public boolean supportsUndo() {
        return false;
    }

    @Override
    public String permission() {
        return "xtrapunish.broadcast.start";
    }

    @Override
    public Optional<String> argText() {
        return Optional.of("[broadcast]");
    }
}
