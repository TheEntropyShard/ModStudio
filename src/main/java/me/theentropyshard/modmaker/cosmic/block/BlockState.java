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

import me.theentropyshard.modmaker.cosmic.block.model.CanPlace;

import java.util.List;
import java.util.Map;

public class BlockState {
    private String langKey;
    private String modelName;
    private String swapGroupId;
    private String blockEventsId;
    private String dropId;
    private boolean allowSwapping = true;
    private boolean isOpaque = true;
    private boolean canRaycastForBreak = true;
    private boolean canRaycastForPlaceOn = true;
    private boolean canRaycastForReplace;
    private boolean catalogHidden;
    private boolean walkThrough;
    private boolean isFluid;
    private int lightAttenuation;
    private int lightLevelRed;
    private int lightLevelGreen;
    private int lightLevelBlue;
    private int rotXZ;
    private float hardness;
    private float blastResistance;
    private List<String> stateGenerators;
    private List<String> tags;
    private Map<String, Object> dropParams;
    private Map<String, Integer> intProperties;
    private CanPlace canPlace;

    public BlockState() {

    }

    public String getLangKey() {
        return this.langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSwapGroupId() {
        return this.swapGroupId;
    }

    public void setSwapGroupId(String swapGroupId) {
        this.swapGroupId = swapGroupId;
    }

    public String getBlockEventsId() {
        return this.blockEventsId;
    }

    public void setBlockEventsId(String blockEventsId) {
        this.blockEventsId = blockEventsId;
    }

    public String getDropId() {
        return this.dropId;
    }

    public void setDropId(String dropId) {
        this.dropId = dropId;
    }

    public boolean isAllowSwapping() {
        return this.allowSwapping;
    }

    public void setAllowSwapping(boolean allowSwapping) {
        this.allowSwapping = allowSwapping;
    }

    public boolean isOpaque() {
        return this.isOpaque;
    }

    public void setOpaque(boolean opaque) {
        this.isOpaque = opaque;
    }

    public boolean isCanRaycastForBreak() {
        return this.canRaycastForBreak;
    }

    public void setCanRaycastForBreak(boolean canRaycastForBreak) {
        this.canRaycastForBreak = canRaycastForBreak;
    }

    public boolean isCanRaycastForPlaceOn() {
        return this.canRaycastForPlaceOn;
    }

    public void setCanRaycastForPlaceOn(boolean canRaycastForPlaceOn) {
        this.canRaycastForPlaceOn = canRaycastForPlaceOn;
    }

    public boolean isCanRaycastForReplace() {
        return this.canRaycastForReplace;
    }

    public void setCanRaycastForReplace(boolean canRaycastForReplace) {
        this.canRaycastForReplace = canRaycastForReplace;
    }

    public boolean isCatalogHidden() {
        return this.catalogHidden;
    }

    public void setCatalogHidden(boolean catalogHidden) {
        this.catalogHidden = catalogHidden;
    }

    public boolean isWalkThrough() {
        return this.walkThrough;
    }

    public void setWalkThrough(boolean walkThrough) {
        this.walkThrough = walkThrough;
    }

    public boolean isFluid() {
        return this.isFluid;
    }

    public void setFluid(boolean fluid) {
        this.isFluid = fluid;
    }

    public int getLightAttenuation() {
        return this.lightAttenuation;
    }

    public void setLightAttenuation(int lightAttenuation) {
        this.lightAttenuation = lightAttenuation;
    }

    public int getLightLevelRed() {
        return this.lightLevelRed;
    }

    public void setLightLevelRed(int lightLevelRed) {
        this.lightLevelRed = lightLevelRed;
    }

    public int getLightLevelGreen() {
        return this.lightLevelGreen;
    }

    public void setLightLevelGreen(int lightLevelGreen) {
        this.lightLevelGreen = lightLevelGreen;
    }

    public int getLightLevelBlue() {
        return this.lightLevelBlue;
    }

    public void setLightLevelBlue(int lightLevelBlue) {
        this.lightLevelBlue = lightLevelBlue;
    }

    public int getRotXZ() {
        return this.rotXZ;
    }

    public void setRotXZ(int rotXZ) {
        this.rotXZ = rotXZ;
    }

    public float getHardness() {
        return this.hardness;
    }

    public void setHardness(float hardness) {
        this.hardness = hardness;
    }

    public float getBlastResistance() {
        return this.blastResistance;
    }

    public void setBlastResistance(float blastResistance) {
        this.blastResistance = blastResistance;
    }

    public List<String> getStateGenerators() {
        return this.stateGenerators;
    }

    public void setStateGenerators(List<String> stateGenerators) {
        this.stateGenerators = stateGenerators;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, Object> getDropParams() {
        return this.dropParams;
    }

    public void setDropParams(Map<String, Object> dropParams) {
        this.dropParams = dropParams;
    }

    public Map<String, Integer> getIntProperties() {
        return this.intProperties;
    }

    public void setIntProperties(Map<String, Integer> intProperties) {
        this.intProperties = intProperties;
    }

    public CanPlace getCanPlace() {
        return this.canPlace;
    }

    public void setCanPlace(CanPlace canPlace) {
        this.canPlace = canPlace;
    }
}
