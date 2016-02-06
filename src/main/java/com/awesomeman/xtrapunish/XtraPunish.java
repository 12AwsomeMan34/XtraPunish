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

import me.flibio.updatifier.Updatifier;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
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

@Updatifier(repoName = "XtraPunish", repoOwner = "12AwsomeMan34", version = XtraPunish.VERSION)
@Plugin(id = "XtraPunish", name = "XtraPunish", version = XtraPunish.VERSION, dependencies = "after: Updatifier")
public class XtraPunish {
    
    public static XtraPunish instance;
    public BroadcastManager broadcastManager;
    public StuckManager stuckManager;
    public CobwebManager cobwebManager;
    // Make sure to use exactly this tag on github releases
    public static final String VERSION = "1.2";
    public @Inject Logger logger;
    
    @Listener
    public void onInit(GameInitializationEvent event) {
        logger.info("Initializing XtraPunish version " + VERSION);
        
        instance = this;
        broadcastManager = new BroadcastManager();
        stuckManager = new StuckManager();
        cobwebManager = new CobwebManager();
        
        EventManager eventManager = Sponge.getEventManager();
        CommandManager service = Sponge.getCommandManager();
        
        eventManager.registerListeners(this, stuckManager);
        eventManager.registerListeners(this, cobwebManager);
        
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
        
        CommandSpec anvilCommand = CommandSpec.builder()
                .permission("xtrapunish.anvil")
                .description(Text.of("Drops three anvils on a player!"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerAnvil())
                .build();
        
        CommandSpec hordeCommand = CommandSpec.builder()
                .permission("xtrapunish.horde")
                .description(Text.of("Spawns a horde of creepers onto the player!"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerHorde())
                .build();
        
        CommandSpec cobwebCommand = CommandSpec.builder()
                .permission("xtrapunish.cobweb")
                .description(Text.of("Spawns cobwebs around a player. When the player exists the cobwebs, they disappear!"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerCobweb())
                .build();
        
        CommandSpec dropCommand = CommandSpec.builder()
                .permission("xtrapunish.drop")
                .description(Text.of("Drops a player from 150 blocks in the air."))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerDrop())
                .build();
        
        CommandSpec strikeCommand = CommandSpec.builder()
                .permission("xtrapunish.strike")
                .description(Text.of("Strikes a player with lightning."))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerStrike())
                .build();
        
        CommandSpec glassCommand = CommandSpec.builder()
                .permission("xtrapunish.glass")
                .description(Text.of("Teleports a player high into the sky and places them on a glass block."))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerGlass())
                .build();
        
        CommandSpec popularCommand = CommandSpec.builder()
                .permission("xtrapunish.popular")
                .description(Text.of("Teleports all players in a server to the specified player."))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new PlayerPopular())
                .build();
        
        CommandSpec versionCommand = CommandSpec.builder()
                .permission("xtrapunish.version")
                .description(Text.of("Displays information about XtraPunish."))
                .executor(new SendVersion())
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
                .child(anvilCommand, "anvil")
                .child(hordeCommand, "horde")
                .child(cobwebCommand, "cobweb")
                .child(dropCommand, "drop")
                .child(strikeCommand, "lightning", "thor", "strike")
                .child(glassCommand, "glass")
                .child(popularCommand, "popular", "tpall")
                .child(versionCommand, "version", "v")
                .child(helpCommand, "help")
                .build();
        
        service.register(this, mainCommand, "xtra", "xtrapunish", "punish");
        logger.info("XtraPunish has successfully loaded!");
    }
}
