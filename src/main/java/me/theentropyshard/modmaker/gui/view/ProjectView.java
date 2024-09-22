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

package me.theentropyshard.modmaker.gui.view;

import me.theentropyshard.modmaker.gui.project.ProjectTree;

import javax.swing.*;
import java.awt.*;

public class ProjectView extends JPanel {
    private final JSplitPane splitPane;

    public ProjectView(Dimension size) {
        super(new BorderLayout());

        this.setPreferredSize(size);

        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.splitPane.setDividerLocation((int) (size.width * 0.25));

        this.add(this.splitPane, BorderLayout.CENTER);
    }

    public void setProjectTree(ProjectTree tree) {
        Dimension size = this.getPreferredSize();
        tree.setMinimumSize(new Dimension((int) (size.width * 0.25), size.height));
        this.splitPane.setLeftComponent(tree);
    }

    public void setEditorView(Component component) {
        Dimension size = this.getPreferredSize();
        component.setMinimumSize(new Dimension((int) (size.width * 0.75), size.height));
        this.splitPane.setRightComponent(component);
    }
}
