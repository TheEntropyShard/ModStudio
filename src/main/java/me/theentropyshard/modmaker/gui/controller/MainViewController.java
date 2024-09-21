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

import me.theentropyshard.modmaker.gui.project.ProjectTree;
import me.theentropyshard.modmaker.gui.view.MainView;
import me.theentropyshard.modmaker.gui.view.ProjectCreationView;
import me.theentropyshard.modmaker.gui.view.ProjectView;
import me.theentropyshard.modmaker.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class MainViewController {
    private final MainView mainView;

    public MainViewController() {
        this.mainView = new MainView();

        ProjectView projectView = new ProjectView(new Dimension(1280, 720));
        projectView.setProjectTree(new ProjectTree());
        projectView.setEditorView(new JPanel());

        this.mainView.createComponents(projectView);

        this.mainView.getNewProjectMenuItem().addActionListener(e -> this.onNewProjectClicked());
    }

    public void onNewProjectClicked() {
        JDialog dialog = new JDialog(this.mainView.getFrame(), "Create New Project", true);
        ProjectCreationView comp = new ProjectCreationView();
        dialog.getRootPane().setDefaultButton(comp.getCreateButton());
        comp.setPreferredSize(new Dimension((int) (1280*0.5), (int) (720*0.5)));
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
