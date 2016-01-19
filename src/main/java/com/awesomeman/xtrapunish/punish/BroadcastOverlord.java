package com.awesomeman.xtrapunish.punish;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.awesomeman.xtrapunish.XtraPunish;

/**
 * Continuously broadcasts a message to the entire server until /punish stop is ran.
 */
public class BroadcastOverlord implements CommandExecutor {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<String> optional = args.<String>getOne("broadcast");
        if(!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Message argument not specified! Correct usage: /punish broadcast <My Message Here>"));
            return CommandResult.empty();
        }
        String broadcast = optional.get();
        
        if(XtraPunish.instance.broadcastManager.getBroadcast() != null) {
            src.sendMessage(Text.of(TextColors.RED, "Broadcast already running! Cannot create another one."));
            return CommandResult.empty();
        }
        
        // Allow our message to be formatted with &
        Text broadcastMessage = TextSerializers.FORMATTING_CODE.deserialize(broadcast);
        MessageChannel channel = MessageChannel.TO_ALL;
        
        Scheduler scheduler = Sponge.getScheduler();
        Task.Builder taskBuilder = scheduler.createTaskBuilder();
        // Runs an asynchronous task that displays the broadcast message twice a second
        Task broadcastTask = taskBuilder.execute(
            task -> {
                channel.send(broadcastMessage);
            }
        ).async().interval(500, TimeUnit.MILLISECONDS).name("XtraPunish broadcast message command.").submit(XtraPunish.instance);
        XtraPunish.instance.broadcastManager.storeBroadcast(broadcastTask);
        return CommandResult.success();
    }
}
