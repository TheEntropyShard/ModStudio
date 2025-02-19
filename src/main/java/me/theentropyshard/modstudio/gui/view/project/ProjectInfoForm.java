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

package me.theentropyshard.modstudio.gui.view.project;

import me.theentropyshard.modstudio.ModStudio;
import net.miginfocom.swing.MigLayout;
import me.theentropyshard.modstudio.gui.utils.SwingUtils;

import javax.swing.*;

public class ProjectInfoForm extends JPanel {
    private final JTextField nameField;
    private final ProjectNamespaceField namespaceField;
    private final JTextField versionField;
    private final ProjectLocationComponent locationComponent;

    public ProjectInfoForm() {
        super(new MigLayout("fillx", "[][]", "[top][top][top][top]"));

        JLabel nameLabel = new JLabel("Name:");
        this.add(nameLabel);

        this.nameField = new JTextField("untitled");
        this.nameField.selectAll();
        this.add(this.nameField, "wrap, growx, pushx");

        JLabel namespaceLabel = new JLabel("Namespace:");
        this.add(namespaceLabel);

        this.namespaceField = new ProjectNamespaceField("untitled");
        this.add(this.namespaceField, "wrap, growx, pushx");

        JLabel versionLabel = new JLabel("Version:");
        this.add(versionLabel);

        this.versionField = new JTextField("0.0.1");
        this.add(this.versionField, "wrap, growx, pushx");

        this.add(new JLabel("Location:"));

        this.locationComponent = new ProjectLocationComponent("untitled", ModStudio.getInstance().getProjectsDir());
        this.add(this.locationComponent, "wrap, growx, pushx");

        SwingUtils.addChangeListener(this.nameField, e -> {
            this.namespaceField.updateText(this.nameField.getText());
            this.locationComponent.updateText(this.nameField.getText(), ModStudio.getInstance().getProjectsDir());
        });
    }

    public JTextField getNameField() {
        return this.nameField;
    }

    public JTextField getNamespaceField() {
        return this.namespaceField;
    }

    public JTextField getVersionField() {
        return this.versionField;
    }

    public ProjectLocationComponent getLocationComponent() {
        return this.locationComponent;
    }
}