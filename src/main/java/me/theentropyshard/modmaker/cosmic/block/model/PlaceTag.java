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

public class PlaceTag {
    private String tag;
    private float[] position;

    public PlaceTag() {

    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float[] getPosition() {
        return this.position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }
}
