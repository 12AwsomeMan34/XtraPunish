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

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class StuckManager {

    private List<Player> stuckPlayers = new ArrayList<>();
    private List<Location<World>> stuckPlayersLoc = new ArrayList<>();

    public boolean setPlayerStuck(Player player) {
        if (!this.stuckPlayers.contains(player)) {
            this.stuckPlayers.add(player);
            this.stuckPlayersLoc.add(player.getLocation());
            return true;
        }
        return false;
    }

    public boolean setPlayerUnstuck(Player player) {
        if (this.stuckPlayers.contains(player)) {
            // We need to remove the loc first, as we get the index from
            // stuckPlayers
            this.stuckPlayersLoc.remove(this.stuckPlayers.indexOf(player));
            this.stuckPlayers.remove(player);
            return true;
        }
        return false;
    }

    // todo: fix for real
    @Listener
    public void playerMove(MoveEntityEvent event) {
    	if (!(event.getTargetEntity() instanceof Player)) {
    		return;
    	}
        if (this.stuckPlayers.contains(event.getTargetEntity())) {
            Location<World> playerLoc = event.getTargetEntity().getLocation();
            // stuckPlayersLoc and stuckPlayers are added together in a list, so
            // they are at the same index
            Location<World> storedLoc = this.stuckPlayersLoc.get(this.stuckPlayers.indexOf(event.getTargetEntity()));
            if (playerLoc.getX() > storedLoc.getX() + 0.5 || playerLoc.getX() < storedLoc.getX() - 0.5
                    || playerLoc.getY() > storedLoc.getY() + 1
                    || playerLoc.getZ() > storedLoc.getZ() + 0.5 || playerLoc.getZ() < storedLoc.getZ() - 0.5) {
                event.getTargetEntity().setLocation(storedLoc);
            }
        }
    }

    @Listener
    public void breakBlock(ChangeBlockEvent event, @First Player player) {
        if (this.stuckPlayers.contains(player)) {
            // Prevent players from changing blocks while they are stuck
            event.setCancelled(true);
        }
    }
}
