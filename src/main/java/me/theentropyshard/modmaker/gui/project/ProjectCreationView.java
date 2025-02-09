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

package me.theentropyshard.modmaker.gui.project;

import me.theentropyshard.modmaker.ModMaker;
import me.theentropyshard.modmaker.gui.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class ProjectCreationView extends JPanel {
    private final ProjectInfoForm projectInfoForm;
    private final JButton createButton;
    private final JButton cancelButton;

    private ProjectCreationInfo projectInfo;

    public ProjectCreationView() {
        super(new BorderLayout());

        this.projectInfoForm = new ProjectInfoForm();
        this.add(this.projectInfoForm, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        this.createButton = new JButton("Create");
        bottomPanel.add(this.createButton);

        this.cancelButton = new JButton("Cancel");
        bottomPanel.add(this.cancelButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void gatherInfo() {
        this.projectInfo = new ProjectCreationInfo(
            this.projectInfoForm.getNameField().getText(),
            this.projectInfoForm.getNamespaceField().getText(),
            this.projectInfoForm.getVersionField().getText()
        );
    }

    public static ProjectCreationView showDialog() {
        ProjectCreationView view = new ProjectCreationView();
        view.setPreferredSize(new Dimension(960, 540));

        JDialog dialog = new JDialog(ModMaker.getInstance().getGui().getFrame(), "Create New Project", true);
        dialog.add(view, BorderLayout.CENTER);
        dialog.getRootPane().setDefaultButton(view.getCreateButton());
        view.getCreateButton().addActionListener(e -> {
            view.gatherInfo();
            dialog.dispose();
        });
        view.getCancelButton().addActionListener(e -> {
            dialog.dispose();
        });
        dialog.pack();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        SwingUtils.centerWindow(dialog, 0);
        dialog.setVisible(true);

        return view;
    }

    public Optional<ProjectCreationInfo> getProjectInfo() {
        return Optional.ofNullable(this.projectInfo);
    }

    private JButton getCreateButton() {
        return this.createButton;
    }

    private JButton getCancelButton() {
        return this.cancelButton;
    }
}