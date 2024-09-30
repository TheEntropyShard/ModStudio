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

package me.theentropyshard.modmaker.gui.project;

import me.theentropyshard.modmaker.ModMaker;
import me.theentropyshard.modmaker.utils.SwingUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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

    private static final class ProjectInfoForm extends JPanel {
        private final JTextField nameField;
        private final JTextField namespaceField;
        private final JTextField versionField;

        public ProjectInfoForm() {
            super(new MigLayout("fillx", "[][]", "[top][top][top][top]"));

            JLabel nameLabel = new JLabel("Name:");
            this.add(nameLabel);

            this.nameField = new JTextField("untitled");
            this.nameField.selectAll();
            this.add(this.nameField, "wrap, growx, pushx");

            JLabel namespaceLabel = new JLabel("Namespace:");
            this.add(namespaceLabel);

            this.namespaceField = new JTextField("untitled");
            this.add(this.namespaceField, "wrap, growx, pushx");

            LocationField locationField = new LocationField();
            locationField.getPathField().setText(ModMaker.getInstance().getProjectsDir().toString());
            locationField.getLocationLabel().setText("Project will be created in: " +
                ModMaker.getInstance().getProjectsDir() + File.separator + this.nameField.getText());

            SwingUtils.addChangeListener(this.nameField, e -> {
                this.namespaceField.setText(
                    this.nameField.getText()
                        .toLowerCase()
                        .replace(" ", "")
                );

                locationField.getLocationLabel().setText("Project will be created in: " +
                    ModMaker.getInstance().getProjectsDir() + File.separator + this.nameField.getText());
            });

            JLabel versionLabel = new JLabel("Version:");
            this.add(versionLabel);

            this.versionField = new JTextField("0.0.1");
            this.add(this.versionField, "wrap, growx, pushx");

            this.add(new JLabel("Location:"));
            this.add(locationField, "wrap, growx, pushx");
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
    }

    private static final class LocationField extends JPanel {
        private final JTextField pathField;
        private final JLabel locationLabel;

        public LocationField() {
            super(new MigLayout("fill, insets 0", "[]", "[][]"));

            this.pathField = new JTextField();
            this.add(this.pathField, "wrap, growx, pushx");

            this.locationLabel = new JLabel();
            this.add(this.locationLabel);
        }

        public JTextField getPathField() {
            return this.pathField;
        }

        public JLabel getLocationLabel() {
            return this.locationLabel;
        }
    }

    public static final class ProjectCreationInfo {
        private final String name;
        private final String namespace;
        private final String version;

        public ProjectCreationInfo(String name, String namespace, String version) {
            this.name = name;
            this.namespace = namespace;
            this.version = version;
        }

        public String getName() {
            return this.name;
        }

        public String getNamespace() {
            return this.namespace;
        }

        public String getVersion() {
            return this.version;
        }
    }

    public static ProjectCreationView showDialog() {
        ProjectCreationView view = new ProjectCreationView();
        view.setPreferredSize(new Dimension(960, 540));

        JDialog dialog = new JDialog(ModMaker.getInstance().getAppFrame(), "Create New Project", true);
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
