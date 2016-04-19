/**
 * This file is part of XtraPunish, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2016 XtraStudio <https://github.com/XtraStudio>
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.awesomeman.xtrapunish.punish;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.extent.Extent;

import com.awesomeman.xtrapunish.util.CommandBase;
import com.awesomeman.xtrapunish.util.UndoSuccess;

public class PlayerCreeper implements CommandBase {
	
	private List<Entity> history = new ArrayList<>();
    
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if(!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish creeper <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();
        
        Extent extent = player.getLocation().getExtent();
        Optional<Entity> optional2 = extent.createEntity(EntityTypes.CREEPER, player.getLocation().getPosition());
        if(optional2.isPresent()) {
            Entity entity = optional2.get();
            entity.offer(Keys.CREEPER_CHARGED, true);
            extent.spawnEntity(entity, Cause.source(EntitySpawnCause.builder().entity(entity).type(SpawnTypes.PLUGIN).build()).build());
            history.add(entity);
            src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Player " + player.getName() + " will need to watch their back!"));
        } else {
            src.sendMessage(Text.of(TextColors.RED, "The entity could not be created!"));
        }
        return CommandResult.success();
    }
    
	@Override
	public String description() {
		return "Spawns a charged creeper on the player.";
	}
	
	@Override
    public String[] command() {
        return new String[] { "creeper" };
    }
	
	@Override
	public CommandSpec commandSpec() {
		return CommandSpec.builder()
				.permission("xtrapunish.creeper")
				.description(Text.of(description()))
				.arguments(GenericArguments.optional(GenericArguments
                        .onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(this)
				.build();
	}
	
	@Override
	public UndoSuccess undoRecent() {
		Entity entity = history.get(history.size() - 1);
		entity.remove();
		history.remove(entity);
		return UndoSuccess.SUCCESS;
	}
}
