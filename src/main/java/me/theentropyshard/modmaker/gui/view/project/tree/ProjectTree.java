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

import me.theentropyshard.modmaker.project.Project;

import javax.swing.*;
import javax.swing.tree.TreeNode;

public class ProjectTree extends JTree {
    public ProjectTree(TreeNode node) {
        super(node);

        this.setShowsRootHandles(true);
        this.setRootVisible(true);
        this.setCellRenderer(new ProjectTreeRenderer());
    }

    public static ProjectTree create(Project project) {
        ProjectTreeNode rootNode = new ProjectTreeNode("Project", ProjectTreeNode.Type.PROJECT);

        ProjectTreeNode blocksNode = new ProjectTreeNode("Blocks", ProjectTreeNode.Type.CATEGORY);
        blocksNode.add(new ProjectTreeNode("Apple", ProjectTreeNode.Type.FILE));
        rootNode.add(blocksNode);

        ProjectTreeNode itemsNode = new ProjectTreeNode("Items", ProjectTreeNode.Type.CATEGORY);
        rootNode.add(itemsNode);

        return new ProjectTree(rootNode);
    }
}
