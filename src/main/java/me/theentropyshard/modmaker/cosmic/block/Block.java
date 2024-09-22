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

package me.theentropyshard.modmaker.cosmic.block;

import java.util.Map;

public class Block {
    private String stringId;
    private String blockEntityId;
    private Map<String, String> defaultParams;
    private Map<String, BlockState> blockStates;
    private Map<String, ?> blockEntityParams;

    public Block() {

    }

    public String getStringId() {
        return this.stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getBlockEntityId() {
        return this.blockEntityId;
    }

    public void setBlockEntityId(String blockEntityId) {
        this.blockEntityId = blockEntityId;
    }

    public Map<String, String> getDefaultParams() {
        return this.defaultParams;
    }

    public void setDefaultParams(Map<String, String> defaultParams) {
        this.defaultParams = defaultParams;
    }

    public Map<String, BlockState> getBlockStates() {
        return this.blockStates;
    }

    public void setBlockStates(Map<String, BlockState> blockStates) {
        this.blockStates = blockStates;
    }

    public Map<String, ?> getBlockEntityParams() {
        return this.blockEntityParams;
    }

    public void setBlockEntityParams(Map<String, ?> blockEntityParams) {
        this.blockEntityParams = blockEntityParams;
    }
}
