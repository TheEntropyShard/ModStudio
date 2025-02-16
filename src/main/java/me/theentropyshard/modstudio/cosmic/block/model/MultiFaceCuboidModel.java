/*
 * ModStudio - https://github.com/TheEntropyShard/ModStudio
 * Copyright (C) 2024-2025 TheEntropyShard
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

package me.theentropyshard.modstudio.cosmic.block.model;

public class MultiFaceCuboidModel extends CuboidModel {
    public MultiFaceCuboidModel() {

    }

    @Override
    public void createFaces() {
        this.addFace("localNegX", new BlockModelFace(new float[] {0, 0, 16, 16}, true, true, "back"));
        this.addFace("localPosX", new BlockModelFace(new float[] {0, 0, 16, 16}, true, true, "front"));
        this.addFace("localNegY", new BlockModelFace(new float[] {0, 0, 16, 16}, true, true, "bottom"));
        this.addFace("localPosY", new BlockModelFace(new float[] {0, 0, 16, 16}, true, true, "top"));
        this.addFace("localNegZ", new BlockModelFace(new float[] {0, 0, 16, 16}, true, true, "left"));
        this.addFace("localPosZ", new BlockModelFace(new float[] {0, 0, 16, 16}, true, true, "right"));
    }
}
