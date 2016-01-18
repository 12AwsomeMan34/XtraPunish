package com.awesomeman.xtrapunish.manager;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;

public class StuckManager {
    
    private Set<Player> stuckPlayers = Collections.newSetFromMap(new WeakHashMap<>());
    
    public boolean setPlayerStuck(Player player) {
        return stuckPlayers.add(player);
    }
    
    public boolean setPlayerUnstuck(Player player) {
        return stuckPlayers.remove(player);
    }
    
    @Listener
    public void playerMove(DisplaceEntityEvent.Move.TargetPlayer event) {
        if(stuckPlayers.contains(event.getTargetEntity())) {
            event.setCancelled(true);
        }
    }
}
