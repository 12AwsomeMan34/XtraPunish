package com.awesomeman.xtrapunish.util;

import java.util.List;

public class CmdUtil {

    /**
     * Removes the most recent history from a block.
     * 
     * @param history The history of the affected blocks
     * @return If the undo was successful
     */
    public static UndoSuccess removeBlockHistory(List<AffectedBlocks> history) {
        AffectedBlocks affected = history.get(history.size() - 1);
        for (int i = 0; i < affected.loc.size(); i++) {
            affected.loc.get(i).setBlock(affected.oldState.get(i));
        }
        history.remove(affected);
        return UndoSuccess.SUCCESS;
    }

    public enum UndoSuccess {

        SUCCESS,
        FAILURE_NO_HISTORY,
        FAILUE_NOT_SUPPORTED,
        FAILUE_FOOD_DATA,
        FAILUE_NO_PLAYER,
        FAILUE_UNKNOWN;
    }
}
