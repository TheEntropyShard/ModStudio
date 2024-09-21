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

import me.theentropyshard.modmaker.gui.FormPanel;

import javax.swing.*;
import java.awt.*;

public class ProjectCreationView extends JPanel {

    private JTextField textField;
    private JButton createButton;

    public ProjectCreationView() {
        super(new BorderLayout());

        FormPanel form = new FormPanel();

        form.addFormRow("Name", "", s -> this.textField.setText(s.replace(" ", "").toLowerCase()), "Project name");
        this.textField = form.addFormRow("Namespace", "", s -> {}, "Namespace of your mod");
        form.addFormRow("Version", "0.0.1", s -> {}, "Version of your mod");

        this.add(form, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        this.createButton = new JButton("Create");
        buttonsPanel.add(this.createButton);

        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    public JButton getCreateButton() {
        return this.createButton;
    }
}
