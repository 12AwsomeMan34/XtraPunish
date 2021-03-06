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

package io.github.narikiro.xtrapunish.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.entity.living.player.Player;

import io.github.narikiro.xtrapunish.XtraPunish;
import io.github.narikiro.xtrapunish.util.ZoomStore;

public class ZoomManager {

    private List<ZoomStore> zoomingPlayers = new ArrayList<>();

    public void storePlayer(ZoomStore store) {
        this.zoomingPlayers.add(store);
    }

    public boolean hasPlayer(Player player) {
        for (ZoomStore store : zoomingPlayers) {
            if (store.player.equals(player)) {
                return true;
            }
        }
        return false;
    }

    public void removePlayer(Player player) {
        for (ZoomStore store : zoomingPlayers) {
            if (store.player.equals(player)) {
                Optional<PotionEffectData> optional = player.get(PotionEffectData.class);
                if (optional.isPresent()) {
                    this.removePlayerData(player, optional.get(), store);
                } else {
                    // If no data, schedule to keep checking
                    Sponge.getScheduler().createTaskBuilder().execute(task -> {
                        Optional<PotionEffectData> optional2 = player.get(PotionEffectData.class);
                        if (optional2.isPresent()) {
                            this.removePlayerData(player, optional2.get(), store);
                        }
                    }).intervalTicks(1).submit(XtraPunish.instance);
                }
                break;
            }
        }
    }

    private void removePlayerData(Player player, PotionEffectData data, ZoomStore store) {
        player.offer(data.remove(store.effect));
        store.task.cancel();
        zoomingPlayers.remove(store);
    }
}
