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

package com.awesomeman.xtrapunish.util.generate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.awesomeman.xtrapunish.punish.*;
import com.awesomeman.xtrapunish.util.CommandBase;
import com.laxamer.file.FileUtils;

/**
 * THIS CLASS SHOULD NEVER BE USED IN PRODUCTION. THIS CLASS IS A BACKEND
 * UTILITY CLASS AND SHOULD BE COMPLETELY IGNORED.
 */
public class GenerateInfo {

    private static List<CommandBase> commandBases = new ArrayList<>();

    private static void generateCmdInfo() {
        registerPunishments();
        File dir = new File("xtragen");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // Remember, this stuff will generate in the plugin root folder.
        // NOT the server root, this should never be used on the server!
        File cmdsFile = FileUtils.getOrCreateFile("xtragen/commands.txt");
        File permsFile = FileUtils.getOrCreateFile("xtragen/permissions.txt");
        FileUtils.wipeFile(cmdsFile);
        FileUtils.wipeFile(permsFile);
        List<String> cmdsText = new ArrayList<>();
        List<String> permsText = new ArrayList<>();
        for (CommandBase command : commandBases) {
            String args = command.argText().isPresent() ? command.argText().get() : "";
            String supports = command.supportsUndo() ? " Supports undo." : "";
            cmdsText.add("/punish " + command.command()[0] + " " + args + " - " + command.description() + supports);
            permsText.add(command.permission());
        }
        FileUtils.writeToFile(cmdsFile, cmdsText);
        FileUtils.writeToFile(permsFile, permsText);
    }

    // Copy this stuff from Punishments.java, easier than
    // touching current system honestly

    private static void registerPunishment(CommandBase commandBase) {
        commandBases.add(commandBase);
    }

    // KEEP UP TO DATE FROM Punishments.java
    private static void registerPunishments() {
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
        registerPunishment(new PluginUndo());
        registerPunishment(new SendVersion());
    }

    public static void main(String[] args) {
        System.out.println("Starting gen stuffz!");
        generateCmdInfo();
    }
}
