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

import me.theentropyshard.modmaker.ModMaker;
import me.theentropyshard.modmaker.gui.AppFrame;
import me.theentropyshard.modmaker.gui.view.MainView;
import me.theentropyshard.modmaker.gui.view.project.creation.ProjectCreationView;
import me.theentropyshard.modmaker.project.Project;
import me.theentropyshard.modmaker.utils.Worker;

import java.util.concurrent.ExecutionException;

public class MainController {
    private final MainView mainView;

    private ProjectController projectController;

    public MainController(AppFrame appFrame, MainView mainView) {
        this.mainView = mainView;

        appFrame.getNewProjectItem().addActionListener(e -> this.onCreateNewProject());
        appFrame.getOpenProjectItem().addActionListener(e -> {
            System.out.println("open");
        });
    }

    private void onCreateNewProject() {
        ProjectCreationView view = ProjectCreationView.showDialog();

        view.getProjectInfo().ifPresent(info -> {
            String name = info.getName();
            String namespace = info.getNamespace();
            String version = info.getVersion();

            new Worker<Project, Void>("creating project " + name) {
                @Override
                protected Project work() throws Exception {
                    Project project = ModMaker.getInstance().getProjectManager().createProject(name, namespace, version);

                    project.loadWorkspace();

                    return project;
                }

                @Override
                protected void done() {
                    Project project;

                    try {
                        project = this.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();

                        return;
                    }

                    MainController.this.openProject(project);
                }
            }.execute();
        });
    }

    public void openProject(Project project) {
        if (this.projectController == null) {
            this.projectController = new ProjectController();
        }

        this.mainView.setProjectView(this.projectController.openProject(project));
        this.mainView.revalidate();
    }
}
