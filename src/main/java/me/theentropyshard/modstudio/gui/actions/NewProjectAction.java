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

package me.theentropyshard.modstudio.gui.actions;

import me.theentropyshard.modstudio.ModStudio;
import me.theentropyshard.modstudio.gui.Gui;
import me.theentropyshard.modstudio.gui.view.project.ProjectCreationView;
import me.theentropyshard.modstudio.gui.view.project.ProjectView;
import me.theentropyshard.modstudio.project.Project;
import me.theentropyshard.modstudio.project.ProjectManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class NewProjectAction extends AbstractAction {
    public NewProjectAction() {
        super("New Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
                            JFrame frame = ModStudio.getInstance().getGui().getFrame();

                            frame.getContentPane().remove(0);
                            frame.add(new ProjectView(Gui.SIZE, project));
                            frame.getContentPane().revalidate();
                        });
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        });
    }
}
