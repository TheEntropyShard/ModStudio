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

import me.theentropyshard.modmaker.ModMaker;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProjectTree extends JTree {
    private final ProjectTreeNode rootNode;
    private final ProjectTreeNode blocksNode;

    public ProjectTree(String projectName) {
        this.rootNode = new ProjectTreeNode(projectName, ProjectTreeNode.Type.PROJECT);
        this.blocksNode = new ProjectTreeNode("Blocks", ProjectTreeNode.Type.CATEGORY);
        this.rootNode.add(this.blocksNode);

        DefaultTreeModel model = new DefaultTreeModel(this.rootNode);
        this.setModel(model);

        this.setRowHeight(20);
        this.setShowsRootHandles(true);
        this.setRootVisible(true);
        this.setCellRenderer(new ProjectTreeCellRenderer());

        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem addBlockItem = new JMenuItem("Add Block");
        JMenuItem deleteBlockItem = new JMenuItem("Delete Block");

        addBlockItem.addActionListener(e -> {
            TreePath selectedPath = this.getSelectionPath();

            if (selectedPath == null) {
                return;
            }

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

            if (selectedNode != this.blocksNode) {
                return;
            }

            String blockName = JOptionPane.showInputDialog(
                ModMaker.getInstance().getAppFrame(),
                "Enter a block name"
            );

            if (blockName == null || blockName.isEmpty()) {
                return;
            }

            ProjectTreeNode newNode = new ProjectTreeNode(blockName, ProjectTreeNode.Type.FILE);
            model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
            this.scrollPathToVisible(new TreePath(newNode.getPath()));
        });

        deleteBlockItem.addActionListener(e -> {
            TreePath selectedPath = this.getSelectionPath();

            if (selectedPath == null) {
                return;
            }

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

            if (selectedNode.getParent() != this.blocksNode) {
                return;
            }

            model.removeNodeFromParent(selectedNode);
        });

        contextMenu.add(addBlockItem);
        contextMenu.add(deleteBlockItem);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    this.showContextMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    this.showContextMenu(e);
                }
            }

            private void showContextMenu(MouseEvent e) {
                TreePath path = ProjectTree.this.getPathForLocation(e.getX(), e.getY());

                if (path == null) {
                    return;
                }

                ProjectTree.this.setSelectionPath(path);

                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }
}
