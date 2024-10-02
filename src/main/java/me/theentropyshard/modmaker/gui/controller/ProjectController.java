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

import me.theentropyshard.modmaker.gui.view.project.ProjectView;
import me.theentropyshard.modmaker.gui.view.project.tree.ProjectTree;
import me.theentropyshard.modmaker.gui.view.project.tree.ProjectTreeNode;
import me.theentropyshard.modmaker.project.Project;
import me.theentropyshard.modmaker.project.ProjectBlock;
import me.theentropyshard.modmaker.utils.Worker;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProjectController {
    private ProjectView projectView;

    public ProjectController() {

    }

    public ProjectView openProject(Project project) {
        this.projectView = new ProjectView();

        new Worker<Void, Object>("loading project") {
            private ProjectTreeNode rootNode;
            private ProjectTreeNode blocksNode;

            {
                this.rootNode = new ProjectTreeNode(project.getName(), ProjectTreeNode.Type.PROJECT);
                this.blocksNode = new ProjectTreeNode("Blocks", ProjectTreeNode.Type.CATEGORY);
            }

            @Override
            protected Void work() throws Exception {
                for (ProjectBlock block : project.getBlocks()) {
                    this.publish(block);
                }

                return null;
            }

            @Override
            protected void process(List<Object> chunks) {
                for (Object chunk : chunks) {
                    if (chunk instanceof ProjectBlock block) {
                        this.blocksNode.add(new ProjectTreeNode(
                            block.getBlock().getStringId(), ProjectTreeNode.Type.FILE
                        ));
                    }
                }
            }

            @Override
            protected void done() {
                this.rootNode.add(this.blocksNode);

                ProjectTree projectTree = new ProjectTree(this.rootNode);
                projectTree.addListener(new ProjectTree.ProjectTreeListener() {
                    @Override
                    public void leftDoubleClick(DefaultMutableTreeNode treeNode, MouseEvent event) {

                    }

                    @Override
                    public void rightClick(DefaultMutableTreeNode treeNode, MouseEvent event) {
                        JPopupMenu popupMenu = new JPopupMenu();

                        String s = String.valueOf(treeNode.getUserObject());

                        if (s.equals("Blocks")) {
                            JMenuItem createBlockItem = new JMenuItem("Create Block");
                            createBlockItem.addActionListener(e -> {

                            });

                            popupMenu.add(createBlockItem);
                        } else if (s.equals("Items")) {
                            JMenuItem createBlockItem = new JMenuItem("Create Item");
                            createBlockItem.addActionListener(e -> {

                            });

                            popupMenu.add(createBlockItem);
                        }

                        popupMenu.show(projectTree, event.getX(), event.getY());
                    }
                });
                ProjectController.this.projectView.setProjectTree(projectTree);
            }
        }.execute();

        return this.projectView;
    }
}
