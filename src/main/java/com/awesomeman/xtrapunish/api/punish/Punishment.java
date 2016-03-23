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

package com.awesomeman.xtrapunish.api.punish;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import com.awesomeman.xtrapunish.util.AffectedBlocks;

public interface Punishment extends CommandExecutor {
    
    /**
     * Permission for the command.
     * 
     * @return The permission for the command
     */
    String permission();
    
    /**
     * The description of the command.
     * 
     * @return The description of the command
     */
    Text description();
    
    /**
     * Gets the help description for a command. This description will
     * be used for the pagination help list, and should be formatted
     * for such.
     * 
     * @return The help description
     */
    Text helpDescription();
    
    /**
     * The arguments for the command. Note that not all commands have
     * arguments.
     * 
     * @return An optional of the arguments for the command, if present
     */
    Optional<CommandElement> arguments();
    
    /**
     * The command or any command aliases to be run alongside /punish.
     * 
     * @return The command to be run alongside /punish
     */
    String[] command();
    
    /**
     * Gets a list of blocks affected by this punishment. Note that not all
     * commands will affect blocks.
     * 
     * @return The blocks affected by this command
     */
    Optional<List<AffectedBlocks>> affectedBlocks();
}
