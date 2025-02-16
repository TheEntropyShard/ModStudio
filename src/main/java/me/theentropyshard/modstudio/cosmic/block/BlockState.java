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
    private Boolean allowSwapping;
    private Boolean isOpaque;
    private Boolean canRaycastForBreak;
    private Boolean canRaycastForPlaceOn;
    private Boolean canRaycastForReplace;
    private Boolean catalogHidden;
    private Boolean walkThrough;
    private Boolean isFluid;
    private Integer lightAttenuation;
    private Integer lightLevelRed;
    private Integer lightLevelGreen;
    private Integer lightLevelBlue;
    private Integer rotXZ;
    private Float hardness;
    private Float blastResistance;
    private List<String> stateGenerators;
    private List<String> tags;
    private Map<String, Object> dropParams;
    private Map<String, Integer> intProperties;
    private CanPlace canPlace;

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

    public Boolean getAllowSwapping() {
        return this.allowSwapping;
    }

    public void setAllowSwapping(Boolean allowSwapping) {
        this.allowSwapping = allowSwapping;
    }

    public Boolean getOpaque() {
        return this.isOpaque;
    }

    public void setOpaque(Boolean opaque) {
        this.isOpaque = opaque;
    }

    public Boolean getCanRaycastForBreak() {
        return this.canRaycastForBreak;
    }

    public void setCanRaycastForBreak(Boolean canRaycastForBreak) {
        this.canRaycastForBreak = canRaycastForBreak;
    }

    public Boolean getCanRaycastForPlaceOn() {
        return this.canRaycastForPlaceOn;
    }

    public void setCanRaycastForPlaceOn(Boolean canRaycastForPlaceOn) {
        this.canRaycastForPlaceOn = canRaycastForPlaceOn;
    }

    public Boolean getCanRaycastForReplace() {
        return this.canRaycastForReplace;
    }

    public void setCanRaycastForReplace(Boolean canRaycastForReplace) {
        this.canRaycastForReplace = canRaycastForReplace;
    }

    public Boolean getCatalogHidden() {
        return this.catalogHidden;
    }

    public void setCatalogHidden(Boolean catalogHidden) {
        this.catalogHidden = catalogHidden;
    }

    public Boolean getWalkThrough() {
        return this.walkThrough;
    }

    public void setWalkThrough(Boolean walkThrough) {
        this.walkThrough = walkThrough;
    }

    public Boolean getFluid() {
        return this.isFluid;
    }

    public void setFluid(Boolean fluid) {
        this.isFluid = fluid;
    }

    public Integer getLightAttenuation() {
        return this.lightAttenuation;
    }

    public void setLightAttenuation(Integer lightAttenuation) {
        this.lightAttenuation = lightAttenuation;
    }

    public Integer getLightLevelRed() {
        return this.lightLevelRed;
    }

    public void setLightLevelRed(Integer lightLevelRed) {
        this.lightLevelRed = lightLevelRed;
    }

    public Integer getLightLevelGreen() {
        return this.lightLevelGreen;
    }

    public void setLightLevelGreen(Integer lightLevelGreen) {
        this.lightLevelGreen = lightLevelGreen;
    }

    public Integer getLightLevelBlue() {
        return this.lightLevelBlue;
    }

    public void setLightLevelBlue(Integer lightLevelBlue) {
        this.lightLevelBlue = lightLevelBlue;
    }

    public Integer getRotXZ() {
        return this.rotXZ;
    }

    public void setRotXZ(Integer rotXZ) {
        this.rotXZ = rotXZ;
    }

    public Float getHardness() {
        return this.hardness;
    }

    public void setHardness(Float hardness) {
        this.hardness = hardness;
    }

    public Float getBlastResistance() {
        return this.blastResistance;
    }

    public void setBlastResistance(Float blastResistance) {
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
