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

import com.formdev.flatlaf.FlatClientProperties;
import me.theentropyshard.modmaker.project.Project;
import me.theentropyshard.modmaker.cosmic.block.Block;
import me.theentropyshard.modmaker.gui.FlatSmoothScrollPaneUI;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntConsumer;

public class ProjectView extends JPanel {
    private final ProjectTree projectTree;
    private final JTabbedPane views;
    private final JSplitPane splitPane;

    public ProjectView(Dimension preferredSize, Project project) {
        super(new BorderLayout());

        this.setPreferredSize(preferredSize);

        this.projectTree = new ProjectTree(project.getName(), (int) (preferredSize.width * 0.25));

        this.views = new JTabbedPane();
        this.views.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSABLE, Boolean.TRUE);
        this.views.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSE_CALLBACK, (IntConsumer) this.views::removeTabAt);

        this.projectTree.addNewBlockListener(stringId -> {
            stringId = project.getNamespace() + ":" + stringId;
            //this.views.addTab(stringId, new BlockEditView(Icons.MOD_DEFAULT_TEXTURE, stringId));
        });

        this.projectTree.addLeftClickListener((clickCount, treePath) -> {
            if (clickCount == 2) {
                Object lastPathComponent = treePath.getLastPathComponent();

                if (!(lastPathComponent instanceof ProjectTreeNode)) {
                    return;
                }

                ProjectTreeNode treeNode = (ProjectTreeNode) lastPathComponent;
                Block block = (Block) treeNode.getUserObject();

                JScrollPane scrollPane = new JScrollPane(
                    new BlockEditView(block),
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
                );
                scrollPane.setUI(new FlatSmoothScrollPaneUI());

                this.views.addTab(block.getStringId(), scrollPane);
            }
        });

        for (Block block : project.getBlocks()) {
            this.projectTree.addBlock(block);
            this.projectTree.revalidate();
        }

        this.projectTree.addDeleteBlockListener(stringId -> {
            System.out.println("deleted " + stringId);
        });

        JScrollPane scrollPane = new JScrollPane(
            this.projectTree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        scrollPane.setUI(new FlatSmoothScrollPaneUI());
        this.projectTree.setVisibleRowCount(100);

        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, this.views);
        this.add(this.splitPane, BorderLayout.CENTER);
    }
}
