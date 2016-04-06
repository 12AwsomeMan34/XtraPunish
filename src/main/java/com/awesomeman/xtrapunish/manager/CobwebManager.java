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

/*package com.awesomeman.xtrapunish.manager;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CobwebManager {
    
    private List<Player> cobwebPlayers = new ArrayList<>();
    // We store a list of locations and blockstates for easy restoration
    private List<List<Location<World>>> cobweb = new ArrayList<>();
    private List<List<BlockState>> restoreBlocks = new ArrayList<>();
    
    public void addPlayer(Player player, List<Location<World>> cobwebs, List<BlockState> restoreBlock) {
        if(!cobwebPlayers.contains(player)) {
            cobwebPlayers.add(player);
            cobweb.add(cobwebs);
            restoreBlocks.add(restoreBlock);
        }
    }
    
    @Listener
    public void move(DisplaceEntityEvent.Move.TargetPlayer event) {
        if(cobwebPlayers.contains(event.getTargetEntity())) {
            double x = event.getTargetEntity().getLocation().getX();
            double z = event.getTargetEntity().getLocation().getZ();
            List<Location<World>> cobwebs = cobweb.get(cobwebPlayers.indexOf(event.getTargetEntity()));
            
            // Mid block should always be stored at zero
            double cobX = cobwebs.get(0).getX();
            double cobZ = cobwebs.get(0).getZ();
            if(x > cobX + 2.5 || x < cobX - 2.5 || z > cobZ + 2.5 || z < cobZ - 2.5) {
                List<BlockState> restore = restoreBlocks.get(cobwebPlayers.indexOf(event.getTargetEntity()));
                for(int i = 0; i < cobwebs.size(); i++) {
                    cobwebs.get(i).setBlock(restore.get(i));
                }
                
                cobwebPlayers.remove(event.getTargetEntity());
                cobweb.remove(cobwebs);
                restoreBlocks.remove(restore);
            }
        }
    }
}
*/