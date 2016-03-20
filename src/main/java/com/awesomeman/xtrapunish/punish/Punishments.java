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

import org.spongepowered.api.command.spec.CommandSpec;

import com.awesomeman.xtrapunish.XtraPunish;
import com.awesomeman.xtrapunish.api.punish.Punishment;

public class Punishments {
    
    /**
     * Construct the {@link CommandSpec} and add the necessary specifications.
     * 
     * @param punishment The punishment to be registered
     */
    public static void registerPunishment(Punishment punishment) {
        CommandSpec.Builder command = CommandSpec.builder()
                .permission(punishment.permission())
                .description(punishment.description())
                .executor(punishment);
        if(punishment.arguments().isPresent()) {
            command.arguments(punishment.arguments().get());
        }
        
        XtraPunish.instance.helpList.add(punishment.helpDescription());
        XtraPunish.instance.commandList.add(punishment.command());
        XtraPunish.instance.commands.add(command.build());
    }
    
    /**
     * ALL commands must be registered through this. They must all be in
     * alphabetical order.
     * 
     * <p>This should only ever run once, on startup.</p>
     */
    public static void registerPunishments() {
        registerPunishment(new BroadcastOverlord());
        registerPunishment(new BroadcastStop());
        registerPunishment(new HelpCommand());
        registerPunishment(new PlayerAnvil());
        registerPunishment(new PlayerBedrock());
        registerPunishment(new PlayerBounceExplode());
        registerPunishment(new PlayerBurning());
        registerPunishment(new PlayerChatSpam());
        registerPunishment(new PlayerCobweb());
        registerPunishment(new PlayerCreeper());
        registerPunishment(new PlayerDrop());
        registerPunishment(new PlayerExplode());
        registerPunishment(new PlayerGlass());
        registerPunishment(new PlayerHorde());
        registerPunishment(new PlayerNoFood());
        registerPunishment(new PlayerPopular());
        registerPunishment(new PlayerSponge());
        registerPunishment(new PlayerStrike());
        registerPunishment(new PlayerStuck());
        registerPunishment(new PlayerUnstuck());
        registerPunishment(new SendVersion());
    }
}
