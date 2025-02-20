/*
 * ModStudio - https://github.com/TheEntropyShard/ModStudio
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

package me.theentropyshard.modstudio.gui;

import com.formdev.flatlaf.FlatLaf;
import me.theentropyshard.modstudio.ModStudio;
import me.theentropyshard.modstudio.gui.laf.DarkModStudioLaf;
import me.theentropyshard.modstudio.gui.laf.LightModStudioLaf;
import me.theentropyshard.modstudio.gui.view.project.ProjectCreationView;
import me.theentropyshard.modstudio.gui.view.project.ProjectView;
import me.theentropyshard.modstudio.gui.utils.MessageBox;
import me.theentropyshard.modstudio.gui.utils.SwingUtils;
import me.theentropyshard.modstudio.gui.utils.Worker;
import me.theentropyshard.modstudio.gui.view.welcome.WelcomeView;
import me.theentropyshard.modstudio.project.Project;
import me.theentropyshard.modstudio.project.ProjectManager;
import me.theentropyshard.modstudio.utils.FileUtils;
import me.theentropyshard.modstudio.utils.OperatingSystem;
import net.lingala.zip4j.ZipFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class Gui {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private final JFrame frame;

    public static boolean darkTheme;

    public Gui(String title, boolean darkTheme) {
        Gui.darkTheme = darkTheme;

        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        FlatLaf.registerCustomDefaultsSource("themes");

        if (darkTheme) {
            DarkModStudioLaf.setup();
        } else {
            LightModStudioLaf.setup();
        }

        UIManager.put("SplitPaneDivider.style", "plain");
        UIManager.put("Button.showMnemonics", true);
        UIManager.put("Component.hideMnemonics", false);

        UIManager.put("Tree.collapsedIcon", Icons.get("chevronRight"));
        UIManager.put("Tree.expandedIcon", Icons.get("chevronDown"));

        this.frame = new JFrame(title);
        Dimension size = new Dimension(Gui.WIDTH, Gui.HEIGHT);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem newProjectItem = new JMenuItem("New Project");
        newProjectItem.setMnemonic(KeyEvent.VK_N);
        newProjectItem.addActionListener(e -> {
            ProjectCreationView.showDialog().getProjectInfo().ifPresent(info -> {
                ModStudio.getInstance().doTask(() -> {
                    try {
                        Project project = ModStudio.getInstance().getProjectManager().createProject(
                            info.getName(), info.getNamespace(), info.getVersion()
                        );

                        ModStudio.getInstance().doTask(() -> {
                            ProjectManager manager = ModStudio.getInstance().getProjectManager();

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

        ProjectManager projectManager = ModStudio.getInstance().getProjectManager();

        for (Project project : projectManager.getProjects()) {
            JMenuItem menuItem = new JMenuItem(project.getName());

            menuItem.addActionListener(e -> {
                new Worker<Project, Void>("changing project") {
                    @Override
                    protected Project work() throws Exception {
                        ProjectManager manager = ModStudio.getInstance().getProjectManager();

                        manager.setCurrentProject(project);

                        try {
                            manager.loadProject(project);
                        } catch (IOException ex) {
                            ex.printStackTrace();

                            return null;
                        }

                        return project;
                    }

                    @Override
                    protected void finish(Project project) {
                        Container contentPane = Gui.this.frame.getContentPane();

                        if (contentPane.getComponentCount() >= 1) {
                            contentPane.remove(0);
                        }

                        Gui.this.frame.add(new ProjectView(size, project));
                        Gui.this.frame.getContentPane().revalidate();
                    }
                }.execute();
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

        JMenuItem openWorkingDir = new JMenuItem("Open working directory");
        openWorkingDir.addActionListener(e -> {
            SwingUtils.startWorker(() -> {
                OperatingSystem.open(ModStudio.getInstance().getWorkDir());
            });
        });
        openWorkingDir.setMnemonic(KeyEvent.VK_W);
        fileMenu.add(openWorkingDir);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            ModStudio.getInstance().shutdown();
        });
        exitItem.setMnemonic(KeyEvent.VK_E);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        JMenu projectMenu = new JMenu("Project");
        projectMenu.setMnemonic(KeyEvent.VK_P);

        JMenuItem openProjectDirItem = new JMenuItem("Open project folder");
        openProjectDirItem.addActionListener(e -> {
            SwingUtils.startWorker(() -> {
                Project currentProject = ModStudio.getInstance().getProjectManager().getCurrentProject();

                if (currentProject != null) {
                    OperatingSystem.open(currentProject.getWorkDir());
                }
            });
        });
        openProjectDirItem.setMnemonic(KeyEvent.VK_O);
        projectMenu.add(openProjectDirItem);

        JMenu exportProjectMenu = new JMenu("Export");
        projectMenu.add(exportProjectMenu);

        JMenuItem exportZipItem = new JMenuItem("Zip file");
        exportZipItem.addActionListener(e -> {
            ModStudio.getInstance().doTask(() -> {
                String pathString = MessageBox.showInputMessage(ModStudio.getInstance().getGui().getFrame(),
                    "Export project as Zip",
                    "Please enter the path to the directory where the zip will be exported to",
                    ""
                );

                ProjectManager manager = ModStudio.getInstance().getProjectManager();
                Project project = manager.getCurrentProject();

                String fileName = FileUtils.sanitizeFileName(project.getName().replace(" ", "_"));

                if (fileName.isEmpty()) {
                    fileName = project.getNamespace();
                }

                fileName += ".zip";

                try (ZipFile zipFile = new ZipFile(pathString + File.separator + fileName)) {
                    zipFile.addFolder(project.getWorkDir().toFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        });
        exportProjectMenu.add(exportZipItem);

        JMenuItem copyFolder = new JMenuItem("Copy folder");
        exportProjectMenu.add(copyFolder);

        menuBar.add(projectMenu);

        this.frame.setJMenuBar(menuBar);
        this.frame.getContentPane().setPreferredSize(size);
        WelcomeView welcomeView = new WelcomeView();
        welcomeView.setPreferredSize(size);
        this.frame.add(welcomeView, BorderLayout.CENTER);

        this.frame.pack();

        SwingUtils.centerWindow(this.frame, 0);
    }

    public void show() {
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public boolean isDarkTheme() {
        return this.darkTheme;
    }
}
