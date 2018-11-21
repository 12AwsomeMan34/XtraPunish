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

package io.github.narikiro.xtrapunish.util;

import java.util.List;

public class CmdUtil {

    /**
     * Removes the most recent history from a block.
     * 
     * @param history The history of the affected blocks
     * @return If the undo was successful
     */
    public static UndoSuccess removeBlockHistory(List<AffectedBlocks> history) {
        if (history.isEmpty()) {
            return UndoSuccess.FAILURE_NO_HISTORY;
        }

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
