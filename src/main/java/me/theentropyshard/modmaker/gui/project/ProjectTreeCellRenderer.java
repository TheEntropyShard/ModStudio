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

package me.theentropyshard.modmaker.gui.project;

import me.theentropyshard.modmaker.gui.Icons;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class ProjectTreeCellRenderer implements TreeCellRenderer {
    private final JLabel label;

    public ProjectTreeCellRenderer() {
        this.label = new JLabel();
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {

        this.label.setText(String.valueOf(value));

        if (value instanceof ProjectNode) {
            this.label.setIcon(Icons.get("project"));
        } else if (value instanceof CategoryNode) {
            this.label.setIcon(Icons.get("folder"));
        } else {
            this.label.setIcon(Icons.get("unknown"));
        }

        if (selected) {
            this.label.setForeground(Color.WHITE);
        } else {
            this.label.setForeground(Color.BLACK);
        }

        return this.label;
    }
}
