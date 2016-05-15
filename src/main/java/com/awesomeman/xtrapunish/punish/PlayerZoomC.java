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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.awesomeman.xtrapunish.punish;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.awesomeman.xtrapunish.XtraPunish;
import com.awesomeman.xtrapunish.manager.Managers;
import com.awesomeman.xtrapunish.util.CmdUtil.UndoSuccess;
import com.awesomeman.xtrapunish.util.CommandBase;
import com.awesomeman.xtrapunish.util.ZoomStore;

public class PlayerZoomC implements CommandBase {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if (!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: ", TextColors.GOLD, "/punish czoom <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();

        if (Managers.zoomManager.hasPlayer(player)) {
            src.sendMessage(
                    Text.of(TextColors.RED, "Player ", TextColors.BLUE, player.getName(), TextColors.RED,
                            " is already having their zoom toggled continuously."));
            return CommandResult.empty();
        }
        PotionEffect potionEffect = PotionEffect.of(PotionEffectTypes.SLOWNESS, 2, 3);

        Task zoomTask = Sponge.getScheduler().createTaskBuilder().execute(
                task -> {
                    player.offer(player.getOrCreate(PotionEffectData.class).get().addElement(potionEffect));
                }).intervalTicks(4).name("XtraPunish czoom command.").submit(XtraPunish.instance);

        Managers.zoomManager.storePlayer(new ZoomStore(zoomTask, player, potionEffect));
        src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Player ", TextColors.BLUE, player.getName(), TextColors.GOLD,
                " will have their screen zoomed in continuously."));
        return CommandResult.success();
    }

    @Override
    public String description() {
        return "Zooms a player's screen in continuously.";
    }

    @Override
    public String[] command() {
        return new String[] {"czoom"};
    }

    @Override
    public CommandSpec commandSpec() {
        return CommandSpec.builder()
                .permission(permission())
                .description(Text.of(description()))
                .arguments(GenericArguments.optional(GenericArguments
                        .onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(this)
                .build();
    }

    @Override
    public UndoSuccess undoRecent() {
        return UndoSuccess.FAILUE_NOT_SUPPORTED;
    }

    @Override
    public boolean supportsUndo() {
        return false;
    }

    @Override
    public String permission() {
        return "xtrapunish.czoom";
    }

    @Override
    public Optional<String> argText() {
        return Optional.of("[player]");
    }
}
