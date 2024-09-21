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

import com.formdev.flatlaf.FlatLightLaf;
import me.theentropyshard.modmaker.utils.SwingUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class Gui {
    public Gui() {
        Gui.prepare();

        DefaultMutableTreeNode project = new DefaultMutableTreeNode("Project");
        DefaultMutableTreeNode blocks = new DefaultMutableTreeNode("Blocks");
        blocks.add(new DefaultMutableTreeNode("sponge.json"));
        project.add(blocks);
        DefaultMutableTreeNode textures = new DefaultMutableTreeNode("Textures");
        textures.add(new DefaultMutableTreeNode("sponge.png"));
        project.add(textures);
        JTree root = new JTree(project);
        root.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                String s = String.valueOf(value);
                if (s.endsWith(".png")) {
                    this.setIcon(Icons.get("image"));
                } else if (s.endsWith(".json")) {
                    this.setIcon(Icons.get("json"));
                }

                return c;
            }
        });

        JFrame frame = new JFrame("ModMaker");
        root.setPreferredSize(new Dimension(800, 600));
        frame.add(root, BorderLayout.CENTER);
        frame.pack();
        SwingUtils.centerWindow(frame, 0);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void prepare() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        FlatLightLaf.setup();
    }
}
