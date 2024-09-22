/*
 * ModMaker - https://github.com/TheEntropyShard/ModMaker
 * Copyright (C) 2024 TheEntropyShard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.modmaker.cosmic.blockevents;

import java.util.List;
import java.util.Map;

public class BlockEvents {
    private String parent;
    private String stringId;
    private Map<String, List<BlockAction>> triggers;

    public BlockEvents() {

    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getStringId() {
        return this.stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public Map<String, List<BlockAction>> getTriggers() {
        return this.triggers;
    }

    public void setTriggers(Map<String, List<BlockAction>> triggers) {
        this.triggers = triggers;
    }
}
