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

import me.theentropyshard.modmaker.utils.MouseClickListener;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProjectTree extends JTree {
    private final Set<ProjectTreeListener> listeners;

    public ProjectTree() {
        super(ProjectTree.getRootNode());

        this.listeners = new LinkedHashSet<>();

        this.setShowsRootHandles(true);
        this.setCellRenderer(new ProjectTreeCellRenderer());
        this.setRowHeight(20);

        this.addMouseListener(new MouseClickListener(e -> {
            TreePath path = this.getPathForLocation(e.getX(), e.getY());
            this.setSelectionPath(path);
            path = this.getSelectionPath();

            if (path == null) {
                return;
            }

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

            for (ProjectTreeListener listener : this.listeners) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    listener.leftDoubleClick(node, e);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    listener.rightClick(node, e);
                }
            }
        }));
    }

    public void addListener(ProjectTreeListener listener) {
        this.listeners.add(listener);
    }

    public interface ProjectTreeListener {
        void leftDoubleClick(DefaultMutableTreeNode treeNode, MouseEvent event);

        void rightClick(DefaultMutableTreeNode treeNode, MouseEvent event);
    }

    public static abstract class ProjectTreeListenerAdapter implements ProjectTreeListener {
        public ProjectTreeListenerAdapter() {

        }

        @Override
        public void leftDoubleClick(DefaultMutableTreeNode treeNode, MouseEvent event) {

        }

        @Override
        public void rightClick(DefaultMutableTreeNode treeNode, MouseEvent event) {

        }
    }

    private static TreeNode getRootNode() {
        ProjectNode rootNode = new ProjectNode("More blocks");

        CategoryNode blocksNode = new CategoryNode("Blocks");
        //blocksNode.add(new DefaultMutableTreeNode("Green Planks"));
        rootNode.add(blocksNode);

        CategoryNode itemsNode = new CategoryNode("Items");
        //itemsNode.add(new DefaultMutableTreeNode("Banana"));
        rootNode.add(itemsNode);

        return rootNode;
    }
}
