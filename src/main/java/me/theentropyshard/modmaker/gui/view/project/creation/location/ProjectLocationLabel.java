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

package me.theentropyshard.modmaker.gui.view.project.creation.location;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;

public class ProjectLocationLabel extends JLabel {
    public ProjectLocationLabel(String projectName, Path projectsDir) {
        this.updateText(projectName, projectsDir);
    }

    public void updateText(String projectName, Path projectsDir) {
        this.setText("Project will be created in: " + projectsDir + File.separator + projectName);
    }
}
