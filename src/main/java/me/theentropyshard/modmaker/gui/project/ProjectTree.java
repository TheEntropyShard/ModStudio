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

import me.theentropyshard.modmaker.ModMaker;
import me.theentropyshard.modmaker.cosmic.block.Block;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

public class ProjectTree extends JTree {
    private final ProjectTreeNode rootNode;
    private final ProjectTreeNode blocksNode;
    private final Set<NewBlockListener> newBlockListeners;
    private final Set<DeleteBlockListener> deleteBlockListeners;
    private final Set<LeftClickListener> leftClickListeners;
    private final int initialRowWidth;

    public ProjectTree(String projectName, int initialRowWidth) {
        this.initialRowWidth = initialRowWidth;
        this.rootNode = new ProjectTreeNode(projectName, ProjectTreeNode.Type.PROJECT, null);
        this.blocksNode = new ProjectTreeNode("Blocks", ProjectTreeNode.Type.CATEGORY, null);
        this.rootNode.add(this.blocksNode);

        this.newBlockListeners = new HashSet<>();
        this.deleteBlockListeners = new HashSet<>();
        this.leftClickListeners = new HashSet<>();

        DefaultTreeModel model = new DefaultTreeModel(this.rootNode);
        this.setModel(model);

        this.setRowHeight(20);
        this.setShowsRootHandles(true);
        this.setRootVisible(true);
        this.setCellRenderer(new ProjectTreeCellRenderer());

        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = ProjectTree.this.getRowForLocation(e.getX(), e.getY());

                TreePath selectedPath = ProjectTree.this.getPathForLocation(e.getX(), e.getY());

                if (selRow != -1 && e.getButton() == MouseEvent.BUTTON1) {
                    for (LeftClickListener listener : ProjectTree.this.leftClickListeners) {
                        listener.onLeftClick(e.getClickCount(), selectedPath);
                    }
                }
            }
        };
        this.addMouseListener(ml);

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
                ModMaker.getInstance().getGui().getFrame(),
                "Enter a block name"
            );

            if (blockName == null || blockName.isEmpty()) {
                return;
            }

            ProjectTreeNode newNode = new ProjectTreeNode(blockName, ProjectTreeNode.Type.FILE, blockName);
            model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
            this.scrollPathToVisible(new TreePath(newNode.getPath()));

            this.newBlockListeners.forEach(listener -> listener.onNewBlock(blockName));
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

            this.deleteBlockListeners.forEach(listener -> listener.onBlockDeleted(String.valueOf(selectedNode.getUserObject())));
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

    public void addBlock(Block block) {
        ProjectTreeNode newNode = new ProjectTreeNode(block.getStringId(), ProjectTreeNode.Type.FILE, block);
        ((DefaultTreeModel) this.getModel()).insertNodeInto(newNode, this.blocksNode, this.blocksNode.getChildCount());
        this.scrollPathToVisible(new TreePath(newNode.getPath()));
    }

    public int getInitialRowWidth() {
        return this.initialRowWidth;
    }

    public interface NewBlockListener {
        void onNewBlock(String stringId);
    }

    public interface DeleteBlockListener {
        void onBlockDeleted(String stringId);
    }

    public interface LeftClickListener {
        void onLeftClick(int clickCount, TreePath treePath);
    }

    public void addNewBlockListener(NewBlockListener newBlockListener) {
        this.newBlockListeners.add(newBlockListener);
    }

    public void addDeleteBlockListener(DeleteBlockListener deleteBlockListener) {
        this.deleteBlockListeners.add(deleteBlockListener);
    }

    public void addLeftClickListener(LeftClickListener leftClickListener) {
        this.leftClickListeners.add(leftClickListener);
    }
}
