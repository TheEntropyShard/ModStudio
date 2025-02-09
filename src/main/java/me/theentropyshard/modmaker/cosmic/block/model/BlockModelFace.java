/*
 * ModMaker - https://github.com/TheEntropyShard/ModMaker
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

package me.theentropyshard.modmaker.cosmic.block.model;

import com.google.gson.annotations.SerializedName;

import java.awt.image.BufferedImage;

public class BlockModelFace {
    private float[] uv;

    @SerializedName("ambientocclusion")
    private boolean ambientOcclusion;

    private boolean cullFace;
    private String texture;

    private transient BufferedImage textureImage;

    public BlockModelFace() {

    }

    public BlockModelFace(float[] uv, boolean ambientOcclusion, boolean cullFace, String texture) {
        this.uv = uv;
        this.ambientOcclusion = ambientOcclusion;
        this.cullFace = cullFace;
        this.texture = texture;
    }

    public float[] getUv() {
        return this.uv;
    }

    public void setUv(float[] uv) {
        this.uv = uv;
    }

    public boolean isAmbientOcclusion() {
        return this.ambientOcclusion;
    }

    public void setAmbientOcclusion(boolean ambientOcclusion) {
        this.ambientOcclusion = ambientOcclusion;
    }

    public boolean isCullFace() {
        return this.cullFace;
    }

    public void setCullFace(boolean cullFace) {
        this.cullFace = cullFace;
    }

    public String getTexture() {
        return this.texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public BufferedImage getTextureImage() {
        return this.textureImage;
    }

    public void setTextureImage(BufferedImage textureImage) {
        this.textureImage = textureImage;
    }
}
