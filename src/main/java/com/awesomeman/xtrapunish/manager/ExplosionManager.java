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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.world.ExplosionEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;

public class ExplosionManager {

    public List<Set<BlockSnapshot>> explosionHistory = new ArrayList<>();
    private List<Explosion> explosions = new ArrayList<>();

    public void addExplosion(Explosion explosion) {
        this.explosions.add(explosion);
    }

    @Listener
    public void onExplode(ExplosionEvent.Detonate event) {
        Explosion eventExplosion = event.getExplosion();
        double eventX = eventExplosion.getLocation().getX();
        double eventY = eventExplosion.getLocation().getY();
        double eventZ = eventExplosion.getLocation().getZ();
        for (Explosion explosion : this.explosions) {
            double explX = explosion.getLocation().getX();
            double explY = explosion.getLocation().getY();
            double explZ = explosion.getLocation().getZ();
            if (eventX == explX && eventY == explY && eventZ == explZ) {
                Set<BlockSnapshot> snapshots = new HashSet<>();
                for (Location<World> location : event.getAffectedLocations()) {
                    snapshots.add(location.createSnapshot());
                }
                this.explosionHistory.add(snapshots);
                this.explosions.remove(explosion);
                break;
            }
        }
    }
}
