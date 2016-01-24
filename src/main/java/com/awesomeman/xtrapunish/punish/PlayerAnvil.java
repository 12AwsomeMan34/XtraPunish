package com.awesomeman.xtrapunish.punish;

import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class PlayerAnvil implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if(!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish anvil Player"));
            return CommandResult.empty();
        }
        Player player = optional.get();
        Location<World> loc = player.getLocation();
        
        Location<World> anvil1 = loc.add(0, 6, 0);
        Location<World> anvil2 = anvil1.getRelative(Direction.UP);
        Location<World> anvil3 = anvil2.getRelative(Direction.UP);
        anvil1.setBlockType(BlockTypes.ANVIL);
        anvil2.setBlockType(BlockTypes.ANVIL);
        anvil3.setBlockType(BlockTypes.ANVIL);
        
        return CommandResult.success();
    }
}
