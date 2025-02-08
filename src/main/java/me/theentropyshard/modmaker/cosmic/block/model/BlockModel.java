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

import java.util.List;
import java.util.Map;

public class BlockModel {
    private String parent;
    private boolean isTransparent;
    private boolean cullsSelf = true;
    private Map<String, BlockModelTexture> textures;
    private List<CuboidModel> cuboids;

    public BlockModel() {

    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isTransparent() {
        return this.isTransparent;
    }

    public void setTransparent(boolean transparent) {
        this.isTransparent = transparent;
    }

    public boolean isCullsSelf() {
        return this.cullsSelf;
    }

    public void setCullsSelf(boolean cullsSelf) {
        this.cullsSelf = cullsSelf;
    }

    public Map<String, BlockModelTexture> getTextures() {
        return this.textures;
    }

    public void setTextures(Map<String, BlockModelTexture> textures) {
        this.textures = textures;
    }

    public List<CuboidModel> getCuboids() {
        return this.cuboids;
    }

    public void setCuboids(List<CuboidModel> cuboids) {
        this.cuboids = cuboids;
    }
}
