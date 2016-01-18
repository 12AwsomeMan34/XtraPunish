package com.awesomeman.xtrapunish.punish;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.world.extent.Extent;

/**
 * Spawns a powerful charged creeper onto the player.
 */
public class PlayerCreeper implements CommandExecutor {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = args.<Player>getOne("player").get();
        Extent extent = player.getLocation().getExtent();
        Optional<Entity> optional = extent.createEntity(EntityTypes.CREEPER, player.getLocation().getBlockPosition());
        if(optional.isPresent()) {
            Entity entity = optional.get();
            entity.offer(Keys.CREEPER_CHARGED, true);
            extent.spawnEntity(entity, Cause.of(NamedCause.of("plugin", this)));
        }
        return CommandResult.success();
    }
}
