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

package com.awesomeman.xtrapunish.manager;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.entity.living.player.Player;

import com.awesomeman.xtrapunish.util.TaskPlayer;

public class ChatSpamManager {

    private List<TaskPlayer> taskPlayers = new ArrayList<>();

    public void storeSpam(TaskPlayer taskPlayer) {
        this.taskPlayers.add(taskPlayer);
    }

    public boolean stopSpam(Player player) {
        for (TaskPlayer taskPlayer : this.taskPlayers) {
            if (taskPlayer.player.equals(player)) {
                taskPlayer.task.cancel();
                this.taskPlayers.remove(taskPlayer);
                return true;
            }
        }
        return false;
    }
}
