/*
The MIT License (MIT)

Copyright © 2016 12AwesomeMan34 / 12AwsomeMan34

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.awesomeman.xtrapunish.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class StuckManager {
    
    // We don't want to store live references to players, we store what we need
    private List<UUID> stuckPlayers = new ArrayList<>();
    private List<Location<World>> stuckPlayersLoc = new ArrayList<>();
    
    public boolean setPlayerStuck(Player player) {
        if(!stuckPlayers.contains(player.getUniqueId())) {
            stuckPlayers.add(player.getUniqueId());
            stuckPlayersLoc.add(player.getLocation());
            return true;
        }
        return false;
    }
    
    public boolean setPlayerUnstuck(Player player) {
        if(stuckPlayers.contains(player.getUniqueId())) {
            // We need to remove the loc first, as we get the index from stuckPlayers
            stuckPlayersLoc.remove(stuckPlayers.indexOf(player.getUniqueId()));
            stuckPlayers.remove(player.getUniqueId());
            return true;
        }
        return false;
    }
    
    @Listener
    public void playerMove(DisplaceEntityEvent.Move.TargetPlayer event) {
        if(stuckPlayers.contains(event.getTargetEntity().getUniqueId())) {
            Location<World> playerLoc = event.getTargetEntity().getLocation();
            // stuckPlayersLoc and stuckPlayers are added together in a list, so they are at the same index
            Location<World> storedLoc = stuckPlayersLoc.get(stuckPlayers.indexOf(event.getTargetEntity().getUniqueId()));
            if(playerLoc.getX() > storedLoc.getX() + 0.5 || playerLoc.getX() < storedLoc.getX() - 0.5
                    || playerLoc.getZ() > storedLoc.getZ() + 0.5 || playerLoc.getZ() < storedLoc.getZ() - 0.5) {
                event.getTargetEntity().setLocation(storedLoc);
            }
        }
    }
    
    @Listener
    public void breakBlock(ChangeBlockEvent.Break event) {
        Optional<Player> optional = event.getCause().<Player>first(Player.class);
        if(optional.isPresent()) {
            if(stuckPlayers.contains(optional.get().getUniqueId())) {
                // Prevent players from breaking blocks while they are stuck
                event.setCancelled(true);
            }
        }
    }
}
