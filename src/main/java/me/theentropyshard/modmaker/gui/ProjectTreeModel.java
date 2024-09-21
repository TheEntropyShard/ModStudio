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

package me.theentropyshard.modmaker.gui;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class ProjectTreeModel extends DefaultTreeModel {
    public ProjectTreeModel() {
        super(ProjectTreeModel.getDummyTreeNode());
    }

    private static TreeNode getDummyTreeNode() {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("Project name");

        DefaultMutableTreeNode blocksNode = new DefaultMutableTreeNode("Blocks");
        blocksNode.add(new DefaultMutableTreeNode("sapling.json"));
        treeNode.add(blocksNode);

        DefaultMutableTreeNode modelsNode = new DefaultMutableTreeNode("Models");
        modelsNode.add(new DefaultMutableTreeNode("sapling_model.json"));
        treeNode.add(modelsNode);

        DefaultMutableTreeNode texturesNode = new DefaultMutableTreeNode("Textures");
        texturesNode.add(new DefaultMutableTreeNode("sapling.png"));
        treeNode.add(texturesNode);

        return treeNode;
    }
}
