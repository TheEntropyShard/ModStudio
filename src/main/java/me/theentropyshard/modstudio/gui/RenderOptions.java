package me.theentropyshard.modstudio.gui;

public class RenderOptions {
    private int scale = 1;
    private boolean noShadow;

    public RenderOptions() {

    }

    public int getScale() {
        return this.scale;
    }

    public RenderOptions setScale(int scale) {
        this.scale = scale;

        return this;
    }

    public boolean isNoShadow() {
        return this.noShadow;
    }

    public RenderOptions setNoShadow(boolean noShadow) {
        this.noShadow = noShadow;

        return this;
    }
}