package com.awesomeman.xtrapunish.punish;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.extent.Extent;

import com.awesomeman.xtrapunish.XtraPunish;

public class PlayerHorde implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if(!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish horde Player"));
            return CommandResult.empty();
        }
        Player player = optional.get();
        Extent extent = player.getLocation().getExtent();
        
        // 20 creepers
        for(int i = 0; i < 20; i++) {
            Optional<Entity> optional2 = extent.createEntity(EntityTypes.CREEPER, player.getLocation().getPosition());
            if(optional2.isPresent()) {
                extent.spawnEntity(optional2.get(), Cause.of(NamedCause.of("plugin", XtraPunish.instance)));
            }
        }
        
        src.sendMessage(Text.of(TextColors.GREEN, "Player " + player.getName() + " might as well be sleeping with the fishes!"));        
        return CommandResult.success();
    }
}
