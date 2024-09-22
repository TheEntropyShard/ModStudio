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

package me.theentropyshard.modmaker.cosmic.block.model;

import com.google.gson.annotations.SerializedName;

public class CanPlace {
    @SerializedName("block_has_tag")
    private PlaceTag blockHasTag;

    public CanPlace() {

    }

    public PlaceTag getBlockHasTag() {
        return this.blockHasTag;
    }

    public void setBlockHasTag(PlaceTag blockHasTag) {
        this.blockHasTag = blockHasTag;
    }
}
