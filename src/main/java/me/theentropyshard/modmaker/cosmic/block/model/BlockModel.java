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
