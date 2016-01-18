package com.awesomeman.xtrapunish;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.awesomeman.xtrapunish.punish.*;
import com.google.inject.Inject;

@Plugin(id = "testplugin", name = "TestPlugin", version = XtraPunish.VERSION)
public class XtraPunish {
    
    protected static final String VERSION = "1.0";
    private @Inject Logger logger;
    private @Inject Game game;
    
    @Listener
    public void onInit(GameInitializationEvent event) {
        logger.info("Initializing XtraPunish version " + VERSION);
        
        CommandManager service = game.getCommandManager();
        
        CommandSpec bedrockCommand = CommandSpec.builder()
                .permission("xtrapunish.trap")
                .description(Text.of("Traps a player in cold bedrock!"))
                .executor(new PlayerBedrock())
                .build();
        
        CommandSpec bounceCommand = CommandSpec.builder()
                .permission("xtrapunish.bounce")
                .description(Text.of("Bounces a player into the sky!"))
                .executor(new PlayerBounce())
                .build();
        
        CommandSpec burningCommand = CommandSpec.builder()
                .permission("xtrapunish.burning")
                .description(Text.of("Sets a player on fire!"))
                .executor(new PlayerBurning())
                .build();
        
        CommandSpec chatSpamCommand = CommandSpec.builder()
                .permission("xtrapunish.chatspam")
                .description(Text.of("Fills a poor player's chat with random charactors!"))
                .executor(new PlayerChatSpam())
                .build();
        
        CommandSpec creeperCommand = CommandSpec.builder()
                .permission("xtrapunish.creeper")
                .description(Text.of("Spawns a very dangerous creeper on the player!"))
                .executor(new PlayerCreeper())
                .build();
        
        CommandSpec explodeCommand = CommandSpec.builder()
                .permission("xtrapunish.explode")
                .description(Text.of("Creates an explosion on a player!"))
                .executor(new PlayerExplode())
                .build();
        
        CommandSpec stuckCommand = CommandSpec.builder()
                .permission("xtrapunish.stuck")
                .description(Text.of("Prevents a player from moving!"))
                .executor(new PlayerBedrock())
                .build();
        
        CommandSpec broadcastCommand = CommandSpec.builder()
                .permission("xtrapunish.broadcast.start")
                .description(Text.of("Broadcasts a message to the server non-stop!"))
                .executor(new BroadcastOverlord())
                .build();
        
        CommandSpec stopBroadcastCommand = CommandSpec.builder()
                .permission("xtrapunish.broadcast.stop")
                .description(Text.of("Stops that annoying broadcast!"))
                .executor(new BroadcastStop())
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
                .child(bounceCommand, "bounce")
                .child(burningCommand, "burn", "fire", "burning")
                .child(chatSpamCommand, "chat", "spam", "chatspam")
                .child(creeperCommand, "creeper", "doom")
                .child(explodeCommand, "explode", "boom")
                .child(stuckCommand, "stuck")
                .child(broadcastCommand, "broadcast")
                .child(stopBroadcastCommand, "stopbroadcast", "stop-broadcast", "stop")
                .child(helpCommand, "help")
                .build();
        
        service.register(this, mainCommand, "xtra", "xtrapunish", "punish");
        logger.info("XtraPunish has successfully loaded!");
    }
}
