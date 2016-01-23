/*
The MIT License (MIT)

Copyright © 2016 12AwesomeMan34 / 12AwsomeMan34

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.awesomeman.xtrapunish;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.awesomeman.xtrapunish.manager.*;
import com.awesomeman.xtrapunish.punish.*;
import com.google.inject.Inject;

@Plugin(id = "XtraPunish", name = "XtraPunish", version = XtraPunish.VERSION)
public class XtraPunish {
    
    public static XtraPunish instance;
    public BroadcastManager broadcastManager;
    public StuckManager stuckManager;
    protected static final String VERSION = "1.0";
    private @Inject Logger logger;
    private @Inject Game game;
    
    @Listener
    public void onInit(GameInitializationEvent event) {
        logger.info("Initializing XtraPunish version " + VERSION);
        
        instance = this;
        broadcastManager = new BroadcastManager();
        stuckManager = new StuckManager();
        
        EventManager eventManager = game.getEventManager();
        CommandManager service = game.getCommandManager();
        
        eventManager.registerListeners(this, stuckManager);
        
        CommandSpec bedrockCommand = CommandSpec.builder()
                .permission("xtrapunish.trap")
                .description(Text.of("Traps a player in cold bedrock!"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerBedrock())
                .build();
        
        CommandSpec burningCommand = CommandSpec.builder()
                .permission("xtrapunish.burning")
                .description(Text.of("Sets a player on fire!"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerBurning())
                .build();
        
        CommandSpec chatSpamCommand = CommandSpec.builder()
                .permission("xtrapunish.chatspam")
                .description(Text.of("Fills a poor player's chat with random charactors!"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerChatSpam())
                .build();
        
        CommandSpec creeperCommand = CommandSpec.builder()
                .permission("xtrapunish.creeper")
                .description(Text.of("Spawns a very dangerous charged creeper on the player!"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerCreeper())
                .build();
        
        CommandSpec explodeCommand = CommandSpec.builder()
                .permission("xtrapunish.explode")
                .description(Text.of("Creates an explosion on a player!"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerExplode())
                .build();
        
        CommandSpec stuckCommand = CommandSpec.builder()
                .permission("xtrapunish.stuck")
                .description(Text.of("Prevents a player from moving!"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerStuck())
                .build();
        
        CommandSpec unstuckCommand = CommandSpec.builder()
                .permission("xtrapunish.unstuck")
                .description(Text.of("Allows a player to move once more."))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerUnstuck())
                .build();
        
        CommandSpec broadcastCommand = CommandSpec.builder()
                .permission("xtrapunish.broadcast.start")
                .description(Text.of("Broadcasts a message to the server non-stop!"))
                .arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("broadcast"))))
                .executor(new BroadcastOverlord())
                .build();
        
        CommandSpec stopBroadcastCommand = CommandSpec.builder()
                .permission("xtrapunish.broadcast.stop")
                .description(Text.of("Stops that annoying broadcast!"))
                .executor(new BroadcastStop())
                .build();
        
        CommandSpec noFoodCommand = CommandSpec.builder()
                .permission("xtrapunish.nofood")
                .description(Text.of("Sets a player's food to zero!"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerNoFood())
                .build();
        
        CommandSpec helpCommand = CommandSpec.builder()
                .permission("xtrapunish.help")
                .description(Text.of("The help command for XtraPunish!"))
                .executor(new HelpCommand())
                .build();
        
        CommandSpec mainCommand = CommandSpec.builder()
                .permission("xtrapunish.help")
                .description(Text.of("Main command for XtraPunish!"))
                .executor(new HelpCommand())
                .child(bedrockCommand, "bedrock", "trap")
                .child(burningCommand, "burn", "fire", "burning")
                .child(chatSpamCommand, "chat", "spam", "chatspam")
                .child(creeperCommand, "creeper", "doom")
                .child(explodeCommand, "explode", "boom")
                .child(stuckCommand, "stuck")
                .child(unstuckCommand, "unstuck", "free")
                .child(broadcastCommand, "broadcast")
                .child(stopBroadcastCommand, "stopbroadcast", "stop-broadcast", "stop")
                .child(noFoodCommand, "nofood", "starve")
                .child(helpCommand, "help")
                .build();
        
        service.register(this, mainCommand, "xtra", "xtrapunish", "punish");
        logger.info("XtraPunish has successfully loaded!");
    }
}
