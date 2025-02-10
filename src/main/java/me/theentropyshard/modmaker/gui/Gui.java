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

package me.theentropyshard.modmaker.gui;

import com.formdev.flatlaf.FlatLaf;
import me.theentropyshard.modmaker.ModMaker;
import me.theentropyshard.modmaker.project.DummyProject;
import me.theentropyshard.modmaker.project.Project;
import me.theentropyshard.modmaker.gui.laf.DarkModMakerLaf;
import me.theentropyshard.modmaker.gui.laf.LightModMakerLaf;
import me.theentropyshard.modmaker.gui.project.ProjectCreationView;
import me.theentropyshard.modmaker.gui.project.ProjectView;
import me.theentropyshard.modmaker.gui.utils.SwingUtils;
import me.theentropyshard.modmaker.project.ProjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Gui {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private final JFrame frame;

    public Gui(String title, boolean darkTheme) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        FlatLaf.registerCustomDefaultsSource("themes");

        if (darkTheme) {
            DarkModMakerLaf.setup();
        } else {
            LightModMakerLaf.setup();
        }

        UIManager.put("SplitPaneDivider.style", "plain");
        UIManager.put("Button.showMnemonics", true);
        UIManager.put("Component.hideMnemonics", false);

        this.frame = new JFrame(title);
        Dimension size = new Dimension(Gui.WIDTH, Gui.HEIGHT);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem newProjectItem = new JMenuItem("New Project");
        newProjectItem.setMnemonic(KeyEvent.VK_N);
        newProjectItem.addActionListener(e -> {
            ProjectCreationView.showDialog().getProjectInfo().ifPresent(info -> {
                ModMaker.getInstance().doTask(() -> {
                    try {
                        ModMaker.getInstance().getProjectManager().createProject(
                            info.getName(), info.getNamespace(), info.getVersion()
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            });
        });

        fileMenu.add(newProjectItem);

        JMenuItem openProjectItem = new JMenuItem("Open");
        openProjectItem.setMnemonic(KeyEvent.VK_O);
        openProjectItem.addActionListener(e -> {

        });

        fileMenu.add(openProjectItem);

        JMenu recentProjectsMenu = new JMenu("Recent Projects");
        recentProjectsMenu.setMnemonic(KeyEvent.VK_R);

        ProjectManager projectManager = ModMaker.getInstance().getProjectManager();

        for (Project project : projectManager.getProjects()) {
            JMenuItem menuItem = new JMenuItem(project.getName());

            menuItem.addActionListener(e -> {
                ModMaker.getInstance().doTask(() -> {
                    ProjectManager manager = ModMaker.getInstance().getProjectManager();

                    manager.setCurrentProject(project);
                    try {
                        manager.loadProject(project);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                        return;
                    }

                   SwingUtilities.invokeLater(() -> {
                       this.frame.getContentPane().remove(0);
                       this.frame.add(new ProjectView(size, project));
                       this.frame.getContentPane().revalidate();
                   });
                });
            });

            recentProjectsMenu.add(menuItem);
        }

        fileMenu.add(recentProjectsMenu);

        JMenuItem closeProjectItem = new JMenuItem("Close Project");
        fileMenu.add(closeProjectItem);

        JMenuItem closeAllProjectsItem = new JMenuItem("Close All Projects");
        fileMenu.add(closeAllProjectsItem);

        JMenuItem closeOtherProjectsItem = new JMenuItem("Close Other Projects");
        fileMenu.add(closeOtherProjectsItem);

        fileMenu.addSeparator();

        JMenuItem settingsItem = new JMenuItem("Settings");
        settingsItem.setMnemonic(KeyEvent.VK_T);
        fileMenu.add(settingsItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            ModMaker.getInstance().shutdown();
        });
        exitItem.setMnemonic(KeyEvent.VK_E);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        this.frame.setJMenuBar(menuBar);
        this.frame.getContentPane().setPreferredSize(size);
        this.frame.add(new ProjectView(size, new DummyProject()));

        this.frame.pack();

        SwingUtils.centerWindow(this.frame, 0);
    }

    public void show() {
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
