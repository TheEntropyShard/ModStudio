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
import me.theentropyshard.modmaker.gui.view.project.creation.ProjectCreationView;
import me.theentropyshard.modmaker.gui.view.MainView;
import me.theentropyshard.modmaker.utils.Worker;

public class MainController {
    public MainController(AppFrame appFrame, MainView mainView) {
        appFrame.getNewProjectItem().addActionListener(e -> this.onCreateNewProject());
    }

    private void onCreateNewProject() {
        ProjectCreationView view = ProjectCreationView.showDialog();

        view.getProjectInfo().ifPresent(info -> {
            String name = info.getName();
            String namespace = info.getNamespace();
            String version = info.getVersion();

            new Worker<Void, Void>("creating project") {
                @Override
                protected Void work() throws Exception {
                    ModMaker.getInstance().getProjectManager().createProject(name, namespace, version);

                    return null;
                }
            }.execute();
        });
    }
}
