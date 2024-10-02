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

import me.theentropyshard.modmaker.gui.controller.MainController;
import me.theentropyshard.modmaker.gui.view.MainView;
import me.theentropyshard.modmaker.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class AppFrame extends JFrame {
    private final JMenuItem newProjectItem;
    private final JMenuItem openProjectItem;

    public AppFrame() {
        super("ModMaker");

        this.newProjectItem = new JMenuItem("New Project");
        this.newProjectItem.setMnemonic(KeyEvent.VK_N);

        this.openProjectItem = new JMenuItem("Open Project");
        this.openProjectItem.setMnemonic(KeyEvent.VK_O);

        MainView mainView = new MainView();
        mainView.setPreferredSize(new Dimension(1280, 720));
        this.add(mainView, BorderLayout.CENTER);

        new MainController(this, mainView);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        fileMenu.add(this.newProjectItem);
        fileMenu.add(this.openProjectItem);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);

        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        SwingUtils.centerWindow(this, 0);
    }

    public JMenuItem getNewProjectItem() {
        return this.newProjectItem;
    }

    public JMenuItem getOpenProjectItem() {
        return this.openProjectItem;
    }
}
