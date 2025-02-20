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

package me.theentropyshard.modstudio.cosmic.block;

import me.theentropyshard.modstudio.cosmic.block.model.BlockModel;
import me.theentropyshard.modstudio.cosmic.block.model.CanPlace;

import java.util.List;
import java.util.Map;

/**
 * All fields here are using wrappers for primitive types, because GSON can exclude null fields from serialization,
 * which makes it very easy to generate only needed data.
 */
public class BlockState {
    private String langKey;
    private String modelName;
    private String swapGroupId;
    private String blockEventsId;
    private String dropId;
    private Boolean allowSwapping/* = true*/;
    private Boolean isOpaque/* = true*/;
    private Boolean canRaycastForBreak/* = true*/;
    private Boolean canRaycastForPlaceOn/* = true*/;
    private Boolean canRaycastForReplace;
    private Boolean catalogHidden;
    private Boolean walkThrough;
    private Boolean isFluid;
    private Integer lightAttenuation/* = 15*/;
    private Integer lightLevelRed;
    private Integer lightLevelGreen;
    private Integer lightLevelBlue;
    private Integer rotXZ;
    private Float hardness/* = 1.5f*/;
    private Float blastResistance/* = 100.0f*/;
    private List<String> stateGenerators;
    private List<String> tags;
    private Map<String, Object> dropParams;
    private Map<String, Integer> intProperties;
    private CanPlace canPlace;
    private Float friction/* = 1.0f*/;
    private Float bounciness/* = 0.0f*/;
    private Float refractiveIndex/* = 1.0f*/;

    private transient BlockModel blockModel;
    private transient String params;

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
        return this.allowSwapping != null && this.allowSwapping;
    }

    public void setAllowSwapping(boolean allowSwapping) {
        this.allowSwapping = allowSwapping;
    }

    public boolean isOpaque() {
        return this.isOpaque != null && this.isOpaque;
    }

    public void setOpaque(boolean opaque) {
        this.isOpaque = opaque;
    }

    public boolean canRaycastForBreak() {
        return this.canRaycastForBreak != null && this.canRaycastForBreak;
    }

    public void setCanRaycastForBreak(boolean canRaycastForBreak) {
        this.canRaycastForBreak = canRaycastForBreak;
    }

    public boolean canRaycastForPlaceOn() {
        return this.canRaycastForPlaceOn != null && this.canRaycastForPlaceOn;
    }

    public void setCanRaycastForPlaceOn(boolean canRaycastForPlaceOn) {
        this.canRaycastForPlaceOn = canRaycastForPlaceOn;
    }

    public boolean canRaycastForReplace() {
        return this.canRaycastForReplace != null && this.canRaycastForReplace;
    }

    public void setCanRaycastForReplace(boolean canRaycastForReplace) {
        this.canRaycastForReplace = canRaycastForReplace;
    }

    public boolean isCatalogHidden() {
        return this.catalogHidden != null && this.catalogHidden;
    }

    public void setCatalogHidden(boolean catalogHidden) {
        this.catalogHidden = catalogHidden;
    }

    public boolean isWalkThrough() {
        return this.walkThrough != null && this.walkThrough;
    }

    public void setWalkThrough(boolean walkThrough) {
        this.walkThrough = walkThrough;
    }

    public boolean isFluid() {
        return this.isFluid != null && this.isFluid;
    }

    public void setFluid(boolean isFluid) {
        this.isFluid = isFluid;
    }

    public int getLightAttenuation() {
        return this.lightAttenuation == null ? 0 : this.lightAttenuation;
    }

    public void setLightAttenuation(int lightAttenuation) {
        this.lightAttenuation = lightAttenuation;
    }

    public int getLightLevelRed() {
        return this.lightLevelRed == null ? 0 : this.lightLevelRed;
    }

    public void setLightLevelRed(int lightLevelRed) {
        this.lightLevelRed = lightLevelRed;
    }

    public int getLightLevelGreen() {
        return this.lightLevelGreen == null ? 0 : this.lightLevelGreen;
    }

    public void setLightLevelGreen(int lightLevelGreen) {
        this.lightLevelGreen = lightLevelGreen;
    }

    public int getLightLevelBlue() {
        return this.lightLevelBlue == null ? 0 : this.lightLevelBlue;
    }

    public void setLightLevelBlue(int lightLevelBlue) {
        this.lightLevelBlue = lightLevelBlue;
    }

    public int getRotXZ() {
        return this.rotXZ == null ? 0 : this.rotXZ;
    }

    public void setRotXZ(int rotXZ) {
        this.rotXZ = rotXZ;
    }

    public float getHardness() {
        return this.hardness == null ? 0.0f : this.hardness;
    }

    public void setHardness(float hardness) {
        this.hardness = hardness;
    }

    public float getBlastResistance() {
        return this.blastResistance == null ? 0.0f : this.blastResistance;
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

    public float getFriction() {
        return this.friction == null ? 0.0f : this.friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getBounciness() {
        return this.bounciness == null ? 0.0f : this.bounciness;
    }

    public void setBounciness(float bounciness) {
        this.bounciness = bounciness;
    }

    public float getRefractiveIndex() {
        return this.refractiveIndex == null ? 0.0f : this.refractiveIndex;
    }

    public void setRefractiveIndex(float refractiveIndex) {
        this.refractiveIndex = refractiveIndex;
    }

    public BlockModel getBlockModel() {
        return this.blockModel;
    }

    public void setBlockModel(BlockModel blockModel) {
        this.blockModel = blockModel;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "BlockState{" +
            "langKey='" + this.langKey + '\'' +
            ", modelName='" + this.modelName + '\'' +
            ", swapGroupId='" + this.swapGroupId + '\'' +
            ", blockEventsId='" + this.blockEventsId + '\'' +
            ", dropId='" + this.dropId + '\'' +
            ", allowSwapping=" + this.allowSwapping +
            ", isOpaque=" + this.isOpaque +
            ", canRaycastForBreak=" + this.canRaycastForBreak +
            ", canRaycastForPlaceOn=" + this.canRaycastForPlaceOn +
            ", canRaycastForReplace=" + this.canRaycastForReplace +
            ", catalogHidden=" + this.catalogHidden +
            ", walkThrough=" + this.walkThrough +
            ", isFluid=" + this.isFluid +
            ", lightAttenuation=" + this.lightAttenuation +
            ", lightLevelRed=" + this.lightLevelRed +
            ", lightLevelGreen=" + this.lightLevelGreen +
            ", lightLevelBlue=" + this.lightLevelBlue +
            ", rotXZ=" + this.rotXZ +
            ", hardness=" + this.hardness +
            ", blastResistance=" + this.blastResistance +
            ", stateGenerators=" + this.stateGenerators +
            ", tags=" + this.tags +
            ", dropParams=" + this.dropParams +
            ", intProperties=" + this.intProperties +
            ", canPlace=" + this.canPlace +
            ", blockModel=" + this.blockModel +
            '}';
    }
}
