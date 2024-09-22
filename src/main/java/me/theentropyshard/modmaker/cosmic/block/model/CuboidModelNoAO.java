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

public class CuboidModelNoAO extends CuboidModel {
    public CuboidModelNoAO() {

    }

    @Override
    public void createFaces() {
        this.addFace("localNegX", new BlockModelFace(new float[] {0, 0, 16, 16}, false, true, "side"));
        this.addFace("localPosX", new BlockModelFace(new float[] {0, 0, 16, 16}, false, true, "side"));
        this.addFace("localNegY", new BlockModelFace(new float[] {0, 0, 16, 16}, false, true, "bottom"));
        this.addFace("localPosY", new BlockModelFace(new float[] {0, 0, 16, 16}, false, true, "top"));
        this.addFace("localNegZ", new BlockModelFace(new float[] {0, 0, 16, 16}, false, true, "side"));
        this.addFace("localPosZ", new BlockModelFace(new float[] {0, 0, 16, 16}, false, true, "side"));
    }
}
