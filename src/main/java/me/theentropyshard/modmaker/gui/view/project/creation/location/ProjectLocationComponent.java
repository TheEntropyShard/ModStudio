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

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.nio.file.Path;

public class ProjectLocationComponent extends JPanel {
    private final ProjectLocationTextField pathField;
    private final ProjectLocationLabel pathLabel;

    public ProjectLocationComponent(String projectName, Path projectsDir) {
        super(new MigLayout("fill, insets 0", "[]", "[][]"));

        this.pathField = new ProjectLocationTextField(projectName, projectsDir);
        this.add(this.pathField, "wrap, growx, pushx");

        this.pathLabel = new ProjectLocationLabel(projectName, projectsDir);
        this.add(this.pathLabel);
    }

    public void updateText(String projectName, Path projectsDir) {
        this.pathField.updateText(projectName, projectsDir);
        this.pathLabel.updateText(projectName, projectsDir);
    }

    public ProjectLocationTextField getPathField() {
        return this.pathField;
    }

    public ProjectLocationLabel getPathLabel() {
        return this.pathLabel;
    }
}
