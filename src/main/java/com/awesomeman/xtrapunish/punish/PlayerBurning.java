package com.awesomeman.xtrapunish.punish;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.manipulator.mutable.entity.IgniteableData;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Sets a player on fire.
 */
public class PlayerBurning implements CommandExecutor {
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = args.<Player>getOne("player").get();
        IgniteableData data = Sponge.getGame().getDataManager().getManipulatorBuilder(IgniteableData.class).get().create();
        // Player should not last this long :P
        player.offer(data.fireTicks().set(Integer.MAX_VALUE));
        return CommandResult.success();
    }
}
