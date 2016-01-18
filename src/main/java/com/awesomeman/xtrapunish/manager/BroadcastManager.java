package com.awesomeman.xtrapunish.manager;

import org.spongepowered.api.scheduler.Task;

public class BroadcastManager {
    
    private Task broadcast;
    
    public void storeBroadcast(Task broadcast) {
        this.broadcast = broadcast;
    }
    
    public Task getBroadcast() {
        return broadcast;
    }
    
    /**
     * Cancel's the broadcast task and sets it to null, so that we don't
     * have multiple broadcasts running.
     * 
     * @return If the broadcast was not running
     */
    public boolean cancelBroadcast() {
        if(broadcast != null) {
            boolean running =  broadcast.cancel();
            broadcast = null;
            return running;
        }
        return true;
    }
}
