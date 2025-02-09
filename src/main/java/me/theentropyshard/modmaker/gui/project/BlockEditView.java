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

package me.theentropyshard.modmaker.gui.project;

import me.theentropyshard.modmaker.cosmic.block.Block;
import me.theentropyshard.modmaker.cosmic.block.BlockState;
import me.theentropyshard.modmaker.cosmic.block.model.BlockModelTexture;
import me.theentropyshard.modmaker.gui.HtmlLabel;
import me.theentropyshard.modmaker.gui.Icons;
import me.theentropyshard.modmaker.gui.components.AccordionPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class BlockEditView extends JPanel {
    private final JLabel blockStringIdLabel;

    public BlockEditView(Block block) {
        super(new MigLayout("", "[grow]", "[top][]"));

        this.blockStringIdLabel = new HtmlLabel.Builder()
            .bold("String id: ")
            .text(block.getStringId())
            .build();
        this.add(this.blockStringIdLabel, "growx, wrap");

        block.getBlockStates().forEach((blockStringId, blockStateId) -> {
            this.addBlockStateView(block.getStringId(), blockStringId, blockStateId);
        });
    }

    private void addBlockStateView(String blockStringId, String blockStateParams, BlockState blockState) {
        Map<String, BlockModelTexture> textures = blockState.getBlockModel().getTextures();

        BufferedImage texture;

        if (textures.containsKey("all")) {
            texture = textures.get("all").getTexture();
        } else if (textures.containsKey("side")) {
            texture = textures.get("side").getTexture();
        } else {
            texture = null;
        }

        JPanel panel = new JPanel(new MigLayout("", "[left][center]", "[]"));
        panel.add(new JLabel(texture == null ? Icons.MOD_DEFAULT_TEXTURE : new ImageIcon(texture)));
        AccordionPanel myPnaleTtitle = new AccordionPanel(blockStringId + "[" + blockStateParams + "]", panel);
        this.add(myPnaleTtitle, "growx, wrap");
    }
}
