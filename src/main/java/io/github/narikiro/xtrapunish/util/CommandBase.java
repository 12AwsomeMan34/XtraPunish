/**
 * This file is part of XtraPunish, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2018 XtraStudio <https://github.com/XtraStudio>
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

package io.github.narikiro.xtrapunish.util;

import java.util.Optional;

import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;

public interface CommandBase extends CommandExecutor {

    /**
     * The description of the command. This is used in the help list and in the
     * {@link CommandSpec}.
     * 
     * @return The description of the command
     */
    String description();

    /**
     * The command or any command aliases to be run.
     * 
     * @return The command to be run
     */
    String[] command();

    /**
     * The built {@link CommandSpec} for the command.
     * 
     * @return The {@link CommandSpec} for the command
     */
    CommandSpec commandSpec();

    /**
     * This should undo any potential damage that may have been caused by the
     * use of this command. Note that not all commands may be undone (ex.
     * message commands).
     * 
     * @return If the command was undone successfully
     */
    CmdUtil.UndoSuccess undoRecent();

    /**
     * If this command supports {@link CommandBase#undoRecent}. Note that this
     * is used for help descriptions.
     * 
     * @return If this command supports undo
     */
    boolean supportsUndo();

    /**
     * The permission for running the command.
     * 
     * @return The permission necessary for running the command
     */
    String permission();

    /**
     * The arg text. This is only used for the generate package.
     * 
     * @return The arg text
     */
    Optional<String> argText();

}
