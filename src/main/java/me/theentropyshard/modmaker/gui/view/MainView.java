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

import com.formdev.flatlaf.FlatIntelliJLaf;
import me.theentropyshard.modmaker.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainView {
    private JFrame frame;
    private JMenuItem newProjectMenuItem;

    public MainView() {
        MainView.prepare();
    }

    private static void prepare() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        UIManager.put("SplitPaneDivider.style", "plain");
        UIManager.getDefaults().put("Button.showMnemonics", true);
        UIManager.getDefaults().put("Component.hideMnemonics", false);

        FlatIntelliJLaf.setup();
    }

    public void createComponents(Component content) {
        this.frame = new JFrame("ModMaker");
        this.frame.setJMenuBar(this.createMenuBar());
        this.frame.add(content, BorderLayout.CENTER);
        this.frame.pack();
        SwingUtils.centerWindow(this.frame, 0);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        {
            JMenu fileMenu = new JMenu("File");
            fileMenu.setMnemonic(KeyEvent.VK_F);

            this.newProjectMenuItem = new JMenuItem("New Project");
            this.newProjectMenuItem.setMnemonic(KeyEvent.VK_P);
            fileMenu.add(this.newProjectMenuItem);

            menuBar.add(fileMenu);
        }

        return menuBar;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public JMenuItem getNewProjectMenuItem() {
        return this.newProjectMenuItem;
    }
}
