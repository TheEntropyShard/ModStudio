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

import java.awt.image.BufferedImage;

public class BlockModelTexture {
    private float[] uv;
    private String fileName;

    private transient BufferedImage texture;

    public BlockModelTexture() {

    }

    public float[] getUv() {
        return this.uv;
    }

    public void setUv(float[] uv) {
        this.uv = uv;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BufferedImage getTexture() {
        return this.texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }
}
