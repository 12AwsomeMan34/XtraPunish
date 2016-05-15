package com.awesomeman.xtrapunish.manager;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.entity.living.player.Player;

import com.awesomeman.xtrapunish.util.ZoomStore;

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
                player.offer(player.get(PotionEffectData.class).get().remove(store.effect));
                zoomingPlayers.remove(store);
                break;
            }
        }
    }
}
