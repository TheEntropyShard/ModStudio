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

package me.theentropyshard.modmaker.gui.controller;

import com.formdev.flatlaf.FlatClientProperties;
import me.theentropyshard.modmaker.gui.project.ProjectTree;
import me.theentropyshard.modmaker.gui.view.*;
import me.theentropyshard.modmaker.utils.SwingUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.IntConsumer;

public class MainViewController {
    private final MainView mainView;
    private final JTabbedPane tabbedPane;

    public MainViewController() {
        this.mainView = new MainView();

        this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        this.tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSABLE, Boolean.TRUE);
        this.tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSE_CALLBACK, (IntConsumer) value -> {
            this.tabbedPane.removeTabAt(value);
        });

        ProjectView projectView = new ProjectView(new Dimension(1280, 720));

        ProjectTree tree = new ProjectTree();
        tree.addListener(new ProjectTree.ProjectTreeListenerAdapter() {
            @Override
            public void leftDoubleClick(DefaultMutableTreeNode treeNode, MouseEvent event) {
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) treeNode.getParent();

                String s = String.valueOf(parent.getUserObject());

                if (s.equals("Blocks")) {
                    String blockName = String.valueOf(treeNode.getUserObject());
                    int index = MainViewController.this.tabbedPane.indexOfTab(blockName);
                } else if (s.equals("Items")) {
                    String itemName = String.valueOf(treeNode.getUserObject());
                    int index = MainViewController.this.tabbedPane.indexOfTab(itemName);
                }
            }

            @Override
            public void rightClick(DefaultMutableTreeNode treeNode, MouseEvent event) {
                JPopupMenu popupMenu = new JPopupMenu();

                String s = String.valueOf(treeNode.getUserObject());

                if (s.equals("Blocks")) {
                    JMenuItem createBlockItem = new JMenuItem("Create Block");
                    createBlockItem.addActionListener(e -> {
                        String name = "Block " + Math.random();
                        BlockEditView component = new BlockEditView();
                        MainViewController.this.tabbedPane.addTab(name, component);
                        treeNode.add(new DefaultMutableTreeNode(name));
                        ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(treeNode);
                    });

                    popupMenu.add(createBlockItem);
                } else if (s.equals("Items")) {
                    JMenuItem createBlockItem = new JMenuItem("Create Item");
                    createBlockItem.addActionListener(e -> {
                        String name = "Item " + Math.random();
                        ItemEditView component = new ItemEditView(name);
                        MainViewController.this.tabbedPane.add(name, component);
                        treeNode.add(new DefaultMutableTreeNode(name));
                        ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(treeNode);
                    });

                    popupMenu.add(createBlockItem);
                }

                popupMenu.show(tree, event.getX(), event.getY());
            }
        });

        projectView.setProjectTree(tree);
        projectView.setEditorView(this.tabbedPane);

        this.mainView.createComponents(projectView);

        this.mainView.getNewProjectMenuItem().addActionListener(e -> this.onNewProjectClicked());
    }

    public void onNewProjectClicked() {
        JDialog dialog = new JDialog(this.mainView.getFrame(), "Create New Project", true);
        ProjectCreationView comp = new ProjectCreationView();
        dialog.getRootPane().setDefaultButton(comp.getCreateButton());
        comp.setPreferredSize(new Dimension((int) (1280 * 0.5), (int) (720 * 0.5)));
        dialog.add(comp, BorderLayout.CENTER);
        dialog.pack();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        SwingUtils.centerWindow(dialog, 0);
        dialog.setVisible(true);
    }

    public void show() {
        this.mainView.getFrame().setVisible(true);
    }
}
