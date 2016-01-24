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

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CobwebManager {
    
    private List<Player> cobwebPlayers = new ArrayList<>();
    private List<Location<World>> cobwebMid = new ArrayList<>();
    
    public void addPlayer(Player player) {
        if(!cobwebPlayers.contains(player)) {
            cobwebPlayers.add(player);
            cobwebMid.add(player.getLocation());
        }
    }
    
    @Listener
    public void move(DisplaceEntityEvent.Move.TargetPlayer event) {
        if(cobwebPlayers.contains(event.getTargetEntity())) {
            double x = event.getTargetEntity().getLocation().getX();
            double z = event.getTargetEntity().getLocation().getZ();
            Location<World> cobweb = cobwebMid.get(cobwebPlayers.indexOf(event.getTargetEntity()));
            double cobX = cobweb.getX();
            double cobZ = cobweb.getZ();
            if(x > cobX + 2.5 || x < cobX - 2.5 || z > cobZ + 2.5 || z < cobZ - 2.5) {
                cobweb.setBlockType(BlockTypes.AIR);
                // Immediately around the mid web
                Location<World> loc1 = cobweb.getRelative(Direction.NORTH);
                Location<World> loc2 = cobweb.getRelative(Direction.EAST);
                Location<World> loc3 = cobweb.getRelative(Direction.SOUTH);
                Location<World> loc4 = cobweb.getRelative(Direction.WEST);
                // Corners
                Location<World> loc5 = loc1.getRelative(Direction.WEST);
                Location<World> loc6 = loc1.getRelative(Direction.EAST);
                Location<World> loc7 = loc3.getRelative(Direction.WEST);
                Location<World> loc8 = loc3.getRelative(Direction.EAST);
                // Tops
                Location<World> loc_1 = cobweb.getRelative(Direction.UP);
                Location<World> loc1_1 = loc1.getRelative(Direction.UP);
                Location<World> loc1_2 = loc2.getRelative(Direction.UP);
                Location<World> loc1_3 = loc3.getRelative(Direction.UP);
                Location<World> loc1_4 = loc4.getRelative(Direction.UP);
                Location<World> loc1_5 = loc5.getRelative(Direction.UP);
                Location<World> loc1_6 = loc6.getRelative(Direction.UP);
                Location<World> loc1_7 = loc7.getRelative(Direction.UP);
                Location<World> loc1_8 = loc8.getRelative(Direction.UP);
                
                loc1.setBlockType(BlockTypes.AIR);
                loc2.setBlockType(BlockTypes.AIR);
                loc3.setBlockType(BlockTypes.AIR);
                loc4.setBlockType(BlockTypes.AIR);
                loc5.setBlockType(BlockTypes.AIR);
                loc6.setBlockType(BlockTypes.AIR);
                loc7.setBlockType(BlockTypes.AIR);
                loc8.setBlockType(BlockTypes.AIR);
                loc_1.setBlockType(BlockTypes.AIR);
                loc1_1.setBlockType(BlockTypes.AIR);
                loc1_2.setBlockType(BlockTypes.AIR);
                loc1_3.setBlockType(BlockTypes.AIR);
                loc1_4.setBlockType(BlockTypes.AIR);
                loc1_5.setBlockType(BlockTypes.AIR);
                loc1_6.setBlockType(BlockTypes.AIR);
                loc1_7.setBlockType(BlockTypes.AIR);
                loc1_8.setBlockType(BlockTypes.AIR);
                
                cobwebPlayers.remove(event.getTargetEntity());
                cobwebMid.remove(cobweb);
            }
        }
    }
}
