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

package me.theentropyshard.modmaker.gui.view.project.tree;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import me.theentropyshard.modmaker.gui.Icons;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class ProjectTreeRenderer extends DefaultTreeCellRenderer {
    private static final Icon PROJECT = Icons.get("project");
    private static final Icon CYAN_FOLDER = Icons.get("folder").setColorFilter(new FlatSVGIcon.ColorFilter(color -> Color.decode("#40b6e0")));
    private static final Icon JSON = Icons.get("json");

    public ProjectTreeRenderer() {

    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof ProjectTreeNode projectNode) {
            this.setText(projectNode.getText());

            switch (projectNode.getType()) {
                case PROJECT -> this.setIcon(ProjectTreeRenderer.PROJECT);
                case CATEGORY -> this.setIcon(ProjectTreeRenderer.CYAN_FOLDER);
                case FILE -> this.setIcon(ProjectTreeRenderer.JSON);
            }
        }

        return c;
    }
}
