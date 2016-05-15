package com.awesomeman.xtrapunish.util;

import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

public class ZoomStore {

    public Task task;
    public Player player;
    public PotionEffect effect;

    public ZoomStore(Task task, Player player, PotionEffect effect) {
        this.task = task;
        this.player = player;
        this.effect = effect;
    }
}
