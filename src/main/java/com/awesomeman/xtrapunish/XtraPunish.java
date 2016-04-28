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

package com.awesomeman.xtrapunish;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.awesomeman.xtrapunish.manager.Managers;
import com.awesomeman.xtrapunish.punish.HelpCommand;
import com.awesomeman.xtrapunish.punish.Punishments;
import com.awesomeman.xtrapunish.util.CommandBase;
import com.google.inject.Inject;

import me.flibio.updatifier.Updatifier;

@Updatifier(repoName = PluginInfo.NAME, repoOwner = PluginInfo.ORGANIZATION, version = PluginInfo.VERSION)
@Plugin(id = PluginInfo.ID, name = PluginInfo.NAME, version = PluginInfo.VERSION, description = PluginInfo.DESCRIPTION)
public class XtraPunish {

    public static XtraPunish instance;
    public @Inject Logger logger;
    public List<CommandBase> commandBases = new ArrayList<>();

    @Listener
    public void onInit(GameInitializationEvent event) {
        this.logger.info("Initializing XtraPunish version " + PluginInfo.VERSION);

        instance = this;
        Managers.initManagers();

        EventManager eventManager = Sponge.getEventManager();
        CommandManager service = Sponge.getCommandManager();

        eventManager.registerListeners(this, Managers.stuckManager);
        eventManager.registerListeners(this, Managers.explosionManager);

        Punishments.registerPunishments();

        CommandSpec.Builder mainCommandBuilder = CommandSpec.builder()
                .permission("xtrapunish.help")
                .description(Text.of("Main command for XtraPunish!"))
                .executor(new HelpCommand());

        for (CommandBase command : this.commandBases) {
            mainCommandBuilder.child(command.commandSpec(), command.command());
        }

        service.register(this, mainCommandBuilder.build(), "xtra", "xtrapunish", "punish");

        this.logger.info("XtraPunish has successfully loaded!");
    }
}
