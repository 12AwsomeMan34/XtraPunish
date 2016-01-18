package com.awesomeman.xtrapunish.punish;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.extent.Extent;

/**
 * Spawns tnt that will instantly explode on the player's location.
 */
public class PlayerExplode implements CommandExecutor {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = args.<Player>getOne("player").get();
        Extent extent = player.getLocation().getExtent();
        Optional<Entity> optional = extent.createEntity(EntityTypes.PRIMED_TNT, player.getLocation().getPosition());
        if(optional.isPresent()) {
            PrimedTNT tnt = (PrimedTNT) optional.get();
            // Temporary, update when spawncause is in api and impl
            extent.spawnEntity(tnt, Cause.of(NamedCause.of("Explosion", this)));
            src.sendMessage(Text.of(TextColors.GREEN, "Kaboom!"));
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Unable to create tnt entity!"));
        }
        return CommandResult.success();
    }
}
